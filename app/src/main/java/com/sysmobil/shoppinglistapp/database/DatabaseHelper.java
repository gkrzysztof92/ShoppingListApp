package com.sysmobil.shoppinglistapp.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Database manager class. Is responsible for managing the database.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    /**
     * Singleton instance of DatabaseHelper class
     */
    private static DatabaseHelper sDbHelper;

    private static final String DATABASE_NAME = "shopping_list.db";
    private static final int DB_VERSION = 1;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    /**
     *  Returns a singleton instance of DatabaseHelper class.
     * @param context - application context
     * @return instance of DatabaseHelper
     */
    public static synchronized DatabaseHelper getInstance(Context context) {

        if (sDbHelper == null) {
            sDbHelper = new DatabaseHelper(context.getApplicationContext());
        }

        return sDbHelper;
    }

    /**
     * Create sqlite database objects
     * @param db instance of SQLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(ProductTable.CREATE_TABLE);
            db.execSQL(ShoppingListTable.CREATE_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Update sqlite database objects
     * @param db instance of SQLiteDatabase
     * @param oldVersion previous version of database
     * @param newVersion new version of database
     */
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

    /**
     * Close connection with database
     */
    @Override
    public synchronized void close() {
        super.close();
    }

    /**
     * Open connection with database
     * @param db instance of SQLiteDatabase
     */
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}
