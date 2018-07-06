package com.inventory.tracking.InventoryTracking.Controller;

import com.inventory.tracking.InventoryTracking.Entity.InventoryItem;
import com.inventory.tracking.InventoryTracking.Entity.InventoryWareHouse;

import com.inventory.tracking.InventoryTracking.Repository.InventoryItemRepository;
import com.inventory.tracking.InventoryTracking.Repository.InventoryWareHouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.inventory.tracking.InventoryTracking.Entity.InventoryItem;
import com.inventory.tracking.InventoryTracking.Entity.InventoryWareHouse;

class AddItemWrapper { //Wrapper provides JSON serializer to get parameters as normally.

    private String name;
    private int number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}
class AddItemToWareHouseWrapper {

    private String itemName;
    private String warehouseName;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }



}

class AddWareHouseWrapper {

    private String name;
    private String location;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
class RemoveItemWrapper {

    private String itemName;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

}

@RestController
public class InventoryTrackingController {

    @Autowired
    InventoryItemRepository inventoryItemRepository;
    @Autowired
    InventoryWareHouseRepository inventoryWareHouseRepository;

    @RequestMapping(value = "/addInventoryItem", method= RequestMethod.POST)
    public Object addInventoryItem(@RequestBody AddItemWrapper JsonBODY) {

        if (JsonBODY.getName() == "" || JsonBODY.getNumber() <1 ||
                JsonBODY.getName().length() < 2) //Stok varsa ve girilen isim fieldi null degilse
        {
            return "Validation error! Check !";

        }
        else {
            String itemName = JsonBODY.getName();
            int numberOfItem = JsonBODY.getNumber();

            InventoryItem inventoryItem = inventoryItemRepository.findByItemName(itemName);
            if(inventoryItem!=null) {
                //return err
                return "Item already added. Please update.";
            }
            else{
                inventoryItem = new InventoryItem();
                inventoryItem.setItemName(itemName);
                inventoryItem.setNumber(numberOfItem);
                inventoryItemRepository.save(inventoryItem);

            }
            return "Success ! Item Record is added succesfully!";

        }




    }

    @GetMapping(value = "/listInventoryItem")
    public Object listInventoryItem() {

        return inventoryItemRepository.findAll();
    }



    @RequestMapping(value = "/addNewWarehouse", method = RequestMethod.POST)
    public Object addNewWarehouse(@RequestBody AddWareHouseWrapper JsonBODY) {


        if (JsonBODY.getName() == "" ||  JsonBODY.getLocation() == "" ||
                JsonBODY.getName().length() <2 || JsonBODY.getLocation().length() <2)
        {
            return "Validation error! Check !";

        }
        else {
            String wareHouseName = JsonBODY.getName();
            String wareHouseLocation = JsonBODY.getLocation();

            InventoryWareHouse inventoryWareHouse = inventoryWareHouseRepository.findByWarehousename(wareHouseName);

            if(inventoryWareHouse!=null) {
                return "Record already exists.";
            }
            else{
                inventoryWareHouse = new InventoryWareHouse();
                inventoryWareHouse.setWarehousename(wareHouseName);
                inventoryWareHouse.setWarehouselocation(wareHouseLocation);
                inventoryWareHouseRepository.save(inventoryWareHouse);

                return "Success ! WareHouse Record is added succesfully!";
            }

        }





    }

    @GetMapping(value = "/listWareHouse")
    public Object listWareHouse() {

        return inventoryWareHouseRepository.findAll();
    }


    @RequestMapping(value = "/addItemToWareHouse", method = RequestMethod.POST)
    public String addItemToWareHouse(@RequestBody AddItemToWareHouseWrapper JsonBODY) {


        String itemName = JsonBODY.getItemName();
        String warehouseName = JsonBODY.getWarehouseName();

        InventoryWareHouse inventoryWareHouse = inventoryWareHouseRepository.findByWarehousename(warehouseName);
        InventoryItem inventoryItem = inventoryItemRepository.findByItemName(itemName);

        //Check the item doesnt exists//

        if(inventoryWareHouse ==null || inventoryItem == null){ return "Error. WareHouse does not exists.";}

        else {
            inventoryWareHouse.addInventoryItem(inventoryItem);
            inventoryItem.addInventoryWareHouse(inventoryWareHouse);
            inventoryItemRepository.save(inventoryItem);
            return "Record added the item to the warehouse. !";

        }


    }

    @RequestMapping(value = "/updateItemStock", method = RequestMethod.PUT)
    public String updateItemStock(@RequestBody AddItemWrapper JsonBODY) {


        String itemName = JsonBODY.getName();
        int stockNumber = JsonBODY.getNumber();

        InventoryItem inventoryItem = inventoryItemRepository.findByItemName(itemName);
        Long inventoryItemId = inventoryItem.getId();

        // InventoryWareHouse inventoryWareHouse = inventoryWareHouseRepository.
        //Check the item doesnt exists//

        if(inventoryItem == null || stockNumber < 0 ){ return "Error. Item does not exists.(or stockNumber is invalid!";}

        else {

            if(stockNumber == 0) {
                //remove item
                //remove item tested. It worked.
                inventoryItemRepository.delete(inventoryItem);


            }
            else {
                inventoryItem.setItemName(itemName);
                inventoryItem.setNumber(stockNumber);
                inventoryItemRepository.save(inventoryItem);
            }


        return "Item is updated. Check from list !";
        }


    }

    @RequestMapping(value = "/deleteItem", method = RequestMethod.DELETE)
    public String deleteItem(@RequestBody RemoveItemWrapper JsonBODY) {

        //Listelemeyi butonlarla yap.


        String willDeleteItemName = JsonBODY.getItemName();
        InventoryItem inventoryItem = inventoryItemRepository.findByItemName(willDeleteItemName);

        //if inventory item is null, so there is nothing to do with delete.
        if (inventoryItem == null ) { return "There is no item exists who has" +willDeleteItemName;}
        else {
            inventoryItemRepository.delete(inventoryItem);
        return willDeleteItemName +" deleted";


        }


    }







}

