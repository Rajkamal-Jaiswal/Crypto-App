package com.e.cellpaycrypto.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.e.cellpaycrypto.Base.CommonUtils;
import com.e.cellpaycrypto.Base.GlobalConstants;
import com.e.cellpaycrypto.Base.Helper;
import com.e.cellpaycrypto.Base.VolleySingleton;
import com.e.cellpaycrypto.WebURLS;
import com.e.cellpaycrypto.apimodels.ModelShowAds;
import com.e.cellpaycrypto.databinding.ActivityShowAdsBinding;
import com.e.cellpaycrypto.mypack.SellActivityMaster;
import com.e.cellpaycrypto.test1.AdapterMyAds;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class AdsFragment extends Fragment {

    ActivityShowAdsBinding binding;

    View root;
    RecyclerView recyclerView;
    private Gson gson;
    private Context activity;
    private SharedPreferences sharedpreferences;
    private String user_id = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = ActivityShowAdsBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        activity = getActivity();
        sharedpreferences = activity != null ? activity.getSharedPreferences(CommonUtils.MyPREFERENCES, Context.MODE_PRIVATE) : null;
        user_id = sharedpreferences.getString(CommonUtils.shared_USER_ID, "");

        recyclerView = binding.recyclerView;
        gson = new Gson();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        hitApiToLoadAddedPosts();

        binding.addSell.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), SellActivityMaster.class));
        });

        return binding.getRoot();
    }

    private void hitApiToLoadAddedPosts() {
        Helper.showLoadingDialog(activity);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GlobalConstants.BASE_URL, response -> {
            Helper.watchLog("EditUpdateUPiApi: ", response);
            Helper.hideLoadingDialog();
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
            Helper.hideLoadingDialog();
            Helper.showToast(error.getLocalizedMessage(), activity);
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("module", WebURLS.depositList);
                map.put("user_id", user_id);
                Helper.watchLog("ParamsAddUpi", map.toString());
                return map;
            }
        };

        VolleySingleton.getInstance(activity).addToRequestQueue(stringRequest);

    }

}
