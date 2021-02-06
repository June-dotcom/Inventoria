package edu.ucucite.inventoria.item_database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import static edu.ucucite.inventoria.item_database.ItemContract.ItemEntry.*;
public class ItemDBHelper extends SQLiteOpenHelper {
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    COLUMN_ITEM_NAME + " TEXT NOT NULL UNIQUE," +
                    COLUMN_BARCODE + " TEXT," +
                    COLUMN_QUANTITY + " INTEGER," +
                    COLUMN_PRICE + " INTEGER," +
                    COLUMN_LOW_QUANTITY + " INTEGER)";

    private static final String SQL_CREATE_ENTRIES_1 =
            "CREATE TABLE " + TABLE_NAME_1 + " (" +
                    COLUMN_ITEM_NAME_1 + " TEXT," +
                    COLUMN_ITEM_COUNT_1+ " INTEGER," +
                    COLUMN_DATE_TIME_1 + " TEXT," +
                    COLUMN_NOTES_1 + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
//
    private static final String SQL_DELETE_ENTRIES_1 =
            "DROP TABLE IF EXISTS " + TABLE_NAME_1;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "InventoryItems.db";

    public ItemDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES_1);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_DELETE_ENTRIES_1);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


}
