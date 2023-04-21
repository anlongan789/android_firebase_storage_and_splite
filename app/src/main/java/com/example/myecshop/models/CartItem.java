package com.example.myecshop.models;

import java.io.Serializable;

public class CartItem implements Serializable {
    private String id;
    private String name;
    private String imgUrl;
    private int quantity;
    private double price;

    public CartItem() {
    }

    public CartItem(String id, String name, String imgUrl, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.quantity = quantity;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
