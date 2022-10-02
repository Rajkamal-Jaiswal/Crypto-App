package com.e.cellpaycrypto.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.e.cellpaycrypto.Activity.ChangePasswordActivity;
import com.e.cellpaycrypto.NotificationManagement;
import com.e.cellpaycrypto.SelectPaymentMethodActivity;
import com.e.cellpaycrypto.databinding.FragmentNewProfileBinding;
import com.e.cellpaycrypto.kycPage.DoKycActivity;
import com.e.cellpaycrypto.screens.SupportActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewProfileFragment extends Fragment {


    FragmentNewProfileBinding binding;

    Context activity;

    public NewProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentNewProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        activity = getContext();

        binding.cvrBecomeMerchant.setOnClickListener(v -> Toast.makeText(activity, "Coming Shortly", Toast.LENGTH_SHORT).show());
//        return inflater.inflate(R.layout.fragment_new_profile, container, false);

        binding.cvrChangePass.setOnClickListener(v -> {
            startActivity(new Intent(activity, ChangePasswordActivity.class));
        });
        binding.cvrNotifications
                .setOnClickListener(v -> {
                    startActivity(new Intent(activity, NotificationManagement.class));
                });
        binding.cvrPayMethods
                .setOnClickListener(v -> {
                    startActivity(new Intent(activity, SelectPaymentMethodActivity.class));
                });
        binding.cvrKycUpdate
                .setOnClickListener(v -> {
                    startActivity(new Intent(activity, DoKycActivity.class));
                });
//        binding.cvrHelpCenter
//                .setOnClickListener(v -> {
//                    startActivity(new Intent(activity, SupportActivity.class));
//                });
        binding.cvrHelpCenter
                .setOnClickListener(v -> {
                    startActivity(new Intent(activity, SupportActivity.class));
                });

        return binding.getRoot();
    }
}