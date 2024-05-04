package com.example.pizzadeliveryapp.models;

import java.io.Serializable;

public class ChefModel2 implements Serializable {

    private String fname;
    private String numberInCart;
    private String status;
    private String address;

    public ChefModel2(){

    }

    public ChefModel2(String fname, String numberInCart) {

        this.fname = fname;
        this.numberInCart = numberInCart;

    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname){
        this.fname = fname;
    }

    public String getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(String numberInCart) {
        this.numberInCart = numberInCart;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

