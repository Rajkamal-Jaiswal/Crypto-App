package com.e.cellpaycrypto.menus.ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cellpaycrypto.Activity.ActiveInactiveActivity;
import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.test1.ModelPojo;

import java.util.List;

public class AdapterPremiumDashboard extends RecyclerView.Adapter<AdapterPremiumDashboard.ExViewHolder> {

    Context context;
    List<ModelPojo> modelPojos;

    public AdapterPremiumDashboard(Context context, List<ModelPojo> modelPojos) {
        this.context = context;
        this.modelPojos = modelPojos;
    }

    @NonNull
    @Override
    public ExViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_premium_membership, parent, false);
        return new ExViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExViewHolder holder, int position) {
//        holder.title.setText("modelPojos.get(position).getTtl()");
        holder.title.setText("");
        holder.numb.setText(String.valueOf(position + 1));
        holder.cvrGreen.setOnClickListener(v -> {
            context.startActivity(new Intent(context, ActiveInactiveActivity.class).putExtra("key", "Active"));

//            Toast.makeText(context, modelPojos.get(position).getTtl(), Toast.LENGTH_SHORT).show();
        });
        holder.cvrRed.setOnClickListener(v -> {
            context.startActivity(new Intent(context, ActiveInactiveActivity.class).putExtra("key", "Inactive"));

//            Toast.makeText(context, modelPojos.get(position).getTtl(), Toast.LENGTH_SHORT).show();
        });


    }

    @Override
    public int getItemCount() {
        return modelPojos.size();
    }

    public class ExViewHolder extends RecyclerView.ViewHolder {
        TextView title, numb;
        LinearLayoutCompat cover;
        LinearLayoutCompat cvrGreen, cvrRed;

        public ExViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            numb = itemView.findViewById(R.id.numb);
            cover = itemView.findViewById(R.id.cover);
            cvrGreen = itemView.findViewById(R.id.cvrGreen);
            cvrRed = itemView.findViewById(R.id.cvrRed);
        }
    }
}
