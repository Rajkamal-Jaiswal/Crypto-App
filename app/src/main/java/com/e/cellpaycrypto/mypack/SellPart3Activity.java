package com.e.cellpaycrypto.mypack;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.e.cellpaycrypto.Base.GlobalConstants;
import com.e.cellpaycrypto.Base.Helper;
import com.e.cellpaycrypto.Base.VolleySingleton;
import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.WebURLS;
import com.e.cellpaycrypto.apimodels.ModelResponse;
import com.e.cellpaycrypto.databinding.ActivitySellPart3Binding;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class SellPart3Activity extends AppCompatActivity {

    ActivitySellPart3Binding binding;
    private Toolbar toolbar;
    private TextView toolbarTitleTV;
    Activity activity;
    private ModelResponse modelSubmitResponse;
    private Gson gson;
    private HashMap<String, String> hashMap;

    private String termsTxt = "";
    private String autoReplyTxt = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySellPart3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;
        gson = new Gson();

        hashMap = (HashMap<String, String>) getIntent().getSerializableExtra("map");

        toolbar = findViewById(R.id.main_toolbar);
        toolbarTitleTV = findViewById(R.id.toolbarTitleTV);
        myToolbar();


        binding.btnPrevious.setOnClickListener(v -> {
            onBackPressed();
        });
        binding.btnNext.setOnClickListener(v -> {
            termsTxt = binding.termsTxt.getText().toString().trim();
            autoReplyTxt = binding.autoReplyTxt.getText().toString().trim();
            if (TextUtils.isEmpty(termsTxt)) {
                termsTxt = "";
            }
            if (TextUtils.isEmpty(autoReplyTxt)) {
                autoReplyTxt = "";
            }
            hitApiToSubmitSellRequest();
        });


    }

    private void hitApiToSubmitSellRequest() {
        Helper.showLoadingDialog(activity);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GlobalConstants.BASE_URL, response -> {
            Helper.watchLog("EditUpdateUPiApi: ", response);
            Helper.hideLoadingDialog();
            try {
                modelSubmitResponse = gson.fromJson(response, ModelResponse
                        .class);
                if (modelSubmitResponse.getResultCode().equalsIgnoreCase("200")) {
                    Helper.showToast(modelSubmitResponse.getMessage(), activity);
                } else {
                    Helper.showToast(modelSubmitResponse.getMessage(), activity);
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
                hashMap.put("module", WebURLS.sellRequest);
                hashMap.put("terms", termsTxt);
                hashMap.put("auto_reply", autoReplyTxt);
                Helper.watchLog("ParamsAddUpi", hashMap.toString());
                return hashMap;
            }
        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private void myToolbar() {
        toolbarTitleTV.setText("Sell");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setTitleTextColor(getResources().getColor(R.color.Black));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }
}