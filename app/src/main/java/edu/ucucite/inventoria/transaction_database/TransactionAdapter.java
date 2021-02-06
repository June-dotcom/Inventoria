package edu.ucucite.inventoria.transaction_database;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import edu.ucucite.inventoria.R;
import edu.ucucite.inventoria.item_database.ItemObj;

import static edu.ucucite.inventoria.item_database.ItemMgmt.update_item;


public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MyViewHolder> {
    private ArrayList<ItemObj> ItemList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView item_name, item_bar_code, item_count, item_low_count;
        //        public MaterialCardView card_menu;
//        public ImageView card_background, icon_edit, icon_delete, icon_share;
        public EditText edit_item_count;
        public ImageView ic_add_btn, ic_deduct_btn;

        public MyViewHolder(View v) {
            super(v);

            item_name = v.findViewById(R.id.transaction_item_name_recycler_txt);
            item_bar_code = v.findViewById(R.id.transaction_item_barcode_recycler_txt);
            item_count = v.findViewById(R.id.transaction_item_count_txt);
            item_low_count = v.findViewById(R.id.transaction_item_low_count_txt);

            edit_item_count = v.findViewById(R.id.edit_count_ed);
            ic_add_btn = v.findViewById(R.id.ic_add_item_qty);
            ic_deduct_btn = v.findViewById(R.id.ic_deduct_item_qty);
        }
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public TransactionAdapter(ArrayList<ItemObj> myDataset) {
        this.ItemList = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TransactionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_transaction_items, parent, false);
        TransactionAdapter.MyViewHolder vh = new TransactionAdapter.MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final TransactionAdapter.MyViewHolder holder, final int position) {
        final ItemObj itemlist_obj = ItemList.get(position);
        holder.item_name.setText(itemlist_obj.getItem_name());
        holder.item_bar_code.setText("Barcode: " + itemlist_obj.getItem_barcode_str());
        holder.item_count.setText("Count: " + itemlist_obj.getItem_count());
        holder.item_low_count.setText("Low count: " + itemlist_obj.getItem_low_count());

        holder.ic_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_deduct(position,
                        Integer.parseInt(String.valueOf(holder.edit_item_count.getText())),
                        "+",
                        itemlist_obj,
                        v, v.getContext());
                // add transaction history
            }
        });

        holder.ic_deduct_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_deduct(position,
                        Integer.parseInt(String.valueOf(holder.edit_item_count.getText())),
                        "-",
                        itemlist_obj,
                        v, v.getContext());
            }
        });

    }

    public void add_deduct(int position,int item_qty_count, String operation, ItemObj itemObj, View view, Context context) {
        int item_qty_upd = 0;
        if (operation.equals("+")) {
            item_qty_upd = itemObj.getItem_count() + item_qty_count;
            Toast.makeText(view.getContext(), "Added " +
                    item_qty_count + " to " + itemObj.getItem_name(), Toast.LENGTH_SHORT).show();

        } else if (operation.equals("-")) {
            if (item_qty_count < itemObj.getItem_count()) {
                item_qty_upd = itemObj.getItem_count() - item_qty_count;
                Toast.makeText(view.getContext(), "Deducted " + item_qty_count + " to " + itemObj.getItem_name(), Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(view.getContext(), "Make sure that it is less than the quantity count", Toast.LENGTH_SHORT).show();
            }
        }
        itemObj.setItem_count(item_qty_upd);

        notifyItemChanged(position, itemObj);
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        String date = df.format(Calendar.getInstance().getTime());
        TransactionObj transactionObj = new TransactionObj(itemObj.getItem_id(), itemObj.getItem_name(), String.valueOf(itemObj.getItem_count()), operation, date, "none" );
        Log.v("obj", transactionObj.toString());
        update_item(itemObj, context);

        // add to databse
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    public void filterList(ArrayList<ItemObj> filteredList) {
        ItemList = filteredList;
        notifyDataSetChanged();
    }
}

