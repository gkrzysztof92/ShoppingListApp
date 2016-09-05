package com.sysmobil.shoppinglistapp.database;

/**
 *  Class represents fields of Shopping List table, and contains sql commends to create and drop table.
 */
public class ShoppingListTable {


    public static final String TABLE_NAME = "SHOPPING_LIST";

    public static final String ID_COLUMN = "ID";
    public static final String LIST_NAME_COLUMN = "LIST_NAME";
    public static final String CREATION_DATE_COLUMN = "CREATION_DATE";
    public static final String PAYMENT_COLUMN = "PAYMENT";
    public static final String IS_PAID_COLUMN = "IS_PAID";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "( "
            + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + LIST_NAME_COLUMN + " TEXT NOT NULL, "
            + CREATION_DATE_COLUMN + " TEXT NOT NULL, "
            + PAYMENT_COLUMN + " NUMERIC, "
            + IS_PAID_COLUMN + " TEXT);";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS "
            + TABLE_NAME;

}
