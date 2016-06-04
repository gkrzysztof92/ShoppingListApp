package com.sysmobil.shoppinglistapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sysmobil.productlistapp.R;
import com.sysmobil.shoppinglistapp.model.ShoppingList;

import java.util.List;

/**
 * Created by krzgac on 2016-06-04.
 */
public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.MyViewHolder> {

    private List<ShoppingList> shoppingListData;
    private LayoutInflater layoutInflater;

    public ShoppingListAdapter(List<ShoppingList> shoppingListData, Context context) {
        this.shoppingListData = shoppingListData;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.my_shopping_list_fragment, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ShoppingList currentObj = shoppingListData.get(position);
        holder.setData(currentObj, position);

    }

    @Override
    public int getItemCount() {
        return shoppingListData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView creationDate;
        int position;
        ShoppingList current;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.shopping_list_name);
            creationDate = (TextView) itemView.findViewById(R.id.shopping_list_creation_date);
        }

        public void setData(ShoppingList currentObj, int position) {

            this.current = currentObj;
            this.position = position;
            this.title.setText(current.getShoppingListName());
            this.creationDate.setText(current.getCreationDate());
        }
    }

}
