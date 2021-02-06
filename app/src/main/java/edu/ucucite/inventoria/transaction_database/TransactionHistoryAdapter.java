package edu.ucucite.inventoria.transaction_database;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.ucucite.inventoria.R;


import static edu.ucucite.inventoria.item_database.ItemMgmt.update_item;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryAdapter.MyViewHolder> {
    private ArrayList<TransactionObj> histList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView item_name, item_action_type, item_date, item_desc;
        //        public MaterialCardView card_menu;
        public EditText edit_item_count;
        public ImageView ic_edit_btn;

        public MyViewHolder(View v) {
            super(v);

            item_name = v.findViewById(R.id.history_item_name);
            item_action_type = v.findViewById(R.id.history_action);
            item_date = v.findViewById(R.id.history_date);
            item_desc = v.findViewById(R.id.history_description);
            ic_edit_btn = v.findViewById(R.id.ic_history_enter_description);
        }
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public TransactionHistoryAdapter(ArrayList<TransactionObj> myDataset) {
        this.histList = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TransactionHistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_transaction_history, parent, false);
        TransactionHistoryAdapter.MyViewHolder vh = new TransactionHistoryAdapter.MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final TransactionHistoryAdapter.MyViewHolder holder, final int position) {
        TransactionObj transactionObj = histList.get(position);
        holder.item_name.setText(transactionObj.getItem_name());
        holder.item_date.setText(transactionObj.getDate_time());
        holder.item_action_type.setText(transactionObj.getTrans_type() + " " + transactionObj.getItem_count());
        holder.item_desc.setText(transactionObj.getTrans_notes());

        holder.ic_edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // dialog edit blah

            }
        });
    

    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return histList.size();
    }
}

