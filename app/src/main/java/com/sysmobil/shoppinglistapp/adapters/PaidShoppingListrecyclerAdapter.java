package com.sysmobil.shoppinglistapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sysmobil.productlistapp.R;
import com.sysmobil.shoppinglistapp.database.DatabaseHelper;
import com.sysmobil.shoppinglistapp.listeners.ChangeShoppingListListener;
import com.sysmobil.shoppinglistapp.model.Product;
import com.sysmobil.shoppinglistapp.model.ShoppingList;
import com.sysmobil.shoppinglistapp.service.ShoppingListService;
import com.sysmobil.shoppinglistapp.service.concrete.ShoppingListServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dyju on 2016-07-05.
 */
public class PaidShoppingListrecyclerAdapter extends RecyclerView.Adapter<PaidShoppingListrecyclerAdapter.MyViewHolder> {

    private List<ShoppingList> shoppingListData;
    private LayoutInflater layoutInflater;
    private ChangeShoppingListListener onDeleteShoppingList;
    private ShoppingListService shoppingListService;

    public PaidShoppingListrecyclerAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        shoppingListService = new ShoppingListServiceImpl(DatabaseHelper.getInstance(context));
        this.shoppingListData = new ArrayList<>();
        this.shoppingListData = shoppingListService.getAllPaidShoppingList();
    }

    public void setOnDeleteShoppingList(ChangeShoppingListListener onDeleteProduct) {
        this.onDeleteShoppingList = onDeleteProduct;
    }

    public void updateOnChangeShoppingList() {

        List<ShoppingList> paidShoppingList = new ArrayList<>();
        for (ShoppingList sl : shoppingListService.getAllPaidShoppingList()) {
            if (sl.getPayment() > 0 ) {
                paidShoppingList.add(sl);
            }
        }
        this.shoppingListData = paidShoppingList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.paid_shopping_list_item, parent, false);
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


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title, price, date;
        ImageView deleteImage;
        int position;
        ShoppingList current;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.title = (TextView) itemView.findViewById(R.id.shopping_list_name);
            this.price = (TextView) itemView.findViewById(R.id.shopping_list_price);
            this.deleteImage = (ImageView) itemView.findViewById(R.id.sli_list_delete);
            this.date = (TextView) itemView.findViewById(R.id.shopping_list_creation_date);
        }

        public void setData(ShoppingList currentObj, int pos){
            current = currentObj;
            position = pos;
            title.setText(current.getShoppingListName());
            price.setText(String.valueOf(current.getPayment()));
            date.setText(current.getCreationDate());

        }
        public void setListeners() {
            deleteImage.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.sli_list_delete:
                    System.out.println("Delete " + position);
                    onDeleteShoppingList.onChangeShoppingListListener(current);
                    updateOnChangeShoppingList();
                    break;
                default:
                    System.out.println("Default " + position);
                    break;
            }


        }
    }
}
