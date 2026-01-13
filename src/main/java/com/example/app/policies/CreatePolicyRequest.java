package com.example.app.policies;

import com.example.app.common.Enums.PolicyEffect;
import com.example.app.common.Enums.SubjectType;
import jakarta.validation.constraints.NotNull;

public class CreatePolicyRequest {
    @NotNull
    private SubjectType subjectType;
    @NotNull
    private Long subjectId;
    @NotNull
    private PolicyEffect effect;
    private int priority;
    @NotNull
    private Boolean active;

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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
