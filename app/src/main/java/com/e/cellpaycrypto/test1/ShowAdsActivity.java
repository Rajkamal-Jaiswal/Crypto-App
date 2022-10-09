package com.e.cellpaycrypto.test1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.e.cellpaycrypto.Base.CommonUtils;
import com.e.cellpaycrypto.Base.GlobalConstants;
import com.e.cellpaycrypto.Base.Helper;
import com.e.cellpaycrypto.Base.VolleySingleton;
import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.WebURLS;
import com.e.cellpaycrypto.apimodels.ModelShowAds;
import com.e.cellpaycrypto.databinding.ActivityShowAdsBinding;
import com.e.cellpaycrypto.mypack.SellActivityMaster;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowAdsActivity extends AppCompatActivity {
    ActivityShowAdsBinding binding;
    RecyclerView recyclerView;
    private Toolbar toolbar;
    private Context activity;
    private Gson gson = new Gson();
    private SharedPreferences sharedpreferences;
    private String user_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowAdsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;
        sharedpreferences = getSharedPreferences(CommonUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        user_id = sharedpreferences.getString(CommonUtils.shared_USER_ID, "");

        recyclerView = binding.recyclerView;
        myToolbar();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        hitApiToLoadAds();




        binding.addSell.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), SellActivityMaster.class));
        });
    }

    private void hitApiToLoadAds() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GlobalConstants.BASE_URL, response -> {
            Helper.watchLog("EditUpdateUPiApi: ", response);
            try {
                ModelShowAds.Root modelResponse = gson.fromJson(response, ModelShowAds.Root.class);
                if (modelResponse.resultCode.equalsIgnoreCase("200")) {
                    AdapterMyAds adapterMyAds = new AdapterMyAds(modelResponse.deposit_list);
                    recyclerView.setAdapter(adapterMyAds);
                    Helper.showToast(modelResponse.message, activity);

                } else {
                    Helper.showToast(modelResponse.message, activity);
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
                map.put("module", WebURLS.depositList);
                map.put("user_id", "10");
                Helper.watchLog("ParamsAddUpi", map.toString());
                return map;
            }
        };

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }

    private void myToolbar() {
        toolbar = findViewById(R.id.main_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }
}