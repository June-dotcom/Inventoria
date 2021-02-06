package edu.ucucite.inventoria.ui.transactions;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import edu.ucucite.inventoria.R;
import edu.ucucite.inventoria.ScanBarcodeActivity;
import edu.ucucite.inventoria.item_database.ItemAdapter;
import edu.ucucite.inventoria.item_database.ItemObj;
import edu.ucucite.inventoria.transaction_database.TransactionAdapter;
import edu.ucucite.inventoria.transaction_database.TransactionObj;

import static edu.ucucite.inventoria.item_database.ItemMgmt.listItems;


public class TransactionsFragment extends Fragment {

    ArrayList<ItemObj> itemlist =  new ArrayList<>();
    private RecyclerView recyclerView ;
    private TransactionAdapter itemAdapter;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_transactions, container, false);
        itemlist = listItems(getContext());
//        Toast.makeText(getContext(), itemlist.get(0).toString(), Toast.LENGTH_SHORT).show();
        recyclerView = view.findViewById(R.id.recycler_transaction_items);
//        layoutManager = new LinearLayoutManager(getContext());
        itemAdapter = new TransactionAdapter(itemlist);
//        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(itemAdapter);
        setHasOptionsMenu(true);
        EditText editText = view.findViewById(R.id.search_query_trans);
//        view.findViewById(R.id.scan_query_trans).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), ScanBarcodeActivity.class);
//                intent.putExtra("search_type", "trans");
//                startActivity(intent);
//            }
//        });
        try {
            Intent intent = getActivity().getIntent();
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




        return view;
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
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_transaction, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.menu_scan_transaction:
//                Snackbar.make(getView(), "Confirm item", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                return true;
//            case R.id.menu_hist_transaction:
//                startActivity(new Intent(getContext(), TransactionHistoryActivity.class));
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}