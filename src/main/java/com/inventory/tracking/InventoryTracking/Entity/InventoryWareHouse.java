package com.inventory.tracking.InventoryTracking.Entity;
import javax.persistence.*;
import java.util.List;


@Entity
public class InventoryWareHouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String warehousename;
    private String warehouselocation;

    @ManyToMany(mappedBy = "inventoryWareHouses")
    private List<InventoryItem> inventoryItems;

    public InventoryWareHouse() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWarehousename() {
        return warehousename;
    }

    public void setWarehousename(String warehousename) {
        this.warehousename = warehousename;
    }

    public String getWarehouselocation() {
        return warehouselocation;
    }

    public void setWarehouselocation(String warehouselocation) {
        this.warehouselocation = warehouselocation;
    }

    public List<InventoryItem> getInventoryItems() {
        return inventoryItems;

    }

    public void setInventoryItems(List<InventoryItem> inventoryItems) {
        this.inventoryItems = inventoryItems;
    }

    public InventoryWareHouse(String warehousename , String warehouselocation) {
        this.warehousename = warehousename;
        this.warehouselocation = warehouselocation;
    }

    public void addInventoryItem(InventoryItem inventoryItem)
    {
        this.inventoryItems.add(inventoryItem);
    }
    public void removeInventoryItem(InventoryItem inventoryItem)
    {
        this.inventoryItems.remove(inventoryItem);
    }
}
