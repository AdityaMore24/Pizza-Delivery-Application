package com.example.pizzadeliveryapp.models;

import java.io.Serializable;

public class ChefModel implements Serializable {

    private String phone;
    private String address;
    private String status;

    public ChefModel(){

    }

    public ChefModel(String phone, String address) {
        this.phone = phone;
        this.address = address;
        this.status = status;

    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String price) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

