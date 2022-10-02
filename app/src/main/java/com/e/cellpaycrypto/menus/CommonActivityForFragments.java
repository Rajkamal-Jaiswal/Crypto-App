package com.e.cellpaycrypto.menus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.e.cellpaycrypto.Fragment.Fragment_Profile_Details;
import com.e.cellpaycrypto.NotificationManagement;
import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.databinding.ActivityCommonForFragmentsBinding;
import com.e.cellpaycrypto.screens.Screen1Activity;
import com.e.cellpaycrypto.screens.SupportActivity;

public class CommonActivityForFragments extends AppCompatActivity {

    ActivityCommonForFragmentsBinding binding;
    FragmentManager fm;
    private String selectedFragment;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCommonForFragmentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity=this;
        Fragment_Profile_Details f4 = new Fragment_Profile_Details();
        addFragment(f4, "profile", fm);

        binding.appbar.headsetIc.setOnClickListener(v -> {
            Intent intent1 = new Intent(CommonActivityForFragments.this, SupportActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent1);
        });
        binding.appbar.notiIc.setOnClickListener(v -> {

            Intent intent1 = new Intent(CommonActivityForFragments.this, NotificationManagement.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent1);

        });
        binding.appbar.qrScanIc.setOnClickListener(v -> {
            Toast.makeText(this, "Coming Shortly!", Toast.LENGTH_SHORT).show();
        });
        binding.appbar.connectIc.setOnClickListener(v -> {
            startActivity(new Intent(activity, Screen1Activity.class));
        });
//        setContentView(R.layout.activity_common_for_fragments);
    }

    public void loadFrag(Fragment f1, String name, FragmentManager fm) {
        selectedFragment = name;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.replace(R.id.frame_nav, f1, name);
        ft.commit();

    }

    public void addFragment(Fragment f1, String name, FragmentManager fm) {
        selectedFragment = name;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.add(R.id.frame_nav, f1, name);
        ft.commit();

    }

}