package com.sysmobil.shoppinglistapp.service.concrete;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sysmobil.shoppinglistapp.database.DatabaseHelper;
import com.sysmobil.shoppinglistapp.database.ShoppingListTable;
import com.sysmobil.shoppinglistapp.model.ShoppingList;
import com.sysmobil.shoppinglistapp.service.ShoppingListService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by krzgac on 2016-05-23.
 */
public class ShoppingListServiceImpl implements ShoppingListService {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;

    public ShoppingListServiceImpl(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
        this.db = databaseHelper.getWritableDatabase();
    }

    @Override
    public List<ShoppingList> getAllShoppingList() {

        List<ShoppingList> shoppingLists = new ArrayList<>();
        ShoppingList shoppingList;
        Cursor cursor;

        cursor = db.query(ShoppingListTable.TABLE_NAME, null, null, null, null, null, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                shoppingList = new ShoppingList();
                prepareSendObject(shoppingList, cursor);
                shoppingLists.add(shoppingList);
            } while (cursor.moveToNext());
                    }

        return shoppingLists;
    }

    @Override
    public ShoppingList getShoppingListById(int id) {

        ShoppingList shoppingList = new ShoppingList();
        Cursor cursor;

        cursor = db.query(ShoppingListTable.TABLE_NAME, null, ShoppingListTable.ID_COLUMN + "=" + id,
                null, null, null, null);
        cursor.moveToFirst();
        if(!cursor.isAfterLast()) {
            prepareSendObject(shoppingList, cursor);
        }

        return shoppingList;
    }

    @Override
    public void addShoppingList(int id, ShoppingList shoppingList) {
        ContentValues values = new ContentValues();
        values.put(ShoppingListTable.ID_COLUMN, id);
        values.put(ShoppingListTable.LIST_NAME_COLUMN, shoppingList.getShoppingListName());
        values.put(ShoppingListTable.CREATION_DATE_COLUMN, shoppingList.getCreationDate());
        if (shoppingList.isPaid()) {
            values.put(ShoppingListTable.IS_PAID_COLUMN, "Y");
        } else {
            values.put(ShoppingListTable.IS_PAID_COLUMN, "N");
        }
        db.insert(ShoppingListTable.TABLE_NAME, null, values);
    }

    @Override
    public void updateShoppingList(ShoppingList shoppingList) {

        ContentValues contentValues = prepareData(shoppingList);

        String whereClause = ShoppingListTable.ID_COLUMN + "=?";
        String whereArgs[] = new String[] {String.valueOf(shoppingList.getId())};

        db.update(ShoppingListTable.TABLE_NAME, contentValues, whereClause, whereArgs);

    }

    @Override
    public void deleteShoppingList(ShoppingList shoppingList) {

        db.delete(ShoppingListTable.TABLE_NAME, ShoppingListTable.ID_COLUMN + "="
                + shoppingList.getId(), null);

    }

    @Override
    public int getNextFreeId() {

        Cursor cursor = db.query(ShoppingListTable.TABLE_NAME, new String[] {"MAX(ID)"}, null, null, null, null, null);
        int id;
        if (cursor != null) {
            cursor.moveToFirst();
            id = cursor.getInt(0);
        } else {
            id = 0;
        }
        return id;
    }


    private ContentValues prepareData(ShoppingList shoppingList) {

        ContentValues values = new ContentValues();
        values.put(ShoppingListTable.LIST_NAME_COLUMN, shoppingList.getShoppingListName());
        values.put(ShoppingListTable.CREATION_DATE_COLUMN, shoppingList.getCreationDate());
        values.put(ShoppingListTable.IS_PAID_COLUMN, shoppingList.isPaid());
        values.put(ShoppingListTable.PAYMENT_COLUMN, shoppingList.getPayment());

        return values;
    }

    private void prepareSendObject(ShoppingList shoppingList, Cursor cursor) {

        shoppingList.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ShoppingListTable.ID_COLUMN)));
        shoppingList.setCreationDate(cursor.getString(cursor.getColumnIndexOrThrow(ShoppingListTable.CREATION_DATE_COLUMN)));
        if (cursor.getString(cursor.getColumnIndexOrThrow(ShoppingListTable.IS_PAID_COLUMN)) != null) {
            shoppingList.setIsPaid((cursor.getString(cursor.getColumnIndexOrThrow(ShoppingListTable.IS_PAID_COLUMN))).
                    equals("Y") ? true : false);
        }
        shoppingList.setShoppingListName(cursor.getString(cursor.getColumnIndexOrThrow(ShoppingListTable.LIST_NAME_COLUMN)));
        shoppingList.setPayment(cursor.getFloat(cursor.getColumnIndexOrThrow(ShoppingListTable.PAYMENT_COLUMN)));

    }


}
