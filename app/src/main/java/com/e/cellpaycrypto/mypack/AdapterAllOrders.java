package com.e.cellpaycrypto.mypack;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cellpaycrypto.R;

public class AdapterAllOrders extends RecyclerView.Adapter<AdapterAllOrders.ExViewHolder> {

    @NonNull
    @Override
    public ExViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_layout_p2p, parent, false);
        return new ExViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 15;
    }

    public class ExViewHolder extends RecyclerView.ViewHolder {
        public ExViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
