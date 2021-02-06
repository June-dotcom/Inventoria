package edu.ucucite.inventoria.item_database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import edu.ucucite.inventoria.transaction_database.TransactionDBHelper;
import edu.ucucite.inventoria.transaction_database.TransactionObj;

import static edu.ucucite.inventoria.item_database.ItemContract.ItemEntry.*;


public class ItemMgmt {


    public static boolean item_insert_ver(String item_name, Context context) {
        if (item_name_fetch(item_name, context).moveToFirst()) {
            // item exists
            return false;
        } else {
            add_item(item_name, context);
            return true;
        }
    }

    public static boolean item_update_ver(String old_name, ItemObj itemObj, Context context) {
        if (old_name.equals(itemObj.getItem_name())) {
            update_item(itemObj, context);
            return true;
        } else {
            if (item_name_fetch(itemObj.getItem_name(), context).moveToFirst()) {
                // item exists
                return false;
            } else {
                // item not exists cont stmt
                update_item(itemObj, context);
                return true;
            }
        }

    }


    public static void update_item(ItemObj itemObj, Context context) {
        SQLiteDatabase db = new ItemDBHelper(context).getWritableDatabase();

// New value for one columm
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_NAME, itemObj.getItem_name());
        values.put(COLUMN_LOW_QUANTITY, String.valueOf(itemObj.getItem_low_count()));
        values.put(COLUMN_BARCODE, itemObj.getItem_barcode_str());
        values.put(COLUMN_QUANTITY, itemObj.getItem_count());
        values.put(COLUMN_PRICE, itemObj.getPrice());
// Which row to update, based on the title
//        String selection = COLUMN_ITEM_NAME + " LIKE ?";
//        String[] selectionArgs = {item_old_name};
//
//        db.update(
//                TABLE_NAME,
//                values,
//                selection,
//                selectionArgs);

            String selection = "_ID=" + itemObj.getItem_id();
            db.update(TABLE_NAME,
                    values,
                    selection,
                    null);
            db.close();
    }

    public static ArrayList<ItemObj> listItems(Context context) {
        ItemDBHelper itemDBHelper = new ItemDBHelper(context);
        String selection = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = itemDBHelper.getReadableDatabase();
        ArrayList<ItemObj> itemObjArrayList = new ArrayList<>();
        Cursor cursor = db.rawQuery(selection, null);
        if (cursor.moveToFirst()) {
            do {
                itemObjArrayList.add(new ItemObj
                        (
                                Integer.parseInt(cursor.getString(0)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NAME)),
                                cursor.getString(cursor.getColumnIndex(COLUMN_BARCODE)),
                                Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_QUANTITY))),
                                Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_LOW_QUANTITY))),
                                Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_PRICE)))
                        )
                );

            } while (cursor.moveToNext());
        }
        db.close();

        cursor.close();
        return itemObjArrayList;
    }

    public static String countItems_types(Context context) {
        String countItems_types = "";
        ItemDBHelper itemDBHelper = new ItemDBHelper(context);
        String selection = "SELECT COUNT(*) FROM " + TABLE_NAME;
        SQLiteDatabase db = itemDBHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(selection, null);
        if (cursor.moveToFirst()) {
            countItems_types = String.valueOf(cursor.getInt(0));
        }
        db.close();

        cursor.close();
        return countItems_types;
    }

    public static String sumItems_all(Context context) {
        String sumItems_all = "";
        ItemDBHelper itemDBHelper = new ItemDBHelper(context);
        String selection = "SELECT SUM(item_quantity) FROM " + TABLE_NAME;
        SQLiteDatabase db = itemDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selection, null);
        if (cursor.moveToFirst()) {
            sumItems_all = String.valueOf(cursor.getInt(0));
        }
        db.close();

        cursor.close();
        return sumItems_all;
    }
    public static void add_item(String item_name, Context context) {
        ItemDBHelper itemDBHelper = new ItemDBHelper(context);
        SQLiteDatabase db = itemDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_NAME, item_name);
        values.put(COLUMN_BARCODE, "");
        values.put(COLUMN_QUANTITY, "0");
        values.put(COLUMN_LOW_QUANTITY, "0");
        values.put(COLUMN_PRICE, "0");

        long newid = db.insert(TABLE_NAME, null, values);
//        itemAdapter.notifyItemInserted(listItems(context).size());
//        itemAdapter.notifyDataSetChanged();
//        Log.v("hello", "te");
        Log.v("resdbval", String.valueOf(newid));
        db.close();

    }

    public static Cursor item_name_fetch(String item_name, Context context) {
        ItemDBHelper dbHelper = new ItemDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = COLUMN_ITEM_NAME + " = ?";
        String[] selectionArgs = {item_name};

        Cursor cursor = db.query(
                ItemContract.ItemEntry.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null// The sort order
        );
        db.close();

        return cursor;
    }


}
