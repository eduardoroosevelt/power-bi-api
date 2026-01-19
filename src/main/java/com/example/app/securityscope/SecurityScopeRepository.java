package com.example.app.securityscope;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecurityScopeRepository extends JpaRepository<SecurityScope, Long> {
    List<SecurityScope> findByPrincipalAndReportKey(String principal, String reportKey);

    void deleteByPrincipalAndReportKey(String principal, String reportKey);
}
