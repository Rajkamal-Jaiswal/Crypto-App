package com.e.cellpaycrypto.Activity;


import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.e.cellpaycrypto.Base.BaseActivity;
import com.e.cellpaycrypto.Base.CommonUtils;
import com.e.cellpaycrypto.Base.GlobalConstants;
import com.e.cellpaycrypto.MainActivity;
import com.e.cellpaycrypto.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WithdrawalRequestActivity extends BaseActivity {

    ImageView imgClose;
    EditText etEnterAmount, etBankName, etAccountName, etAccountNo, etIFSC, etNote;
    Button btn_submit;
    TextView tvAvailableBal, tVeligible_for_withdrawal;
    ProgressDialog dialog;
    RequestQueue queue;
    SharedPreferences sharedpreferences;
    String userid;
    private HashMap<String, String> map = new HashMap<>();
    private BroadcastReceiver mConnReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
            String reason = intent.getStringExtra(ConnectivityManager.EXTRA_REASON);
            boolean isFailover = intent.getBooleanExtra(ConnectivityManager.EXTRA_IS_FAILOVER, false);

            NetworkInfo currentNetworkInfo = (NetworkInfo) intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
            NetworkInfo otherNetworkInfo = (NetworkInfo) intent.getParcelableExtra(ConnectivityManager.EXTRA_OTHER_NETWORK_INFO);

            if (currentNetworkInfo.isConnected()) {
                Log.d("=============", "Connected");
                finish();
                startActivity(getIntent());
                Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_LONG).show();
            } else {
                Log.d("============", "Not Connected");
                Toast.makeText(getApplicationContext(), "Not Connected",
                        Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal_request);

        imgClose = findViewById(R.id.imgClose);
        etEnterAmount = findViewById(R.id.etEnterAmount);
        etBankName = findViewById(R.id.etBankName);
        etAccountName = findViewById(R.id.etAccountName);
        etAccountNo = findViewById(R.id.etAccountTwo);
        etIFSC = findViewById(R.id.etIFSC);
        etNote = findViewById(R.id.etNote);
        btn_submit = findViewById(R.id.btn_submit);
        tvAvailableBal = findViewById(R.id.tvAvailableBal);
        tVeligible_for_withdrawal = findViewById(R.id.tVeligible_for_withdrawal);

        queue = Volley.newRequestQueue(this);
        checkLocationPermission();
        sharedpreferences = getSharedPreferences(CommonUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        userid = sharedpreferences.getString(CommonUtils.shared_USER_ID, "");
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WithdrawalRequestActivity.this, MainActivity.class));
                finishActivity();
            }
        });


        if (isNetworkConnectionAvailable()) {
            availableBal();
            findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (etEnterAmount.getText().toString().equalsIgnoreCase("")) {
                        etEnterAmount.setError("Enter Amount");
                        Toast.makeText(WithdrawalRequestActivity.this, "Please Enter Amount", Toast.LENGTH_SHORT).show();
                    } else if (etBankName.getText().toString().equalsIgnoreCase("")) {
                        etBankName.setError("Enter Bank Name");
                        Toast.makeText(WithdrawalRequestActivity.this, "Please Enter Bank Name", Toast.LENGTH_SHORT).show();
                    } else if (etAccountName.getText().toString().equalsIgnoreCase("")) {
                        etAccountName.setError("Enter Name");
                        Toast.makeText(WithdrawalRequestActivity.this, "Please Enter Account Number", Toast.LENGTH_SHORT).show();
                    } else if (etAccountNo.getText().toString().equalsIgnoreCase("")) {
                        etAccountNo.setError("Enter Account Number");
                        Toast.makeText(WithdrawalRequestActivity.this, "Re Enter account number", Toast.LENGTH_SHORT).show();
                    } else if (etIFSC.getText().toString().equalsIgnoreCase("")) {
                        etIFSC.setError("Enter IFSC Code");
                        Toast.makeText(WithdrawalRequestActivity.this, "Please Enter IFSC Code", Toast.LENGTH_SHORT).show();
                    } else {
                        register();
                    }
                }
            });
        } else {
            this.registerReceiver(this.mConnReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            Toast.makeText(WithdrawalRequestActivity.this, "Network Not Available", Toast.LENGTH_LONG).show();
        }

    }

    private void availableBal() {

        map.put("module", "walletBalance");
        map.put("user_id", userid);
        System.out.println("jkhkjhkjhjk0" + map);
        readPost(GlobalConstants.BASE_URL, map);


    }

    public void readPost(String url, final HashMap<String, String> map) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onResponse(String response) {
                        System.out.println("lsghldhlkdh" + response);
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String resultCode = jsonObject.getString("resultCode");
                            String message = jsonObject.getString("message");
                            JSONObject jsonArray = jsonObject.getJSONObject("wallet_data");
                            if (resultCode.equalsIgnoreCase("200")) {

                                String avilable_balance = jsonArray.getString("avilable_balance");
                                String eligible_for_withdrawal = jsonArray.getString("eligible_for_withdrawal");
                                tvAvailableBal.setText("Rs " + avilable_balance);
                                tVeligible_for_withdrawal.setText("Rs " + eligible_for_withdrawal);

                            } else {
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        NetworkResponse response = error.networkResponse;
                        String errorMsg = "";
                        if (response != null && response.data != null) {
                            String errorString = new String(response.data);
                            Log.i("log error", errorString);
                        }
                        try {
                            dialog.dismiss();
                            JSONObject jsonObject = new JSONObject(new String(error.networkResponse.data));
                            String errormsg = jsonObject.getString("message");
                            Toast.makeText(getApplicationContext(), errormsg, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.d("logger - SignInActivity", "Error while parsing Login Error Response: " + e.toString());
                        }
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

    private void register() {
        dialog = new ProgressDialog(WithdrawalRequestActivity.this);
        dialog.setMessage("Please wait.");
        dialog.show();
        String amount = etEnterAmount.getText().toString().trim();
        String bank_name = etBankName.getText().toString().trim();
        String account_name = etAccountName.getText().toString().trim();
        String account_number = etAccountNo.getText().toString().trim();
        String ifsc_code = etIFSC.getText().toString().trim();
        String note = etNote.getText().toString().trim();
        map.put("module", "withdrawalRequest");
        map.put("amount", amount);
        map.put("bank_name", bank_name);
        map.put("user_id", userid);
        map.put("account_name", account_name);
        map.put("account_number", account_number);
        map.put("ifsc_code", ifsc_code);
        map.put("note", note);
        System.out.println("vrthdcgvbvg" + map);
        readPostSignUp(GlobalConstants.BASE_URL, map);
    }

    public void readPostSignUp(String url, final HashMap<String, String> map) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onResponse(String response) {
                        System.out.println("lsghldhlkdh" + response);
                        try {
                            dialog.dismiss();
                            JSONObject jsonObject = new JSONObject(response);
                            String resultCode = jsonObject.getString("resultCode");
                            String message = jsonObject.getString("message");
                            if (resultCode.equalsIgnoreCase("200")) {
                                dialog.dismiss();
                                String user_id = jsonObject.getString("user_id");
                                Intent intent = new Intent(WithdrawalRequestActivity.this, MainActivity.class);
                                Toast.makeText(WithdrawalRequestActivity.this, message, Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            } else {
                                dialog.dismiss();
                                Toast.makeText(WithdrawalRequestActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        NetworkResponse response = error.networkResponse;
                        String errorMsg = "";
                        if (response != null && response.data != null) {
                            String errorString = new String(response.data);
                            Log.i("log error", errorString);
                        }
                        try {
                            dialog.dismiss();
                            JSONObject jsonObject = new JSONObject(new String(error.networkResponse.data));
                            String errormsg = jsonObject.getString("message");
                            Toast.makeText(WithdrawalRequestActivity.this, errormsg, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.d("logger - SignInActivity", "Error while parsing Login Error Response: " + e.toString());
                        }
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(WithdrawalRequestActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public boolean checkLocationPermission() {
        String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    public boolean isNetworkConnectionAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        if (isConnected) {
            Log.d("Network", "Connected");

            return true;
        } else {
            checkNetworkConnection();
            Log.d("Network", "Not Connected");
            return false;
        }
    }

    public void checkNetworkConnection() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("No internet Connection");
        builder.setMessage("Please turn on internet connection to continue");
        builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        android.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    public void bavck(View view) {
        onBackPressed();
    }
}
