package com.e.cellpaycrypto.referral;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.databinding.ActivityReferralBinding;
import com.e.cellpaycrypto.referral.ui.main.ContentFragment;
import com.google.android.material.tabs.TabLayout;

public class ReferralActivity extends AppCompatActivity {


    TabLayout tabLayout;
    ViewPager viewPager;
    PagerAdapter adapter;
    private ActivityReferralBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityReferralBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        myToolbar();

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.pager);

        if (viewPager != null) {
            setupViewPager(viewPager);
        }
    }

    private void myToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);

//        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new PagerAdapter(getSupportFragmentManager(), this);
        adapter.addFragment(new ContentFragment().newInstance("Refer"), "Refer");
        adapter.addFragment(new ContentFragment().newInstance("My Referrals"), "Referral");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}