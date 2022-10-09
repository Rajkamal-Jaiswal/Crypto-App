package com.e.cellpaycrypto.mypack;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.e.cellpaycrypto.Base.CommonUtils;
import com.e.cellpaycrypto.Base.GlobalConstants;
import com.e.cellpaycrypto.Base.Helper;
import com.e.cellpaycrypto.Base.SettingsManager;
import com.e.cellpaycrypto.Base.VolleySingleton;
import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.WebURLS;
import com.e.cellpaycrypto.apimodels.ModelAssets;
import com.e.cellpaycrypto.apimodels.ModelCurrency;
import com.e.cellpaycrypto.databinding.ActivitySellMasterBinding;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

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
    private SharedPreferences sharedpreferences;
    private String user_id = "";
    private Context activity;
    private Gson gson;
    private ModelCurrency.Root modelCurrency;
    private ModelAssets.Root modelAssets;
    private String selectedCurrencyId = "";
    private String selectedCurrencyType = "";
    private SettingsManager settingsManager;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySellMasterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;
        gson = new Gson();
        settingsManager = new SettingsManager(activity);
        sharedpreferences = getSharedPreferences(CommonUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        user_id = sharedpreferences.getString(CommonUtils.shared_USER_ID, "");
        toolbar = findViewById(R.id.main_toolbar);
        toolbarTitleTV = findViewById(R.id.toolbarTitleTV);
        myToolbar();

        hitApiToLoadCurrencyList();
        hitApiToLoadAssetLists();


        binding.btnAssetCvr.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(activity, v, Gravity.FILL_VERTICAL);
            popupMenu.setOnMenuItemClickListener(item -> {
                binding.assetTypeTxt.setText(item.getTitle().toString());
                popupMenu.dismiss();
                return true;
            });
            Menu mymenuSubject = popupMenu.getMenu();
            mymenuSubject.add(modelAssets.spc.getTotal_spc().trim());
            popupMenu.show();
        });
        binding.btnFiatCvr.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(activity, v, Gravity.FILL_VERTICAL);
            popupMenu.setOnMenuItemClickListener(item -> {
                popupMenu.dismiss();
                binding.fiatTypeTxt.setText(item.getTitle().toString().trim());
                getSelectedCurrencyDetails(item.getTitle().toString().trim());
                return true;
            });
            Menu mymenuSubject = popupMenu.getMenu();
            for (int i = 0; i < modelCurrency.category_list.size(); i++) {
                mymenuSubject.add(modelCurrency.category_list.get(i).getCurrency().trim());
            }
            popupMenu.show();
        });

/*
total_amount:100000
order_limit_min:5000
order_limit_max:900000
payment_method:paytm
terms:this is terms
auto_reply:this is auto reply
*/
        binding.btnNext.setOnClickListener(v -> {
            HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put("user_id", user_id);
            hashMap.put("coin_id", "1");
            hashMap.put("price_type", selectedCurrencyId);
            hashMap.put("highest_order_price", settingsManager.getSettings().getP2p_sell_limit());
            startActivity(new Intent(getApplicationContext(), SellPart2Activity.class).putExtra("map", hashMap));

        });

        disableButtons();
        binding.fixedBtn.setBackground(getResources().getDrawable(R.drawable.rect_1_white));


        binding.fixedBtn.setOnClickListener(v -> {
            disableButtons();
            binding.fixedBtn.setBackground(getResources().getDrawable(R.drawable.rect_1_white));
            binding.etEnterAmt.setHint("Enter Fixed Amount");
        });
        binding.floatingBtn.setOnClickListener(v -> {
            disableButtons();
            binding.floatingBtn.setBackground(getResources().getDrawable(R.drawable.rect_1_white));
            binding.etEnterAmt.setHint("Enter Floating Amount");

        });
        fixedAmt = (Double.parseDouble(binding.amtTv.getText().toString()) - 5);
        fixedAmt2 = (Double.parseDouble(binding.amtTv.getText().toString()) + 5);
        binding.btnMinus.setOnClickListener(v -> {
            if (Double.parseDouble(binding.amtTv.getText()
                    .toString()) > fixedAmt) {
                Double aDouble = Double.valueOf(binding.amtTv.getText()
                        .toString());
                aDouble = aDouble - 1;
                binding.amtTv.setText(String.valueOf(aDouble));
            }

        });
        binding.btnPlus.setOnClickListener(v -> {
            if (Double.parseDouble(binding.amtTv.getText()
                    .toString()) < fixedAmt2) {
                Double aDouble = Double.valueOf(binding.amtTv.getText()
                        .toString());
                aDouble = aDouble + 1;
                binding.amtTv.setText(String.valueOf(aDouble));
            }
        });
    }

    private void getSelectedCurrencyDetails(String currencyName) {
        int m = 0;
        for (int i = 0; i < modelCurrency.category_list.size(); i++) {
            if (modelCurrency.category_list.get(i).getCurrency().toString().trim().equalsIgnoreCase(currencyName)) {
                m = i;
            }
        }
        selectedCurrencyId = modelCurrency.category_list.get(m).getId().trim();
        selectedCurrencyType = modelCurrency.category_list.get(m).getCurrency().trim();
        binding.fiatTypeTxt.setText(selectedCurrencyType);
    }

    private void hitApiToLoadAssetLists() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GlobalConstants.BASE_URL, response -> {
            Helper.watchLog("EditUpdateUPiApi: ", response);
            try {
                modelAssets = gson.fromJson(response, ModelAssets.Root.class);
                if (modelAssets.resultCode.equalsIgnoreCase("200")) {
                    binding.assetTypeTxt.setText(modelAssets.spc.getTotal_spc() + " SPC");
                } else {
                    Helper.showToast(modelAssets.message, activity);
                }
            } catch (Exception e1) {
                Helper.watchLog("Excep", e1.getLocalizedMessage());
                Helper.showToast("Something went wrong", activity);
            }
        }, error -> {

            Helper.showToast(error.getLocalizedMessage(), activity);
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("module", WebURLS.userSpc);
                map.put("user_id", user_id);
                Helper.watchLog("ParamsAddUpi", map.toString());
                return map;
            }
        };

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }

    private void hitApiToLoadCurrencyList() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GlobalConstants.BASE_URL, response -> {
            Helper.watchLog("EditUpdateUPiApi: ", response);
            try {
                modelCurrency = gson.fromJson(response, ModelCurrency.Root.class);
                if (modelCurrency.resultCode.equalsIgnoreCase("200")) {
                    binding.fiatTypeTxt.setText(modelCurrency.category_list.get(0).getCurrency());
                    selectedCurrencyId = modelCurrency.category_list.get(0).getId();
                    selectedCurrencyType = modelCurrency.category_list.get(0).getCurrency();
//                    Helper.showToast(modelCurrency.message, activity);

                } else {
                    Helper.showToast(modelCurrency.message, activity);
                }
            } catch (Exception e1) {
                Helper.watchLog("Excep", e1.getLocalizedMessage());
                Helper.showToast("Something went wrong", activity);
            }
        }, error -> {

            Helper.showToast(error.getLocalizedMessage(), activity);
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("module", WebURLS.currencyList);
                Helper.watchLog("ParamsAddUpi", map.toString());
                return map;
            }
        };

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }


    private void disableButtons() {
        binding.fixedBtn.setBackground(getResources().getDrawable(R.drawable.rect_1_simple));
        binding.floatingBtn.setBackground(getResources().getDrawable(R.drawable.rect_1_simple));
    }

    private void myToolbar() {
        toolbarTitleTV.setText("Sell");
        toolbar.setNavigationIcon(R.drawable.ic_back);

//        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        toolbar.setTitleTextColor(getResources().getColor(R.color.Black));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }


}