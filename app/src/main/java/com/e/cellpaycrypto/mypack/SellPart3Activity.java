package com.e.cellpaycrypto.mypack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.databinding.ActivitySellPart3Binding;
import com.e.cellpaycrypto.test1.ShowAdsActivity;

public class SellPart3Activity extends AppCompatActivity {

    ActivitySellPart3Binding binding;
    private Toolbar toolbar;
    private TextView toolbarTitleTV;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySellPart3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;
        toolbar = findViewById(R.id.main_toolbar);
        toolbarTitleTV = findViewById(R.id.toolbarTitleTV);
        myToolbar();


        binding.btnPrevious.setOnClickListener(v -> {
            onBackPressed();
        });
        binding.btnNext.setOnClickListener(v -> {
            startActivity(new Intent(activity, ShowAdsActivity.class));
        });

    }

    private void myToolbar() {
        toolbarTitleTV.setText("Sell");
        toolbar.setNavigationIcon(R.drawable.ic_back);

//        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        toolbar.setTitleTextColor(getResources().getColor(R.color.Black));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }
}