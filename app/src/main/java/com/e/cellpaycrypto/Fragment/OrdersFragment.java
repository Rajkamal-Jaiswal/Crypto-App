package com.e.cellpaycrypto.Fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.databinding.ActivityOrdersBinding;
import com.e.cellpaycrypto.mypack.CompletedOrdersAdapterViewPager;
import com.e.cellpaycrypto.mypack.PendingOrdersAdapterViewPager;
import com.google.android.material.tabs.TabLayout;

public class OrdersFragment extends Fragment {

    ActivityOrdersBinding binding;
    ViewPager viewPager;
    View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = ActivityOrdersBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        viewPager = root.findViewById(R.id.viewpager);
        loadViewpagerForPendingOrders();
        handleButtons();
        binding.pendingBtn.setBackground(getResources().getDrawable(R.drawable.rect2));

        binding.pendingBtn.setOnClickListener(v -> {
            handleButtons();
            loadViewpagerForPendingOrders();
            binding.pendingBtn.setBackground(getResources().getDrawable(R.drawable.rect2));

        });
        binding.completedBtn.setOnClickListener(v -> {
            handleButtons();
            loadViewpagerForCompletedOrders();
            binding.completedBtn.setBackground(getResources().getDrawable(R.drawable.rect2));

        });


        return binding.getRoot();
    }

    private void loadViewpagerForCompletedOrders() {
        viewPager.setAdapter(new CompletedOrdersAdapterViewPager(getChildFragmentManager()));
        TabLayout tabLayout = root.findViewById(R.id.tab_layout);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#1f57ff"));
        tabLayout.setSelectedTabIndicatorHeight((int) (4 *
                getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#9d9d9d"),
                Color.parseColor("#0d0e10"));
        tabLayout.setupWithViewPager(viewPager);
    }

    private void loadViewpagerForPendingOrders() {
        viewPager.setAdapter(new PendingOrdersAdapterViewPager(getChildFragmentManager()));
        TabLayout tabLayout = root.findViewById(R.id.tab_layout);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#1f57ff"));
        tabLayout.setSelectedTabIndicatorHeight((int) (4 *
                getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#9d9d9d"),
                Color.parseColor("#0d0e10"));
        tabLayout.setupWithViewPager(viewPager);
    }

    private void myToolbar() {
//        toolbarTitleTV.setText("P2P");
    /*    toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        toolbar.setTitleTextColor(getResources().getColor(R.color.Black));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());*/
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void handleButtons() {
        binding.pendingBtn.setBackground(getResources().getDrawable(R.drawable.rect_1_white));
        binding.completedBtn.setBackground(getResources().getDrawable(R.drawable.rect_1_white));
    }

//        return binding.getRoot();
}

