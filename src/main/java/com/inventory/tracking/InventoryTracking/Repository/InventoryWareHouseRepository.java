package com.inventory.tracking.InventoryTracking.Repository;

import com.inventory.tracking.InventoryTracking.Entity.InventoryItem;
import com.inventory.tracking.InventoryTracking.Entity.InventoryWareHouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryWareHouseRepository extends JpaRepository<InventoryWareHouse, Long> {

    InventoryWareHouse findByWarehousename (String warehousename);

}
