package com.e.cellpaycrypto.Adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cellpaycrypto.R;

public class AdapterActiveInActive extends RecyclerView.Adapter<AdapterActiveInActive.ExViewHolder> {

    String key = "";
    Context context;

    public AdapterActiveInActive(String key, Context context) {
        this.key = key;
        this.context = context;
    }

    @NonNull
    @Override
    public ExViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_active_inactive, parent, false);
        return new ExViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ExViewHolder holder, int position) {
        if (key.equalsIgnoreCase("Active")) {
            holder.btnCall.setBackground(context.getDrawable(R.drawable.btn_ripple));
        } else {
            holder.btnCall.setBackground(context.getDrawable(R.drawable.btn_color_cp_ripple));

        }
        holder.btnCall.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + holder.btnCall.getText().toString().trim()));// Initiates the Intent
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            v.getRootView().getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ExViewHolder extends RecyclerView.ViewHolder {
        TextView btnCall;

        public ExViewHolder(@NonNull View itemView) {
            super(itemView);
            btnCall = itemView.findViewById(R.id.btnCall);
        }
    }
}
