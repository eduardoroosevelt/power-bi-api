package com.example.app.rbac;

import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @EntityGraph(attributePaths = {"grupos", "grupos.permissoes", "permissoes", "orgao", "unidade"})
    Optional<Usuario> findByUsername(String username);
}
