package edu.ucucite.inventoria.ui.items;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import edu.ucucite.inventoria.R;
import edu.ucucite.inventoria.item_database.ItemAdapter;
import edu.ucucite.inventoria.item_database.ItemObj;

import static edu.ucucite.inventoria.item_database.ItemMgmt.item_insert_ver;
import static edu.ucucite.inventoria.item_database.ItemMgmt.listItems;


public class ItemsFragment extends Fragment {

    FloatingActionButton fab_add;
    ArrayList<ItemObj> itemlist;
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_items, container, false);
        fab_add = view.findViewById(R.id.fab_add_item);

        init_list();

        // build items
        init_recycler(view);

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_add_item();

            }
        });

        return view;
    }

    public void init_list() {
        itemlist = new ArrayList<>();

        itemlist.clear();
        itemlist = listItems(getContext());
    }

    public void init_recycler(View view) {
        //        Toast.makeText(getContext(), itemlist.get(0).toString(), Toast.LENGTH_SHORT).show();
        recyclerView = view.findViewById(R.id.recycler_view_items_act);
//        layoutManager = new LinearLayoutManager(getContext());
        itemAdapter = new ItemAdapter(itemlist);

//        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(itemAdapter);
    }

    @Override
    public void onResume() {
        init_list();
        init_recycler(getView());
        super.onResume();


    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void dialog_add_item() {
        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

                if (item_insert_ver(item_name_txt, getContext())) {
                    Toast.makeText(getContext(), "Insert Successfully", Toast.LENGTH_SHORT).show();
                    itemlist.add(new ItemObj(0, item_name_txt, "", 0, 0, 0));
                    Log.v("size", String.valueOf(itemlist.size()));
                    itemAdapter.notifyItemInserted(itemlist.size());
                } else {
                    Toast.makeText(getContext(), "Item exists", Toast.LENGTH_SHORT).show();
                }
                // sqlite and arraylist fun
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