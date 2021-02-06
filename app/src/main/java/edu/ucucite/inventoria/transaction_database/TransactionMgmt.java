package edu.ucucite.inventoria.transaction_database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;


import static edu.ucucite.inventoria.transaction_database.TransactionContract.Transaction_entry.*;



public class TransactionMgmt {

    public static void add_transaction_log(TransactionObj transactionObj, Context context) {
        Log.v("resdb", transactionObj.getDate_time());
        TransactionDBHelper transactionDBHelper = new TransactionDBHelper(context);
        SQLiteDatabase db = transactionDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_NAME, transactionObj.getItem_name());
        values.put(COLUMN_ITEM_COUNT, transactionObj.getItem_count());
        values.put(COLUMN_TYPE, transactionObj.getTrans_type());
        values.put(COLUMN_DATE_TIME, transactionObj.getDate_time());
        values.put(COLUMN_NOTES, transactionObj.getTrans_notes());
        long newid= db.insert(TABLE_NAME, null, values);
        Log.v("resdb", String.valueOf(newid));
//        itemAdapter.notifyItemInserted(listItems(context).size());
//        itemAdapter.notifyDataSetChanged();
//        Log.v("hello", "te");

    }


    // READ trans log list
    public static ArrayList<TransactionObj> trans_log_list(Context context) {
        TransactionDBHelper transactionDBHelper = new TransactionDBHelper(context);
        String selection = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = transactionDBHelper.getReadableDatabase();
        ArrayList<TransactionObj> transactionObjArrayList = new ArrayList<>();
        Cursor cursor = db.rawQuery(selection, null);
        if (cursor.moveToFirst()) {
            do {
                transactionObjArrayList.add(new TransactionObj(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_COUNT)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_TYPE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DATE_TIME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_NOTES))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return transactionObjArrayList;
    }

}
