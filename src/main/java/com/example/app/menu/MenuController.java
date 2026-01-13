package com.example.app.menu;

import com.example.app.rbac.Usuario;
import com.example.app.rbac.UsuarioRepository;
import com.example.app.security.SecurityUtils;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/me")
public class MenuController {
    private final MenuService menuService;
    private final UsuarioRepository usuarioRepository;

    public MenuController(MenuService menuService, UsuarioRepository usuarioRepository) {
        this.menuService = menuService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/menu")
    public List<MenuItemDto> menu() {
        String username = SecurityUtils.getCurrentUsername();
        Usuario usuario = usuarioRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalStateException("User not found"));
        return menuService.getMenuForUser(usuario);
    }
}
