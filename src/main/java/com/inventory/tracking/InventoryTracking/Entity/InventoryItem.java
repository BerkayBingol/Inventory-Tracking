package com.inventory.tracking.InventoryTracking.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "inventory_item")
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="item_name")
    private String itemName;

    private int number; //

    public InventoryItem() {}

    @JsonIgnore
    @ManyToMany
    @JoinTable(name="warehouse_inventory",
            joinColumns = {@JoinColumn(name="inventory_item_id",referencedColumnName = "id")},
            inverseJoinColumns ={@JoinColumn(name="warehouse_id",referencedColumnName = "id")})
    private List<InventoryWareHouse> inventoryWareHouses;

    public List<InventoryWareHouse> getInventoryWareHouses() {
        return inventoryWareHouses;
    }

    public void setInventoryWareHouses(List<InventoryWareHouse> inventoryWareHouses) {
        this.inventoryWareHouses = inventoryWareHouses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id  = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public InventoryItem(String itemName, Integer number) {

        this.itemName = itemName;
        this.number = number;


    }
    public void addInventoryWareHouse(InventoryWareHouse inventoryWareHouse)
    {
        this.inventoryWareHouses.add(inventoryWareHouse);
    }
    public void removeInventoryWareHouse(InventoryWareHouse inventoryWareHouse)
    {
        this.inventoryWareHouses.remove(inventoryWareHouse);
    }


}
