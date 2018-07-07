package com.inventory.tracking.InventoryTracking;

public class ResponseWrapper {
    private String message;
    private int responseCode;

    public ResponseWrapper() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }



}
