package com.e.cellpaycrypto.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    List<ModelPojo> modelPojos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = ActivityShowAdsBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        recyclerView = binding.recyclerView;

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        modelPojos = new ArrayList<>();
        modelPojos.add(new ModelPojo("a"));
        modelPojos.add(new ModelPojo("b"));
        modelPojos.add(new ModelPojo("c"));
        modelPojos.add(new ModelPojo("d"));
        modelPojos.add(new ModelPojo("e"));
        modelPojos.add(new ModelPojo("f"));
        modelPojos.add(new ModelPojo("g"));
        AdapterMyAds adapterMyAds = new AdapterMyAds(modelPojos);
        recyclerView.setAdapter(adapterMyAds);


        binding.addSell.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), SellActivityMaster.class));
        });

        return binding.getRoot();
    }

}
