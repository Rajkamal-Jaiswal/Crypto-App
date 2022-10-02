package com.e.cellpaycrypto;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddBankTransferIndiaActivity extends AppCompatActivity {
    EditText etFullName, etAccountNo, etIfscCode, etAccOpenBra;
    Spinner spinnBankType, spinBankName;
    Button btnConfirm;
    RequestQueue queue;
    String Type;
    String userid;
    SharedPreferences sharedpreferences;
    TextView nametype;
    List<Map<String, Object>> membershipTitle1 = new ArrayList<>();
    List<Map<String, Object>> membershipTitle12 = new ArrayList<>();
    String bankname, accounttypr;
    ProgressDialog dialog;
    private HashMap<String, String> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank_transfer_india);
        queue = Volley.newRequestQueue(AddBankTransferIndiaActivity.this);
        etFullName = findViewById(R.id.etFullName);
        sharedpreferences = getSharedPreferences(CommonUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        userid = sharedpreferences.getString(CommonUtils.shared_USER_ID, "");
        nametype = findViewById(R.id.nametype);
        Type = getIntent().getStringExtra("BankAdd");
        if (Type != null) {
            if (Type.equalsIgnoreCase("BankAdd")) {
                nametype.setText("Add Bank Transfer (India)");
            } else if (Type.equalsIgnoreCase("IMPS")) {
                nametype.setText("Add IMPS");
            }
        } else {
            Type = "BankAdd";
        }
        etAccountNo = findViewById(R.id.etAccountNo);
        etIfscCode = findViewById(R.id.etIfscCode);
        etAccOpenBra = findViewById(R.id.etAccOpenBra);
        spinBankName = findViewById(R.id.spinBankName);
        spinnBankType = findViewById(R.id.spinnBankType);
        btnConfirm = findViewById(R.id.btnConfirm);
        setLognTask();
        setLognTask1();
        btnConfirm.setBackgroundResource(R.drawable.btnnormsalbavk);
        btnConfirm.setTextColor(getResources().getColor(R.color.Black));
        spinBankName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> selectedItem1 = membershipTitle1.get(spinBankName.getSelectedItemPosition());
                bankname = selectedItem1.get("name").toString();
                String id1 = selectedItem1.get("id").toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnBankType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> selectedItem12 = membershipTitle12.get(spinnBankType.getSelectedItemPosition());
                accounttypr = selectedItem12.get("name").toString();
                String id2 = selectedItem12.get("id").toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnConfirm.setBackgroundResource(R.drawable.btnbxckl);
                btnConfirm.setTextColor(getResources().getColor(R.color.white));

                if (etFullName.getText().toString().isEmpty()) {
                    etFullName.setError("Enter Name");
                } else if (etAccountNo.getText().toString().isEmpty()) {
                    etAccountNo.setError("Enter Account numbere");
                } else if (etIfscCode.getText().toString().isEmpty()) {
                    etIfscCode.setError("Enter IFSC");
                } else if (etAccOpenBra.getText().toString().isEmpty()) {
                    etAccOpenBra.setError("Enter Branch Name");
                } else {
                    register();
                }
            }
        });
    }

    private void register() {
        dialog = new ProgressDialog(AddBankTransferIndiaActivity.this);
        dialog.setMessage("Please wait.");
        dialog.show();
        String name = etFullName.getText().toString().trim();
        String phone = etAccountNo.getText().toString().trim();
        String email = etIfscCode.getText().toString().trim();
        String address = etAccOpenBra.getText().toString().trim();
        if (Type.equalsIgnoreCase("IMPS")) {
            map.put("module", "addBankDetails");
            map.put("user_id", userid);
            map.put("account_holder_name", name);
            map.put("account_number", phone);
            map.put("ifsc_code", email);
            map.put("account_type", accounttypr);
            map.put("bank_name", bankname);
            map.put("branch_name", address);
            map.put("transfer_mode", "IMPS");
            System.out.println("vrthdcgvbvg" + map);
            readPostSignUp(GlobalConstants.BASE_URL, map);
        } else {
            map.put("module", "addBankDetails");
            map.put("user_id", userid);
            map.put("account_holder_name", name);
            map.put("account_number", phone);
            map.put("ifsc_code", email);
            map.put("account_type", accounttypr);
            map.put("bank_name", bankname);
            map.put("branch_name", address);
            System.out.println("vrthdcgvbvg" + map);
            readPostSignUp(GlobalConstants.BASE_URL, map);
        }


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
                                Toast.makeText(AddBankTransferIndiaActivity.this, message, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AddBankTransferIndiaActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                dialog.dismiss();
                                Toast.makeText(AddBankTransferIndiaActivity.this, message, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(AddBankTransferIndiaActivity.this, errormsg, Toast.LENGTH_SHORT).show();
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

    public void setLognTask() {
//        dialog = new ProgressDialog(Customers_Entry.this);
//        dialog.setMessage("Please wait.");
//        dialog.show();
        HashMap<String, String> map = new HashMap<>();
        map.put("module", "bankList");
        readPostSgnin(GlobalConstants.BASE_URL, map);

    }

    public void readPostSgnin(String url, final HashMap<String, String> map) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("rtrtyrtyty" + response);

                        try {
                            //dialog.dismiss();
                            JSONObject jsonObject = new JSONObject(response);
                            String resultCode = jsonObject.getString("resultCode");
                            String message = jsonObject.getString("message");
                            JSONArray jsonArray = jsonObject.getJSONArray("bank_list");
                            if (resultCode.equalsIgnoreCase("200")) {
                                // dialog.dismiss();

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String id = object.getString("id");
                                    System.out.println("bhgvgcfgcnffc" + id);
                                    String title = object.getString("bank_name");
                                    //  String description = object.getString("price");
                                    System.out.println("bhgfcfxfdfc");
                                    Map<String, Object> item = new HashMap<>();
                                    item.put("id", id);
                                    item.put("name", title);
                                    membershipTitle1.add(item);
                                }

                                SimpleAdapter arrayAdapter = new SimpleAdapter(AddBankTransferIndiaActivity.this, membershipTitle1,
                                        R.layout.spinneritemback,
                                        new String[]{"name"}, new int[]{R.id.text1});
                                arrayAdapter.setDropDownViewResource(R.layout.spinnershowitemdropdown);
                                spinBankName.setAdapter(arrayAdapter);

                                Map<String, Object> selectedItem1 = membershipTitle1.get(spinBankName.getSelectedItemPosition());
                                String name = selectedItem1.get("name").toString();
                                String id = selectedItem1.get("id").toString();
                                System.out.println("dkhkjhgjkdjkd" + name + id);

                            } else {
                                // dialog.dismiss();
                                //  boo.setVisibility(View.GONE);
                                Toast.makeText(AddBankTransferIndiaActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            //boo.setVisibility(View.GONE);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(AddBankTransferIndiaActivity.this, "Server Error! Try again after some time.", Toast.LENGTH_LONG);
                        View view1 = toast.getView();
                        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);

                        TextView text = (TextView) view1.findViewById(android.R.id.message);
                        text.setGravity(View.TEXT_ALIGNMENT_CENTER);
                        text.setTextSize(12);

                        toast.show();

                        try {
                            //  dialog.dismiss();
                            JSONObject jsonObject = new JSONObject(new String(error.networkResponse.data));
                            String errormsg = jsonObject.getString("message");
                            Toast.makeText(AddBankTransferIndiaActivity.this, errormsg, Toast.LENGTH_SHORT).show();
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

    public void setLognTask1() {
//        dialog = new ProgressDialog(Customers_Entry.this);
//        dialog.setMessage("Please wait.");
//        dialog.show();
        HashMap<String, String> map = new HashMap<>();
        map.put("module", "bankAccountTypes");
        readPostSgin(GlobalConstants.BASE_URL, map);
        System.out.println("jjkhkhjh" + map);
    }

    public void readPostSgin(String url, final HashMap<String, String> map) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("rtrtyrtyty" + response);

                        try {
                            //dialog.dismiss();
                            JSONObject jsonObject = new JSONObject(response);
                            String resultCode = jsonObject.getString("resultCode");
                            String message = jsonObject.getString("message");
                            JSONArray jsonArray = jsonObject.getJSONArray("account_type_list");
                            if (resultCode.equalsIgnoreCase("200")) {
                                // dialog.dismiss();

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String id = object.getString("id");
                                    System.out.println("bhgvgcfgcnffc" + id);
                                    String title = object.getString("account_type");
                                    //  String description = object.getString("price");
                                    System.out.println("bhgfcfxfdfc");
                                    Map<String, Object> item = new HashMap<>();
                                    item.put("id", id);
                                    item.put("name", title);
                                    membershipTitle12.add(item);
                                }

                                SimpleAdapter arrayAdapter = new SimpleAdapter(AddBankTransferIndiaActivity.this, membershipTitle12,
                                        R.layout.spinneritemback,
                                        new String[]{"name"}, new int[]{R.id.text1});
                                arrayAdapter.setDropDownViewResource(R.layout.spinnershowitemdropdown);
                                spinnBankType.setAdapter(arrayAdapter);

                                Map<String, Object> selectedItem12 = membershipTitle12.get(spinnBankType.getSelectedItemPosition());
                                String name = selectedItem12.get("name").toString();
                                String id = selectedItem12.get("id").toString();
                                System.out.println("dkhkjhgjkdjkd" + name + id);

                            } else {
                                // dialog.dismiss();
                                //  boo.setVisibility(View.GONE);
                                Toast.makeText(AddBankTransferIndiaActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            //boo.setVisibility(View.GONE);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(AddBankTransferIndiaActivity.this, "Server Error! Try again after some time.", Toast.LENGTH_LONG);
                        View view1 = toast.getView();
                        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);

                        TextView text = (TextView) view1.findViewById(android.R.id.message);
                        text.setGravity(View.TEXT_ALIGNMENT_CENTER);
                        text.setTextSize(12);

                        toast.show();

                        try {
                            //  dialog.dismiss();
                            JSONObject jsonObject = new JSONObject(new String(error.networkResponse.data));
                            String errormsg = jsonObject.getString("message");
                            Toast.makeText(AddBankTransferIndiaActivity.this, errormsg, Toast.LENGTH_SHORT).show();
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