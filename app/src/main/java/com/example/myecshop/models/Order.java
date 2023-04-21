package com.example.myecshop.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {
    private String id;
    private ArrayList<CartItem> cartItems;
    private double totalPrice;
    private String paymentMethod;

    public Order() {
    }

    public Order(String id, ArrayList<CartItem> cartItems, double totalPrice, String paymentMethod) {
        this.id = id;
        this.cartItems = cartItems;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(ArrayList<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
