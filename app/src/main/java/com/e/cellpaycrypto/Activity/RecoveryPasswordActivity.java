package com.e.cellpaycrypto.Activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.e.cellpaycrypto.Base.GlobalConstants;
import com.e.cellpaycrypto.Base.Helper;
import com.e.cellpaycrypto.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RecoveryPasswordActivity extends AppCompatActivity {
    ProgressDialog dialog;
    RequestQueue queue;
    EditText etphone;
    Button submit;
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
                Toast.makeText(activity, "Connected", Toast.LENGTH_LONG).show();
            } else {
                Log.d("============", "Not Connected");
                Toast.makeText(activity, "Not Connected",
                        Toast.LENGTH_LONG).show();
            }
        }
    };
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery_password);
        activity = this;
        Helper.enableScreenShot(activity);
        queue = Volley.newRequestQueue(activity);
        etphone = findViewById(R.id.etPhone);
        submit = findViewById(R.id.btn_passwoed);

        if (isNetworkConnectionAvailable()) {
            findViewById(R.id.btn_passwoed).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String phone = etphone.toString();
                    if (phone.length() < 10) {
                        etphone.setError("Enter Phone Number");
                        Toast.makeText(RecoveryPasswordActivity.this, "Please Enter Number", Toast.LENGTH_SHORT).show();
                    } else {
                        login();
                    }
                }
            });
        } else {
            this.registerReceiver(this.mConnReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            Toast.makeText(RecoveryPasswordActivity.this, "Network Not Available", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RecoveryPasswordActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
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

    private void login() {
        dialog = new ProgressDialog(RecoveryPasswordActivity.this);
        dialog.setMessage("Please wait.");
        dialog.show();
        String inPhon = etphone.getText().toString().trim();
        ;
        map.put("module", "forgotPassword");
        map.put("phone", inPhon);
        System.out.println("vrthdcgvbvg" + map);
        readPostSignin(GlobalConstants.BASE_URL, map);

    }

    public void readPostSignin(String url, final HashMap<String, String> map) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onResponse(String response) {
                        System.out.println("tgdfgfdghfghgfh" + response);
                        try {
                            dialog.dismiss();
                            JSONObject jsonObject = new JSONObject(response);
                            String resultCode = jsonObject.getString("resultCode");
                            String message = jsonObject.getString("message");
                            if (resultCode.equalsIgnoreCase("200")) {
                                dialog.dismiss();
                                String user_id = jsonObject.getString("user_id");
                                String otp_id = jsonObject.getString("otp_id");
                                Intent intent = new Intent(RecoveryPasswordActivity.this, Regiterotp.class);
                                intent.putExtra("userid", user_id);
                                intent.putExtra("otp_id", otp_id);
                                startActivity(intent);
                            } else {
                                dialog.dismiss();
                                Toast.makeText(RecoveryPasswordActivity.this, message, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(RecoveryPasswordActivity.this, errormsg, Toast.LENGTH_SHORT).show();
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


}