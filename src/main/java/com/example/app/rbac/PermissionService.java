package com.example.app.rbac;

import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {
    public Set<String> getEffectivePermissions(Usuario usuario) {
        Set<String> direct = usuario.getPermissoes().stream()
            .map(Permissao::getCode)
            .collect(Collectors.toSet());
        Set<String> group = usuario.getGrupos().stream()
            .flatMap(grupo -> grupo.getPermissoes().stream())
            .map(Permissao::getCode)
            .collect(Collectors.toSet());
        direct.addAll(group);
        return direct;
    }

    public boolean hasPermission(Usuario usuario, String code) {
        return getEffectivePermissions(usuario).contains(code);
    }
}
