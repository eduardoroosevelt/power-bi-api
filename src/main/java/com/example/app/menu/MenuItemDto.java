package com.example.app.menu;

import com.example.app.common.Enums.ResourceType;
import java.util.ArrayList;
import java.util.List;

public class MenuItemDto {
    private Long id;
    private String label;
    private String route;
    private String icon;
    private int ordem;
    private ResourceType resourceType;
    private Long resourceId;
    private List<MenuItemDto> children = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public List<MenuItemDto> getChildren() {
        return children;
    }

    public void setChildren(List<MenuItemDto> children) {
        this.children = children;
    }
}
