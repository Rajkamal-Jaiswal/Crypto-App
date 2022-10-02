package com.e.cellpaycrypto.mypack;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.databinding.ActivityOrdersBinding;
import com.google.android.material.tabs.TabLayout;

public class OrdersActivity extends AppCompatActivity {

    ActivityOrdersBinding binding;
    ViewPager viewPager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrdersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.main_toolbar);
        myToolbar();

        viewPager = findViewById(R.id.viewpager);
        loadViewpagerForPendingOrders();
        handleButtons();
        binding.pendingBtn.setBackground(getResources().getDrawable(R.drawable.rect_1_white));

        binding.pendingBtn.setOnClickListener(v -> {
            handleButtons();
            loadViewpagerForPendingOrders();
            binding.pendingBtn.setBackground(getResources().getDrawable(R.drawable.rect_1_white));

        });
        binding.completedBtn.setOnClickListener(v -> {
            handleButtons();
            loadViewpagerForCompletedOrders();
            binding.completedBtn.setBackground(getResources().getDrawable(R.drawable.rect_1_white));

        });


    }

    private void loadViewpagerForCompletedOrders() {
        viewPager.setAdapter(new CompletedOrdersAdapterViewPager(getSupportFragmentManager()));
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#1f57ff"));
        tabLayout.setSelectedTabIndicatorHeight((int) (4 *
                getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#9d9d9d"),
                Color.parseColor("#0d0e10"));
        tabLayout.setupWithViewPager(viewPager);
    }

    private void loadViewpagerForPendingOrders() {
        viewPager.setAdapter(new PendingOrdersAdapterViewPager(getSupportFragmentManager()));
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#1f57ff"));
        tabLayout.setSelectedTabIndicatorHeight((int) (4 *
                getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#9d9d9d"),
                Color.parseColor("#0d0e10"));
        tabLayout.setupWithViewPager(viewPager);
    }

    private void myToolbar() {
//        toolbarTitleTV.setText("P2P");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        toolbar.setTitleTextColor(getResources().getColor(R.color.Black));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void handleButtons() {
        binding.pendingBtn.setBackground(getResources().getDrawable(R.drawable.rect2));
        binding.completedBtn.setBackground(getResources().getDrawable(R.drawable.rect2));
    }
}