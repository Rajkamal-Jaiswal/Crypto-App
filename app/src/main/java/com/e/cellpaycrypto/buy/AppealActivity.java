package com.e.cellpaycrypto.buy;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.databinding.ActivityAppealBinding;
import com.e.cellpaycrypto.menus.DashboardActivity;

public class AppealActivity extends AppCompatActivity {

   private ActivityAppealBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAppealBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.cancelAppeal.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Alert!");
            builder.setMessage("Do you really want to cancel this appeal ?");
            builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                dialogInterface.dismiss();


                final Dialog dialog = new Dialog(AppealActivity.this);
                dialog.setCancelable(false);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
                dialog.setContentView(R.layout.order_cancel_dialog);
                Button gotoHome = dialog.findViewById(R.id.gotoHome);
                TextView titleDialog = dialog.findViewById(R.id.titleDialog);
                titleDialog.setText("Appeal Cancelled Successfully!");
                gotoHome.setOnClickListener(v2 -> {
                    dialog.cancel();
                    startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                    finishAffinity();
                });

                try {
                    dialog.show();
                } catch (Exception e) {
                    Log.d("Dialog", "makeDialog: " + e.getMessage());
                }


            }).setNegativeButton("No", (dialogInterface, i) -> {
                dialogInterface.dismiss();
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
        myToolbar();
    }

    private void myToolbar() {
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        TextView title = findViewById(R.id.toolbarTitleTV);
        title.setText("Appeal Pending");
        TextView btn = findViewById(R.id.btn);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        btn.setVisibility(View.GONE);
        /*btn.setOnClickListener(v->{
            Toast.makeText(this, "Coming Shortly!", Toast.LENGTH_SHORT).show();
        });*/
    }
}