package com.e.cellpaycrypto.menus.ui.wallets;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;

import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.databinding.ActivityTransferBinding;

public class TransferActivity extends AppCompatActivity {

    ActivityTransferBinding binding;

    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransferBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = TransferActivity.this;
        myToolbar();

        binding.tvCashWalletPopup.setOnClickListener(v -> {
/*            if (binding.tvCashWalletPopup.getText().toString().trim().equalsIgnoreCase("Cash Wallet") ||
                    binding.tvCashWalletPopup.getText().toString().trim().equalsIgnoreCase("Trade Wallet")) {*/
            PopupMenu popupMenu = new PopupMenu(activity, v, Gravity.FILL_VERTICAL);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
               /* view.setText(item.getTitle().toString());
                view.setError(null);*/

                    if (item.getTitle().equals("Cash Wallet")) {
                        popupMenu.dismiss();
                        binding.tvCashWalletPopup.setText("Cash Wallet");
                    } else if (item.getTitle().equals("Trade Wallet")) {
                        popupMenu.dismiss();
                        binding.tvCashWalletPopup.setText("Trade Wallet");
                    } else {
                        binding.tvCashWalletPopup.setText("Bonus");
                    }
                    return true;
                }
            });
            Menu mymenuSubject = popupMenu.getMenu();
            mymenuSubject.add("Cash Wallet");
            mymenuSubject.add("Trade Wallet");
            mymenuSubject.add("Bonus");

            popupMenu.show();
        });
        binding.toWallet.setOnClickListener(v -> {
/*            if (binding.tvCashWalletPopup.getText().toString().trim().equalsIgnoreCase("Cash Wallet") ||
                    binding.tvCashWalletPopup.getText().toString().trim().equalsIgnoreCase("Trade Wallet")) {*/
            PopupMenu popupMenu = new PopupMenu(activity, v, Gravity.FILL_VERTICAL);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
               /* view.setText(item.getTitle().toString());
                view.setError(null);*/

                    if (item.getTitle().toString().trim().equals("Cash Wallet")) {
                        popupMenu.dismiss();
                        binding.toWallet.setText("   Cash Wallet");
                    } else if (item.getTitle().toString().trim().equals("Trade Wallet")) {
                        popupMenu.dismiss();
                        binding.toWallet.setText("   Trade Wallet");
                    } else {
                        binding.toWallet.setText("Bonus");
                    }
                    return true;
                }
            });
            Menu mymenuSubject = popupMenu.getMenu();

            mymenuSubject.add("Cash Wallet");
            mymenuSubject.add("Trade Wallet");
//            mymenuSubject.add("Bonus");

            popupMenu.show();
        });


        binding.btnSwitch.setOnClickListener(v -> {
            if (binding.tvCashWalletPopup.getText().toString().trim().equalsIgnoreCase("Trade Wallet")) {
                binding.tvCashWalletPopup.setText("Cash Wallet");
                binding.toWallet.setText("     Trade Wallet");
            } else if (binding.tvCashWalletPopup.getText().toString().trim().equalsIgnoreCase("Cash Wallet")) {
                binding.tvCashWalletPopup.setText("Trade Wallet");
                binding.toWallet.setText("     Cash Wallet");
            }
        });
    }


    private void myToolbar() {
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        TextView title = findViewById(R.id.toolbarTitleTV);
        title.setText("Transfer");
        TextView btn = findViewById(R.id.btn);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        btn.setVisibility(View.GONE);
    }
}