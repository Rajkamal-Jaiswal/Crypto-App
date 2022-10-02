package com.e.cellpaycrypto.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.e.cellpaycrypto.Adaptor.CoinAdapter;
import com.e.cellpaycrypto.Base.GlobalConstants;
import com.e.cellpaycrypto.Getset.Coingetset;
import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.WebURLS;
import com.e.cellpaycrypto.apimodels.coinList.ModelCoinList;
import com.e.cellpaycrypto.databinding.ActivityCoinListBinding;
import com.e.cellpaycrypto.mypack.OrdersActivity;
import com.e.cellpaycrypto.mypack.P2PMethodsActivity;
import com.e.cellpaycrypto.mypack.SellActivityMaster;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class P2PFragment extends Fragment {

    ActivityCoinListBinding binding;

    RecyclerView listView;
    RequestQueue queue;
    Coingetset myListData1;
    List<Coingetset> listdd;
    String status = "";
    ProgressDialog dialog;
    HashMap<String, String> map1 = new HashMap<>();
    private Toolbar toolbar;
    private TextView toolbarTitleTV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = ActivityCoinListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        listdd = new ArrayList<>();
        listView = root.findViewById(R.id.listView);
        toolbar = root.findViewById(R.id.main_toolbar);
        toolbarTitleTV = root.findViewById(R.id.toolbarTitleTV);
        myToolbar();
        queue = Volley.newRequestQueue(getActivity());
//        setLoginTask();


        status = "1";
        disableButtons();
        binding.buyBtn.setBackground(getResources().getDrawable(R.drawable.rect2));
        binding.buyBtn.setTypeface(null, Typeface.BOLD);

        binding.buyBtn.setOnClickListener(v -> {
            status = "1";
            disableButtons();
            binding.buyBtn.setBackground(getResources().getDrawable(R.drawable.rect2));
            binding.buyBtn.setTypeface(null, Typeface.BOLD);
        });
        binding.sellBtn.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), SellActivityMaster.class));
          /*  status = "0";
            disableButtons();
            binding.sellBtn.setBackground(getResources().getDrawable(R.drawable.rect2));
            binding.sellBtn.setTypeface(null, Typeface.BOLD);*/
        });


        binding.p2pMethodsBtn.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), P2PMethodsActivity.class));
        });
        binding.OrdersBtn.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), OrdersActivity.class));
        });
        binding.sendMoneyBtn.setVisibility(View.GONE);
        return binding.getRoot();
    }

    private void disableButtons() {

        binding.buyBtn.setBackground(getResources().getDrawable(R.drawable.rect_normal));
        binding.sellBtn.setBackground(getResources().getDrawable(R.drawable.rect_normal));
        setLoginTask();

    }

    private void myToolbar() {
        toolbarTitleTV.setText("P2P");
        toolbar.setNavigationIcon(R.drawable.ic_back);
//        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);

        toolbar.setTitleTextColor(getResources().getColor(R.color.Black));
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
    }

    public void setLoginTask() {
        listView.setVisibility(View.GONE);
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Please wait.");
        dialog.show();
        map1.put("module", WebURLS.URL_P2P_COIN_LIST);
        readPostSignin(GlobalConstants.BASE_URL, map1);
    }

    public void readPostSignin(String url, final HashMap<String, String> map) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    listView.setVisibility(View.VISIBLE);
                    try {
                        dialog.dismiss();

                        Gson gson = new Gson();
                        ModelCoinList modelCoinList = gson.fromJson(response, ModelCoinList.class);
                        if (modelCoinList.getResultCode().equalsIgnoreCase("200")) {
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                            CoinAdapter adapter = new CoinAdapter(modelCoinList.getCoinList(), getActivity(), status);
                            listView.setLayoutManager(layoutManager);
                            listView.setAdapter(adapter);
                            listView.setNestedScrollingEnabled(false);
                        } else {

                        }
                    /*    JSONObject jsonObject = new JSONObject(response);
                        String resultCode = jsonObject.getString("resultCode");
                        String message = jsonObject.getString("message");
                        JSONArray memberlist = jsonObject.getJSONArray("coin_list");
                        if (resultCode.equalsIgnoreCase("200")) {
                            dialog.dismiss();
                            listdd.clear();
                            for (int i = 0; i < memberlist.length(); i++) {
                                JSONObject list = memberlist.getJSONObject(i);
                                String title = list.getString("title");
                                String id = list.getString("id");
                                String cost = list.getString("cost");
                                String logo_1 = list.getString("logo_1");
                                myListData1 = new Coingetset(id, title, cost, logo_1);
                                System.out.println("klhfl" + myListData1);
                                listdd.add(myListData1);
                            }


                        } else {
                            dialog.dismiss();
                            listdd.clear();
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        }*/
                    } catch (Exception e) {
                    }

                },
                error -> {
                    Toast toast = Toast.makeText(getActivity(), "Server Error! Try again after some time.", Toast.LENGTH_LONG);
                    View view1 = toast.getView();
                    toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);

                    TextView text = (TextView) view1.findViewById(android.R.id.message);
                    text.setGravity(View.TEXT_ALIGNMENT_CENTER);
                    text.setTextSize(12);

                    toast.show();

                    try {
                        dialog.dismiss();
                        JSONObject jsonObject = new JSONObject(new String(error.networkResponse.data));
                        String errormsg = jsonObject.getString("message");
                        Toast.makeText(getActivity(), errormsg, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Log.d("logger - SignInActivity", "Error while parsing Login Error Response: " + e.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                return map;
            }
        };
        queue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
}

