package com.e.cellpaycrypto.Activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.e.cellpaycrypto.Base.CommonUtils;
import com.e.cellpaycrypto.Base.GlobalConstants;
import com.e.cellpaycrypto.Base.Helper;
import com.e.cellpaycrypto.MainActivity;
import com.e.cellpaycrypto.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordActivity extends AppCompatActivity {
    String Userid;
    RequestQueue queue;
    ImageView igmBack;
    SharedPreferences sharedpreferences;
    EditText password, confirmPassword, oldpasswoed;
    Button submit;
    ProgressDialog dialog;

    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cahnge_passwoerd);
        activity = this;
        Helper.enableScreenShot(activity);
        password = findViewById(R.id.passwoed);
        confirmPassword = findViewById(R.id.confirmPasswoed);
        oldpasswoed = findViewById(R.id.oldpasswoed);
        submit = findViewById(R.id.btn_submit);
        igmBack = findViewById(R.id.igmBack);

        queue = Volley.newRequestQueue(ChangePasswordActivity.this);
        sharedpreferences = getSharedPreferences(CommonUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        Userid = sharedpreferences.getString(CommonUtils.shared_USER_ID, "");

        igmBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (oldpasswoed.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(ChangePasswordActivity.this, "Enter Old Password", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(ChangePasswordActivity.this, "Enter New Password", Toast.LENGTH_SHORT).show();
                } else if (confirmPassword.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(ChangePasswordActivity.this, "Enter Confirm Password", Toast.LENGTH_SHORT).show();
                } else if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
                    Toast.makeText(ChangePasswordActivity.this, "Password Not Matched", Toast.LENGTH_SHORT).show();
                } else {
                    submitOtp();
                }
            }
        });
    }

    private void submitOtp() {
        dialog = new ProgressDialog(ChangePasswordActivity.this);
        dialog.setMessage("Please wait.");
        dialog.show();
        HashMap<String, String> map = new HashMap<>();
        map.put("module", "userChangePassword");
        map.put("id", Userid);
        map.put("old_password", oldpasswoed.getText().toString());
        map.put("new_password", password.getText().toString());
        System.out.println("vrthdcgvbvg" + map);
        readPostSignp(GlobalConstants.BASE_URL, map);

    }

    public void readPostSignp(String url, final HashMap<String, String> map) {
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
                                Intent intent = new Intent(ChangePasswordActivity.this, MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(ChangePasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                            } else {
                                dialog.dismiss();
                                Toast.makeText(ChangePasswordActivity.this, message, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(ChangePasswordActivity.this, errormsg, Toast.LENGTH_SHORT).show();
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