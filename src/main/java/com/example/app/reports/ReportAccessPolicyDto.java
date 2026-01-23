package com.example.app.reports;

import com.example.app.common.Enums.PolicyEffect;
import com.example.app.common.Enums.SubjectType;
import java.util.ArrayList;
import java.util.List;

public class ReportAccessPolicyDto {
    private Long id;
    private SubjectType subjectType;
    private Long subjectId;
    private PolicyEffect effect;
    private int priority;
    private boolean active;
    private List<ReportAccessPolicyRuleDto> rules = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SubjectType getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(SubjectType subjectType) {
        this.subjectType = subjectType;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public PolicyEffect getEffect() {
        return effect;
    }

    public void setEffect(PolicyEffect effect) {
        this.effect = effect;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<ReportAccessPolicyRuleDto> getRules() {
        return rules;
    }

    public void setRules(List<ReportAccessPolicyRuleDto> rules) {
        this.rules = rules;
    }
}
