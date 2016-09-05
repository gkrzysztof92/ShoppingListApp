package com.sysmobil.shoppinglistapp.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Simple POJO Class. Represents Shopping List entity.
 */
public class ShoppingList {

    private int id;
    private String shoppingListName;
    private float payment;
    private String creationDate;
    private boolean isPaid;

    public ShoppingList() {
    }

    public ShoppingList(int id, String shoppingListName) {
        this.id = id;
        this.shoppingListName = shoppingListName;
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        this.creationDate = dateFormat.format(date);
    }

    public ShoppingList(int id, String shoppingListName, float payment, String creationDate, boolean isPaid) {
        this.id = id;
        this.shoppingListName = shoppingListName;
        this.payment = payment;
        this.creationDate = creationDate;
        this.isPaid = isPaid;
    }

    public int getId() {
        return id;
    }

    public String getShoppingListName() {
        return shoppingListName;
    }

    public float getPayment() {
        return payment;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setShoppingListName(String shoppingListName) {
        this.shoppingListName = shoppingListName;
    }

    public void setPayment(float payment) {
        this.payment = payment;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    @Override
    public String toString() {
        return "ShoppingList{" +
                "id=" + id +
                ", shoppingListName='" + shoppingListName + '\'' +
                ", payment=" + payment +
                ", creationDate='" + creationDate + '\'' +
                ", isPaid=" + isPaid +
                '}';
    }
}
