package com.example.app.policies;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportAccessPolicyRuleRepository extends JpaRepository<ReportAccessPolicyRule, Long> {
    List<ReportAccessPolicyRule> findByPolicyIdAndActiveTrue(Long policyId);
}
