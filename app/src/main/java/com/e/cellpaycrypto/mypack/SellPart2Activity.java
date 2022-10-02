package com.e.cellpaycrypto.mypack;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.databinding.ActivitySellPart2Binding;
import com.e.cellpaycrypto.databinding.SelectPaymentMethodSellBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class SellPart2Activity extends AppCompatActivity {

    ActivitySellPart2Binding binding;
    boolean[] selectedLanguage;
    ArrayList<Integer> langList = new ArrayList<>();
    String[] langArray = {"PhonePe", "UPI", "SellPay"};
    BottomSheetDialog watchlist;
    Activity activity;
    private Toolbar toolbar;
    private TextView toolbarTitleTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySellPart2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = SellPart2Activity.this;


        toolbar = findViewById(R.id.main_toolbar);
        toolbarTitleTV = findViewById(R.id.toolbarTitleTV);
        myToolbar();
        binding.btnPrevious.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.btnNext.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), SellPart3Activity.class));
        });
        binding.cvrSelectPaymethods.setOnClickListener(v -> {
            watchlist = new BottomSheetDialog(activity, R.style.videosheetDialogTheme);
            SelectPaymentMethodSellBinding binding;
            binding = SelectPaymentMethodSellBinding.inflate(getLayoutInflater());
            watchlist.setContentView(binding.getRoot());
            Objects.requireNonNull(watchlist.getWindow()).getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            watchlist.setCancelable(false);
            watchlist.setCanceledOnTouchOutside(true);
            binding.doneBtn.setOnClickListener(v1 -> {
                watchlist.dismiss();
            });
//            binding.btnCnfrmSubmit.setOnClickListener(v1 -> {
//                watchlist.cancel();
//            });
            watchlist.show();

        });


        // initialize selected language array
        selectedLanguage = new boolean[langArray.length];
        binding.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(SellPart2Activity.this);

                // set title
                builder.setTitle("Select Coins");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(langArray, selectedLanguage, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position  in lang list
                            langList.add(i);
                            // Sort array list
                            Collections.sort(langList);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            langList.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        // use for loop
                        for (int j = 0; j < langList.size(); j++) {
                            // concat array value
                            stringBuilder.append(langArray[langList.get(j)]);
                            // check condition
                            if (j != langList.size() - 1) {
                                // When j value  not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        binding.textView.setText(stringBuilder.toString());
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedLanguage.length; j++) {
                            // remove all selection
                            selectedLanguage[j] = false;
                            // clear language list
                            langList.clear();
                            // clear text view value
                            binding.textView.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();
            }
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