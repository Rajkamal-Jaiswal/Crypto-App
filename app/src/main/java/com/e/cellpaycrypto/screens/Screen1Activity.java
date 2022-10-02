package com.e.cellpaycrypto.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.databinding.ActivityScreen1Binding;
import com.e.cellpaycrypto.test1.ModelPojo;

import java.util.ArrayList;
import java.util.List;

public class Screen1Activity extends AppCompatActivity {


    RecyclerView recyclerView;

    Button continueBtn;

    com.e.cellpaycrypto.databinding.ActivityScreen1Binding binding;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScreen1Binding.inflate(getLayoutInflater());
        activity = this;
        setContentView(binding.getRoot());

        myToolbar();
        recyclerView = findViewById(R.id.recyclerView);
        continueBtn = findViewById(R.id.btnNext);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<ModelPojo> modelPojos = new ArrayList<>();
        modelPojos.add(new ModelPojo("9118919678"));
        modelPojos.add(new ModelPojo("6392657909"));
        modelPojos.add(new ModelPojo("7800456789"));
        AdapterCommonPayee adapterCommonPayee = new AdapterCommonPayee(this, modelPojos);
        recyclerView.setAdapter(adapterCommonPayee);

        continueBtn.setOnClickListener(v -> {
            startActivity(new Intent(activity, Screen2Activity.class));
        });


        disableAll();
        binding.btnEmail.setBackground(getResources().getDrawable(R.drawable.rect2));


        binding.btnEmail.setOnClickListener(v -> {
            disableAll();
            binding.btnEmail.setBackground(getResources().getDrawable(R.drawable.rect2));

        });
        binding.btnPhone.setOnClickListener(v -> {
            disableAll();
            binding.btnPhone.setBackground(getResources().getDrawable(R.drawable.rect2));

        });
        binding.btnPhonpe.setVisibility(View.GONE);
        binding.btnPhonpe.setOnClickListener(v -> {
            disableAll();
            binding.btnPhonpe.setBackground(getResources().getDrawable(R.drawable.rect2));

        });
        binding.btnSellPay.setOnClickListener(v -> {
            disableAll();
            binding.btnSellPay.setBackground(getResources().getDrawable(R.drawable.rect2));

        });

    }

    private void disableAll() {
        binding.btnPhone.setBackground(getResources().getDrawable(R.drawable.rect_normal));
        binding.btnEmail.setBackground(getResources().getDrawable(R.drawable.rect_normal));
        binding.btnPhonpe.setBackground(getResources().getDrawable(R.drawable.rect_normal));
        binding.btnSellPay.setBackground(getResources().getDrawable(R.drawable.rect_normal));
    }

    private void myToolbar() {
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        toolbar.setTitle("Send to SellPay User");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }
}