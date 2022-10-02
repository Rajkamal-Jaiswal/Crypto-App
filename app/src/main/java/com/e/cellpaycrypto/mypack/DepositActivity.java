package com.e.cellpaycrypto.mypack;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.databinding.ActivityDepositBinding;
import com.e.cellpaycrypto.databinding.DepositDialogBinding;
import com.e.cellpaycrypto.menus.DashboardActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Objects;

public class DepositActivity extends AppCompatActivity {

    ActivityDepositBinding binding;
    BottomSheetDialog watchlist;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDepositBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = DepositActivity.this;
        myToolbar();


    }

    private void myToolbar() {
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        TextView title = findViewById(R.id.toolbarTitleTV);
        title.setText("Deposit");
        TextView btn = findViewById(R.id.btn);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        btn.setVisibility(View.VISIBLE);
        btn.setText("Next");
        btn.setOnClickListener(v -> {
            watchlist = new BottomSheetDialog(activity, R.style.videosheetDialogTheme);
            DepositDialogBinding binding;
            binding = DepositDialogBinding.inflate(getLayoutInflater());
            watchlist.setContentView(binding.getRoot());
            Objects.requireNonNull(watchlist.getWindow()).getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            watchlist.setCancelable(false);
            watchlist.setCanceledOnTouchOutside(true);
            binding.btnCancel.setOnClickListener(v1 -> {
                watchlist.dismiss();
            });
            binding.btnSubmit.setOnClickListener(v1 -> {
                watchlist.cancel();
                /* Toast.makeText(this, "Remaining!", Toast.LENGTH_SHORT).show();*/
                openAlertDialog();

            });
            watchlist.show();
        });
    }

    private void openAlertDialog() {
        final Dialog dialog = new Dialog(DepositActivity.this);
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        dialog.setContentView(R.layout.success_dialog_send);
        TextView titleDialog = (TextView) dialog.findViewById(R.id.titleDialog);
        titleDialog.setText("Successfully Deposit!");
        TextView msgDialog = (TextView) dialog.findViewById(R.id.msgDialog);
        TextView nhbvh = (TextView) dialog.findViewById(R.id.nhbvh);
        nhbvh.setVisibility(View.INVISIBLE);
        Button btn_submit = (Button) dialog.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
            finishAffinity();
        });

        try {
            dialog.show();
        } catch (Exception e) {
            Log.d("Dialog", "makeDialog: " + e.getMessage());
        }

    }

}