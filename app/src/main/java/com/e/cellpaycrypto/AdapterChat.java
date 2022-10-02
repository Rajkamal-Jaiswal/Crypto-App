package com.e.cellpaycrypto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cellpaycrypto.test1.ModelPojo2;

import java.util.List;

public class AdapterChat extends RecyclerView.Adapter<AdapterChat.ExViewHolder> {
    Context context;
    List<ModelPojo2> modelPojo2s;

    public AdapterChat(Context context, List<ModelPojo2> modelPojo2s) {
        this.context = context;
        this.modelPojo2s = modelPojo2s;
    }

    @NonNull
    @Override
    public ExViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_chat_layout, parent, false);
        return new ExViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExViewHolder holder, int position) {

        if (modelPojo2s.get(position).getTtl1().equalsIgnoreCase("1")) {
            holder.cvrLeft.setVisibility(View.VISIBLE);
            holder.cvrRight.setVisibility(View.GONE);
            holder.letfmessageTv.setText(modelPojo2s.get(position).getTtl2());
        } else {
            holder.cvrLeft.setVisibility(View.GONE);
            holder.cvrRight.setVisibility(View.VISIBLE);
            holder.rightmessage.setText(modelPojo2s.get(position).getTtl2());
        }
    }

    @Override
    public int getItemCount() {
        return modelPojo2s.size();
    }

    public class ExViewHolder extends RecyclerView.ViewHolder {
        TextView letfmessageTv, rightmessage;
        RelativeLayout cvrLeft, cvrRight;

        public ExViewHolder(@NonNull View itemView) {
            super(itemView);
            letfmessageTv = itemView.findViewById(R.id.letfmessageTv);
            rightmessage = itemView.findViewById(R.id.rightmessage);
            cvrLeft = itemView.findViewById(R.id.cvrLeft);
            cvrRight = itemView.findViewById(R.id.cvrRight);
        }
    }
}
