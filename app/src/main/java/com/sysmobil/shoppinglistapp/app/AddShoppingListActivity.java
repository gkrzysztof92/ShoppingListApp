package com.sysmobil.shoppinglistapp.app;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.sysmobil.productlistapp.R;
import com.sysmobil.shoppinglistapp.adapters.ProductViewRecyclerAdapter;
import com.sysmobil.shoppinglistapp.database.DatabaseHelper;
import com.sysmobil.shoppinglistapp.dialogs.SimpleProductAddDialog;
import com.sysmobil.shoppinglistapp.fragments.MyShoppingListFragment;
import com.sysmobil.shoppinglistapp.listeners.ChangeProductListener;
import com.sysmobil.shoppinglistapp.model.Product;
import com.sysmobil.shoppinglistapp.model.ShoppingList;
import com.sysmobil.shoppinglistapp.service.ProductService;
import com.sysmobil.shoppinglistapp.service.ShoppingListService;
import com.sysmobil.shoppinglistapp.service.concrete.ProductServiceImpl;
import com.sysmobil.shoppinglistapp.service.concrete.ShoppingListServiceImpl;
import com.sysmobil.shoppinglistapp.utils.MyTextWatcher;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import at.markushi.ui.CircleButton;

/**
 * Created by krzgac on 2016-07-02.
 */
public class AddShoppingListActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int REQ_CODE_SPEECH_INPUT = 100;
    public static final int REQ_CODE_EDIT_PRODUCT = 101;
    public static final String REQ_TYPE = "REQ_TYPE";
    public static final String PASSING_ID = "ID";

    private ViewPager viewPager;
    private Toolbar toolbar;
    private EditText inputShoppingListName;
    private TextInputLayout inputLayoutShoppingListName;
    private CircleButton microfoneButton;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private ProductViewRecyclerAdapter adapter;
    private ImageView sebmitEdditImageView;

    private List<Product> productList;
    private SimpleProductAddDialog simpleProductAddDialog;

    private ShoppingListService shoppingListService;
    private ProductService productService;

    private int reqType;
    private ShoppingList shoppingList;
    private int newShopingListId;


    private int productShopingId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_shopping_list);

        toolbar = (Toolbar) findViewById(R.id.asl_toolbar);
        toolbar.setTitle("Dodaj listę zakupów");

        inputShoppingListName = (EditText) findViewById(R.id.asl_input_name);
        inputLayoutShoppingListName = (TextInputLayout) findViewById(R.id.asl_input_layout_name);
        fab = (FloatingActionButton) findViewById(R.id.asl_fab);
        fab.setOnClickListener(this);

        inputShoppingListName.addTextChangedListener(new MyTextWatcher(inputShoppingListName));

        microfoneButton = (CircleButton) findViewById(R.id.asl_mic);
        sebmitEdditImageView = (ImageView) findViewById(R.id.asl_submitEditNote);

        microfoneButton.setOnClickListener(this);
        sebmitEdditImageView.setOnClickListener(this);

        shoppingListService = new ShoppingListServiceImpl(DatabaseHelper.getInstance(getBaseContext()));
        productService = new ProductServiceImpl(DatabaseHelper.getInstance(getBaseContext()));

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.reqType = extras.getInt(MyShoppingListFragment.REQ_TYPE);
            if (this.reqType == MyShoppingListFragment.REQ_SHOPPING_LIST_EDIT) {
                int id = extras.getInt(MyShoppingListFragment.PASSING_ID);
                shoppingList = shoppingListService.getShoppingListById(id);
                if (shoppingList != null) {
                    inputShoppingListName.setText(shoppingList.getShoppingListName());
                }
            }
        }

        setUpRecyclerViewer(getProductsFromDatbase());
        setProductShopingId();
    }

    public void setProductShopingId() {

        if (reqType == MyShoppingListFragment.REQ_SHOPPING_LIST_ADD) {
            this.productShopingId = shoppingListService.getNextFreeId() + 1;
        } else {
            this.productShopingId = this.shoppingList.getId();
        }
    }

    private boolean validateShoppingListName() {
        if (inputShoppingListName.getText().toString().trim().isEmpty()) {
            inputLayoutShoppingListName.setError("Proszę podać nazwe listy zakupów");
            requestFocus(inputShoppingListName);
            return false;
        } else {
            inputLayoutShoppingListName.setErrorEnabled(false);
        }
        return true;
    }

    private List<Product> getProductsFromDatbase() {

        List<Product> productList;

        if (reqType == MyShoppingListFragment.REQ_SHOPPING_LIST_EDIT) {
            productList = productService.getAllProducts(shoppingList.getId());
        } else {
            productList = new ArrayList<>();
        }

        return productList;
    }

    public void addProductToDb(Product product) {

        product.setCreationDate(getDate());
        product.setIsBought(false);
        Log.i(getLocalClassName(), "Dodawanie produktu do bazy dl list o id: " + productShopingId);
        productService.addProduct(this.productShopingId, product);
        adapter.refreshProductList(productService.getAllProducts(this.productShopingId));
    }

    private void setUpRecyclerViewer(List<Product> shoppingLists) {

        recyclerView = (RecyclerView) findViewById(R.id.asl_recycler_wiever);
        adapter = new ProductViewRecyclerAdapter(shoppingLists, this.getApplicationContext());
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        setProductViewRecycleListeners();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.asl_mic:
                System.out.println("Voice input");
                promptSpeechinput();
                break;
            case R.id.asl_fab:

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    SimpleProductAddDialog dialog = new SimpleProductAddDialog();
                    dialog.setChangeProductListener(new ChangeProductListener() {
                    @Override
                    public void onReturnValue(Product product) {
                        int id = productService.getNextFreeId() + 1;
                        product.setId(id);
                        addProductToDb(product);
                    }
                });
                dialog.show(fragmentManager, "Fragment");
                break;
            case R.id.asl_submitEditNote:
                if (validateShoppingListName()) {
                    if (reqType == MyShoppingListFragment.REQ_SHOPPING_LIST_ADD) {
                        saveShoppingListToDb();
                    } else if (reqType == MyShoppingListFragment.REQ_SHOPPING_LIST_EDIT) {
                        updateShoppingList();
                    }
                    setResult(RESULT_OK);
                    finish();
                }
                break;
        }

    }

    private void setProductViewRecycleListeners() {

        adapter.setDeleteProductListener(new ChangeProductListener() {
            @Override
            public void onReturnValue(Product product) {
                Log.i(getLocalClassName(), "Usuwanie produktu o id: " + product.getId());
                productService.deleteProduct(product);
                adapter.refreshProductList(productService.getAllProducts(productShopingId));
            }
        });

        adapter.setEditProductListener(new ChangeProductListener() {
            @Override
            public void onReturnValue(Product product) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                SimpleProductAddDialog dialog = new SimpleProductAddDialog();
                dialog.setProduct(product);
                dialog.setReqType(REQ_CODE_EDIT_PRODUCT);
                dialog.setChangeProductListener(new ChangeProductListener() {
                    @Override
                    public void onReturnValue(Product product) {
                        Log.i(getLocalClassName(), "Update produktu o id: " + product.getId());
                        product.setId(productShopingId);
                        productService.updateProduct(product);
                        System.out.println("update productu: " + product.getProductName() + " " + productShopingId);
                        adapter.refreshProductList(productService.getAllProducts(productShopingId));
                    }
                });
                dialog.show(fragmentManager, "Fragment");
            }
        });
    }

    private void saveShoppingListToDb() {
        this.newShopingListId  = shoppingListService.getNextFreeId() + 1;
        System.out.println(newShopingListId);
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setShoppingListName(inputShoppingListName.getText().toString());
        shoppingList.setIsPaid(false);
        shoppingList.setCreationDate(getDate());
        shoppingListService.addShoppingList(newShopingListId, shoppingList);
    }

    private void updateShoppingList() {
        this.shoppingList.setShoppingListName(inputShoppingListName.getText().toString());
        shoppingListService.updateShoppingList(this.shoppingList);
    }

    private void promptSpeechinput() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Wprowadź głosowo nazwę listy zakupów");

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(),"Wprowadzanie głosowe nie obsługiwane!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    inputShoppingListName.setText(result.get(0));
                }
        }
    }

    private String getDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(calendar.getTime());
    }

}
