package com.example.app.menu;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findAllByAtivoTrueOrderByOrdemAsc();

    Optional<MenuItem> findByResourceIdAndResourceType(Long resourceId, com.example.app.common.Enums.ResourceType resourceType);
}
