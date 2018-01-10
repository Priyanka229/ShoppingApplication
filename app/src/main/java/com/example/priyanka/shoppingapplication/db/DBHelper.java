package com.example.priyanka.shoppingapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.priyanka.shoppingapplication.models.OrderedItem;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ORDERS_INFO";

    public static final String ORDER_TABLE_NAME = "order_info";
    public static final String ORDER_COLUMN_PRIMARY_KEY = "primary_id";
    public static final String ORDER_COLUMN_ID = "product_id";
    public static final String ORDER_COLUMN_IS_ORDERED = "is_ordered";

    private static DBHelper mDBHelper;
    public static DBHelper getInstance(Context context) {
        if (mDBHelper == null) {
            mDBHelper = new DBHelper(context);
        }

        return mDBHelper;
    }

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        tableCreateStatements(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ORDER_TABLE_NAME);
        onCreate(db);
    }

    private void tableCreateStatements(SQLiteDatabase db) {
        try {
            db.execSQL(
                    "CREATE TABLE IF NOT EXISTS "
                            + ORDER_TABLE_NAME + "("
                            + ORDER_COLUMN_PRIMARY_KEY + " INTEGER PRIMARY KEY, "
                            + ORDER_COLUMN_ID + " VARCHAR(20), "
                            + ORDER_COLUMN_IS_ORDERED + " VARCHAR(20) "
                            + ")"
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public OrderedItem getOrderedItem(String orderId) throws Resources.NotFoundException, NullPointerException {
        Cursor cursor = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            cursor = db.rawQuery(
                    "SELECT * FROM "
                            + ORDER_TABLE_NAME
                            + " WHERE "
                            + ORDER_COLUMN_ID
                            + "= ?",
                    new String[]{orderId + ""});

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                OrderedItem orderedItem = new OrderedItem();
                orderedItem.setProductId(cursor.getString(cursor.getColumnIndex(ORDER_COLUMN_ID)));
                orderedItem.setOrdered("1".equalsIgnoreCase(cursor.getString(cursor.getColumnIndex(ORDER_COLUMN_IS_ORDERED))));

                return orderedItem;
            } else {
                return null;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public Long insertFavouriteMovie(OrderedItem orderedItem) throws Exception {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(ORDER_COLUMN_ID, orderedItem.getProductId());
            contentValues.put(ORDER_COLUMN_IS_ORDERED, orderedItem.isOrdered());

            return db.insert(ORDER_TABLE_NAME, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
