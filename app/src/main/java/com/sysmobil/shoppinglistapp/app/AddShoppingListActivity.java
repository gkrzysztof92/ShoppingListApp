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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.sysmobil.productlistapp.R;
import com.sysmobil.shoppinglistapp.adapters.ProductViewRecyclerAdapter;
import com.sysmobil.shoppinglistapp.dialogs.SimpleProductAddDialog;
import com.sysmobil.shoppinglistapp.listeners.ProductDialogListener;
import com.sysmobil.shoppinglistapp.model.Product;
import com.sysmobil.shoppinglistapp.model.ShoppingList;
import com.sysmobil.shoppinglistapp.utils.MyTextWatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import at.markushi.ui.CircleButton;

/**
 * Created by krzgac on 2016-07-02.
 */
public class AddShoppingListActivity extends AppCompatActivity implements View.OnClickListener {

    private final int REQ_CODE_SPEECH_INPUT = 100;
    private final int REQ_EDIT_SHOPPING_LIST = 101;

    private ViewPager viewPager;
    private Toolbar toolbar;
    private EditText inputShoppingListName;
    private TextInputLayout inputLayoutShoppingListName;
    private CircleButton microfoneButton;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private ProductViewRecyclerAdapter adapter;

    private List<Product> productList;
    private ShoppingList shoppingList;
    private SimpleProductAddDialog simpleProductAddDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_shopping_list);

        toolbar = (Toolbar) findViewById(R.id.asl_toolbar);
        setSupportActionBar(toolbar);

        inputShoppingListName = (EditText) findViewById(R.id.asl_input_name);
        inputLayoutShoppingListName = (TextInputLayout) findViewById(R.id.asl_input_layout_name);
        fab = (FloatingActionButton) findViewById(R.id.asl_fab);
        fab.setOnClickListener(this);

        inputShoppingListName.addTextChangedListener(new MyTextWatcher(inputShoppingListName));

        microfoneButton = (CircleButton) findViewById(R.id.asl_mic);
        microfoneButton.setOnClickListener(this);

        setUpRecyclerViewer(getProductsFromDatbase());

    }

    private boolean validateShoppingListName() {
        if (inputShoppingListName.getText().toString().trim().isEmpty()) {
            inputLayoutShoppingListName.setError("Shopping list name is empty");
            requestFocus(inputShoppingListName);
            return false;
        } else {
            inputLayoutShoppingListName.setErrorEnabled(false);
        }
        return true;
    }

    private List<Product> getProductsFromDatbase() {
        productList = new ArrayList<>();
//        Product prduct = new Product(1, "Product 1", 245f, 25, "456825");
//        productList.add(prduct);
//        productList.add(prduct);
//        productList.add(prduct);
//        productList.add(prduct);
//        productList.add(prduct);
//        productList.add(prduct);
//        productList.add(prduct);
//        productList.add(prduct);
//        productList.add(prduct);
//        productList.add(prduct);
//        productList.add(prduct);
//        productList.add(prduct);
//        productList.add(prduct);

        return productList;
    }

    public void addProductToList(Product product) {

        productList.add(product);
        //TO DO save in db

    }

    private void setUpRecyclerViewer(List<Product> shoppingLists) {

        recyclerView = (RecyclerView) findViewById(R.id.asl_recycler_wiever);
        adapter = new ProductViewRecyclerAdapter(shoppingLists, this.getApplicationContext());
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
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
                dialog.setProductDialogListener(new ProductDialogListener() {
                    @Override
                    public void onReturnValue(Product product) {
                        saveProduct(product);
                    }
                });
                dialog.show(fragmentManager, "Fragment");
        }

    }

    private void saveProduct(Product product) {
        adapter.addItem(product);
    }

    private void promptSpeechinput() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "test");

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(),"Seach input not suported",
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


}
