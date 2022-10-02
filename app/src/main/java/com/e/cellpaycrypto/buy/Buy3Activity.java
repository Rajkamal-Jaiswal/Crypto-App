package com.e.cellpaycrypto.buy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.databinding.ActivityBuy3Binding;
import com.e.cellpaycrypto.ChatAppealActivity;
import com.e.cellpaycrypto.screens.SupportActivity;

public class Buy3Activity extends AppCompatActivity {

    ActivityBuy3Binding binding;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBuy3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;
        myToolbar();


        binding.btnNext.setOnClickListener(v -> {
            startActivity(new Intent(activity, ReleaseActivity.class));
        });
        binding.btnPrevious.setOnClickListener(v -> {
//            Toast.makeText(this, "Help Page Open", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(activity, SupportActivity.class));
        });

        binding.chatBtn.setOnClickListener(v -> {
            startActivity(new Intent(
                    activity, ChatAppealActivity.class
            ));
        });
    }

    private void myToolbar() {
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        TextView title = findViewById(R.id.toolbarTitleTV);
        title.setText("");
        TextView btn = findViewById(R.id.btn);
        toolbar.setNavigationIcon(R.drawable.ic_back);
//        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        btn.setVisibility(View.GONE);
        /*btn.setOnClickListener(v->{
            Toast.makeText(this, "Coming Shortly!", Toast.LENGTH_SHORT).show();
        });*/
    }
}