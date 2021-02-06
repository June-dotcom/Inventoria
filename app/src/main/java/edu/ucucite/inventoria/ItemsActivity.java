package edu.ucucite.inventoria;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import edu.ucucite.inventoria.item_database.ItemAdapter;
import edu.ucucite.inventoria.item_database.ItemObj;

import static edu.ucucite.inventoria.item_database.ItemMgmt.add_item;
import static edu.ucucite.inventoria.item_database.ItemMgmt.item_insert_ver;
import static edu.ucucite.inventoria.item_database.ItemMgmt.listItems;

public class ItemsActivity extends AppCompatActivity {
    FloatingActionButton fab_add;
    ArrayList<ItemObj> itemlist = new ArrayList<>();
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        fab_add = findViewById(R.id.fab_add_item_act);
        getSupportActionBar().setTitle("Manage Items");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init_list();
        init_recycler();
        EditText editText = findViewById(R.id.search_query);
        findViewById(R.id.scan_query).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ScanBarcodeActivity.class);
                intent.putExtra("search_type", "main");

                startActivity(intent);
            }
        });
        try {
            Intent intent = getIntent();
            String bcode_search = intent.getStringExtra("barcode_search_val");
            filter(bcode_search);
        }catch (Exception e){

        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });


        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_add_item();

            }
        });

    }
    private void filter(String text) {
        ArrayList<ItemObj> filteredList = new ArrayList<>();
        for (ItemObj item : itemlist) {
            if (item.getItem_name().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
//            if (item.getItem_count() == Integer.parseInt(text) || item.getItem_low_count() == Integer.parseInt(text)) {
//                filteredList.add(item);
//            }
//
            if (item.getItem_barcode_str().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        itemAdapter.filterList(filteredList);
    }


//    @Override
//    protected void onResume() {
//        init_list();
//        init_recycler();
//        Log.v("update", "resume");
//        super.onResume();
//    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
        super.onBackPressed();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void init_list() {
        Log.v("update", "itemlist init");
        itemlist = listItems(getApplicationContext());
        Log.v("itemlist", itemlist.toString());
    }

    public void init_recycler() {
        //        Toast.makeText(getApplicationContext(), itemlist.get(0).toString(), Toast.LENGTH_SHORT).show();
        recyclerView = findViewById(R.id.recycler_view_items_act);
//        layoutManager = new LinearLayoutManager(getApplicationContext());
        itemAdapter = new ItemAdapter(itemlist);

//        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerView.setAdapter(itemAdapter);
    }


    public void dialog_add_item() {
        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // set the custom layout
        builder.setTitle("Enter item name to add");
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_add_item, null);
        builder.setView(customLayout);
        // add a button
        builder.setPositiveButton("ADD ITEM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // send data from the AlertDialog to the Activity
                TextInputEditText item_name = customLayout.findViewById(R.id.item_name_ed);
                String item_name_txt = item_name.getText().toString();
//                add_item(item_name_txt, getApplicationContext());
//
                add_item(item_name_txt, getApplicationContext());
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
//                if (item_insert_ver(item_name_txt, getApplicationContext())) {
//                    Toast.makeText(getApplicationContext(), "Insert Successfully", Toast.LENGTH_SHORT).show();
////                    itemlist.add(new ItemObj(0, item_name_txt, "", 0, 0));
//                    Log.v("size", String.valueOf(itemlist.size()));
////                    itemAdapter.notifyItemInserted(itemlist.size());
////                    finish();
////                    overridePendingTransition(0, 0);
////                    startActivity(getIntent());
////                    overridePendingTransition(0, 0);
//                } else {
//                    Toast.makeText(getApplicationContext(), "Item exists", Toast.LENGTH_SHORT).show();
//                }
//                 sqlite and arraylist fun
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
}