package com.sysmobil.shoppinglistapp.service;

import com.sysmobil.shoppinglistapp.model.ShoppingList;

import java.util.List;

/**
 * Created by krzgac on 2016-05-23.
 */
public interface ShoppingListService {

    List<ShoppingList> getAllShoppingList();
    ShoppingList getShoppingListById(int id);
    void addShoppingList(int id, ShoppingList shoppingList);
    void updateShoppingList(ShoppingList shoppingList);
    void deleteShoppingList(ShoppingList shoppingList);
    int getNextFreeId();

}
