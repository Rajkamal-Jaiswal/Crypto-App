package com.e.cellpaycrypto.test1;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.apimodels.ModelShowAds;

import java.util.List;

public class AdapterMyAds extends RecyclerView.Adapter<AdapterMyAds.ExViewHolder> {


    List<ModelShowAds.DepositList> modelPojo;

    public AdapterMyAds(List<ModelShowAds.DepositList> modelPojo) {
        this.modelPojo = modelPojo;
    }

    @NonNull
    @Override
    public ExViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_ads_layout, parent, false);
        return new ExViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.txtPrice.setText(modelPojo.get(position).getCoin_cost());
        holder.ttlAmt.setText(modelPojo.get(position).getTotal_cost());
        holder.orderLimit.setText(modelPojo.get(position).getCoin_cost());
        holder.switchOnlineBtn.setOnCheckedChangeListener((compoundButton, b) -> {
            if (!b) {
                holder.switchOnlineBtn.setChecked(true);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(holder.itemView.getRootView().getContext());
                alertDialog.setTitle("Alert!").setMessage("Do you want to cancel this post?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        modelPojo.remove(position);
                        notifyDataSetChanged();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        holder.switchOnlineBtn.setChecked(true);
                    }
                });
                AlertDialog alertDialog1 = alertDialog.create();
                alertDialog1.show();

            }/*else {}*/
        });


    }

    @Override
    public int getItemCount() {
        return modelPojo.size();
    }

    public class ExViewHolder extends RecyclerView.ViewHolder {
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch switchOnlineBtn;
        TextView txtPrice, orderLimit, ttlAmt;

        public ExViewHolder(@NonNull View itemView) {
            super(itemView);
            switchOnlineBtn = itemView.findViewById(R.id.switchOnlineBtn);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            orderLimit = itemView.findViewById(R.id.orderLimit);
            ttlAmt = itemView.findViewById(R.id.ttlAmt);
        }
    }
}
