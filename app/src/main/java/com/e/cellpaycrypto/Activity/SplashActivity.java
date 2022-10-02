package com.e.cellpaycrypto.Activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.e.cellpaycrypto.Base.BaseActivity;
import com.e.cellpaycrypto.Base.CommonUtils;
import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.menus.DashboardActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SplashActivity extends BaseActivity {
    SharedPreferences sharedpreferences;
    String Tokens;
    Handler handler;

    ImageView imageView;
    TextView textView;
    LinearLayoutCompat cvrBack;
    View view1;
    Animation animationUptoDown, animationDownToUp;
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
        setContentView(R.layout.activity_spalsh);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        handler = new Handler();
        sharedpreferences = getSharedPreferences(CommonUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        Tokens = sharedpreferences.getString(CommonUtils.shared_USER_ID, "");
        System.out.println("vgccvcv" + Tokens);


        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
        view1 = findViewById(R.id.view1);
        cvrBack = findViewById(R.id.cvrBack);
       /* animationUptoDown = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.uptodownanim);
        animationDownToUp = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.downtotopanim);
        imageView.setAnimation(animationUptoDown);
        textView.setAnimation(animationDownToUp);*/

        imageView.animate().translationXBy(1000).setDuration(1400).setStartDelay(1500);
        cvrBack.animate().translationY(-2200).setDuration(1400).setStartDelay(1500);
//        imageView.animate().translationY(2000).setDuration(1000).setStartDelay(2500);
        textView.animate().translationY(1400).setDuration(1400).setStartDelay(1500);
        view1.animate().translationY(1400).setDuration(1400).setStartDelay(1500);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!Tokens.equalsIgnoreCase("")) {
                    finish();
                    Intent intent = new Intent(SplashActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                } else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        }, 3000);
//        if (isNetworkConnectionAvailable()) {
//
//
//        } else {
//            this.registerReceiver(this.mConnReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
//            Toast.makeText(SpalshActivity.this, "Network Not Available", Toast.LENGTH_LONG).show();
//        }


    }

    void createJson() {


        try {
            JSONObject object = new JSONObject();
            object.put("id", 9);
            object.put("parent_permission_id", 3);
            object.put("display_name", "Add");
            object.put("isSelected", null);
            object.put("scope_value", "scope.roles.add");

            JSONArray array = new JSONArray();
            for (int i = 0; i < 3; i++) {
                array.put(i, object);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", 3);
            jsonObject.put("parent_permission_id", 0);
            jsonObject.put("display_name", "Roles");
            jsonObject.put("description", "Role Management");
            jsonObject.put("childdata", array);

            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < 2; i++) {
                jsonArray.put(i, jsonObject);
            }

            System.out.println("lksjdlfsdf__ " + jsonArray);

            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("PERMISSION", jsonArray);
            System.out.println("njhjhjjh" + jsonObject1);
            String s = String.valueOf(jsonObject1);
            System.out.println("fghfghgfh" + s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("No internet Connection");
        builder.setMessage("Please turn on internet connection to continue");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
            }
        });
        builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(SplashActivity.this, "Please Open Your Internet Connection", Toast.LENGTH_LONG).show();
                finish();
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}