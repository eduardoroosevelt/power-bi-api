package com.example.app.securityscope;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SecurityScopeService {
    private final SecurityScopeRepository securityScopeRepository;

    public SecurityScopeService(SecurityScopeRepository securityScopeRepository) {
        this.securityScopeRepository = securityScopeRepository;
    }

    @Transactional
    public void materialize(String principal, String reportKey, Map<String, List<String>> valuesByDimension) {
        securityScopeRepository.deleteByPrincipalAndReportKey(principal, reportKey);
        List<SecurityScope> scopes = new ArrayList<>();
        Instant now = Instant.now();
        valuesByDimension.forEach((dimension, values) -> values.forEach(value -> {
            SecurityScope scope = new SecurityScope();
            scope.setPrincipal(principal);
            scope.setReportKey(reportKey);
            scope.setDimensionKey(dimension);
            scope.setDimensionValue(value);
            scope.setActive(true);
            scope.setUpdatedAt(now);
            scopes.add(scope);
        }));
        securityScopeRepository.saveAll(scopes);
    }
}
