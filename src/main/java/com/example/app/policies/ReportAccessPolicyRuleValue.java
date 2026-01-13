package com.example.app.policies;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "report_access_policy_rule_value")
@IdClass(ReportAccessPolicyRuleValueId.class)
public class ReportAccessPolicyRuleValue {
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "rule_id")
    private ReportAccessPolicyRule rule;

    @Id
    @jakarta.persistence.Column(name = "rule_value")
    private String ruleValue;

    public ReportAccessPolicyRule getRule() {
        return rule;
    }

    public void setRule(ReportAccessPolicyRule rule) {
        this.rule = rule;
    }

    public String getRuleValue() {
        return ruleValue;
    }

    public void setRuleValue(String ruleValue) {
        this.ruleValue = ruleValue;
    }
}
