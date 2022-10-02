package com.e.cellpaycrypto.screens;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.databinding.PayPinLayoutBinding;
import com.e.cellpaycrypto.menus.DashboardActivity;
import com.goodiebag.pinview.Pinview;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Objects;

public class Screen2Activity extends AppCompatActivity {

    Button btnNext;
    BottomSheetDialog watchlist;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen2);
        activity = Screen2Activity.this;
        myToolbar();
        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(v -> {
            watchlist = new BottomSheetDialog(activity, R.style.videosheetDialogTheme);
            PayPinLayoutBinding binding;
            binding = PayPinLayoutBinding.inflate(getLayoutInflater());
            watchlist.setContentView(binding.getRoot());
            Objects.requireNonNull(watchlist.getWindow()).getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            watchlist.setCancelable(false);
            watchlist.setCanceledOnTouchOutside(true);
            Pinview pin = watchlist.findViewById(R.id.pinview);
            pin.requestPinEntryFocus();
            pin.setPinViewEventListener((pinview, fromUser) -> {
                if (pinview.getPinLength() == 6) {
                    watchlist.dismiss();
                    final Dialog dialog = new Dialog(Screen2Activity.this);
                    dialog.setCancelable(false);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
                    dialog.setContentView(R.layout.success_dialog_send);
                    TextView titleDialog = dialog.findViewById(R.id.titleDialog);
                    TextView msgDialog = dialog.findViewById(R.id.msgDialog);
                    Button btn_submit = dialog.findViewById(R.id.btn_submit);
                    btn_submit.setOnClickListener(v1 -> {
                        dialog.cancel();
                        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                        finishAffinity();

                    });

                    try {
                        dialog.show();
                    } catch (Exception e) {
                        Log.d("Dialog", "makeDialog: " + e.getMessage());
                    }

                }
            });


           /* binding.titleDialog.setText("Add Paytm Details");
            binding.btnCancel.setOnClickListener(v1 -> {
                watchlist.dismiss();
            });
            binding.btnSubmit.setOnClickListener(v1 -> {
                Toast.makeText(Screen2Activity.this, "Remaining!", Toast.LENGTH_SHORT).show();
                watchlist.cancel();
            });*/
            watchlist.show();
        });
    }

    private void myToolbar() {
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }
}