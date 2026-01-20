package com.example.app.policies;

import com.example.app.common.BadRequestException;
import com.example.app.common.ForbiddenException;
import com.example.app.common.NotFoundException;
import com.example.app.common.Enums.PolicyEffect;
import com.example.app.common.Enums.SubjectType;
import com.example.app.common.Enums.ValuesMode;
import com.example.app.reports.ReportDimension;
import com.example.app.reports.ReportDimensionRepository;
import com.example.app.rbac.Usuario;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class PolicyService {
    private final ReportAccessPolicyRepository policyRepository;
    private final ReportAccessPolicyRuleRepository ruleRepository;
    private final ReportAccessPolicyRuleValueRepository ruleValueRepository;
    private final ReportDimensionRepository dimensionRepository;

    public PolicyService(ReportAccessPolicyRepository policyRepository,
                         ReportAccessPolicyRuleRepository ruleRepository,
                         ReportAccessPolicyRuleValueRepository ruleValueRepository,
                         ReportDimensionRepository dimensionRepository) {
        this.policyRepository = policyRepository;
        this.ruleRepository = ruleRepository;
        this.ruleValueRepository = ruleValueRepository;
        this.dimensionRepository = dimensionRepository;
    }

    public ReportAccessPolicy resolvePolicy(Long reportId, Usuario usuario) {
        return policyRepository
            .findTopByReportIdAndSubjectTypeAndSubjectIdAndActiveTrueOrderByPriorityDesc(
                reportId, SubjectType.USER, usuario.getId())
            .orElseGet(() -> resolveGroupPolicy(reportId, usuario));
    }

    private ReportAccessPolicy resolveGroupPolicy(Long reportId, Usuario usuario) {
        List<Long> groupIds = usuario.getGrupos().stream()
            .map(grupo -> grupo.getId())
            .toList();

        if (groupIds.isEmpty()) {
            return null;
        }

        List<ReportAccessPolicy> policies = policyRepository
            .findByReportIdAndSubjectTypeAndSubjectIdInAndActiveTrueOrderByPriorityDesc(
                reportId, SubjectType.GROUP, groupIds);

        return policies.isEmpty() ? null : policies.get(0);
    }

    public Map<String, List<String>> buildScopeValues(Long reportId, Usuario usuario, ReportAccessPolicy policy) {
        if (policy == null) {
            throw new ForbiddenException("No active policy found for report");
        }

        List<ReportDimension> dimensions = dimensionRepository.findByReportIdAndActiveTrue(reportId);

        if (dimensions.isEmpty()) {
            throw new BadRequestException("No dimensions configured for report");
        }

        if (policy.getEffect() == PolicyEffect.ALLOW_ALL) {
            Map<String, List<String>> map = new HashMap<>();
            dimensions.forEach(dimension -> map.put(dimension.getDimensionKey(), List.of("*")));
            return map;
        }

        List<ReportAccessPolicyRule> rules = ruleRepository.findByPolicyIdAndActiveTrue(policy.getId());

        Map<String, ReportAccessPolicyRule> ruleMap = rules.stream()
            .collect(Collectors.toMap(ReportAccessPolicyRule::getDimensionKey, rule -> rule));

        Map<String, List<String>> valuesByDimension = new HashMap<>();

        for (ReportDimension dimension : dimensions) {
            ReportAccessPolicyRule rule = ruleMap.get(dimension.getDimensionKey());
            if (rule == null) {
                throw new ForbiddenException("Missing policy rule for dimension " + dimension.getDimensionKey());
            }
            if (rule.getValuesMode() == ValuesMode.FROM_USER_ATTRIBUTE) {

                String value = resolveUserAttribute(usuario, rule.getUserAttribute());
                valuesByDimension.put(dimension.getDimensionKey(), List.of(value));


            } else {

                List<ReportAccessPolicyRuleValue> values = ruleValueRepository.findByRuleId(rule.getId());
                Set<String> mapped = values.stream().map(ReportAccessPolicyRuleValue::getRuleValue).collect(Collectors.toSet());

                if (mapped.isEmpty()) {
                    throw new ForbiddenException("Policy rule values missing for dimension " + dimension.getDimensionKey());
                }
                if (mapped.contains("*")) {
                    valuesByDimension.put(dimension.getDimensionKey(), List.of("*"));
                    continue;
                }
                valuesByDimension.put(dimension.getDimensionKey(), mapped.stream().toList());
            }
        }
        return valuesByDimension;
    }

    private String resolveUserAttribute(Usuario usuario, String attribute) {
        if (attribute == null) {
            throw new ForbiddenException("Missing user attribute mapping");
        }
        String value = switch (attribute) {
            case "orgao_id" -> String.valueOf(usuario.getOrgao().getId());
            case "unidade_id" -> usuario.getUnidade() != null ? String.valueOf(usuario.getUnidade().getId()) : null;
            case "cidade_id" -> usuario.getCidadeId() != null ? String.valueOf(usuario.getCidadeId()) : null;
            default -> throw new NotFoundException("Unsupported user attribute: " + attribute);
        };
        if (value == null) {
            throw new ForbiddenException("User attribute " + attribute + " is missing");
        }
        return value;
    }
}
