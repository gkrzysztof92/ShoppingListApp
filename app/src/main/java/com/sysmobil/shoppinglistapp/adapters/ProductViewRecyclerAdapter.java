package com.sysmobil.shoppinglistapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sysmobil.productlistapp.R;
import com.sysmobil.shoppinglistapp.listeners.ChangeProductListener;
import com.sysmobil.shoppinglistapp.model.Product;

import java.util.List;

/**
 * Created by krzgac on 2016-07-02.
 */
public class ProductViewRecyclerAdapter extends RecyclerView.Adapter<ProductViewRecyclerAdapter.MyViewHolder> {

    private List<Product> productList;
    private LayoutInflater layoutInflater;

    private ChangeProductListener editProductListener, deleteProductListener;

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

    public void setDeleteProductListener(ChangeProductListener deleteProductListener) {
        this.deleteProductListener = deleteProductListener;
    }

    public void addItem(Product product) {
        int position = productList.size();
        productList.add(position, product);
        notifyItemInserted(position);
        notifyItemRangeChanged(position, productList.size());
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView productName, quantity;
        ImageView editProduct, deleteProduct;
        int position;
        Product current;

        public MyViewHolder(View itemView) {
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.pli_name);
            quantity = (TextView) itemView.findViewById(R.id.pli_quantity);
            editProduct = (ImageView) itemView.findViewById(R.id.pli_product_edit);
            deleteProduct = (ImageView) itemView.findViewById(R.id.pli_product_delete);
        }

        public void setData(Product currentObj, int position) {
            this.current = currentObj;
            this.position = position;
            productName.setText(current.getProductName());
            quantity.setText("Ilość: " + current.getQuantity());
        }

        public void setListeners() {
            editProduct.setOnClickListener(this);
            deleteProduct.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.pli_product_delete:
                    deleteProductListener.onReturnValue(current);
                    System.out.println("Delete product");
                    break;
                case R.id.pli_product_edit:
                    editProductListener.onReturnValue(current);
                    break;
                default:
                    break;
            }
        }
    }
}
