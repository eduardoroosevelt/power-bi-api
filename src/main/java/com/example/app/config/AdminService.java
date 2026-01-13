package com.example.app.config;

import com.example.app.common.BadRequestException;
import com.example.app.common.NotFoundException;
import com.example.app.common.Enums.RuleOperator;
import com.example.app.common.Enums.ValuesMode;
import com.example.app.policies.CreatePolicyRequest;
import com.example.app.policies.CreateRuleRequest;
import com.example.app.policies.CreateRuleValueRequest;
import com.example.app.policies.ReportAccessPolicy;
import com.example.app.policies.ReportAccessPolicyRepository;
import com.example.app.policies.ReportAccessPolicyRule;
import com.example.app.policies.ReportAccessPolicyRuleRepository;
import com.example.app.policies.ReportAccessPolicyRuleValue;
import com.example.app.policies.ReportAccessPolicyRuleValueRepository;
import com.example.app.reports.CreateDimensionRequest;
import com.example.app.reports.CreateReportRequest;
import com.example.app.reports.PowerBiReport;
import com.example.app.reports.PowerBiReportRepository;
import com.example.app.reports.ReportDimension;
import com.example.app.reports.ReportDimensionRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {
    private final PowerBiReportRepository reportRepository;
    private final ReportDimensionRepository dimensionRepository;
    private final ReportAccessPolicyRepository policyRepository;
    private final ReportAccessPolicyRuleRepository ruleRepository;
    private final ReportAccessPolicyRuleValueRepository valueRepository;

    public AdminService(PowerBiReportRepository reportRepository,
                        ReportDimensionRepository dimensionRepository,
                        ReportAccessPolicyRepository policyRepository,
                        ReportAccessPolicyRuleRepository ruleRepository,
                        ReportAccessPolicyRuleValueRepository valueRepository) {
        this.reportRepository = reportRepository;
        this.dimensionRepository = dimensionRepository;
        this.policyRepository = policyRepository;
        this.ruleRepository = ruleRepository;
        this.valueRepository = valueRepository;
    }

    public List<PowerBiReport> listReports() {
        return reportRepository.findAll();
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
