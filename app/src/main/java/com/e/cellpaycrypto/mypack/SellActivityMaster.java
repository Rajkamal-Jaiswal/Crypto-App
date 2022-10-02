package com.e.cellpaycrypto.mypack;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.databinding.ActivitySellMasterBinding;

public class SellActivityMaster extends AppCompatActivity {

    ActivitySellMasterBinding binding;
    FragmentManager fragmentManager;
    int status = 1;
    int plusCount = 0;
    Double fixedAmt = 0.0;
    Double fixedAmt2 = 0.0;
    int minusCount = 0;
    private String selectedFragment = "";
    private Toolbar toolbar;
    private TextView toolbarTitleTV;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySellMasterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolbar = findViewById(R.id.main_toolbar);
        toolbarTitleTV = findViewById(R.id.toolbarTitleTV);
        myToolbar();
 /*       if (status == 1) {
            Sell_FragmentPart1 sellFrag1 = new Sell_FragmentPart1();
            loadFrag(sellFrag1, "part1", fragmentManager);
        }
        if (status == 2) {
            Sell_FragmentPart1 sellFrag1 = new Sell_FragmentPart1();
            loadFrag(sellFrag1, "part1", fragmentManager);
        }
        if (status == 3) {
            Sell_FragmentPart1 sellFrag1 = new Sell_FragmentPart1();
            loadFrag(sellFrag1, "part1", fragmentManager);
        }*/

        binding.btnNext.setOnClickListener(v -> {
   /*         if (status == 1) {
                status = 2;
                SellFragmentPart2 sellFragmentPart2 = new SellFragmentPart2();
                addFragment(sellFragmentPart2, "part2", fragmentManager);
            }*/
            startActivity(new Intent(getApplicationContext(), SellPart2Activity.class));
        });

        disableButtons();
        binding.cvrLayout.fixedBtn.setBackground(getResources().getDrawable(R.drawable.rect_1_white));


        binding.cvrLayout.fixedBtn.setOnClickListener(v -> {
            disableButtons();
            binding.cvrLayout.fixedBtn.setBackground(getResources().getDrawable(R.drawable.rect_1_white));
            binding.cvrLayout.etEnterAmt.setHint("Enter Fixed Amount");
        });
        binding.cvrLayout.floatingBtn.setOnClickListener(v -> {
            disableButtons();
            binding.cvrLayout.floatingBtn.setBackground(getResources().getDrawable(R.drawable.rect_1_white));
            binding.cvrLayout.etEnterAmt.setHint("Enter Floating Amount");

        });
        fixedAmt = (Double.parseDouble(binding.cvrLayout.amtTv.getText().toString()) - 5);
        fixedAmt2 = (Double.parseDouble(binding.cvrLayout.amtTv.getText().toString()) + 5);
        binding.cvrLayout.btnMinus.setOnClickListener(v -> {

            /** 50-5 = 45
             *
             * txt>=45*/
//            minusCount++;

            if (Double.parseDouble(binding.cvrLayout.amtTv.getText()
                    .toString()) > fixedAmt) {
                Double aDouble = Double.valueOf(binding.cvrLayout.amtTv.getText()
                        .toString());
                aDouble = aDouble - 1;
                binding.cvrLayout.amtTv.setText(String.valueOf(aDouble));
            }

        });
        binding.cvrLayout.btnPlus.setOnClickListener(v -> {
          /*  plusCount++;
            {*/
            if (Double.parseDouble(binding.cvrLayout.amtTv.getText()
                    .toString()) < fixedAmt2) {
                Double aDouble = Double.valueOf(binding.cvrLayout.amtTv.getText()
                        .toString());
                aDouble = aDouble + 1;
                binding.cvrLayout.amtTv.setText(String.valueOf(aDouble));
            }
//            }
        });
    }

    private void disableButtons() {
        binding.cvrLayout.fixedBtn.setBackground(getResources().getDrawable(R.drawable.rect_1_simple));
        binding.cvrLayout.floatingBtn.setBackground(getResources().getDrawable(R.drawable.rect_1_simple));
    }

    private void myToolbar() {
        toolbarTitleTV.setText("Sell");
        toolbar.setNavigationIcon(R.drawable.ic_back);

//        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        toolbar.setTitleTextColor(getResources().getColor(R.color.Black));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }


}