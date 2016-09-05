package com.sysmobil.shoppinglistapp.listeners;

import com.sysmobil.shoppinglistapp.model.Product;

/**
 * Interface representing action on change product state.
 */
public interface ChangeProductListener {
    void onReturnValue(Product product);

}
