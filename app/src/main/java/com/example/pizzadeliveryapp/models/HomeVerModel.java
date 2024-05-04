package com.example.pizzadeliveryapp.models;

import java.io.Serializable;

public class HomeVerModel implements Serializable {

    //int image;
    private String fname;
    private Double price;
    private Float rating;
    private String timing;
    private String url;
    private int numberInCart;
    private String details;

    public HomeVerModel(){

    }

    public HomeVerModel(String fname, Double price, Float rating, String timing, String url, String details) {

        this.fname = fname;
        this.price = price;
        this.rating = rating;
        this.timing = timing;
        this.url = url;
        this.details = details; // Default is 0, 0: Placed, 1: Shipped, 2: Delivered

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname){
        this.fname = fname;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
