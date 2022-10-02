package com.e.cellpaycrypto.buy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.databinding.ActivityReleaseBinding;

public class ReleaseActivity extends AppCompatActivity {

    ActivityReleaseBinding binding;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReleaseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;
        myToolbar();

        binding.btnAppeal.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Alert!");
            builder.setMessage("Do you really want to Appeal ?");
            builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                dialogInterface.dismiss();
                startActivity(new Intent(activity, AppealActivity.class));
            }).setNegativeButton("No", (dialogInterface, i) -> {
                dialogInterface.dismiss();
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        });

        binding.btnCancel.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Alert!");
            builder.setMessage("Do you really want to Cancel ?");
            builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                dialogInterface.dismiss();
                startActivity(new Intent(activity, CancelOrderActivity.class));
            }).setNegativeButton("No", (dialogInterface, i) -> {
                dialogInterface.dismiss();
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

    }

    private void myToolbar() {
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        TextView title = findViewById(R.id.toolbarTitleTV);
        title.setText("Release Order");
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