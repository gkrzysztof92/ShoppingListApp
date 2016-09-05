package com.sysmobil.shoppinglistapp.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

/**
 * Simple implementation of TextWatcher Interface
 */
public class MyTextWatcher implements TextWatcher {

    private View view;

    public MyTextWatcher(View view) {
        this.view = view;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}