package com.e.cellpaycrypto.menus.ui.notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.test1.ModelPojo;

import java.util.List;

public class AdapterHistoryTrade extends RecyclerView.Adapter<AdapterHistoryTrade.ExViewHolder> {
    List<ModelPojo> modelPojos;
    Context context;

    public AdapterHistoryTrade(List<ModelPojo> modelPojos, Context context) {
        this.modelPojos = modelPojos;
        this.context = context;
    }


    @NonNull
    @Override
    public AdapterHistoryTrade.ExViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history_trade, parent, false);
        return new AdapterHistoryTrade.ExViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExViewHolder holder, int position) {
        holder.winningLosingtTV.setText(modelPojos.get(position).getTtl());
        if (Double.parseDouble(modelPojos.get(position).getTtl()) > 2) {
            holder.winningLosingtTV.setTextColor(context.getResources().getColor(R.color.sucess));
        } else {
            holder.winningLosingtTV.setTextColor(context.getResources().getColor(R.color.unPaidcolor));
        }
    }

    @Override
    public int getItemCount() {
        return modelPojos.size();
    }

    public class ExViewHolder extends RecyclerView.ViewHolder {
        TextView winningLosingtTV;

        public ExViewHolder(@NonNull View itemView) {
            super(itemView);
            winningLosingtTV = itemView.findViewById(R.id.winningLosingtTV);
        }
    }
}
