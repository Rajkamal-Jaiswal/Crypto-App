package com.e.cellpaycrypto.screens;

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

public class AdapterCommonPayee extends RecyclerView.Adapter<AdapterCommonPayee.ExViewHolder> {

    Context context;
    List<ModelPojo> modelPojos;

    public AdapterCommonPayee(Context context, List<ModelPojo> modelPojos) {
        this.context = context;
        this.modelPojos = modelPojos;
    }

    @NonNull
    @Override
    public ExViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_layout_common_payee, parent, false);
        return new ExViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExViewHolder holder, int position) {
        holder.idTxt.setText(modelPojos.get(position).getTtl());
    }

    @Override
    public int getItemCount() {
        return modelPojos.size();
    }

    public class ExViewHolder extends RecyclerView.ViewHolder {
        TextView idTxt;

        public ExViewHolder(@NonNull View itemView) {
            super(itemView);
            idTxt = itemView.findViewById(R.id.idTxt);
        }
    }
}
