package com.example.app.reports;

import com.example.app.common.ForbiddenException;
import com.example.app.common.NotFoundException;
import com.example.app.common.BadRequestException;
import com.example.app.common.Enums.PolicyEffect;
import com.example.app.common.Enums.RuleOperator;
import com.example.app.common.Enums.ValueType;
import com.example.app.menu.MenuItemRepository;
import com.example.app.common.Enums.ResourceType;
import com.example.app.policies.PolicyService;
import com.example.app.policies.ReportAccessPolicy;
import com.example.app.policies.ReportAccessPolicyRepository;
import com.example.app.policies.ReportAccessPolicyRule;
import com.example.app.policies.ReportAccessPolicyRuleRepository;
import com.example.app.policies.ReportAccessPolicyRuleValue;
import com.example.app.policies.ReportAccessPolicyRuleValueRepository;
import com.example.app.rbac.PermissionService;
import com.example.app.rbac.Usuario;
import com.example.app.rbac.UsuarioRepository;
import com.example.app.securityscope.SecurityScopeService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
    private final ReportAccessPolicyRepository policyRepository;
    private final SecurityScopeService securityScopeService;
    private final PowerBiClient powerBiClient;
    private final ReportAccessPolicyRuleRepository ruleRepository;
    private final ReportAccessPolicyRuleValueRepository ruleValueRepository;

    public ReportService(PowerBiReportRepository reportRepository,
                         ReportDimensionRepository dimensionRepository,
                         UsuarioRepository usuarioRepository,
                         PermissionService permissionService,
                         MenuItemRepository menuItemRepository,
                         PolicyService policyService,
                         ReportAccessPolicyRepository policyRepository,
                         SecurityScopeService securityScopeService,
                         PowerBiClient powerBiClient,
                         ReportAccessPolicyRuleRepository ruleRepository,
                         ReportAccessPolicyRuleValueRepository ruleValueRepository) {
        this.reportRepository = reportRepository;
        this.dimensionRepository = dimensionRepository;
        this.usuarioRepository = usuarioRepository;
        this.permissionService = permissionService;
        this.menuItemRepository = menuItemRepository;
        this.policyService = policyService;
        this.policyRepository = policyRepository;
        this.securityScopeService = securityScopeService;
        this.powerBiClient = powerBiClient;
        this.ruleRepository = ruleRepository;
        this.ruleValueRepository = ruleValueRepository;
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
        dto.setPolicies(buildPolicyDtos(reportId));
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
        response.setFilters(buildFilters(reportId, policy, values));
        return response;
    }

    private ReportDimensionDto toDto(ReportDimension dimension) {
        ReportDimensionDto dto = new ReportDimensionDto();
        dto.setId(dimension.getId());
        dto.setDimensionKey(dimension.getDimensionKey());
        dto.setDimensionLabel(dimension.getDimensionLabel());
        dto.setTableName(dimension.getTableName());
        dto.setColumnName(dimension.getColumnName());
        dto.setValueType(dimension.getValueType());
        dto.setActive(dimension.isActive());
        return dto;
    }

    private List<ReportAccessPolicyDto> buildPolicyDtos(Long reportId) {
        return policyRepository.findByReportId(reportId)
            .stream()
            .map(this::toPolicyDto)
            .toList();
    }

    private ReportAccessPolicyDto toPolicyDto(ReportAccessPolicy policy) {
        ReportAccessPolicyDto dto = new ReportAccessPolicyDto();
        dto.setId(policy.getId());
        dto.setSubjectType(policy.getSubjectType());
        dto.setSubjectId(policy.getSubjectId());
        dto.setEffect(policy.getEffect());
        dto.setPriority(policy.getPriority());
        dto.setActive(policy.isActive());
        List<ReportAccessPolicyRule> rules = ruleRepository.findByPolicyId(policy.getId());
        dto.setRules(rules.stream().map(this::toRuleDto).toList());
        return dto;
    }

    private ReportAccessPolicyRuleDto toRuleDto(ReportAccessPolicyRule rule) {
        ReportAccessPolicyRuleDto dto = new ReportAccessPolicyRuleDto();
        dto.setId(rule.getId());
        dto.setDimensionKey(rule.getDimensionKey());
        dto.setOperator(rule.getOperator());
        dto.setValuesMode(rule.getValuesMode());
        dto.setUserAttribute(rule.getUserAttribute());
        dto.setActive(rule.isActive());
        List<ReportAccessPolicyRuleValue> values = ruleValueRepository.findByRuleId(rule.getId());
        dto.setValues(values.stream().map(ReportAccessPolicyRuleValue::getRuleValue).toList());
        return dto;
    }

    private List<EmbedFilter> buildFilters(Long reportId,
                                           ReportAccessPolicy policy,
                                           Map<String, List<String>> valuesByDimension) {
        if (policy == null || policy.getEffect() == PolicyEffect.ALLOW_ALL) {
            return List.of();
        }

        List<ReportDimension> dimensions = dimensionRepository.findByReportIdAndActiveTrue(reportId);
        Map<String, ReportAccessPolicyRule> rules = ruleRepository
            .findByPolicyIdAndActiveTrue(policy.getId())
            .stream()
            .collect(Collectors.toMap(ReportAccessPolicyRule::getDimensionKey, rule -> rule));

        List<EmbedFilter> filters = new ArrayList<>();

        for (ReportDimension dimension : dimensions) {
            ReportAccessPolicyRule rule = rules.get(dimension.getDimensionKey());
            if (rule == null) {
                throw new ForbiddenException("Missing policy rule for dimension " + dimension.getDimensionKey());
            }
            if (rule.getOperator() == RuleOperator.ALL) {
                continue;
            }
            List<String> values = valuesByDimension.get(dimension.getDimensionKey());
            if (values == null || values.isEmpty() || values.contains("*")) {
                continue;
            }
            if (dimension.getTableName() == null || dimension.getTableName().isBlank()
                || dimension.getColumnName() == null || dimension.getColumnName().isBlank()) {
                throw new BadRequestException("Dimension mapping is missing for " + dimension.getDimensionKey());
            }

            List<Object> typedValues = mapValues(values, dimension.getValueType(), dimension.getDimensionKey());
            EmbedFilter filter = new EmbedFilter();
            EmbedFilterTarget target = new EmbedFilterTarget();
            target.setTable(dimension.getTableName());
            target.setColumn(dimension.getColumnName());
            filter.setTarget(target);
            filter.setOperator(mapOperator(rule.getOperator()));
            filter.setValues(typedValues);
            filters.add(filter);
        }
        return filters;
    }

    private String mapOperator(RuleOperator operator) {
        return switch (operator) {
            case IN -> "In";
            case NOT_IN -> "NotIn";
            case ALL -> "All";
        };
    }

    private List<Object> mapValues(List<String> values, ValueType valueType, String dimensionKey) {
        return values.stream()
            .map(value -> switch (valueType) {
                case INT -> parseInteger(value, dimensionKey);
                case UUID, STRING -> value;
            })
            .collect(Collectors.toList());
    }

    private Integer parseInteger(String value, String dimensionKey) {
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException ex) {
            throw new BadRequestException("Invalid INT value for dimension " + dimensionKey);
        }
    }
}
