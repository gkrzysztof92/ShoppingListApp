package com.sysmobil.shoppinglistapp.fragments;


import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sysmobil.productlistapp.R;
import com.sysmobil.shoppinglistapp.adapters.ShoppingListRecyclerAdapter;
import com.sysmobil.shoppinglistapp.model.ShoppingList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by krzgac on 2016-06-02.
 */
public class MyShoppingListFragment extends Fragment implements View.OnClickListener {

    //private FloatingActionButton floatingActionButton;


    public MyShoppingListFragment() {
        Log.i("Fragment Check", "Fragment MyShoppingList Created");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.my_shopping_list_fragment, container, false);
        /*floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(this);*/;
        System.out.println("##### Set up database data");
        setUpRecyclerViewer(view, getDataFromDb());

        return view;
    }

    private List<ShoppingList> getDataFromDb() {
        List<ShoppingList> shoppingLists = new ArrayList<>();
        ShoppingList sp1 = new ShoppingList(1, "MyList1");
        System.out.println("##### " + sp1);
        shoppingLists.add(sp1);
        ShoppingList sp2 = new ShoppingList(1, "MyList2");
        System.out.println("##### " + sp2);
        shoppingLists.add(sp2);
        ShoppingList sp3 = new ShoppingList(1, "MyList3");
        System.out.println("##### " + sp3);
        shoppingLists.add(sp3);
        shoppingLists.add(new ShoppingList(2, "MyList4"));
        return shoppingLists;
    }

    private void setUpRecyclerViewer(View view, List<ShoppingList> shoppingLists) {

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.mslRecyclerView);
        ShoppingListRecyclerAdapter adapter = new ShoppingListRecyclerAdapter(shoppingLists, this.getContext());
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
     }




    @Override
    public void onClick(View v) {

        /*switch (v.getId()) {
            case R.id.fab:
                Log.i("FAB CLICKED", "FAB CLICKED");
        }*/

    }

}
