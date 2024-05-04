package com.example.pizzadeliveryapp.models;

public class CartModel {

    private String fname;
    private String price;
    private Double rating;
    private String quantity;

    public CartModel(){

    }

    public CartModel(String fname, String price, Double rating, String quantity) {

        this.fname = fname;
        this.price = price;
        this.rating = rating;
        this.quantity = quantity;

    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname){
        this.fname = fname;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

}
