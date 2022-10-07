package com.e.cellpaycrypto.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cellpaycrypto.apimodels.ModelShowAds;
import com.e.cellpaycrypto.databinding.ActivityShowAdsBinding;
import com.e.cellpaycrypto.mypack.SellActivityMaster;
import com.e.cellpaycrypto.test1.AdapterMyAds;
import com.e.cellpaycrypto.test1.ModelPojo;

import java.util.ArrayList;
import java.util.List;

public class AdsFragment extends Fragment {

    ActivityShowAdsBinding binding;

    View root;
    RecyclerView recyclerView;
    //    private Toolbar toolbar;
    List<ModelShowAds.DepositList> modelPojos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = ActivityShowAdsBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        recyclerView = binding.recyclerView;

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        modelPojos = new ArrayList<>();
        modelPojos.add(new ModelShowAds.DepositList("a","","","","",""));
        modelPojos.add(new ModelShowAds.DepositList("b","","","","",""));
        modelPojos.add(new ModelShowAds.DepositList("c","","","","",""));
        modelPojos.add(new ModelShowAds.DepositList("d","","","","",""));
        modelPojos.add(new ModelShowAds.DepositList("e","","","","",""));
        modelPojos.add(new ModelShowAds.DepositList("f","","","","",""));
        modelPojos.add(new ModelShowAds.DepositList("g","","","","",""));
        AdapterMyAds adapterMyAds = new AdapterMyAds(modelPojos);
        recyclerView.setAdapter(adapterMyAds);


        binding.addSell.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), SellActivityMaster.class));
        });

        return binding.getRoot();
    }

}
