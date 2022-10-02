package com.e.cellpaycrypto.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.e.cellpaycrypto.Base.GlobalConstants;
import com.e.cellpaycrypto.Base.Helper;
import com.e.cellpaycrypto.Getset.Coingetset;
import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.databinding.ActivityCoinListBinding;
import com.e.cellpaycrypto.mypack.OrdersActivity;
import com.e.cellpaycrypto.mypack.P2PMethodsActivity;
import com.e.cellpaycrypto.mypack.SellActivityMaster;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class P2PActivity extends AppCompatActivity {
    RecyclerView listView;
    RequestQueue queue;
    Coingetset myListData1;
    List<Coingetset> listdd;
    ActivityCoinListBinding binding;
    String status = "";
    ProgressDialog dialog;
    HashMap<String, String> map1 = new HashMap<>();
    private Toolbar toolbar;
    private TextView toolbarTitleTV;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCoinListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;
        Helper.enableScreenShot(activity);
        listdd = new ArrayList<>();

        listView = findViewById(R.id.listView);

        toolbar = findViewById(R.id.main_toolbar);
        toolbarTitleTV = findViewById(R.id.toolbarTitleTV);
        myToolbar();

        queue = Volley.newRequestQueue(P2PActivity.this);
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
            startActivity(new Intent(activity, SellActivityMaster.class));
          /*  status = "0";
            disableButtons();
            binding.sellBtn.setBackground(getResources().getDrawable(R.drawable.rect2));
            binding.sellBtn.setTypeface(null, Typeface.BOLD);*/
        });
        binding.p2pMethodsBtn.setVisibility(View.GONE);

        binding.p2pMethodsBtn.setOnClickListener(v -> {
            startActivity(new Intent(activity, P2PMethodsActivity.class));
        });
        binding.OrdersBtn.setOnClickListener(v -> {
            startActivity(new Intent(activity, OrdersActivity.class));
        });
        binding.sendMoneyBtn.setVisibility(View.GONE);
    }

    private void disableButtons() {

        binding.buyBtn.setBackground(getResources().getDrawable(R.drawable.rect_normal));
        binding.sellBtn.setBackground(getResources().getDrawable(R.drawable.rect_normal));
//        setLoginTask();

    }

    private void myToolbar() {
        toolbarTitleTV.setText("P2P");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setTitleTextColor(getResources().getColor(R.color.Black));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    public void setLoginTask() {
        listView.setVisibility(View.GONE);
        dialog = new ProgressDialog(P2PActivity.this);
        dialog.setMessage("Please wait.");
        dialog.show();
        map1.put("module", "coinList");
        readPostSignin(GlobalConstants.BASE_URL, map1);
        System.out.println("jhgjgg" + map1);
    }

    public void readPostSignin(String url, final HashMap<String, String> map) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
//                    System.out.println("klhdjflk" + response);
                    listView.setVisibility(View.VISIBLE);
                    try {
                        dialog.dismiss();
                        JSONObject jsonObject = new JSONObject(response);
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

                            LinearLayoutManager layoutManager = new LinearLayoutManager(P2PActivity.this, LinearLayoutManager.VERTICAL, false);
//                            CoinAdapter adapter = new CoinAdapter(listdd, P2PActivity.this, status);
                            listView.setLayoutManager(layoutManager);
//                            listView.setAdapter(adapter);
                            listView.setNestedScrollingEnabled(false);
                        } else {
                            dialog.dismiss();
                            listdd.clear();
                            Toast.makeText(P2PActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                    }

                },
                error -> {
                    Toast toast = Toast.makeText(P2PActivity.this, "Server Error! Try again after some time.", Toast.LENGTH_LONG);
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
                        Toast.makeText(P2PActivity.this, errormsg, Toast.LENGTH_SHORT).show();
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