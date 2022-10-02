package com.e.cellpaycrypto.test1;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.databinding.ActivityShowAdsBinding;
import com.e.cellpaycrypto.mypack.SellActivityMaster;

import java.util.ArrayList;
import java.util.List;

public class ShowAdsActivity extends AppCompatActivity {
    ActivityShowAdsBinding binding;
    RecyclerView recyclerView;
    List<ModelPojo> modelPojos;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowAdsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        recyclerView = binding.recyclerView;
        myToolbar();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        modelPojos = new ArrayList<>();
        modelPojos.add(new ModelPojo("a"));
        modelPojos.add(new ModelPojo("b"));
        modelPojos.add(new ModelPojo("c"));
        modelPojos.add(new ModelPojo("d"));
        modelPojos.add(new ModelPojo("e"));
        modelPojos.add(new ModelPojo("f"));
        modelPojos.add(new ModelPojo("g"));
        AdapterMyAds adapterMyAds = new AdapterMyAds(modelPojos);
        recyclerView.setAdapter(adapterMyAds);


        binding.addSell.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), SellActivityMaster.class));
        });
    }

    private void myToolbar() {
        toolbar = findViewById(R.id.main_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }
}