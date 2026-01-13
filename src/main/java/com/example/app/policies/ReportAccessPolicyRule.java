package com.example.app.policies;

import com.example.app.common.Enums.RuleOperator;
import com.example.app.common.Enums.ValuesMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "report_access_policy_rule")
public class ReportAccessPolicyRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "policy_id")
    private ReportAccessPolicy policy;

    private String dimensionKey;

    @Enumerated(EnumType.STRING)
    private RuleOperator operator;

    @Enumerated(EnumType.STRING)
    private ValuesMode valuesMode;

    private String userAttribute;

    private boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReportAccessPolicy getPolicy() {
        return policy;
    }

    public void setPolicy(ReportAccessPolicy policy) {
        this.policy = policy;
    }

    public String getDimensionKey() {
        return dimensionKey;
    }

    public void setDimensionKey(String dimensionKey) {
        this.dimensionKey = dimensionKey;
    }

    public RuleOperator getOperator() {
        return operator;
    }

    public void setOperator(RuleOperator operator) {
        this.operator = operator;
    }

    public ValuesMode getValuesMode() {
        return valuesMode;
    }

    public void setValuesMode(ValuesMode valuesMode) {
        this.valuesMode = valuesMode;
    }

    public String getUserAttribute() {
        return userAttribute;
    }

    public void setUserAttribute(String userAttribute) {
        this.userAttribute = userAttribute;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
