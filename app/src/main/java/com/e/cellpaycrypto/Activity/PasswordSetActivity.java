package com.e.cellpaycrypto.Activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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

public class PasswordSetActivity extends AppCompatActivity {
    String Userid;
    RequestQueue queue;

    EditText password, confirmPassword;
    Button submit;
    ProgressDialog dialog;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_set);
        activity = this;
        Helper.enableScreenShot(activity);
        password = findViewById(R.id.passwoed);
        Userid = getIntent().getStringExtra("userid");
        confirmPassword = findViewById(R.id.confirmPasswoed);
        submit = findViewById(R.id.btn_submit);
        queue = Volley.newRequestQueue(PasswordSetActivity.this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (password.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(PasswordSetActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                } else if (confirmPassword.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(PasswordSetActivity.this, "Enter Confirm Password", Toast.LENGTH_SHORT).show();
                } else if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
                    Toast.makeText(PasswordSetActivity.this, "Password Not Matched", Toast.LENGTH_SHORT).show();
                } else {
                    submitOtp();
                }
            }
        });
    }

    private void submitOtp() {
        dialog = new ProgressDialog(PasswordSetActivity.this);
        dialog.setMessage("Please wait.");
        dialog.show();
        HashMap<String, String> map = new HashMap<>();
        map.put("module", "setpassword");
        map.put("user_id", Userid);
        map.put("pin", password.getText().toString());
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
                                dialog.dismiss();
                                String user_id = jsonObject.getString("user_id");
                                Intent intent = new Intent(PasswordSetActivity.this, LoginActivity.class);
                                intent.putExtra("userid", user_id);
                                startActivity(intent);
                                Toast.makeText(PasswordSetActivity.this, message, Toast.LENGTH_SHORT).show();
                            } else {
                                dialog.dismiss();
                                Toast.makeText(PasswordSetActivity.this, message, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(PasswordSetActivity.this, errormsg, Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(PasswordSetActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}