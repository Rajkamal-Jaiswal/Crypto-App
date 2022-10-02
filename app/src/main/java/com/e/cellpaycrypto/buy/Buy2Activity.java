package com.e.cellpaycrypto.buy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.databinding.ActivityBuy2Binding;
import com.e.cellpaycrypto.databinding.SelectPaymentMethodToBuyBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Objects;

public class Buy2Activity extends AppCompatActivity {

    ActivityBuy2Binding binding;
    BottomSheetDialog watchlist;
    Activity activity;
    RadioButton radio1, radio2, radio3, radio4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBuy2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = Buy2Activity.this;


        myToolbar();
        binding.btnNext.setOnClickListener(v -> {
            watchlist = new BottomSheetDialog(activity, R.style.videosheetDialogTheme);
            SelectPaymentMethodToBuyBinding binding;
            binding = SelectPaymentMethodToBuyBinding.inflate(getLayoutInflater());
            watchlist.setContentView(binding.getRoot());
            Objects.requireNonNull(watchlist.getWindow()).getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            watchlist.setCancelable(false);
            watchlist.setCanceledOnTouchOutside(true);
            binding.doneBtn.setOnClickListener(v1 -> {
                watchlist.dismiss();
                startActivity(new Intent(getApplicationContext(), Buy3Activity.class));
            });

            radio1 = binding.editPaytm;
            radio2 = binding.editPhonePay;
            radio3 = binding.editUpi;
            radio4 = binding.editBankDetails;
            disableAllButton();
            radio1.setOnClickListener(view -> {
                disableAllButton();
                radio1.setChecked(true);
            });
            radio2.setOnClickListener(view -> {
                disableAllButton();
                radio2.setChecked(true);
            });
            radio3.setOnClickListener(view -> {
                disableAllButton();
                radio3.setChecked(true);
            });
            radio4.setOnClickListener(view -> {
                disableAllButton();
                radio4.setChecked(true);
            });
            watchlist.show();
        });
        binding.btnPrevious.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Alert!");
            builder.setMessage("Do you really want to cancel ?");
            builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                dialogInterface.dismiss();
                onBackPressed();
            }).setNegativeButton("No", (dialogInterface, i) -> {
                dialogInterface.dismiss();
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
//            startActivity(new Intent(getApplicationContext(), CancelOrderActivity.class));
        });

    }

    private void disableAllButton() {
        radio1.setChecked(false);
        radio2.setChecked(false);
        radio3.setChecked(false);
        radio4.setChecked(false);
    }

    private void myToolbar() {
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        TextView title = findViewById(R.id.toolbarTitleTV);
        title.setText("Order Created");
        TextView btn = findViewById(R.id.btn);
        toolbar.setNavigationIcon(R.drawable.ic_back);

//        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        btn.setVisibility(View.GONE);
        /*btn.setOnClickListener(v->{
            Toast.makeText(this, "Coming Shortly!", Toast.LENGTH_SHORT).show();
        });*/
    }
}