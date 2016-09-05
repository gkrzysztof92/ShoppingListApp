package com.sysmobil.shoppinglistapp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
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
import com.sysmobil.shoppinglistapp.adapters.ShoppingListRecyclerAdapter;
import com.sysmobil.shoppinglistapp.app.AddShoppingListActivity;
import com.sysmobil.shoppinglistapp.app.MainActivity;
import com.sysmobil.shoppinglistapp.app.PayShoppingListActivity;
import com.sysmobil.shoppinglistapp.database.DatabaseHelper;
import com.sysmobil.shoppinglistapp.listeners.ChangeShoppingListListener;
import com.sysmobil.shoppinglistapp.model.ShoppingList;
import com.sysmobil.shoppinglistapp.service.ProductService;
import com.sysmobil.shoppinglistapp.service.ShoppingListService;
import com.sysmobil.shoppinglistapp.service.concrete.ProductServiceImpl;
import com.sysmobil.shoppinglistapp.service.concrete.ShoppingListServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 *  My Shopping List Fragment Class. Is responsible for viewing shopping list created by user.
 */
public class MyShoppingListFragment extends Fragment implements View.OnClickListener {

    final static String TAG = "MyShoppingListFragment";
    public static final int REQ_SHOPPING_LIST_ADD = 102;
    public static final int REQ_SHOPPING_LIST_EDIT = 103;
    public static final int REQ_PAY_SHOPPING_LIST = 104;
    public static final String REQ_TYPE = "REQ_TYPE";
    public static final String PASSING_ID = "ID";

    private FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    ShoppingListRecyclerAdapter adapter;
    ShoppingListService shoppingListService;
    ProductService productService;

    public MyShoppingListFragment() {
        Log.i("Fragment Check", "Fragment MyShoppingList Created");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.my_shopping_list_fragment, container, false);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(this);
        setUpRecyclerViewer(view);
        setRecyclerViewerListeners();
        shoppingListService = new ShoppingListServiceImpl(DatabaseHelper.getInstance(getContext()));
        productService = new ProductServiceImpl(DatabaseHelper.getInstance(getContext()));
        return view;
    }

    /**
     *  Method set up Recycler Viewer for Shopping List Item.
     * @param view
     */
    private void setUpRecyclerViewer(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.mslRecyclerView);
        adapter = new ShoppingListRecyclerAdapter(this.getContext());
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
     }

    @Override
    public void onResume() {
        super.onResume();
        adapter.updateOnChangeShoppingList();
    }

    /**
     * Set up recycle viewer listeners. Is responsible for detecting interaction shopping
     * list items with user.
     */
    void setRecyclerViewerListeners() {

        adapter.setOnDeleteShoppingList(new ChangeShoppingListListener() {
            @Override
            public void onChangeShoppingListListener(ShoppingList shoppingList) {
                shoppingListService.deleteShoppingList(shoppingList);
                productService.deleteProductsByShoppingListId(shoppingList.getId());
            }
        });

        adapter.setOnEditShoppingList(new ChangeShoppingListListener() {
            @Override
            public void onChangeShoppingListListener(ShoppingList shoppingList) {
                Intent intent = new Intent(getContext(), AddShoppingListActivity.class);
                intent.putExtra(REQ_TYPE,  REQ_SHOPPING_LIST_EDIT);
                intent.putExtra(PASSING_ID, shoppingList.getId());
                startActivityForResult(intent, REQ_SHOPPING_LIST_EDIT);
            }
        });

        adapter.setOnPayShoppingList(new ChangeShoppingListListener() {
            @Override
            public void onChangeShoppingListListener(ShoppingList shoppingList) {
                Intent intent = new Intent(getContext(), PayShoppingListActivity.class);
                intent.putExtra(REQ_TYPE, REQ_PAY_SHOPPING_LIST);
                intent.putExtra(PASSING_ID, shoppingList.getId());
                startActivityForResult(intent, REQ_PAY_SHOPPING_LIST);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_SHOPPING_LIST_ADD) {
            if (resultCode == getActivity().RESULT_OK) {
                adapter.updateOnChangeShoppingList();
            } else if (resultCode == getActivity().RESULT_CANCELED) {

            }
        }

        if (requestCode == REQ_SHOPPING_LIST_EDIT) {
            if (resultCode == getActivity().RESULT_OK) {
                adapter.updateOnChangeShoppingList();
            } else if (resultCode == getActivity().RESULT_CANCELED) {

            }
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fab:
                Log.i("FAB CLICKED", "FAB CLICKED");
                Intent intent = new Intent(getActivity(), AddShoppingListActivity.class);
                intent.putExtra(REQ_TYPE, REQ_SHOPPING_LIST_ADD);
                startActivityForResult(intent, REQ_SHOPPING_LIST_ADD);
                break;
        }

    }

}
