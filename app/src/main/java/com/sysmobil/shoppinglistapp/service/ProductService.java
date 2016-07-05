package com.sysmobil.shoppinglistapp.service;

import com.sysmobil.shoppinglistapp.model.Product;

import java.util.List;

/**
 * Created by krzgac on 2016-05-23.
 */
public interface ProductService {

    /**
     *
     * @param id
     * @return
     */
    List<Product> getAllProducts(int id);
    Product getProductById(int id);
    void addProduct(int shoppingListId, Product product);
    void updateProduct(Product product);
    void deleteProduct(Product product);
}
