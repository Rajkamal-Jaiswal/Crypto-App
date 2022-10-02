package com.e.cellpaycrypto.mypack;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cellpaycrypto.databinding.FragmentAllOrdersBinding;


public class AllOrdersFragment extends Fragment {

    FragmentAllOrdersBinding binding;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentAllOrdersBinding.inflate(getLayoutInflater());

        recyclerView = binding.recyclerView;

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        AdapterAllOrders adapterAllOrders = new AdapterAllOrders();
        recyclerView.setAdapter(adapterAllOrders);


        return binding.getRoot();
//        return inflater.inflate(R.layout.fragment_all_orders, container, false);
    }
}