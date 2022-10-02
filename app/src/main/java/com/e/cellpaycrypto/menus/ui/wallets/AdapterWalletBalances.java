package com.e.cellpaycrypto.menus.ui.wallets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cellpaycrypto.R;

public class AdapterWalletBalances extends RecyclerView.Adapter<AdapterWalletBalances.ExVieHolder> {
    @NonNull
    @Override
    public ExVieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_available_balances, parent, false);
        return new ExVieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExVieHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class ExVieHolder extends RecyclerView.ViewHolder {
        public ExVieHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
