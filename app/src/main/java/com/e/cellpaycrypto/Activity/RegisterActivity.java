package com.e.cellpaycrypto.Activity;

import android.annotation.TargetApi;
import android.app.Activity;
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
import com.e.cellpaycrypto.Base.GlobalConstants;
import com.e.cellpaycrypto.Base.Helper;
import com.e.cellpaycrypto.R;
import com.hbb20.CountryCodePicker;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends BaseActivity {

    Activity activity;
    EditText etName, etEmail, etPhone, etAddress, etReferalCode;
    TextView etLoginTv;
    Button registerBtn;
    ProgressDialog dialog;
    SharedPreferences sharedpreferences;
    RequestQueue queue;
    SharedPreferences.Editor prefEditor;
    int phone;
    String jw;
    CountryCodePicker countryCodePicker;
    private HashMap<String, String> map = new HashMap<>();
    private NetworkStateReceiver networkStateReceiver;
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
        setContentView(R.layout.activity_register);
        activity = this;
        Helper.enableScreenShot(activity);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhoneNumber);
        etAddress = findViewById(R.id.etAddress);
        etReferalCode = findViewById(R.id.etReferelCode);
        etLoginTv = findViewById(R.id.loginacc);
        registerBtn = findViewById(R.id.btn_register);
        countryCodePicker = findViewById(R.id.ccp);
        queue = Volley.newRequestQueue(this);
        checkLocationPermission();
        String j = countryCodePicker.getDefaultCountryName();
        jw = countryCodePicker.getDefaultCountryCode();
        System.out.println("kjhkhkk" + j + "     " + jw);


        etLoginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finishActivity();
            }
        });

//        registerBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(RegisterActivity.this,Regiterotp.class));
//                finishActivity();
//            }
//        });
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (isNetworkConnectionAvailable()) {
            findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (etName.getText().toString().equalsIgnoreCase("")) {
                        etName.setError("Enter Name");
                        Toast.makeText(RegisterActivity.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                    } else if (etPhone.getText().toString().equalsIgnoreCase("")) {
                        etPhone.setError("Enter Phone");
                        Toast.makeText(RegisterActivity.this, "Please Enter Phone Number", Toast.LENGTH_SHORT).show();
                    } else if ((etPhone.getText().toString().length() < 10) || (etPhone.getText().toString().length() > 10)) {
                        Toast.makeText(RegisterActivity.this, "Please Enter 10 Digit Valid Phone Number", Toast.LENGTH_SHORT).show();
                    } else if (etEmail.getText().toString().equalsIgnoreCase("")) {
                        etEmail.setError("Enter Email");
                        Toast.makeText(RegisterActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    } else if (!etEmail.getText().toString().matches(emailPattern)) {
                        Toast.makeText(RegisterActivity.this, "Please Enter Valid Email", Toast.LENGTH_SHORT).show();
                    } else if (etAddress.getText().toString().equalsIgnoreCase("")) {
                        etAddress.setError("Enter Address");
                        Toast.makeText(RegisterActivity.this, "Please Enter Address", Toast.LENGTH_SHORT).show();
                    } else {
                        register();


                    }
                }
            });
        } else {
            this.registerReceiver(this.mConnReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            Toast.makeText(RegisterActivity.this, "Network Not Available", Toast.LENGTH_LONG).show();
        }

    }

    private void register() {
        dialog = new ProgressDialog(RegisterActivity.this);
        dialog.setMessage("Please wait.");
        dialog.show();
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String referalCode = etReferalCode.getText().toString().trim();
        String phonewithcode = "+91" + phone;
        System.out.println("hjgjghjgj" + phonewithcode);
        map.put("module", "userSignup");
        map.put("name", name);
        map.put("country_code", jw);
        map.put("phone", phone);
        map.put("email", email);
        map.put("address", address);
        map.put("referral_code", referalCode);
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
                                String otp = jsonObject.getString("otp");
                                String otp_id = jsonObject.getString("otp_id");
                                Intent intent = new Intent(RegisterActivity.this, Regiterotp.class);
                                intent.putExtra("userid", user_id);
                                intent.putExtra("otp_id", otp_id);
                                startActivity(intent);
                            } else {
                                dialog.dismiss();
                                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(RegisterActivity.this, errormsg, Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
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

}