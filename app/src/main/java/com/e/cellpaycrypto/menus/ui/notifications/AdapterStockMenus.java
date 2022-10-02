package com.e.cellpaycrypto.menus.ui.notifications;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.test1.ModelPojo;

import java.util.List;

public class AdapterStockMenus extends RecyclerView.Adapter<AdapterStockMenus.ExViewHolder> {

    List<ModelPojo> modelPojos;

    // if checkedPosition = -1, there is no default selection
    // if checkedPosition = 0, 1st item is selected by default
    private int checkedPosition = 0;

    public AdapterStockMenus(List<ModelPojo> modelPojos) {
        this.modelPojos = modelPojos;
    }

    @NonNull
    @Override
    public ExViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_stock_menus, parent, false);
        return new ExViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExViewHolder holder, int position) {

        holder.ttl.setText(modelPojos.get(position).getTtl());

    }

    @Override
    public int getItemCount() {
        return modelPojos.size();
    }

    public class ExViewHolder extends RecyclerView.ViewHolder {
        TextView ttl;

        public ExViewHolder(@NonNull View itemView) {
            super(itemView);
            ttl = itemView.findViewById(R.id.ttl);
        }
    }
}
