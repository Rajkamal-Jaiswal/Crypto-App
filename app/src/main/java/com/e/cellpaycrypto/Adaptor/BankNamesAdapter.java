package com.e.cellpaycrypto.Adaptor;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.apimodels.bankList.BankListItem;

import java.util.List;

public class BankNamesAdapter extends RecyclerView.Adapter<BankNamesAdapter.MyViewHolder> {

    Context context;
    List<BankListItem> modelBankNameList;
    TextView bankNameTv;
    Dialog searchDialog;


    public BankNamesAdapter(Activity context, List<BankListItem> modelBankNameList, Dialog searchDialog, TextView bankNameTv) {
        this.context = context;
        this.modelBankNameList = modelBankNameList;
        this.bankNameTv = bankNameTv;
        this.searchDialog = searchDialog;
    }

    @NonNull
    @Override
    public BankNamesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.state_city_dialog_adapter_item, viewGroup, false);
        return new BankNamesAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BankNamesAdapter.MyViewHolder myViewHolder, final int i) {
        BankListItem country = modelBankNameList.get(i);
        myViewHolder.tvName.setText(country.getBankName());

        myViewHolder.tvName.setOnClickListener(v -> {
            if (searchDialog != null) {
                searchDialog.dismiss();
            }
            bankNameTv.setText(country.getBankName());
//            switch (searchType) {
//                case "1":
//                    onStateCityClick(searchType, country);
//                    break;
//                case "2":
//                    onStateCityClick(searchType, country);
//                    break;
//                case "3":
//                    onStateCityClick(searchType, country);
//                    break;
//                default:
//                    break;
//            }
        });
    }

    @Override
    public int getItemCount() {
        return modelBankNameList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.nameTv);
        }
    }
}


