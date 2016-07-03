package com.sysmobil.shoppinglistapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sysmobil.productlistapp.R;
import com.sysmobil.shoppinglistapp.model.ShoppingList;

import java.util.List;

/**
 * Created by krzgac on 2016-06-04.
 */
public class ShoppingListRecyclerAdapter extends RecyclerView.Adapter<ShoppingListRecyclerAdapter.MyViewHolder> {

    private List<ShoppingList> shoppingListData;
    private LayoutInflater layoutInflater;

    public ShoppingListRecyclerAdapter(List<ShoppingList> shoppingListData, Context context) {
        this.shoppingListData = shoppingListData;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.shopping_list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ShoppingList currentObj = shoppingListData.get(position);
        holder.setData(currentObj, position);
        holder.setListeners();
    }

    @Override
    public int getItemCount() {
        return shoppingListData.size();
    }

    public void removeItem(int position) {
        shoppingListData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, shoppingListData.size());
    }

    public void addItem(int position, ShoppingList shoppingList) {
        shoppingListData.add(position, shoppingList);
        notifyItemInserted(position);
        notifyItemRangeChanged(position, shoppingListData.size());
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title, creationDate;
        ImageView editImage, deleteImage;
        int position;
        ShoppingList current;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.title = (TextView) itemView.findViewById(R.id.shopping_list_name);
            this.creationDate = (TextView) itemView.findViewById(R.id.shopping_list_creation_date);
            this.editImage = (ImageView) itemView.findViewById(R.id.sli_list_edit);
            this.deleteImage = (ImageView) itemView.findViewById(R.id.sli_list_delete);
        }

        public void setData(ShoppingList currentObj, int pos) {

            current = currentObj;
            position = pos;
            title.setText(current.getShoppingListName());
            creationDate.setText(current.getCreationDate());
        }

        public void setListeners() {
            editImage.setOnClickListener(this);
            deleteImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.sli_list_delete:
                    System.out.println("Delete " + position);
                    removeItem(position);
                    break;
                case R.id.sli_list_edit:
                    System.out.println("Edit " + position);
                    Intent intent = new Intent();
                    break;
                default:
                    System.out.println("Default " + position);
                    break;
            }


        }
    }

}
