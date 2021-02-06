package edu.ucucite.inventoria.item_database;

import android.provider.BaseColumns;

public class ItemContract {
    private ItemContract() {

    }

    public static final class ItemEntry implements BaseColumns {
        public static final String TABLE_NAME = "Item_tbl";
        public static final String COLUMN_ITEM_NAME = "item_name";
        public static final String COLUMN_BARCODE = "item_barcode";
        public static final String COLUMN_QUANTITY = "item_quantity";
        public static final String COLUMN_LOW_QUANTITY = "item_low_quantity";
        public static final String COLUMN_PRICE = "item_price";


        public static final String TABLE_NAME_1 = "Transactions";
        public static final String COLUMN_ITEM_NAME_1 = "item_name";
        public static final String COLUMN_ITEM_COUNT_1 = "item_count";
        public static final String COLUMN_DATE_TIME_1 = "item_quantity";
        public static final String COLUMN_NOTES_1 = "item_low_quantity";

    }
}
