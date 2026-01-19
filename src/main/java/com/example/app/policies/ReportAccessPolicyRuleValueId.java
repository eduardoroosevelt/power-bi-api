package com.example.app.policies;

import java.io.Serializable;
import java.util.Objects;

public class ReportAccessPolicyRuleValueId implements Serializable {
    private Long rule;
    private String ruleValue;

    public ReportAccessPolicyRuleValueId() {
    }

    public ReportAccessPolicyRuleValueId(Long rule, String ruleValue) {
        this.rule = rule;
        this.ruleValue = ruleValue;
    }

    public Long getRule() {
        return rule;
    }

    public void setRule(Long rule) {
        this.rule = rule;
    }

    public String getRuleValue() {
        return ruleValue;
    }

    public void setRuleValue(String ruleValue) {
        this.ruleValue = ruleValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReportAccessPolicyRuleValueId that = (ReportAccessPolicyRuleValueId) o;
        return Objects.equals(rule, that.rule) && Objects.equals(ruleValue, that.ruleValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rule, ruleValue);
    }
}
