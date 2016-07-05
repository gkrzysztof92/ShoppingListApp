package com.sysmobil.shoppinglistapp.model;

import java.util.Date;

/**
 * Created by krzgac on 2016-05-16.
 */
public class Product {

    private int id;
    private String productName;
    private float productPrice;
    private String productBarcode;
    private int quantity;
    private String creationDate;
    private boolean isBought;
    private int productShoppingListId;

    public Product() {
    }

    public Product(int id, String productName, float productPrice, int quantity, String productBarcode) {
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.productBarcode = productBarcode;
    }

    public Product(int id, String productName, float productPrice, String productBarcode, int quantity, String creationDate, boolean isBought) {
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productBarcode = productBarcode;
        this.quantity = quantity;
        this.creationDate = creationDate;
        this.isBought = isBought;
    }

    public void setProductShoppingListId (int productShoppingListId) {
        this.productShoppingListId = productShoppingListId;
    }

    public int getProductShoppingListId() {
        return productShoppingListId;
    }

    public int getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public String getProductBarcode() {
        return productBarcode;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public boolean isBought() {
        return isBought;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductBarcode(String productBarcode) {
        this.productBarcode = productBarcode;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public void setIsBought(boolean isBought) {
        this.isBought = isBought;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productBarcode='" + productBarcode + '\'' +
                ", quantity=" + quantity +
                ", creationDate='" + creationDate + '\'' +
                ", isBought=" + isBought +
                '}';
    }
}
