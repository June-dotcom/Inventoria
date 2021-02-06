package edu.ucucite.inventoria.ui.transactions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import edu.ucucite.inventoria.R;
import edu.ucucite.inventoria.item_database.ItemObj;
import edu.ucucite.inventoria.transaction_database.TransactionAdapter;
import edu.ucucite.inventoria.transaction_database.TransactionHistoryAdapter;
import edu.ucucite.inventoria.transaction_database.TransactionObj;

import static edu.ucucite.inventoria.item_database.ItemMgmt.listItems;
import static edu.ucucite.inventoria.transaction_database.TransactionMgmt.trans_log_list;

public class TransactionHistoryActivity extends AppCompatActivity {
    ArrayList<TransactionObj> itemlist =  new ArrayList<>();
    private RecyclerView recyclerView ;
    private TransactionHistoryAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        itemlist = trans_log_list(getApplicationContext());
        Log.v("itemlist", itemlist.toString());
//        Toast.makeText(getContext(), itemlist.get(0).toString(), Toast.LENGTH_SHORT).show();
        recyclerView = findViewById(R.id.recycler_trans_log);
//        layoutManager = new LinearLayoutManager(getContext());
        itemAdapter = new TransactionHistoryAdapter(itemlist);
//        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerView.setAdapter(itemAdapter);
    }
}