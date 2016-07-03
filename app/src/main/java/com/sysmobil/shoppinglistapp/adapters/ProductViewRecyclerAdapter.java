package com.sysmobil.shoppinglistapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sysmobil.productlistapp.R;
import com.sysmobil.shoppinglistapp.model.Product;

import java.util.List;

/**
 * Created by krzgac on 2016-07-02.
 */
public class ProductViewRecyclerAdapter extends RecyclerView.Adapter<ProductViewRecyclerAdapter.MyViewHolder> {

    private List<Product> productList;
    private LayoutInflater layoutInflater;

    public ProductViewRecyclerAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.product_list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
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

    public void addItem(Product product) {
        int position = productList.size();
        productList.add(position, product);
        notifyItemInserted(position);
        notifyItemRangeChanged(position, productList.size());
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView productName, quantity;
        int position;
        Product current;

        public MyViewHolder(View itemView) {
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.pli_quantity);
            quantity = (TextView) itemView.findViewById(R.id.pli_name);
        }

        public void setData(Product currentObj, int position) {

            this.current = currentObj;
            this.position = position;
            productName.setText(current.getProductName());
            quantity.setText("Quantity: " + current.getQuantity());
        }

        public void setListeners() {


        }
    }
}
