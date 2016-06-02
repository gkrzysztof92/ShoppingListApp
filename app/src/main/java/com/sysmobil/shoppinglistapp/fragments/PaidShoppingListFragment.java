package com.sysmobil.shoppinglistapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sysmobil.productlistapp.R;

/**
 * Created by krzgac on 2016-06-02.
 */
public class PaidShoppingListFragment extends Fragment{

    public PaidShoppingListFragment() {
        Log.i("Fragment Check", "Fragment MyShoppingList Created");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.paid_shopping_list_fragment, container, false);
    }

}
