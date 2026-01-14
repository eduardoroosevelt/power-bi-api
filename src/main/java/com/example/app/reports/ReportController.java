package com.example.app.reports;

import com.example.app.security.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reportService;
    private final ReportAccessLogService logService;

    public ReportController(ReportService reportService, ReportAccessLogService logService) {
        this.reportService = reportService;
        this.logService = logService;
    }

    @GetMapping("/{reportId}")
    public ReportDetailDto getReport(@PathVariable Long reportId) {
        return reportService.getReport(reportId);
    }

    @PostMapping("/{reportId}/embed")
    public ResponseEntity<EmbedResponse> embed(@PathVariable Long reportId) {
        String username = SecurityUtils.getCurrentUsername();
        return ResponseEntity.ok(reportService.generateEmbed(reportId, username));
    }

    @PostMapping("/{reportId}/access-logs")
    public ResponseEntity<ReportAccessLogResponse> logAccess(@PathVariable Long reportId,
                                                             @Valid @RequestBody ReportAccessLogRequest request,
                                                             HttpServletRequest httpServletRequest) {
        String username = SecurityUtils.getCurrentUsername();
        String ipAddress = resolveClientIp(httpServletRequest);
        ReportAccessLogResponse response = logService.logAccess(reportId, username, ipAddress, request.getDurationSeconds());
        return ResponseEntity.ok(response);
    }

    private String resolveClientIp(HttpServletRequest request) {
        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded != null && !forwarded.isBlank()) {
            return forwarded.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
