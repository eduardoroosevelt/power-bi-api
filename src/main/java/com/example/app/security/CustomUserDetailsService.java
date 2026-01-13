package com.example.app.security;

import com.example.app.rbac.Usuario;
import com.example.app.rbac.UsuarioRepository;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Set<SimpleGrantedAuthority> authorities = usuario.getPermissoes().stream()
            .map(permissao -> new SimpleGrantedAuthority("PERM_" + permissao.getCode()))
            .collect(Collectors.toSet());
        usuario.getGrupos().forEach(grupo -> grupo.getPermissoes().forEach(permissao ->
            authorities.add(new SimpleGrantedAuthority("PERM_" + permissao.getCode()))));
        return new SecurityUserDetails(usuario.getUsername(), usuario.getPasswordHash(), usuario.isAtivo(), authorities);
    }
}
