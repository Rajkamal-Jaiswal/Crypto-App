package com.e.cellpaycrypto.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cellpaycrypto.Adaptor.AdapterActiveInActive;
import com.e.cellpaycrypto.Base.Helper;
import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.databinding.ActivityActiveInactiveBinding;

public class ActiveInactiveActivity extends AppCompatActivity {
    ActivityActiveInactiveBinding binding;

    RecyclerView recyclerView;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityActiveInactiveBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;
        Helper.enableScreenShot(activity);
        myToolbar(getIntent().getStringExtra("key"));
        recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AdapterActiveInActive adapterActiveInActive = new AdapterActiveInActive(getIntent().getStringExtra("key"), activity);
        recyclerView.setAdapter(adapterActiveInActive);
    }

    private void myToolbar(String ttl) {
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        TextView toolbarTitleTV = findViewById(R.id.toolbarTitleTV);
        toolbarTitleTV.setText(ttl);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setTitleTextColor(getResources().getColor(R.color.Black));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }
}