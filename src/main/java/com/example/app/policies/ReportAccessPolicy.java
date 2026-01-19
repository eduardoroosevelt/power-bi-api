package com.example.app.policies;

import com.example.app.common.Enums.PolicyEffect;
import com.example.app.common.Enums.SubjectType;
import com.example.app.reports.PowerBiReport;
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
@Table(name = "report_access_policy")
public class ReportAccessPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "report_id")
    private PowerBiReport report;

    @Enumerated(EnumType.STRING)
    private SubjectType subjectType;

    private Long subjectId;

    @Enumerated(EnumType.STRING)
    private PolicyEffect effect;

    private int priority;

    private boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PowerBiReport getReport() {
        return report;
    }

    public void setReport(PowerBiReport report) {
        this.report = report;
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
}
