package com.e.cellpaycrypto.menus.ui.notifications;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cellpaycrypto.Adaptor.SingleAdapter;
import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.databinding.FragmentNotificationsBinding;
import com.e.cellpaycrypto.screens.Screen1Activity;
import com.e.cellpaycrypto.test1.ModelPojo;

import java.util.ArrayList;
import java.util.List;
//import com.sellpay.android.databinding.FragmentNotificationsBinding;

//import com.sellpay.android.menus.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    RecyclerView recyclerView1, recyclerView2, recyclerView3;
    TextView historyBtn, positionsBtn;
    private FragmentNotificationsBinding binding;

    @SuppressLint("UseCompatLoadingForDrawables")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        recyclerView1 = binding.recyclerView1;
        recyclerView2 = binding.recyclerView2;
        recyclerView3 = binding.recyclerView3;
        recyclerView1.setHasFixedSize(true);
        recyclerView2.setHasFixedSize(true);
        recyclerView3.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        ArrayList<ModelPojo> modelPojos = new ArrayList<>();
        modelPojos.add(new ModelPojo("1M"));
        modelPojos.add(new ModelPojo("3M"));
        modelPojos.add(new ModelPojo("5M"));
        SingleAdapter adapterStockMenus = new SingleAdapter(getContext(), modelPojos);
        recyclerView1.setAdapter(adapterStockMenus);


        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView3.setLayoutManager(new LinearLayoutManager(getContext()));
        AdapterPositions adapterPositions = new AdapterPositions();
        recyclerView2.setAdapter(adapterPositions);

        positionsBtn = binding.positionsBtn;
        historyBtn = binding.historyBtn;
        positionsBtn.setBackground(getContext().getDrawable(R.drawable.rect2));
        recyclerView2.setVisibility(View.VISIBLE);
        recyclerView3.setVisibility(View.GONE);

        List<ModelPojo> modelPojos1 = new ArrayList<>();
        modelPojos1.add(new ModelPojo("2.32"));
        modelPojos1.add(new ModelPojo("2.42"));
        modelPojos1.add(new ModelPojo("1.32"));
        modelPojos1.add(new ModelPojo("1.12"));
        modelPojos1.add(new ModelPojo("3.24"));

        AdapterHistoryTrade adapterHistoryTrade = new AdapterHistoryTrade(modelPojos1, getContext());
        recyclerView3.setAdapter(adapterHistoryTrade);

        positionsBtn.setOnClickListener(v -> {
            disableBtns();
            positionsBtn.setBackground(getContext().getDrawable(R.drawable.rect2));
            recyclerView2.setVisibility(View.VISIBLE);
            recyclerView3.setVisibility(View.GONE);
        });

        historyBtn.setOnClickListener(v -> {
            disableBtns();
            historyBtn.setBackground(getContext().getDrawable(R.drawable.rect2));
            recyclerView2.setVisibility(View.GONE);
            recyclerView3.setVisibility(View.VISIBLE);
        });


        binding.im3.setOnClickListener(v -> {
            Intent intent1 = new Intent(getContext(), Screen1Activity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent1);
//            Toast.makeText(getContext(), "Open Transfer Page", Toast.LENGTH_SHORT).show();
        });


        disableAllButtons();
//        binding.cashBtn.setBackground(getContext().getResources().getDrawable(R.drawable.rect2));


        binding.cashBtn.setOnClickListener(v -> {
            disableAllButtons();
            binding.cashBtn.setBackground(getContext().getResources().getDrawable(R.drawable.rect2));
        });
        binding.tradeBtn.setOnClickListener(v -> {
            disableAllButtons();
            binding.tradeBtn.setBackground(getContext().getResources().getDrawable(R.drawable.rect2));

        });
        binding.bonusBtn.setOnClickListener(v -> {
            disableAllButtons();
            binding.bonusBtn.setBackground(getContext().getResources().getDrawable(R.drawable.rect2));
        });
        binding.bonus100Btn.setOnClickListener(v -> {
            disableAllButtons();
            binding.bonus100Btn.setBackground(getContext().getResources().getDrawable(R.drawable.rect2));
        });


        return root;
    }

    private void disableAllButtons() {

        binding.cashBtn.setBackground(getContext().getResources().getDrawable(R.drawable.rect_normal));
        binding.tradeBtn.setBackground(getContext().getResources().getDrawable(R.drawable.rect_normal));
        binding.bonusBtn.setBackground(getContext().getResources().getDrawable(R.drawable.rect_normal));
        binding.bonus100Btn.setBackground(getContext().getResources().getDrawable(R.drawable.rect_normal));
//        }

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void disableBtns() {
        historyBtn.setBackground(getContext().getDrawable(R.drawable.rect_normal));
        positionsBtn.setBackground(getContext().getDrawable(R.drawable.rect_normal));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}