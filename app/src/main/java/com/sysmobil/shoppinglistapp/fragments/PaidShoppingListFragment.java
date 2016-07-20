package com.sysmobil.shoppinglistapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sysmobil.productlistapp.R;
import com.sysmobil.shoppinglistapp.adapters.PaidShoppingListrecyclerAdapter;
import com.sysmobil.shoppinglistapp.adapters.ShoppingListRecyclerAdapter;
import com.sysmobil.shoppinglistapp.database.DatabaseHelper;
import com.sysmobil.shoppinglistapp.listeners.ChangeShoppingListListener;
import com.sysmobil.shoppinglistapp.model.ShoppingList;
import com.sysmobil.shoppinglistapp.service.ShoppingListService;
import com.sysmobil.shoppinglistapp.service.concrete.ShoppingListServiceImpl;

public class PaidShoppingListFragment extends Fragment{


    RecyclerView recyclerView;
    PaidShoppingListrecyclerAdapter adapter;
    ShoppingListService shoppingListService;

    public PaidShoppingListFragment() {
        Log.i("Fragment Check", "Fragment MyShoppingList Created");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.paid_shopping_list_fragment, container, false);
        setUpRecyclerViewer(view);
        setRecyclerViewerListeners();
        shoppingListService = new ShoppingListServiceImpl(DatabaseHelper.getInstance(getContext()));
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        adapter.updateOnChangeShoppingList();
    }

    private void setUpRecyclerViewer(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.mslRecyclerView1);
        adapter = new PaidShoppingListrecyclerAdapter(this.getContext());
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

        void setRecyclerViewerListeners(){
            adapter.setOnDeleteShoppingList(new ChangeShoppingListListener() {
                @Override
                public void onChangeShoppingListListener(ShoppingList shoppingList) {
                    shoppingListService.deleteShoppingList(shoppingList);
                }
            });



    }



}
