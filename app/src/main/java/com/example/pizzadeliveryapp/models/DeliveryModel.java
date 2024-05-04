package com.example.pizzadeliveryapp.models;

import java.io.Serializable;

public class DeliveryModel implements Serializable {

    private String phone;
    private String address;

    public DeliveryModel(){

    }

    public DeliveryModel(String phone, String address) {

        this.phone = phone;
        this.address = address;

    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}

