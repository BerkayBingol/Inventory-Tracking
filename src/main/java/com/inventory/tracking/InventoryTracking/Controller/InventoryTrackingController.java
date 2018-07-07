package com.inventory.tracking.InventoryTracking.Controller;

import com.inventory.tracking.InventoryTracking.Entity.InventoryItem;
import com.inventory.tracking.InventoryTracking.Entity.InventoryWareHouse;

import com.inventory.tracking.InventoryTracking.Repository.InventoryItemRepository;
import com.inventory.tracking.InventoryTracking.Repository.InventoryWareHouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.inventory.tracking.InventoryTracking.ResponseWrapper;
import javax.servlet.http.HttpServletResponse;


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
    ResponseWrapper responseWrapper = new ResponseWrapper();


    @RequestMapping(value = "/addInventoryItem", method= RequestMethod.POST)
    public Object addInventoryItem(@RequestBody AddItemWrapper JsonBODY, HttpServletResponse response) {

        if (JsonBODY.getName() == "" || JsonBODY.getNumber() <1 ||
                JsonBODY.getName().length() < 2) //Stok varsa ve girilen isim fieldi null degilse
        {

            responseWrapper.setMessage("Validation Error");
            responseWrapper.setResponseCode(400);
            return responseWrapper.getMessage();


        }



        else {
            String itemName = JsonBODY.getName();
            int numberOfItem = JsonBODY.getNumber();

            InventoryItem inventoryItem = inventoryItemRepository.findByItemName(itemName);
            if(inventoryItem!=null) {
                //return err
                responseWrapper.setMessage("Error, Item was already added!");
                responseWrapper.setResponseCode(400);
                return responseWrapper.getMessage();
            }
            else{
                inventoryItem = new InventoryItem();
                inventoryItem.setItemName(itemName);
                inventoryItem.setNumber(numberOfItem);
                inventoryItemRepository.save(inventoryItem);
                responseWrapper.setMessage("Success! Item is added.");
                responseWrapper.setResponseCode(200);
                return responseWrapper.getMessage();

            }

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
            responseWrapper.setMessage("Validation Error");
            responseWrapper.setResponseCode(400);
            return responseWrapper.getMessage();

        }
        else {
            String wareHouseName = JsonBODY.getName();
            String wareHouseLocation = JsonBODY.getLocation();

            InventoryWareHouse inventoryWareHouse = inventoryWareHouseRepository.findByWarehousename(wareHouseName);

            if(inventoryWareHouse!=null) {
                responseWrapper.setMessage("Error, Warehouse is already added! ");
                responseWrapper.setResponseCode(400);
                return responseWrapper.getMessage();
            }
            else{
                inventoryWareHouse = new InventoryWareHouse();
                inventoryWareHouse.setWarehousename(wareHouseName);
                inventoryWareHouse.setWarehouselocation(wareHouseLocation);
                inventoryWareHouseRepository.save(inventoryWareHouse);

                responseWrapper.setMessage("Success! Warehouse is added!");
                responseWrapper.setResponseCode(200);
                return responseWrapper.getMessage();
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



            //Check the item added before
        for (int i = 0; i <inventoryWareHouse.getInventoryItems().size(); i++) {

           if(inventoryWareHouse.getInventoryItems().get(i).getItemName().equalsIgnoreCase(itemName)) {
               responseWrapper.setMessage
                       ("Error, item added before.");
               responseWrapper.setResponseCode(400);
               return responseWrapper.getMessage();
           }



        }
        //Check the item does not exists//

        if(inventoryWareHouse ==null || inventoryItem == null) {
            responseWrapper.setMessage
                    ("Error,Warehouse or item does not exists.");
            responseWrapper.setResponseCode(400);
            return responseWrapper.getMessage(); }

        else {
            inventoryWareHouse.addInventoryItem(inventoryItem);
            inventoryItem.addInventoryWareHouse(inventoryWareHouse);
            inventoryItemRepository.save(inventoryItem);
            responseWrapper.setMessage("Success, item was added to "+ inventoryWareHouse.getWarehousename());
            responseWrapper.setResponseCode(200);
            return responseWrapper.getMessage();
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

        if(inventoryItem == null || stockNumber < 0 ){ responseWrapper.setMessage("Error, Item does not exists. Check!");
            responseWrapper.setResponseCode(400);
            return responseWrapper.getMessage();}

        else {

            if(stockNumber == 0) {
                //remove item
                //remove item tested. It worked.
                responseWrapper.setMessage("Success " +inventoryItem.getItemName()+ " is removed.");

                inventoryItemRepository.delete(inventoryItem);
                responseWrapper.setResponseCode(200);
                return responseWrapper.getMessage();

            }
            else {
                inventoryItem.setItemName(itemName);
                inventoryItem.setNumber(stockNumber);
                inventoryItemRepository.save(inventoryItem);

                responseWrapper.setMessage("Success " +inventoryItem.getItemName()+ " is updated." +
                        " New stock number is: ."+inventoryItem.getNumber()+"");

                inventoryItemRepository.delete(inventoryItem);
                responseWrapper.setResponseCode(200);
                return responseWrapper.getMessage();

            }


        }


    }

    @RequestMapping(value = "/deleteItem", method = RequestMethod.DELETE)
    public String deleteItem(@RequestBody RemoveItemWrapper JsonBODY) {

        String willDeleteItemName = JsonBODY.getItemName();
        InventoryItem inventoryItem = inventoryItemRepository.findByItemName(willDeleteItemName);

        //if inventory item is null, so there is nothing to do with delete.
        if (inventoryItem == null ) { responseWrapper.setMessage("Error!, there is no item named as "
                +willDeleteItemName  );
            responseWrapper.setResponseCode(400);
            return responseWrapper.getMessage();}

        else {
            inventoryItemRepository.delete(inventoryItem);
            responseWrapper.setMessage("Success " +willDeleteItemName+ " is removed.");
            responseWrapper.setResponseCode(200);
            return responseWrapper.getMessage();

        }


    }







}

