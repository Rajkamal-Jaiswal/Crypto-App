package com.e.cellpaycrypto.mypack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.SelectPaymentMethodActivity;
import com.e.cellpaycrypto.databinding.ActivityP2PmethodsBinding;
import com.e.cellpaycrypto.databinding.EditBankDetailsLayoutBinding;
import com.e.cellpaycrypto.databinding.EditPaytmLayoutBinding;
import com.e.cellpaycrypto.databinding.EditPhonePayLayoutBinding;
import com.e.cellpaycrypto.databinding.EditUpiLayoutBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Objects;

public class P2PMethodsActivity extends AppCompatActivity {

    ActivityP2PmethodsBinding binding;
    BottomSheetDialog watchlist;
    Activity activity;
    private Toolbar toolbar;
    private TextView toolbarTitleTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_p2_pmethods);
        binding = ActivityP2PmethodsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = P2PMethodsActivity.this;


        myToolbar();

        binding.editPaytm.setOnClickListener(v -> {
            watchlist = new BottomSheetDialog(activity, R.style.videosheetDialogTheme);
            EditPaytmLayoutBinding binding;
            binding = EditPaytmLayoutBinding.inflate(getLayoutInflater());
            watchlist.setContentView(binding.getRoot());
            Objects.requireNonNull(watchlist.getWindow()).getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            watchlist.setCancelable(false);
            watchlist.setCanceledOnTouchOutside(true);
            binding.btnCancel.setOnClickListener(v1 -> {
                watchlist.dismiss();
            });
            binding.btnSubmit.setOnClickListener(v1 -> {
                Toast.makeText(this, "Remaining!", Toast.LENGTH_SHORT).show();
                watchlist.cancel();
            });
            watchlist.show();
        });
        binding.editPhonePay.setOnClickListener(v -> {
            watchlist = new BottomSheetDialog(activity, R.style.videosheetDialogTheme);
            EditPhonePayLayoutBinding binding;
            binding = EditPhonePayLayoutBinding.inflate(getLayoutInflater());
            watchlist.setContentView(binding.getRoot());
            Objects.requireNonNull(watchlist.getWindow()).getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            watchlist.setCancelable(false);
            watchlist.setCanceledOnTouchOutside(true);
            binding.btnCancel.setOnClickListener(v1 -> {
                watchlist.dismiss();
            });
            binding.btnSubmit.setOnClickListener(v1 -> {
                Toast.makeText(this, "Remaining!", Toast.LENGTH_SHORT).show();
                watchlist.cancel();
            });
            watchlist.show();
        });
        binding.editUpi.setOnClickListener(v -> {
            watchlist = new BottomSheetDialog(activity, R.style.videosheetDialogTheme);
            EditUpiLayoutBinding binding;
            binding = EditUpiLayoutBinding.inflate(getLayoutInflater());
            watchlist.setContentView(binding.getRoot());
            Objects.requireNonNull(watchlist.getWindow()).getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            watchlist.setCancelable(false);
            watchlist.setCanceledOnTouchOutside(true);
            binding.btnCancel.setOnClickListener(v1 -> {
                watchlist.dismiss();
            });
            binding.btnSubmit.setOnClickListener(v1 -> {
                Toast.makeText(this, "Remaining!", Toast.LENGTH_SHORT).show();
                watchlist.cancel();
            });
            watchlist.show();
        });
        binding.editBankDetails.setOnClickListener(v -> {
            watchlist = new BottomSheetDialog(activity, R.style.videosheetDialogTheme);
            EditBankDetailsLayoutBinding binding;
            binding = EditBankDetailsLayoutBinding.inflate(getLayoutInflater());
            watchlist.setContentView(binding.getRoot());
            Objects.requireNonNull(watchlist.getWindow()).getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            watchlist.setCancelable(false);
            watchlist.setCanceledOnTouchOutside(true);
            binding.btnCancel.setOnClickListener(v1 -> {
                watchlist.dismiss();
            });
            binding.btnSubmit.setOnClickListener(v1 -> {
                Toast.makeText(this, "Remaining!", Toast.LENGTH_SHORT).show();
                watchlist.cancel();
            });
            watchlist.show();
        });

        binding.btnAddPaymethods.setOnClickListener(v -> {
            startActivity(new Intent(activity, SelectPaymentMethodActivity.class));
//            Toast.makeText(activity, "Coming shortly!", Toast.LENGTH_SHORT).show();
        });
    }

    private void myToolbar() {
        toolbar = findViewById(R.id.main_toolbar);
        toolbarTitleTV = findViewById(R.id.toolbarTitleTV);
        toolbarTitleTV.setText("P2P Payment Methods");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setTitleTextColor(getResources().getColor(R.color.Black));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

}