package com.sysmobil.shoppinglistapp.database;

/**
 * Created by krzgac on 2016-05-23.
 */
public class ProductTable {

    public static final String TABLE_NAME = "PRODUCTS";

    public static final String ID_COLUMN = "_id";
    public static final String PRODUCT_NAME_COLUMN = "PRODUCT_NAME";
    public static final String SHOPPING_LIST_ID_COLUMN = "SHOPPING_LIST_ID";
    public static final String CREATION_DATE_COLUMN = "CREATION_DATE";
    public static final String PRODUCT_PRICE_COLUMN = "PRODUCT_PRICE";
    public static final String PRODUCT_BARCODE_COLUMN = "PRODUCT_BARCODE";
    public static final String IS_BOUGHT_COLUMN = "IS_BOUGHT";
    public static final String QUANTITY_COLUMN = "QUANTITY";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( "
            + ID_COLUMN + " integer primary key autoincrement, "
            + PRODUCT_NAME_COLUMN + " TEXT NOT NULL, "
            + SHOPPING_LIST_ID_COLUMN + " INTEGER REFERENCES "
            + ShoppingListTable.TABLE_NAME + "(" + ShoppingListTable.ID_COLUMN + "), "
            + CREATION_DATE_COLUMN + " TEXT, "
            + PRODUCT_PRICE_COLUMN + " NUMERIC, "
            + PRODUCT_BARCODE_COLUMN + " TEXT, "
            + IS_BOUGHT_COLUMN + " TEXT, "
            + QUANTITY_COLUMN + " INTEGER);";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS "
            + TABLE_NAME;

}
