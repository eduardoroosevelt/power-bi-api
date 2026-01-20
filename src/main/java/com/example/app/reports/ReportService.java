package com.example.app.reports;

import com.example.app.common.ForbiddenException;
import com.example.app.common.NotFoundException;
import com.example.app.menu.MenuItemRepository;
import com.example.app.common.Enums.ResourceType;
import com.example.app.policies.PolicyService;
import com.example.app.policies.ReportAccessPolicy;
import com.example.app.rbac.PermissionService;
import com.example.app.rbac.Usuario;
import com.example.app.rbac.UsuarioRepository;
import com.example.app.securityscope.SecurityScopeService;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReportService {
    private final PowerBiReportRepository reportRepository;
    private final ReportDimensionRepository dimensionRepository;
    private final UsuarioRepository usuarioRepository;
    private final PermissionService permissionService;
    private final MenuItemRepository menuItemRepository;
    private final PolicyService policyService;
    private final SecurityScopeService securityScopeService;
    private final PowerBiClient powerBiClient;

    public ReportService(PowerBiReportRepository reportRepository,
                         ReportDimensionRepository dimensionRepository,
                         UsuarioRepository usuarioRepository,
                         PermissionService permissionService,
                         MenuItemRepository menuItemRepository,
                         PolicyService policyService,
                         SecurityScopeService securityScopeService,
                         PowerBiClient powerBiClient) {
        this.reportRepository = reportRepository;
        this.dimensionRepository = dimensionRepository;
        this.usuarioRepository = usuarioRepository;
        this.permissionService = permissionService;
        this.menuItemRepository = menuItemRepository;
        this.policyService = policyService;
        this.securityScopeService = securityScopeService;
        this.powerBiClient = powerBiClient;
    }

    public ReportDetailDto getReport(Long reportId) {
        PowerBiReport report = reportRepository.findById(reportId)
            .orElseThrow(() -> new NotFoundException("Report not found"));
        ReportDetailDto dto = new ReportDetailDto();
        dto.setId(report.getId());
        dto.setNome(report.getNome());
        dto.setWorkspaceId(report.getWorkspaceId());
        dto.setReportId(report.getReportId());
        dto.setDatasetId(report.getDatasetId());
        dto.setAtivo(report.isAtivo());
        List<ReportDimension> dimensions = dimensionRepository.findByReportIdAndActiveTrue(reportId);
        dto.setDimensions(dimensions.stream().map(this::toDto).toList());
        return dto;
    }

    @Transactional
    public EmbedResponse generateEmbed(Long reportId, String username) {

        PowerBiReport report = reportRepository.findByIdAndAtivoTrue(reportId)
            .orElseThrow(() -> new NotFoundException("Report not found"));

        Usuario usuario = usuarioRepository.findByUsername(username)
            .orElseThrow(() -> new NotFoundException("User not found"));

        menuItemRepository.findByResourceIdAndResourceType(reportId, ResourceType.POWERBI_REPORT)
            .ifPresent(menuItem -> {
                if (menuItem.getPermissao() != null
                    && !permissionService.hasPermission(usuario, menuItem.getPermissao().getCode())) {
                    throw new ForbiddenException("Missing permission for report");
                }
            });

        ReportAccessPolicy policy = policyService.resolvePolicy(reportId, usuario);
        Map<String, List<String>> values = policyService.buildScopeValues(reportId, usuario, policy);

        String principal = usuario.getUsername();
        String reportKey = String.valueOf(report.getId());

     //   securityScopeService.materialize(principal, reportKey, values);

        PowerBiEmbedResult result = powerBiClient.generateEmbed(report, principal, reportKey);
        EmbedResponse response = new EmbedResponse();
        response.setReportInternalId(report.getReportId());
        response.setEmbedUrl(result.getEmbedUrl());
        response.setAccessToken(result.getAccessToken());
        response.setExpiresAt(result.getExpiresAt());
        response.setPrincipal(principal);
        response.setReportKey(reportKey);
        return response;
    }

    private ReportDimensionDto toDto(ReportDimension dimension) {
        ReportDimensionDto dto = new ReportDimensionDto();
        dto.setId(dimension.getId());
        dto.setDimensionKey(dimension.getDimensionKey());
        dto.setDimensionLabel(dimension.getDimensionLabel());
        dto.setValueType(dimension.getValueType());
        dto.setActive(dimension.isActive());
        return dto;
    }
}
