package com.e.cellpaycrypto.Adaptor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.apimodels.coinList.CoinListItem;
import com.e.cellpaycrypto.mypack.SellActivityMaster;
import com.e.cellpaycrypto.buy.BuyActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CoinAdapter extends RecyclerView.Adapter<CoinAdapter.ViewHolder> {
    Context context;
    String status;
    private List<CoinListItem> listdata;

    public CoinAdapter(List<CoinListItem> listdata, Context context, String status) {
        this.listdata = listdata;
        this.context = context;
        this.status = status;
    }

    @NonNull
    @Override
    public CoinAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.coin_list_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CoinAdapter.ViewHolder holder, int position) {
        if (status.equalsIgnoreCase("1")) {
            holder.btn1.setText("Buy");
        } else {
            holder.btn1.setText("Sell");
        }
        CoinListItem myListData = listdata.get(position);
        holder.tvTitle.setText(myListData.getTitle());
        holder.tvCost.setText(myListData.getCost());
        if (!myListData.getLogo1().equalsIgnoreCase("")) {
            Glide.with(context)
                    .load(myListData.getLogo1())
                    .error(R.mipmap.default_pic)
                    .into(holder.circulerImage);

        }

        holder.btn1.setOnClickListener(v -> {
            if (holder.btn1.getText().toString().trim().equalsIgnoreCase("Sell")) {
                context.startActivity(new Intent(context, SellActivityMaster.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            } else {
                context.startActivity(new Intent(context, BuyActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circulerImage;
        TextView tvTitle, tvCost;
        Button btn1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circulerImage = itemView.findViewById(R.id.circulerImage);
            tvCost = itemView.findViewById(R.id.tvCost);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            btn1 = itemView.findViewById(R.id.btn1);
        }
    }
}
