package com.sysmobil.shoppinglistapp.service;

import com.sysmobil.shoppinglistapp.model.Product;

import java.util.List;

/**
 * Interface representing CRUD operations on Product table.
 */
public interface ProductService {

    /**
     * Returns all products in product Table
     * @param id
     * @return
     */
    List<Product> getAllProducts(int id);

    /**
     * Returns product with specified id
     * @param id - id of product
     * @return
     */
    Product getProductById(int id);

    /**
     * Creates product with specified id
     * @param shoppingListId id of products
     * @param product instance of product
     */
    void addProduct(int shoppingListId, Product product);

    /**
     * Updates specified product
     * @param product instance of product
     */
    void updateProduct(Product product);

    /**
     * Delete specified products
     * @param product instance of products
     */
    void deleteProduct(Product product);

    /**
     * Returns next value of sequence in Product table
     * @return
     */
    int getNextFreeId();

    /**
     * Deletes all products with specified shopping list id
     * @param id shoping list id
     */
    void deleteProductsByShoppingListId(int id);
}
