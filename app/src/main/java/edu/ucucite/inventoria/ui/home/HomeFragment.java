package edu.ucucite.inventoria.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import edu.ucucite.inventoria.HelpActivity;
import edu.ucucite.inventoria.ItemsActivity;
import edu.ucucite.inventoria.R;
import static edu.ucucite.inventoria.item_database.ItemMgmt.countItems_types;
import static edu.ucucite.inventoria.item_database.ItemMgmt.sumItems_all;

public class HomeFragment extends Fragment {

    CardView card_manage_items, card_export_data, card_import_data, card_help;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        card_manage_items = root.findViewById(R.id.btn_start_manage_items);
//        card_export_data = root.findViewById(R.id.btn_start_export_data);
//        card_import_data = root.findViewById(R.id.btn_start_import_data);
        TextView it_type = root.findViewById(R.id.item_type_qty_val);
        it_type.setText(countItems_types(getContext()));
        TextView it_qty = root.findViewById(R.id.total_item_qty_val);
        it_qty.setText(sumItems_all(getContext()));
//        card_help = root.findViewById(R.id.btn_start_help);
        card_manage_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ItemsActivity.class));
            }
        });
//        card_help.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getContext(), HelpActivity.class));
//            }
//        });

        return root;
    }
}