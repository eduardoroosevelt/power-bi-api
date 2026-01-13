package com.example.app.config;

import com.example.app.config.dto.CreateGrupoRequest;
import com.example.app.config.dto.CreateOrgaoRequest;
import com.example.app.config.dto.CreatePermissaoRequest;
import com.example.app.config.dto.CreateUnidadeRequest;
import com.example.app.config.dto.GrupoPermissaoRequest;
import com.example.app.config.dto.UpdateGrupoRequest;
import com.example.app.config.dto.UpdateOrgaoRequest;
import com.example.app.config.dto.UpdatePermissaoRequest;
import com.example.app.config.dto.UpdateUnidadeRequest;
import com.example.app.policies.CreatePolicyRequest;
import com.example.app.policies.CreateRuleRequest;
import com.example.app.policies.CreateRuleValueRequest;
import com.example.app.policies.ReportAccessPolicy;
import com.example.app.policies.ReportAccessPolicyRule;
import com.example.app.policies.ReportAccessPolicyRuleValue;
import com.example.app.rbac.Grupo;
import com.example.app.rbac.Orgao;
import com.example.app.rbac.Permissao;
import com.example.app.rbac.Unidade;
import com.example.app.reports.CreateDimensionRequest;
import com.example.app.reports.CreateReportRequest;
import com.example.app.reports.PowerBiReport;
import com.example.app.reports.ReportDimension;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/orgaos")
    public List<Orgao> listOrgaos() {
        return adminService.listOrgaos();
    }

    @PostMapping("/orgaos")
    public Orgao createOrgao(@Valid @RequestBody CreateOrgaoRequest request) {
        return adminService.createOrgao(request);
    }

    @PutMapping("/orgaos/{id}")
    public Orgao updateOrgao(@PathVariable Long id, @Valid @RequestBody UpdateOrgaoRequest request) {
        return adminService.updateOrgao(id, request);
    }

    @DeleteMapping("/orgaos/{id}")
    public void deleteOrgao(@PathVariable Long id) {
        adminService.deleteOrgao(id);
    }

    @GetMapping("/unidades")
    public List<Unidade> listUnidades() {
        return adminService.listUnidades();
    }

    @PostMapping("/unidades")
    public Unidade createUnidade(@Valid @RequestBody CreateUnidadeRequest request) {
        return adminService.createUnidade(request);
    }

    @PutMapping("/unidades/{id}")
    public Unidade updateUnidade(@PathVariable Long id, @Valid @RequestBody UpdateUnidadeRequest request) {
        return adminService.updateUnidade(id, request);
    }

    @DeleteMapping("/unidades/{id}")
    public void deleteUnidade(@PathVariable Long id) {
        adminService.deleteUnidade(id);
    }

    @GetMapping("/permissoes")
    public List<Permissao> listPermissoes() {
        return adminService.listPermissoes();
    }

    @PostMapping("/permissoes")
    public Permissao createPermissao(@Valid @RequestBody CreatePermissaoRequest request) {
        return adminService.createPermissao(request);
    }

    @PutMapping("/permissoes/{id}")
    public Permissao updatePermissao(@PathVariable Long id, @Valid @RequestBody UpdatePermissaoRequest request) {
        return adminService.updatePermissao(id, request);
    }

    @DeleteMapping("/permissoes/{id}")
    public void deletePermissao(@PathVariable Long id) {
        adminService.deletePermissao(id);
    }

    @GetMapping("/grupos")
    public List<Grupo> listGrupos() {
        return adminService.listGrupos();
    }

    @PostMapping("/grupos")
    public Grupo createGrupo(@Valid @RequestBody CreateGrupoRequest request) {
        return adminService.createGrupo(request);
    }

    @PutMapping("/grupos/{id}")
    public Grupo updateGrupo(@PathVariable Long id, @Valid @RequestBody UpdateGrupoRequest request) {
        return adminService.updateGrupo(id, request);
    }

    @DeleteMapping("/grupos/{id}")
    public void deleteGrupo(@PathVariable Long id) {
        adminService.deleteGrupo(id);
    }

    @GetMapping("/grupos/{grupoId}/permissoes")
    public List<Permissao> listGrupoPermissoes(@PathVariable Long grupoId) {
        return adminService.listGrupoPermissoes(grupoId).stream().toList();
    }

    @PostMapping("/grupos/{grupoId}/permissoes")
    public List<Permissao> addGrupoPermissao(@PathVariable Long grupoId,
                                             @Valid @RequestBody GrupoPermissaoRequest request) {
        return adminService.addGrupoPermissao(grupoId, request).stream().toList();
    }

    @DeleteMapping("/grupos/{grupoId}/permissoes/{permissaoId}")
    public List<Permissao> removeGrupoPermissao(@PathVariable Long grupoId,
                                                @PathVariable Long permissaoId) {
        return adminService.removeGrupoPermissao(grupoId, permissaoId).stream().toList();
    }
}
