package edu.ucucite.inventoria.transaction_database;

import android.provider.BaseColumns;

public class TransactionContract {
    private TransactionContract(){

    }

    public static final class Transaction_entry implements BaseColumns {
        public static final String TABLE_NAME = "Transaction_tbl";
        public static final String COLUMN_ITEM_NAME = "trans_item_name";
        public static final String COLUMN_ITEM_COUNT = "trans_item_count";
        public static final String COLUMN_TYPE = "trans_item_type";
        public static final String COLUMN_DATE_TIME = "trans_date";
        public static final String COLUMN_NOTES = "item_notes";
    }
}
