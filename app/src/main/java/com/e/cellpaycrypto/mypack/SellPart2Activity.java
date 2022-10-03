package com.e.cellpaycrypto.mypack;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.e.cellpaycrypto.Base.CommonUtils;
import com.e.cellpaycrypto.Base.GlobalConstants;
import com.e.cellpaycrypto.Base.Helper;
import com.e.cellpaycrypto.Base.VolleySingleton;
import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.SelectPaymentMethodActivity;
import com.e.cellpaycrypto.WebURLS;
import com.e.cellpaycrypto.apimodels.ModelResponse;
import com.e.cellpaycrypto.apimodels.showPaymentOptions.ModelPaymentShow;
import com.e.cellpaycrypto.databinding.ActivitySellPart2Binding;
import com.e.cellpaycrypto.databinding.SelectPaymentMethodSellBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
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
    private String userid;
    private SharedPreferences sharedpreferences;

    private ModelPaymentShow.Root modelPaymentOptionsShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySellPart2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = SellPart2Activity.this;
        sharedpreferences = getSharedPreferences(CommonUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        userid = sharedpreferences.getString(CommonUtils.shared_USER_ID, "");

        loadPaymentOptionsToSelect();
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

            binding.paytmTxt.setText(modelPaymentOptionsShow.getPaymentDeatil().getPaytm_no());
            binding.phonePeTxt.setText(modelPaymentOptionsShow.getPaymentDeatil().getPhonepe_no());
            binding.upiIdTxt.setText(modelPaymentOptionsShow.getPaymentDeatil().getUpi_id());
            binding.phPeName.setText(modelPaymentOptionsShow.getPaymentDeatil().getUpi_name());
            binding.upiTxtName.setText(modelPaymentOptionsShow.getPaymentDeatil().getUpi_name());
            binding.accountHolderName.setText(modelPaymentOptionsShow.getBankdata().getAccount_holder_name());
            binding.accountNumber.setText(modelPaymentOptionsShow.getBankdata().getAccount_number());
            binding.ifscTxt.setText("IFSC: "+modelPaymentOptionsShow.getBankdata().getIfsc_code());
            binding.accType.setText("Account Type: "+modelPaymentOptionsShow.getBankdata().getAccount_type());
            binding.bankNameTxt.setText("Bank Name: "+modelPaymentOptionsShow.getBankdata().getBank_name());
//            binding.branchName.setText(modelPaymentOptionsShow.getBankdata().getBranch_name());
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

    private void loadPaymentOptionsToSelect() {
        Helper.showLoadingDialog(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GlobalConstants.BASE_URL, response -> {
            Helper.watchLog("EditUpdateUPiApi: ", response);
            Helper.hideLoadingDialog();
            try {
                modelPaymentOptionsShow = new Gson().fromJson(response, ModelPaymentShow.Root.class);
                if (modelPaymentOptionsShow.getResultCode().equalsIgnoreCase("200")) {
//                    Helper.showToast(modelResponse.getMessage(), activity);
                } else {
                    Helper.showToast(modelPaymentOptionsShow.getMessage(), activity);
                }
            } catch (Exception e1) {
                Helper.watchLog("Excep", e1.getLocalizedMessage());
                Helper.showToast("Something went wrong", activity);
            }
        }, error -> {
            Helper.hideLoadingDialog();
            Helper.showToast(error.getLocalizedMessage(), activity);
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("module", WebURLS.userPaymentData);
                map.put("user_id", userid);
                Helper.watchLog("ParamsAddUpi", map.toString());
                return map;
            }
        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private void myToolbar() {
        toolbarTitleTV.setText("Sell");
        toolbar.setNavigationIcon(R.drawable.ic_back);

//        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        toolbar.setTitleTextColor(getResources().getColor(R.color.Black));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }
}