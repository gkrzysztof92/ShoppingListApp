package com.sysmobil.shoppinglistapp.dialogs;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sysmobil.productlistapp.R;
import com.sysmobil.shoppinglistapp.app.AddShoppingListActivity;
import com.sysmobil.shoppinglistapp.listeners.ChangeProductListener;
import com.sysmobil.shoppinglistapp.model.Product;
import com.sysmobil.shoppinglistapp.utils.MyTextWatcher;

import java.util.ArrayList;
import java.util.Locale;

import at.markushi.ui.CircleButton;

/**
 * Class represents extended product dialog window. Is responsible for managing products.
 */
public class ExtendedProductAddDialog extends DialogFragment implements View.OnClickListener {

    public static final int REQ_CODE_SPEECH_INPUT = 100;
    public static final int RESULT_OK = -1;

    private EditText productNameInput, quantityInput, priceInput;
    private TextInputLayout productNameLayoutinput, quantityLayoutInput, priceLayoutInput;
    private Button okButton, dismissbutton;

    private Product product;
    private int reqType;
    private ChangeProductListener changeProductListener;
    private CircleButton microphoneButton1;

    public ExtendedProductAddDialog() {

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

        View view = inflater.inflate(R.layout.extended_add_product, container, false);

        getDialog().getWindow().setTitle("Add product");

        microphoneButton1 = (CircleButton) view.findViewById(R.id.eap_mic);
        microphoneButton1.setOnClickListener(this);
        productNameInput = (EditText) view.findViewById(R.id.eap_product_name);
        quantityInput = (EditText) view.findViewById(R.id.eap_input_quantity);
        priceInput = (EditText) view.findViewById(R.id.eap_input_price) ;
        productNameInput.addTextChangedListener(new MyTextWatcher(productNameInput));

        priceLayoutInput = (TextInputLayout) view.findViewById(R.id.eap_layout_input_price);
        productNameLayoutinput = (TextInputLayout) view.findViewById(R.id.eap_input_layout_product_name);
        quantityLayoutInput = (TextInputLayout) view.findViewById(R.id.eap_input_layout_quantity);

        okButton = (Button) view.findViewById(R.id.eap_add_product);
        dismissbutton = (Button) view.findViewById(R.id.eap_dismiss);

        okButton.setOnClickListener(this);
        dismissbutton.setOnClickListener(this);

        productNameInput.setText(product.getProductName());
        quantityInput.setText(Integer.toString(product.getQuantity()));

        if(product.getProductPrice() > 0) {
            priceInput.setText(Float.toString(product.getProductPrice()));
        }

        return view;
    }

    /**
     * Validate data of products entered by the user.
     * @return if data is valid returns true, else returns false
     */
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

    /**
     *  Validate quantity of products entered by the user.
     * @return true if quantity is number and is greater than 0, else returns false
     */
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

    /**
     * Focus the text imput.
     * @param view
     */
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.eap_add_product:
                if (validateProductName() && validateQuantity()) {
                    product.setProductName(productNameInput.getText().toString());
                    product.setQuantity(Integer.parseInt(quantityInput.getText().toString()));
                    product.setProductPrice(Float.parseFloat(priceInput.getText().toString()));
                    changeProductListener.onReturnValue(product);
                    getDialog().dismiss();
                }
                break;
            case R.id.eap_dismiss:
                getDialog().dismiss();

            case R.id.eap_mic:
                System.out.println("Voice input");
                promptSpeechinput();
                break;
            default:
                break;
        }
    }

    /**
     * Open dialog for speech recognition, and capture the speech to text
     */
    private void promptSpeechinput() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Wprowadź głosowo nazwę listy zakupów");

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getContext(),"Wprowadzanie głosowe nie obsługiwane!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    productNameInput.setText(result.get(0));
                }
        }
    }

}
