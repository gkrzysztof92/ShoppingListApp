package com.sysmobil.shoppinglistapp.dialogs;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.sysmobil.productlistapp.R;
import com.sysmobil.shoppinglistapp.app.AddShoppingListActivity;
import com.sysmobil.shoppinglistapp.listeners.ChangeProductListener;
import com.sysmobil.shoppinglistapp.model.Product;
import com.sysmobil.shoppinglistapp.utils.MyTextWatcher;

/**
 * Created by krzgac on 2016-07-03.
 */
public class SimpleProductAddDialog extends DialogFragment implements View.OnClickListener {

    private EditText productNameInput, quantityInput;
    private TextInputLayout productNameLayoutinput, quantityLayoutInput;
    private Button okButton, dismissbutton;

    private Product product;
    private int reqType;
    private ChangeProductListener changeProductListener;

    public SimpleProductAddDialog() {

    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setReqType(int reqType) {
        this.reqType = reqType;
    }

    public void setChangeProductListener(ChangeProductListener changeProductListener) {
        this.changeProductListener = changeProductListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.simple_add_product, container, false);

        getDialog().getWindow().setTitle("Add product");

        productNameInput = (EditText) view.findViewById(R.id.sap__product_name);
        quantityInput = (EditText) view.findViewById(R.id.sap_input_quantity);

        productNameInput.addTextChangedListener(new MyTextWatcher(productNameInput));

        productNameLayoutinput = (TextInputLayout) view.findViewById(R.id.sap_input_layout_product_name);
        quantityLayoutInput = (TextInputLayout) view.findViewById(R.id.sap_input_layout_quantity);

        okButton = (Button) view.findViewById(R.id.sap_add_product);
        dismissbutton = (Button) view.findViewById(R.id.sap_dismiss);

        okButton.setOnClickListener(this);
        dismissbutton.setOnClickListener(this);

        if(reqType == AddShoppingListActivity.REQ_CODE_EDIT_PRODUCT) {
            productNameInput.setText(product.getProductName());
            quantityInput.setText(Integer.toString(product.getQuantity()));
        }

        return view;
    }

    private boolean validateProductName() {
        if (productNameInput.getText().toString().trim().isEmpty()) {
            productNameLayoutinput.setError("Nazwa produktu mui być podana!");
            requestFocus(productNameLayoutinput);
            return false;
        } else {
            productNameLayoutinput.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateQuantity() {
        int quantity;
        try {
            quantity = Integer.parseInt(quantityInput.getText().toString().trim());
        } catch (Exception e) {
            quantity = 0;
            quantityLayoutInput.setError("Wprowadzona wartość nie jest liczbą!");
            requestFocus(quantityLayoutInput);
        }
        if (quantityInput.getText().toString().trim().isEmpty()) {
            quantityLayoutInput.setError("Ilość musi być podana!");
            requestFocus(quantityLayoutInput);
            return false;
        } else if (quantity < 1) {
            quantityLayoutInput.setError("Wprowadzona wartość musi być większa od 0!");
            requestFocus(quantityLayoutInput);
            return false;
        } else {
            productNameLayoutinput.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sap_add_product:
                if (validateProductName() && validateQuantity()) {
                    Product product = new Product();
                    product.setProductName(productNameInput.getText().toString());
                    product.setQuantity(Integer.parseInt(quantityInput.getText().toString()));
                    changeProductListener.onReturnValue(product);
                    getDialog().dismiss();
                }
                break;
            case R.id.sap_dismiss:
                getDialog().dismiss();
            default:
                break;
        }
    }
}
