package com.example.app.rbac;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissaoRepository extends JpaRepository<Permissao, Long> {
    Optional<Permissao> findByCode(String code);
}
