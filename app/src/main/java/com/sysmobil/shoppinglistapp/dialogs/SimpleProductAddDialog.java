package com.sysmobil.shoppinglistapp.dialogs;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sysmobil.productlistapp.R;
import com.sysmobil.shoppinglistapp.listeners.ProductDialogListener;
import com.sysmobil.shoppinglistapp.model.Product;
import com.sysmobil.shoppinglistapp.utils.MyTextWatcher;

/**
 * Created by krzgac on 2016-07-03.
 */
public class SimpleProductAddDialog extends DialogFragment implements View.OnClickListener {

    private EditText productNameInput, quantityInput;
    private TextInputLayout productNameLayoutinput, quantityLayoutInput;
    private Button okButton, dismissbutton;

    private ProductDialogListener productDialogListener;

    public SimpleProductAddDialog() {

    }

    public void setProductDialogListener(ProductDialogListener productDialogListener) {
        this.productDialogListener = productDialogListener;
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


        return view;
    }

    private boolean validateProductName() {
        if (productNameInput.getText().toString().trim().isEmpty()) {
            productNameLayoutinput.setError("Shopping list name is empty");
            requestFocus(productNameLayoutinput);
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
                if (validateProductName()) {
                    Product product = new Product();
                    product.setProductName(productNameInput.getText().toString());
                    product.setQuantity(Integer.parseInt(quantityInput.getText().toString()));
                    productDialogListener.onReturnValue(product);
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
