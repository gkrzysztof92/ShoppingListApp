package com.sysmobil.shoppinglistapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sysmobil.productlistapp.R;
import com.sysmobil.shoppinglistapp.listeners.ChangeProductListener;
import com.sysmobil.shoppinglistapp.model.Product;

import java.util.List;

/**
 * Created by krzgac on 2016-07-02.
 */
public class PayProductViewRecyclerAdapter extends RecyclerView.Adapter<PayProductViewRecyclerAdapter.MyViewHolder> {

    private List<Product> productList;
    private LayoutInflater layoutInflater;

    private ChangeProductListener editProductListener;

    public PayProductViewRecyclerAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.paid_product_list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    public void refreshProductList(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Product currentObj = productList.get(position);
        holder.setData(currentObj, position);
        holder.setListeners();
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void removeItem(int position) {
        productList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, productList.size());
    }

    public void setEditProductListener(ChangeProductListener editProductListener) {
        this.editProductListener = editProductListener;
    }

    public void addItem(Product product) {
        int position = productList.size();
        productList.add(position, product);
        notifyItemInserted(position);
        notifyItemRangeChanged(position, productList.size());
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView productName, quantity, price;
        CheckBox checkBox;
        int position;
        Product current;

        public MyViewHolder(View itemView) {
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.ppli_name);
            quantity = (TextView) itemView.findViewById(R.id.ppli_quantity);
            price = (TextView) itemView.findViewById(R.id.ppli_price);
            checkBox = (CheckBox) itemView.findViewById(R.id.ppli_check);
        }

        public void setData(Product currentObj, int position) {
            this.current = currentObj;
            this.position = position;
            productName.setText(current.getProductName());
            quantity.setText("Ilość: " + current.getQuantity());
            price.setText("Cena: " + Float.toString(current.getProductPrice()) + "zł");
            if(current.getProductPrice() > 0) {
                checkBox.setChecked(true);
            }
        }

        public void setListeners() {
            checkBox.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            editProductListener.onReturnValue(current);
            checkBox.setChecked(true);
        }

    }
}
