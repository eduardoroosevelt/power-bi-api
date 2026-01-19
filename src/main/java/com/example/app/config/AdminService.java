package com.example.app.config;

import com.example.app.common.BadRequestException;
import com.example.app.common.NotFoundException;
import com.example.app.common.Enums.RuleOperator;
import com.example.app.common.Enums.ValuesMode;
import com.example.app.config.dto.CreateGrupoRequest;
import com.example.app.config.dto.CreateOrgaoRequest;
import com.example.app.config.dto.CreatePermissaoRequest;
import com.example.app.config.dto.CreateUnidadeRequest;
import com.example.app.config.dto.GrupoPermissaoRequest;
import com.example.app.config.dto.UpdateGrupoRequest;
import com.example.app.config.dto.UpdateOrgaoRequest;
import com.example.app.config.dto.UpdatePermissaoRequest;
import com.example.app.config.dto.UpdateUnidadeRequest;
import com.example.app.policies.CreatePolicyRequest;
import com.example.app.policies.CreateRuleRequest;
import com.example.app.policies.CreateRuleValueRequest;
import com.example.app.policies.ReportAccessPolicy;
import com.example.app.policies.ReportAccessPolicyRepository;
import com.example.app.policies.ReportAccessPolicyRule;
import com.example.app.policies.ReportAccessPolicyRuleRepository;
import com.example.app.policies.ReportAccessPolicyRuleValue;
import com.example.app.policies.ReportAccessPolicyRuleValueRepository;
import com.example.app.rbac.Grupo;
import com.example.app.rbac.GrupoRepository;
import com.example.app.rbac.Orgao;
import com.example.app.rbac.OrgaoRepository;
import com.example.app.rbac.Permissao;
import com.example.app.rbac.PermissaoRepository;
import com.example.app.rbac.Unidade;
import com.example.app.rbac.UnidadeRepository;
import com.example.app.reports.CreateDimensionRequest;
import com.example.app.reports.CreateReportRequest;
import com.example.app.reports.PowerBiReport;
import com.example.app.reports.PowerBiReportRepository;
import com.example.app.reports.ReportDimension;
import com.example.app.reports.ReportDimensionRepository;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {
    private final PowerBiReportRepository reportRepository;
    private final ReportDimensionRepository dimensionRepository;
    private final ReportAccessPolicyRepository policyRepository;
    private final ReportAccessPolicyRuleRepository ruleRepository;
    private final ReportAccessPolicyRuleValueRepository valueRepository;
    private final OrgaoRepository orgaoRepository;
    private final UnidadeRepository unidadeRepository;
    private final PermissaoRepository permissaoRepository;
    private final GrupoRepository grupoRepository;

    public AdminService(PowerBiReportRepository reportRepository,
                        ReportDimensionRepository dimensionRepository,
                        ReportAccessPolicyRepository policyRepository,
                        ReportAccessPolicyRuleRepository ruleRepository,
                        ReportAccessPolicyRuleValueRepository valueRepository,
                        OrgaoRepository orgaoRepository,
                        UnidadeRepository unidadeRepository,
                        PermissaoRepository permissaoRepository,
                        GrupoRepository grupoRepository) {
        this.reportRepository = reportRepository;
        this.dimensionRepository = dimensionRepository;
        this.policyRepository = policyRepository;
        this.ruleRepository = ruleRepository;
        this.valueRepository = valueRepository;
        this.orgaoRepository = orgaoRepository;
        this.unidadeRepository = unidadeRepository;
        this.permissaoRepository = permissaoRepository;
        this.grupoRepository = grupoRepository;
    }

    public List<PowerBiReport> listReports() {
        return reportRepository.findAll();
    }

    public List<Orgao> listOrgaos() {
        return orgaoRepository.findAll();
    }

    public Orgao createOrgao(CreateOrgaoRequest request) {
        Orgao orgao = new Orgao();
        orgao.setNome(request.getNome());
        orgao.setCodigo(request.getCodigo());
        return orgaoRepository.save(orgao);
    }

    public Orgao updateOrgao(Long id, UpdateOrgaoRequest request) {
        Orgao orgao = orgaoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Orgao not found"));
        orgao.setNome(request.getNome());
        orgao.setCodigo(request.getCodigo());
        return orgaoRepository.save(orgao);
    }

    public void deleteOrgao(Long id) {
        orgaoRepository.deleteById(id);
    }

    public List<Unidade> listUnidades() {
        return unidadeRepository.findAll();
    }

    public Unidade createUnidade(CreateUnidadeRequest request) {
        Orgao orgao = orgaoRepository.findById(request.getOrgaoId())
            .orElseThrow(() -> new NotFoundException("Orgao not found"));
        Unidade unidade = new Unidade();
        unidade.setOrgao(orgao);
        unidade.setNome(request.getNome());
        unidade.setCodigo(request.getCodigo());
        return unidadeRepository.save(unidade);
    }

    public Unidade updateUnidade(Long id, UpdateUnidadeRequest request) {
        Unidade unidade = unidadeRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Unidade not found"));
        Orgao orgao = orgaoRepository.findById(request.getOrgaoId())
            .orElseThrow(() -> new NotFoundException("Orgao not found"));
        unidade.setOrgao(orgao);
        unidade.setNome(request.getNome());
        unidade.setCodigo(request.getCodigo());
        return unidadeRepository.save(unidade);
    }

    public void deleteUnidade(Long id) {
        unidadeRepository.deleteById(id);
    }

    public List<Permissao> listPermissoes() {
        return permissaoRepository.findAll();
    }

    public Permissao createPermissao(CreatePermissaoRequest request) {
        Permissao permissao = new Permissao();
        permissao.setCode(request.getCode());
        permissao.setDescricao(request.getDescricao());
        return permissaoRepository.save(permissao);
    }

    public Permissao updatePermissao(Long id, UpdatePermissaoRequest request) {
        Permissao permissao = permissaoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Permissao not found"));
        permissao.setCode(request.getCode());
        permissao.setDescricao(request.getDescricao());
        return permissaoRepository.save(permissao);
    }

    public void deletePermissao(Long id) {
        permissaoRepository.deleteById(id);
    }

    public List<Grupo> listGrupos() {
        return grupoRepository.findAll();
    }

    public Grupo createGrupo(CreateGrupoRequest request) {
        Grupo grupo = new Grupo();
        grupo.setNome(request.getNome());
        grupo.setDescricao(request.getDescricao());
        return grupoRepository.save(grupo);
    }

    public Grupo updateGrupo(Long id, UpdateGrupoRequest request) {
        Grupo grupo = grupoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Grupo not found"));
        grupo.setNome(request.getNome());
        grupo.setDescricao(request.getDescricao());
        return grupoRepository.save(grupo);
    }

    public void deleteGrupo(Long id) {
        grupoRepository.deleteById(id);
    }

    @Transactional
    public Set<Permissao> listGrupoPermissoes(Long grupoId) {
        Grupo grupo = grupoRepository.findById(grupoId)
            .orElseThrow(() -> new NotFoundException("Grupo not found"));
        return grupo.getPermissoes();
    }

    @Transactional
    public Set<Permissao> addGrupoPermissao(Long grupoId, GrupoPermissaoRequest request) {
        Grupo grupo = grupoRepository.findById(grupoId)
            .orElseThrow(() -> new NotFoundException("Grupo not found"));
        Permissao permissao = permissaoRepository.findById(request.getPermissaoId())
            .orElseThrow(() -> new NotFoundException("Permissao not found"));
        grupo.getPermissoes().add(permissao);
        grupoRepository.save(grupo);
        return grupo.getPermissoes();
    }

    @Transactional
    public Set<Permissao> removeGrupoPermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = grupoRepository.findById(grupoId)
            .orElseThrow(() -> new NotFoundException("Grupo not found"));
        Permissao permissao = permissaoRepository.findById(permissaoId)
            .orElseThrow(() -> new NotFoundException("Permissao not found"));
        grupo.getPermissoes().remove(permissao);
        grupoRepository.save(grupo);
        return grupo.getPermissoes();
    }

    public PowerBiReport createReport(CreateReportRequest request) {
        PowerBiReport report = new PowerBiReport();
        report.setNome(request.getNome());
        report.setWorkspaceId(request.getWorkspaceId());
        report.setReportId(request.getReportId());
        report.setDatasetId(request.getDatasetId());
        report.setAtivo(request.getAtivo());
        return reportRepository.save(report);
    }

    public ReportDimension addDimension(Long reportId, CreateDimensionRequest request) {
        if (dimensionRepository.existsByReportIdAndDimensionKey(reportId, request.getDimensionKey())) {
            throw new BadRequestException("Dimension already exists for report");
        }
        PowerBiReport report = reportRepository.findById(reportId)
            .orElseThrow(() -> new NotFoundException("Report not found"));
        ReportDimension dimension = new ReportDimension();
        dimension.setReport(report);
        dimension.setDimensionKey(request.getDimensionKey());
        dimension.setDimensionLabel(request.getDimensionLabel());
        dimension.setValueType(request.getValueType());
        dimension.setActive(request.getActive());
        return dimensionRepository.save(dimension);
    }

    public ReportAccessPolicy addPolicy(Long reportId, CreatePolicyRequest request) {
        PowerBiReport report = reportRepository.findById(reportId)
            .orElseThrow(() -> new NotFoundException("Report not found"));
        ReportAccessPolicy policy = new ReportAccessPolicy();
        policy.setReport(report);
        policy.setSubjectType(request.getSubjectType());
        policy.setSubjectId(request.getSubjectId());
        policy.setEffect(request.getEffect());
        policy.setPriority(request.getPriority());
        policy.setActive(request.getActive());
        return policyRepository.save(policy);
    }

    public ReportAccessPolicyRule addRule(Long policyId, CreateRuleRequest request) {
        ReportAccessPolicy policy = policyRepository.findById(policyId)
            .orElseThrow(() -> new NotFoundException("Policy not found"));
        if (!dimensionRepository.existsByReportIdAndDimensionKey(policy.getReport().getId(), request.getDimensionKey())) {
            throw new BadRequestException("Dimension does not exist for report");
        }
        if (request.getValuesMode() == ValuesMode.FROM_USER_ATTRIBUTE && request.getUserAttribute() == null) {
            throw new BadRequestException("userAttribute is required for FROM_USER_ATTRIBUTE");
        }
        if (request.getValuesMode() == ValuesMode.FROM_USER_ATTRIBUTE
            && request.getValues() != null && !request.getValues().isEmpty()) {
            throw new BadRequestException("values are not allowed for FROM_USER_ATTRIBUTE");
        }
        if (request.getValuesMode() == ValuesMode.STATIC && !request.isAllowAll()) {
            if (request.getValues() == null || request.getValues().isEmpty()) {
                throw new BadRequestException("values are required for STATIC rules");
            }
        }
        ReportAccessPolicyRule rule = new ReportAccessPolicyRule();
        rule.setPolicy(policy);
        rule.setDimensionKey(request.getDimensionKey());
        rule.setOperator(request.getOperator() == null ? RuleOperator.IN : request.getOperator());
        rule.setValuesMode(request.getValuesMode());
        rule.setUserAttribute(request.getUserAttribute());
        rule.setActive(request.getActive());
        ReportAccessPolicyRule saved = ruleRepository.save(rule);
        if (request.isAllowAll()) {
            ReportAccessPolicyRuleValue value = new ReportAccessPolicyRuleValue();
            value.setRule(saved);
            value.setRuleValue("*");
            valueRepository.save(value);
        } else if (request.getValuesMode() == ValuesMode.STATIC && request.getValues() != null) {
            for (String value : request.getValues()) {
                ReportAccessPolicyRuleValue ruleValue = new ReportAccessPolicyRuleValue();
                ruleValue.setRule(saved);
                ruleValue.setRuleValue(value);
                valueRepository.save(ruleValue);
            }
        }
        return saved;
    }

    @Transactional
    public ReportAccessPolicyRuleValue addRuleValue(Long ruleId, CreateRuleValueRequest request) {
        ReportAccessPolicyRule rule = ruleRepository.findById(ruleId)
            .orElseThrow(() -> new NotFoundException("Rule not found"));
        if (rule.getValuesMode() == ValuesMode.FROM_USER_ATTRIBUTE) {
            throw new BadRequestException("Cannot add static values for FROM_USER_ATTRIBUTE rule");
        }
        ReportAccessPolicyRuleValue value = new ReportAccessPolicyRuleValue();
        value.setRule(rule);
        value.setRuleValue(request.getValue());
        return valueRepository.save(value);
    }
}
