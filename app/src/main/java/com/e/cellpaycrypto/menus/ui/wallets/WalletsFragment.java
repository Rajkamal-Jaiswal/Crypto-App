package com.e.cellpaycrypto.menus.ui.wallets;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.databinding.FragmentWalletsBinding;
import com.e.cellpaycrypto.mypack.DepositActivity;
//import com.sellpay.android.databinding.FragmentWalletsBinding;


public class WalletsFragment extends Fragment {


    FragmentWalletsBinding binding;
    /*TabLayout tabLayout;
    ViewPager viewPager;
    PagerAdapterWallet adapter;*/
    RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_wallets, container, false);

        WalltesViewModel notificationsViewModel =
                new ViewModelProvider(this).get(WalltesViewModel.class);
        binding = FragmentWalletsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        textView.setVisibility(View.GONE);
//
//        tabLayout = (TabLayout) root.findViewById(R.id.tab_layout);
//        viewPager = (ViewPager) root.findViewById(R.id.pager);
//        if (viewPager != null) {
//            setupViewPager(viewPager);
//        }

        disableAllButtons();
        binding.cashBtn.setBackground(getContext().getResources().getDrawable(R.drawable.rect2));


        binding.cashBtn.setOnClickListener(v -> {
            disableAllButtons();
            binding.cashBtn.setBackground(getContext().getResources().getDrawable(R.drawable.rect2));
            binding.tvAvailableBalance.setText("Available Balance");
        });
        binding.tradeBtn.setOnClickListener(v -> {
            disableAllButtons();
            binding.tradeBtn.setBackground(getContext().getResources().getDrawable(R.drawable.rect2));
            binding.tvAvailableBalance.setText("Available Wallet Balance");

        });
        binding.bonusBtn.setOnClickListener(v -> {
            disableAllButtons();
            binding.bonusBtn.setBackground(getContext().getResources().getDrawable(R.drawable.rect2));
            binding.tvAvailableBalance.setText("Available Bonus");

        });

        binding.btnShowTransaction.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), TransactionHistoryActivity.class));
        });
        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        AdapterWalletBalances adapterWalletBalances = new AdapterWalletBalances();
        recyclerView.setAdapter(adapterWalletBalances);


        binding.transferBtn.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), TransferActivity.class));
        });
        binding.depositBtn.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), DepositActivity.class));
        });
        return root;
    }

    private void disableAllButtons() {
        binding.cashBtn.setBackground(getContext().getResources().getDrawable(R.drawable.rect_normal));
        binding.tradeBtn.setBackground(getContext().getResources().getDrawable(R.drawable.rect_normal));
        binding.bonusBtn.setBackground(getContext().getResources().getDrawable(R.drawable.rect_normal));
    }

//    private void setupViewPager(ViewPager viewPager) {
//        adapter = new PagerAdapterWallet(getParentFragmentManager(), getContext());
//        adapter.addFragment(new WalletContentFragment().newInstance("Cash Wallet"), "Cash Wallet");
//        adapter.addFragment(new WalletContentFragment().newInstance("Trade Wallet"), "Trade Wallet");
//        viewPager.setAdapter(adapter);
//        tabLayout.setupWithViewPager(viewPager);
//    }
}