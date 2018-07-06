package com.inventory.tracking.InventoryTracking.Repository;

import com.inventory.tracking.InventoryTracking.Entity.InventoryItem;

import org.springframework.data.jpa.repository.JpaRepository;


public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {

    InventoryItem findByItemName(String name);


}
