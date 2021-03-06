package com.sysmobil.shoppinglistapp.service.concrete;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sysmobil.shoppinglistapp.database.DatabaseHelper;
import com.sysmobil.shoppinglistapp.database.ProductTable;
import com.sysmobil.shoppinglistapp.database.ShoppingListTable;
import com.sysmobil.shoppinglistapp.model.Product;
import com.sysmobil.shoppinglistapp.service.ProductService;

import java.util.ArrayList;
import java.util.List;

/**
 * Class implements ProductService interface
 */
public class ProductServiceImpl implements ProductService {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;

    public ProductServiceImpl(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
        db = databaseHelper.getWritableDatabase();
    }

    @Override
    public List<Product> getAllProducts(int id) {

        List<Product> products = new ArrayList<>();
        Product product;
        Cursor cursor;

        cursor = db.query(ProductTable.TABLE_NAME, null, ProductTable.SHOPPING_LIST_ID_COLUMN + "=" + id,
                null, null, null, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                product = new Product();
                prepareSendObject(product, cursor);
                products.add(product);
            } while (cursor.moveToNext());
        }

        return products;
    }

    @Override
    public Product getProductById(int id) {

        Product product = new Product();
        Cursor cursor = db.query(ProductTable.TABLE_NAME, null,
                ProductTable.ID_COLUMN + "=" + id, null, null, null, null);
        cursor.moveToFirst();
        if(cursor.isAfterLast()) {
            prepareSendObject(product, cursor);
        }
        return product;
    }

    @Override
    public void addProduct(int shoppingListId, Product product) {

        ContentValues values = new ContentValues();
        values.put(ProductTable.ID_COLUMN, product.getId());
        values.put(ProductTable.SHOPPING_LIST_ID_COLUMN, shoppingListId);
        values.put(ProductTable.PRODUCT_NAME_COLUMN, product.getProductName());
        if (product.isBought()) {
            values.put(ProductTable.IS_BOUGHT_COLUMN, "Y");
        } else {
            values.put(ProductTable.IS_BOUGHT_COLUMN, "N");
        }
        values.put(ProductTable.CREATION_DATE_COLUMN, product.getCreationDate());
        values.put(ProductTable.QUANTITY_COLUMN, product.getQuantity());
        values.put(ProductTable.PRODUCT_PRICE_COLUMN, 0.00);

        db.insert(ProductTable.TABLE_NAME, null, values);

    }

    @Override
    public void updateProduct(Product product) {

        ContentValues values = prepareData(product);

        String whereClause = ProductTable.ID_COLUMN + "=?";
        String whereArgs[] = new String[] {String.valueOf(product.getId())};

        db.update(ProductTable.TABLE_NAME, values, whereClause, whereArgs);

    }

    @Override
    public int getNextFreeId() {

        Cursor cursor = db.query(ProductTable.TABLE_NAME, new String[] {"MAX(_id)"}, null, null, null, null, null);
        int id;
        if (cursor != null) {
            cursor.moveToFirst();
            id = cursor.getInt(0);
        } else {
            id = 0;
        }
        return id;
    }

    @Override
    public void deleteProduct(Product product) {

        db.delete(ProductTable.TABLE_NAME, ProductTable.ID_COLUMN + "=" + product.getId(), null);

    }

    @Override
    public void deleteProductsByShoppingListId(int id) {

        db.delete(ProductTable.TABLE_NAME, ProductTable.SHOPPING_LIST_ID_COLUMN + "=" + id, null);

    }

    /**
     * Map database data to product class specified in param.
     * @param product instance of product class
     * @param cursor database cursor
     */
    private void prepareSendObject(Product product, Cursor cursor) {

        product.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ProductTable.ID_COLUMN)));
        product.setCreationDate(cursor.getString(cursor.getColumnIndexOrThrow(ProductTable.CREATION_DATE_COLUMN)));
        product.setProductBarcode(cursor.getString(cursor.getColumnIndexOrThrow(ProductTable.PRODUCT_BARCODE_COLUMN)));
        product.setProductName(cursor.getString(cursor.getColumnIndexOrThrow(ProductTable.PRODUCT_NAME_COLUMN)));
        product.setProductPrice(cursor.getFloat(cursor.getColumnIndexOrThrow(ProductTable.PRODUCT_PRICE_COLUMN)));
        product.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow(ProductTable.QUANTITY_COLUMN)));
        product.setIsBought((cursor.getString(cursor.getColumnIndexOrThrow(ProductTable.IS_BOUGHT_COLUMN))).equals("Y") ? true : false);
        product.setProductShoppingListId(cursor.getInt(cursor.getColumnIndexOrThrow(ProductTable.SHOPPING_LIST_ID_COLUMN)));
    }

    /**
     * Prepares data to save in database.
     * @param product
     * @return
     */
    private ContentValues prepareData(Product product) {

        ContentValues values = new ContentValues();
        values.put(ProductTable.PRODUCT_NAME_COLUMN, product.getProductName());
        values.put(ProductTable.CREATION_DATE_COLUMN, product.getCreationDate());
        values.put(ProductTable.PRODUCT_BARCODE_COLUMN, product.getProductBarcode());
        values.put(ProductTable.PRODUCT_PRICE_COLUMN, product.getProductPrice());
        values.put(ProductTable.IS_BOUGHT_COLUMN, product.isBought());
        values.put(ProductTable.QUANTITY_COLUMN, product.getQuantity());
        values.put(ProductTable.SHOPPING_LIST_ID_COLUMN, product.getProductShoppingListId());
        return values;
    }

}
