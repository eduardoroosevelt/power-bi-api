package com.example.app.reports;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ReportAccessLogRequest {
    @NotNull
    @Min(0)
    private Integer durationSeconds;

    public Integer getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(Integer durationSeconds) {
        this.durationSeconds = durationSeconds;
    }
}
