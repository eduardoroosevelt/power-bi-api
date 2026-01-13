package com.example.app.reports;

import com.example.app.security.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
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
}
