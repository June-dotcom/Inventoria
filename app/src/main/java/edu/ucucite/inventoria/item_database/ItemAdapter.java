 package edu.ucucite.inventoria.item_database;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import edu.ucucite.inventoria.R;
import edu.ucucite.inventoria.UpdateBarcodeActivity;

import static edu.ucucite.inventoria.item_database.ItemMgmt.item_update_ver;
import static edu.ucucite.inventoria.item_database.ItemMgmt.update_item;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {
    private ArrayList<ItemObj> ItemList;


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView item_name, item_bar_code, item_count, item_low_count, item_price;
        //        public MaterialCardView card_menu;
//        public ImageView card_background, icon_edit, icon_delete, icon_share;
        public ImageView ic_del_btn, ic_edit_btn, ic_scan_btn;

        public MyViewHolder(View v) {
            super(v);
//            quiz_name = v.findViewById(R.id.menu_quiz_title);
            item_name = v.findViewById(R.id.item_name_recycler_txt);
            item_bar_code = v.findViewById(R.id.item_barcode_recycler_txt);
            item_low_count = v.findViewById(R.id.item_low_count_txt);
            item_count = v.findViewById(R.id.item_count_txt);
            item_price = v.findViewById(R.id.item_price_txt);
            ic_del_btn = v.findViewById(R.id.del_btn_item_recycler);
            ic_edit_btn = v.findViewById(R.id.edit_btn_item_recycler);
            ic_scan_btn = v.findViewById(R.id.scan_btn_item_recycler);
        }
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public ItemAdapter(ArrayList<ItemObj> myDataset) {
        this.ItemList = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_inventory_items, parent, false);
        ItemAdapter.MyViewHolder vh = new ItemAdapter.MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ItemAdapter.MyViewHolder holder, final int position) {
        final ItemObj itemlist_obj = ItemList.get(position);
        holder.item_name.setText(itemlist_obj.getItem_name());
        holder.item_bar_code.setText("Barcode: " + itemlist_obj.getItem_barcode_str());
        holder.item_count.setText("Count: " + itemlist_obj.getItem_count());
        holder.item_low_count.setText("Low count: " + itemlist_obj.getItem_low_count());
        holder.item_price.setText("Item price: PHP" + itemlist_obj.getPrice());

        holder.ic_del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int id = itemlist_obj.getItem_id();
                SQLiteOpenHelper dbhelper = new ItemDBHelper(v.getContext());
                SQLiteDatabase inv_db = dbhelper.getWritableDatabase();
                inv_db.delete(ItemContract.ItemEntry.TABLE_NAME,
                        ItemContract.ItemEntry._ID + " = " + id, null);
                notifyItemRangeChanged(position, ItemList.size());
                ItemList.remove(position);
                notifyItemRemoved(position);
                inv_db.close();
            }
        });

        holder.ic_edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog_edit_item(v, position, itemlist_obj);
//                dialog_edit_item(item_old_name,v);
//                notifyItemChanged(position);


            }
        });

        holder.ic_scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), UpdateBarcodeActivity.class);
                intent.putExtra("item_obj", itemlist_obj);
                intent.putExtra("item_position", position);
                v.getContext().startActivity(intent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return ItemList.size();
    }


    public void dialog_edit_item(View view, int position, ItemObj itemObj) {
        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        // set the custom layout
        String old_item_name = itemObj.getItem_name();
        builder.setTitle("Edit item " + old_item_name);
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View customLayout = inflater.inflate(R.layout.dialog_edit_item, null);
        builder.setView(customLayout);
        // add a button
        TextInputEditText item_name = customLayout.findViewById(R.id.dialog_item_name_edit_ed);
        TextInputEditText item_barcode = customLayout.findViewById(R.id.dialog_item_barcode_edit_txt);
        TextInputEditText item_low_count = customLayout.findViewById(R.id.dialog_item_low_count_edit_txt);
        TextInputEditText item_price = customLayout.findViewById(R.id.dialog_item_price_edit_txt);

        item_name.setText(itemObj.getItem_name());
        item_barcode.setText(itemObj.getItem_barcode_str());
        item_low_count.setText(String.valueOf(itemObj.getItem_low_count()));
        item_price.setText(String.valueOf(itemObj.getPrice()));

        builder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // send data from the AlertDialog to the Activity
                
                String item_name_str = item_name.getText().toString();
                String item_barcode_str = item_barcode.getText().toString();

                String low_count_str = item_low_count.getText().toString();
                int low_count_int = Integer.parseInt(low_count_str);

                String item_price_str = item_price.getText().toString();
                int item_price_int = Integer.parseInt(item_price_str);

                itemObj.setItem_name(item_name_str);
                itemObj.setItem_barcode_str(item_barcode_str);
                itemObj.setItem_low_count(low_count_int);
                itemObj.setPrice(item_price_int);

                update_item(itemObj, view.getContext());
                notifyItemChanged(position, itemObj);
//                if (item_update_ver(old_item_name, itemObj, view.getContext())){
//                    Toast.makeText(view.getContext(), "Successfully updated", Toast.LENGTH_SHORT).show();
//                    notifyItemChanged(position, itemObj);
//                }else{
//                    Toast.makeText(view.getContext(), "Item exists", Toast.LENGTH_SHORT).show();
//                }
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void filterList(ArrayList<ItemObj> filteredList) {
        ItemList = filteredList;
        notifyDataSetChanged();
    }
}