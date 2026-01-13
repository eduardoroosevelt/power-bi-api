package com.example.app.auth;

import com.example.app.common.ForbiddenException;
import com.example.app.rbac.Usuario;
import com.example.app.rbac.UsuarioRepository;
import com.example.app.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new ForbiddenException("Invalid credentials"));
        if (!usuario.isAtivo() || !passwordEncoder.matches(request.getPassword(), usuario.getPasswordHash())) {
            throw new ForbiddenException("Invalid credentials");
        }
        String token = jwtService.generateToken(usuario.getUsername(), usuario.getId());
        return new LoginResponse(token);
    }
}
