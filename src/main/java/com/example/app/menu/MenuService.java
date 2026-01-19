package com.example.app.menu;

import com.example.app.rbac.PermissionService;
import com.example.app.rbac.Usuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class MenuService {
    private final MenuItemRepository menuItemRepository;
    private final PermissionService permissionService;

    public MenuService(MenuItemRepository menuItemRepository, PermissionService permissionService) {
        this.menuItemRepository = menuItemRepository;
        this.permissionService = permissionService;
    }

    public List<MenuItemDto> getMenuForUser(Usuario usuario) {
        List<MenuItem> items = menuItemRepository.findAllByAtivoTrueOrderByOrdemAsc();
        Set<String> permissions = permissionService.getEffectivePermissions(usuario);
        List<MenuItem> filtered = items.stream()
            .filter(item -> item.getPermissao() == null || permissions.contains(item.getPermissao().getCode()))
            .collect(Collectors.toList());
        return buildTree(filtered);
    }

    private List<MenuItemDto> buildTree(List<MenuItem> items) {
        Map<Long, MenuItemDto> map = new HashMap<>();
        for (MenuItem item : items) {
            MenuItemDto dto = toDto(item);
            map.put(item.getId(), dto);
        }
        List<MenuItemDto> roots = new ArrayList<>();
        for (MenuItem item : items) {
            MenuItemDto dto = map.get(item.getId());
            if (item.getParent() != null && map.containsKey(item.getParent().getId())) {
                map.get(item.getParent().getId()).getChildren().add(dto);
            } else {
                roots.add(dto);
            }
        }
        pruneEmptyParents(roots);
        sortChildren(roots);
        return roots;
    }

    private void pruneEmptyParents(List<MenuItemDto> nodes) {
        List<MenuItemDto> toRemove = new ArrayList<>();
        for (MenuItemDto node : nodes) {
            pruneEmptyParents(node.getChildren());
            if (node.getRoute() == null && node.getChildren().isEmpty()) {
                toRemove.add(node);
            }
        }
        nodes.removeAll(toRemove);
    }

    private void sortChildren(List<MenuItemDto> nodes) {
        nodes.forEach(node -> sortChildren(node.getChildren()));
        nodes.sort((a, b) -> Integer.compare(a.getOrdem(), b.getOrdem()));
    }

    private MenuItemDto toDto(MenuItem item) {
        MenuItemDto dto = new MenuItemDto();
        dto.setId(item.getId());
        dto.setLabel(item.getLabel());
        dto.setIcon(item.getIcon());
        dto.setRoute(item.getRoute());
        dto.setOrdem(item.getOrdem());
        dto.setResourceType(item.getResourceType());
        dto.setResourceId(item.getResourceId());
        return dto;
    }
}
