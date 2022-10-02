package com.e.cellpaycrypto;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cellpaycrypto.databinding.ActivityChatAppealBinding;
import com.e.cellpaycrypto.test1.ModelPojo2;

import java.util.ArrayList;
import java.util.List;

public class ChatAppealActivity extends AppCompatActivity {

    ActivityChatAppealBinding binding;
    RecyclerView recyclerView;
    List<ModelPojo2> modelPojo2s = new ArrayList<>();
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatAppealBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = ChatAppealActivity.this;
        myToolbar();
        recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        modelPojo2s.add(new ModelPojo2("1", "I'm Online"));
        modelPojo2s.add(new ModelPojo2("1", "Make Payment"));
        modelPojo2s.add(new ModelPojo2("2", "Hi I'm online pls pay fast  and I'll release  crypto soon"));
        modelPojo2s.add(new ModelPojo2("1", "You want any help? for trade"));
        modelPojo2s.add(new ModelPojo2("2", "Please check and release  asset"));
        AdapterChat adapterChat = new AdapterChat(this, modelPojo2s);
        recyclerView.setAdapter(adapterChat);

        binding.cancelCvrFake.setOnClickListener(v -> {
            binding.cvrFakeNews.setVisibility(View.GONE);
        });

        binding.btnDetails.setOnClickListener(v -> {
            final Dialog dialog = new Dialog(activity);
            dialog.setCancelable(false);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
            dialog.setContentView(R.layout.dialog_risk_notice);
          /*  TextView titleDialog = (TextView) dialog.findViewById(R.id.titleDialog);
            TextView msgDialog = (TextView) dialog.findViewById(R.id.msgDialog);
            TextView descTv = (TextView) dialog.findViewById(R.id.nhbvh);*/
            Button btn_submit = (Button) dialog.findViewById(R.id.btn_submit);
         /*   msgDialog.setVisibility(View.GONE);
            titleDialog.setText("Redeem Request Successful");
            descTv.setText("Your Redeem will be credited to your account within 24-48 hours.");*/
            btn_submit.setOnClickListener(v2 -> {
                dialog.cancel();
                binding.cvrFakeNews.setVisibility(View.GONE);
            });

            try {
                dialog.show();
            } catch (Exception e) {
                Log.d("Dialog", "makeDialog: " + e.getMessage());
            }
        });
    }

    private void myToolbar() {
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        TextView title = findViewById(R.id.toolbarTitleTV);
        title.setText("Seller");
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