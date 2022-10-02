package com.e.cellpaycrypto.menus.ui.notifications;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cellpaycrypto.R;

public class AdapterPositions extends RecyclerView.Adapter<AdapterPositions.ExViewHolder> {
    @NonNull
    @Override
    public AdapterPositions.ExViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_positions_trade, parent, false);
        return new AdapterPositions.ExViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class ExViewHolder extends RecyclerView.ViewHolder {
        public ExViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
