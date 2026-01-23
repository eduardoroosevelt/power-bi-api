package com.example.app.policies;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportAccessPolicyRuleValueRepository extends JpaRepository<ReportAccessPolicyRuleValue, ReportAccessPolicyRuleValueId> {
    List<ReportAccessPolicyRuleValue> findByRuleId(Long ruleId);

    boolean existsByRuleIdAndRuleValue(Long ruleId, String ruleValue);
}
