package com.e.cellpaycrypto.menus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cellpaycrypto.R;

public class AdapterDashRecyc extends RecyclerView.Adapter<AdapterDashRecyc.ExViewHolder> {

    Context context;

    @NonNull
    @Override
    public ExViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dash1, parent, false);
        return new ExViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class ExViewHolder extends RecyclerView.ViewHolder {
        public ExViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
