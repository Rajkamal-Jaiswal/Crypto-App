package com.e.cellpaycrypto.referral;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.databinding.ActivityEarnBinding;

public class EarnActivity extends AppCompatActivity {

    ActivityEarnBinding binding;
    int counterTrading = 0;
    int counterReferral = 0;
    int counterPrimeMembership = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEarnBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        myToolbar();


        binding.clickTrading.setOnClickListener(v -> {
            if (counterTrading == 0) { //means it is Gone
                counterTrading = 1;
                binding.cvrShowHideTrading.setVisibility(View.VISIBLE);
                binding.arrowTrading.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
            } else {
                counterTrading = 0;
                binding.cvrShowHideTrading.setVisibility(View.GONE);
                binding.arrowTrading.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24);

            }
        });
        binding.clickReferral.setOnClickListener(v -> {
            if (counterReferral == 0) { //means it is Gone
                counterReferral = 1;
                binding.cvrShowHideReferral.setVisibility(View.VISIBLE);
                binding.arrowReferralBonus.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);

            } else {
                counterReferral = 0;
                binding.cvrShowHideReferral.setVisibility(View.GONE);
                binding.arrowReferralBonus.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24);

            }
        });
        binding.clickPrimeMembership.setOnClickListener(v -> {
            if (counterPrimeMembership == 0) { //means it is Gone
                counterPrimeMembership = 1;
                binding.cvrShowHidePrimeMember.setVisibility(View.VISIBLE);
                binding.arrowPrimeMembership.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);

            } else {
                counterPrimeMembership = 0;
                binding.cvrShowHidePrimeMember.setVisibility(View.GONE);
                binding.arrowPrimeMembership.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24);

            }
        });
    }

    private void myToolbar() {
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        TextView toolbarTitleTV = findViewById(R.id.toolbarTitleTV);
        toolbar.setNavigationIcon(R.drawable.ic_back);
//        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        toolbarTitleTV.setText("Earn");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

}