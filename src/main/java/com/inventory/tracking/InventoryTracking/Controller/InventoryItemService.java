package com.inventory.tracking.InventoryTracking.Controller;


import com.inventory.tracking.InventoryTracking.Entity.InventoryItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class InventoryItemService {




    List <InventoryItem> inventoryItem = new ArrayList<>(Arrays.asList(

            new InventoryItem("Pencil", 25),
            new InventoryItem("Not a pencil", 35),
            new InventoryItem("Not a pencil, but still good item.", 1)));

        public List<InventoryItem> getInventoryItem() {
            return inventoryItem;
        }




}
