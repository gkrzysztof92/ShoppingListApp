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
import com.sysmobil.shoppinglistapp.model.ShoppingList;
import com.sysmobil.shoppinglistapp.service.ShoppingListService;
import com.sysmobil.shoppinglistapp.service.concrete.ShoppingListServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by krzgac on 2016-06-04.
 */
public class ShoppingListRecyclerAdapter extends RecyclerView.Adapter<ShoppingListRecyclerAdapter.MyViewHolder> {

    private List<ShoppingList> shoppingListData;
    private LayoutInflater layoutInflater;
    private ChangeShoppingListListener onDeleteShoppingList, onEditShoppingList, onPayShoppingList;
    private ShoppingListService shoppingListService;


    public ShoppingListRecyclerAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        shoppingListService = new ShoppingListServiceImpl(DatabaseHelper.getInstance(context));
        this.shoppingListData = new ArrayList<>();
        this.shoppingListData = getNotPaidShoppingList(shoppingListService.getAllShoppingList());
    }

    public void setOnDeleteShoppingList(ChangeShoppingListListener onDeleteProduct) {
        this.onDeleteShoppingList = onDeleteProduct;
    }

    public void setOnEditShoppingList(ChangeShoppingListListener onEditProduct) {
        this.onEditShoppingList = onEditProduct;
    }

    public void setOnPayShoppingList(ChangeShoppingListListener onPayShoppingList) {
        this.onPayShoppingList = onPayShoppingList;
    }

    public void updateOnChangeShoppingList() {
        this.shoppingListData = getNotPaidShoppingList(shoppingListService.getAllShoppingList());
        notifyDataSetChanged();
    }

    public List<ShoppingList> getNotPaidShoppingList(List<ShoppingList> shoppingLists) {

        List<ShoppingList> notPaidShoppingList = new ArrayList<>();
        for (ShoppingList sl : shoppingListService.getAllPaidShoppingList()) {
            if (sl.getPayment() == 0 ) {
                notPaidShoppingList.add(sl);
            }
        }

        return notPaidShoppingList;
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


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title, creationDate;
        ImageView editImage, deleteImage, payImage;
        int position;
        ShoppingList current;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.title = (TextView) itemView.findViewById(R.id.shopping_list_name);
            this.creationDate = (TextView) itemView.findViewById(R.id.shopping_list_creation_date);
            this.editImage = (ImageView) itemView.findViewById(R.id.sli_list_edit);
            this.deleteImage = (ImageView) itemView.findViewById(R.id.sli_list_delete);
            this.payImage = (ImageView) itemView.findViewById(R.id.sli_pay);
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
            payImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.sli_list_delete:
                    System.out.println("Delete " + position);
                    onDeleteShoppingList.onChangeShoppingListListener(current);
                    updateOnChangeShoppingList();
                    break;
                case R.id.sli_list_edit:
                    System.out.println("Edit " + position);
                    onEditShoppingList.onChangeShoppingListListener(current);
                    break;
                case R.id.sli_pay:
                    onPayShoppingList.onChangeShoppingListListener(current);
                default:
                    System.out.println("Default " + position);
                    break;
            }


        }
    }

}
