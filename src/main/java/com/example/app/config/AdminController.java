package com.example.app.config;

import com.example.app.policies.CreatePolicyRequest;
import com.example.app.policies.CreateRuleRequest;
import com.example.app.policies.CreateRuleValueRequest;
import com.example.app.policies.ReportAccessPolicy;
import com.example.app.policies.ReportAccessPolicyRule;
import com.example.app.policies.ReportAccessPolicyRuleValue;
import com.example.app.reports.CreateDimensionRequest;
import com.example.app.reports.CreateReportRequest;
import com.example.app.reports.PowerBiReport;
import com.example.app.reports.ReportDimension;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAuthority('PERM_ADMIN')")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/reports")
    public List<PowerBiReport> listReports() {
        return adminService.listReports();
    }

    @PostMapping("/reports")
    public PowerBiReport createReport(@Valid @RequestBody CreateReportRequest request) {
        return adminService.createReport(request);
    }

    @PostMapping("/reports/{id}/dimensions")
    public ReportDimension addDimension(@PathVariable Long id, @Valid @RequestBody CreateDimensionRequest request) {
        return adminService.addDimension(id, request);
    }

    @PostMapping("/reports/{id}/policies")
    public ReportAccessPolicy addPolicy(@PathVariable Long id, @Valid @RequestBody CreatePolicyRequest request) {
        return adminService.addPolicy(id, request);
    }

    @PostMapping("/policies/{policyId}/rules")
    public ReportAccessPolicyRule addRule(@PathVariable Long policyId, @Valid @RequestBody CreateRuleRequest request) {
        return adminService.addRule(policyId, request);
    }

    @PostMapping("/rules/{ruleId}/values")
    public ReportAccessPolicyRuleValue addRuleValue(@PathVariable Long ruleId,
                                                    @Valid @RequestBody CreateRuleValueRequest request) {
        return adminService.addRuleValue(ruleId, request);
    }
}
