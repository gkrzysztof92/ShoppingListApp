package com.sysmobil.shoppinglistapp.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sysmobil.productlistapp.R;
import com.sysmobil.shoppinglistapp.adapters.PayProductViewRecyclerAdapter;
import com.sysmobil.shoppinglistapp.database.DatabaseHelper;
import com.sysmobil.shoppinglistapp.dialogs.ExtendedProductAddDialog;
import com.sysmobil.shoppinglistapp.dialogs.SimpleProductAddDialog;
import com.sysmobil.shoppinglistapp.fragments.MyShoppingListFragment;
import com.sysmobil.shoppinglistapp.listeners.ChangeProductListener;
import com.sysmobil.shoppinglistapp.model.Product;
import com.sysmobil.shoppinglistapp.model.ShoppingList;
import com.sysmobil.shoppinglistapp.service.ProductService;
import com.sysmobil.shoppinglistapp.service.ShoppingListService;
import com.sysmobil.shoppinglistapp.service.concrete.ProductServiceImpl;
import com.sysmobil.shoppinglistapp.service.concrete.ShoppingListServiceImpl;

import java.util.List;

/**
 * Created by krzgac on 2016-07-05.
 */
public class PayShoppingListActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;
    private ImageView submitEditImmage;
    private TextView shopingListName, shoppingListPrice, shoppingListCunter;
    private RecyclerView recyclerView;
    private PayProductViewRecyclerAdapter adapter;

    private ProductService productService;
    private ShoppingListService shoppingListService;

    private ShoppingList shoppingList;
    private int shoppingListId;
    private List<Product> productList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_shopping_list);

        toolbar = (Toolbar) findViewById(R.id.psl_toolbar);
        submitEditImmage = (ImageView) findViewById(R.id.psl_submitEditNote);
        shopingListName = (TextView) findViewById(R.id.psl_shoppingListName);
        shoppingListPrice = (TextView) findViewById(R.id.psl_shoppingListPrice);
        shoppingListCunter = (TextView) findViewById(R.id.psl_counter);

        submitEditImmage.setOnClickListener(this);

        productService = new ProductServiceImpl(DatabaseHelper.getInstance(getBaseContext()));
        shoppingListService = new ShoppingListServiceImpl(DatabaseHelper.getInstance(getBaseContext()));

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int reqtype = extras.getInt(MyShoppingListFragment.REQ_TYPE);
            if (reqtype == MyShoppingListFragment.REQ_PAY_SHOPPING_LIST) {
                shoppingListId = extras.getInt(MyShoppingListFragment.PASSING_ID);
            }
        }

        shoppingList = shoppingListService.getShoppingListById(shoppingListId);
        productList = productService.getAllProducts(shoppingListId);
        setUpRecyclerViewer(productList);
        SetListenersInAdapters();
        setDataForTextViews();
    }

    private void setUpRecyclerViewer(List<Product> shoppingLists) {

        recyclerView = (RecyclerView) findViewById(R.id.psl_recycler_wiever);
        adapter = new PayProductViewRecyclerAdapter(shoppingLists, this.getApplicationContext());
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    private void SetListenersInAdapters() {

        adapter.setEditProductListener(new ChangeProductListener() {
            @Override
            public void onReturnValue(Product product) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                ExtendedProductAddDialog dialog = new ExtendedProductAddDialog();
                System.out.println("$$$ Product id: " + product.getId());
                dialog.setProduct(product);
                dialog.setChangeProductListener(new ChangeProductListener() {
                    @Override
                    public void onReturnValue(Product product) {
                        Log.i(getLocalClassName(), "Update produktu o id: " + product.getId());
                        product.setIsBought(true);
                        productService.updateProduct(product);
                        System.out.println("update productu: " + product.getProductPrice() + " " + product.getProductShoppingListId());
                        productList = productService.getAllProducts(product.getProductShoppingListId());
                        adapter.refreshProductList(productList);
                        setDataForTextViews();
                    }
                });
                dialog.show(fragmentManager, "Fragment");

            }
        });

    }

    private void setDataForTextViews() {

        this.shopingListName.setText(shoppingList.getShoppingListName());
        this.shoppingListPrice.setText("Do zapłacenia: " + calculatePayment() + "zł");
        this.shoppingListCunter.setText("Produkty w koszyku: " + countPaidProducts() + "/" + productList.size() );

    }

    private int countPaidProducts() {

        int sum = 0;
        for (Product prod : productList) {

            if (prod.getProductPrice() > 0) {
                sum++;
            }
        }
        return  sum;
    }

    private float calculatePayment() {

        float sum = 0;
        for (Product prod : productList) {
                sum += prod.getProductPrice() * prod.getQuantity();
        }
        return  sum;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.psl_submitEditNote:
                shoppingList.setPayment(calculatePayment());
                shoppingList.setIsPaid(true);
                shoppingListService.updateShoppingList(shoppingList);
                setResult(RESULT_OK);
                finish();
        }
    }
}
