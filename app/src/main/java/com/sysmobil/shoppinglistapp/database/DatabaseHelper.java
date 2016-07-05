package com.sysmobil.shoppinglistapp.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper sDbHelper;

    private static final String DATABASE_NAME = "shopping_list.db";
    private static final int DB_VERSION = 1;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {

        if (sDbHelper == null) {
            sDbHelper = new DatabaseHelper(context.getApplicationContext());
        }

        return sDbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(ProductTable.CREATE_TABLE);
            db.execSQL(ShoppingListTable.CREATE_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {
            db.execSQL(ProductTable.DROP_TABLE);
            db.execSQL(ShoppingListTable.DROP_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        onCreate(db);
    }

    @Override
    public synchronized void close() {
        super.close();
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}
