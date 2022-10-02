package com.e.cellpaycrypto;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.e.cellpaycrypto.Base.CommonUtils;
import com.e.cellpaycrypto.Base.GlobalConstants;
import com.e.cellpaycrypto.Fragment.Mainfragment;
import com.e.cellpaycrypto.Fragment.ProfileFragment;
import com.e.cellpaycrypto.Fragment.WithdrawalFragment;
import com.e.cellpaycrypto.Fragment.stackFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MainActivity";
    public static Toolbar toolbar;
    Handler handler;
    Runnable runnable;
    RequestQueue queue;
    String selectedFragment = "";
    Context context;
    ProgressDialog dialog;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor prefEditor;
    TextView wallet;
    Mainfragment f1;
    FragmentManager fm;
    String userid;
    private HashMap<String, String> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.heading));

        context = MainActivity.this;

        queue = Volley.newRequestQueue(MainActivity.this);
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 500);
            }
        };
        handler.postDelayed(runnable, 500);
        f1 = new Mainfragment();
        loadFrag(f1, "home", fm);
        final Intent intent = getIntent();
        wallet = findViewById(R.id.wallet);
        sharedpreferences = getSharedPreferences(CommonUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        userid = sharedpreferences.getString(CommonUtils.shared_USER_ID, "");
        findViewById(R.id.notifsctyp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, NotificationManagement.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
            }
        });
        findViewById(R.id.ivProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileFragment f4 = new ProfileFragment();
                loadFrag(f4, "profiler", fm);
            }
        });
        availableBal();
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.allpropetie:
                        f1 = new Mainfragment();
                        loadFrag(f1, "home", fm);
                        break;

                    case R.id.accnt:
                        WithdrawalFragment f3 = new WithdrawalFragment();
                        loadFrag(f3, "withdral", fm);
                        break;
                    case R.id.myaccount:
                        stackFragment f2 = new stackFragment();
                        loadFrag(f2, "stack", fm);
                        break;
                    case R.id.support:

                        break;
                }
                return true;
            }
        });
    }

    private void availableBal() {

        map.put("module", "walletBalance");
        map.put("user_id", userid);
        System.out.println("jkhkjhkjhjk0" + map);
        readPost(GlobalConstants.BASE_URL, map);


    }

    public void readPost(String url, final HashMap<String, String> map) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    System.out.println("lsghldhlkdh" + response);
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        String resultCode = jsonObject.getString("resultCode");
                        String message = jsonObject.getString("message");
                        JSONObject jsonArray = jsonObject.getJSONObject("wallet_data");
                        if (resultCode.equalsIgnoreCase("200")) {

                            String avilable_balance = jsonArray.getString("avilable_balance");

                            wallet.setText("$" + avilable_balance);


                        } else {
                            dialog.dismiss();
                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                    }

                },
                error -> {

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
                        Toast.makeText(MainActivity.this, errormsg, Toast.LENGTH_SHORT).show();
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

    public void loadFrag(Fragment f1, String name, FragmentManager fm) {
        selectedFragment = name;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.replace(R.id.frame_nav, f1, name);
        ft.commit();

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Scanreddem.this.finish();
//                System.exit(0);
                finishAffinity();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog1, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog1.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }
}