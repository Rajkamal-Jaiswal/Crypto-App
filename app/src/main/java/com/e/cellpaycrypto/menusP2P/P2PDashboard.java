package com.e.cellpaycrypto.menusP2P;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.e.cellpaycrypto.Base.Helper;
import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.databinding.ActivityP2PdashboardBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class P2PDashboard extends AppCompatActivity {

    ActivityP2PdashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityP2PdashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Helper.enableScreenShot(this);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_orders, R.id.navigation_ads, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_dashboardp2p);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }
}