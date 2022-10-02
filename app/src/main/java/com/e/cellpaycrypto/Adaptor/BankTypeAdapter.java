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
import com.e.cellpaycrypto.apimodels.accountTypes.AccountTypeListItem;

import java.util.List;

public class BankTypeAdapter extends RecyclerView.Adapter<BankTypeAdapter.MyViewHolder> {

    Context context;
    List<AccountTypeListItem> modelAccountTypes;

    TextView accountTypeTV;
    Dialog searchDialog;


    public BankTypeAdapter(Activity context, List<AccountTypeListItem> modelAccountTypes, Dialog searchDialog, TextView accountTypeTV) {
        this.context = context;
        this.modelAccountTypes = modelAccountTypes;
        this.accountTypeTV = accountTypeTV;
        this.searchDialog = searchDialog;
    }

    @NonNull
    @Override
    public BankTypeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.state_city_dialog_adapter_item, viewGroup, false);
        return new BankTypeAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BankTypeAdapter.MyViewHolder myViewHolder, final int i) {
        AccountTypeListItem country = modelAccountTypes.get(i);
        myViewHolder.tvName.setText(country.getAccountType());

        myViewHolder.tvName.setOnClickListener(v -> {
            if (searchDialog != null) {
                searchDialog.dismiss();
            }
            accountTypeTV.setText(country.getAccountType());
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
        return modelAccountTypes.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.nameTv);
        }
    }
}

