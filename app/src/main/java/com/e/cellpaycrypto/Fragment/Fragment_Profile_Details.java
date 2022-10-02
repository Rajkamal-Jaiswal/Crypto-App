package com.e.cellpaycrypto.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.e.cellpaycrypto.Activity.ChangePasswordActivity;
import com.e.cellpaycrypto.Activity.LoginActivity;
import com.e.cellpaycrypto.Base.CommonUtils;
import com.e.cellpaycrypto.Base.DialogUtils;
import com.e.cellpaycrypto.NotificationManagement;
import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.SelectPaymentMethodActivity;
import com.e.cellpaycrypto.databinding.EditPaytmLayoutBinding;
import com.e.cellpaycrypto.kycPage.DoKycActivity;
import com.e.cellpaycrypto.screens.SupportActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Objects;


public class Fragment_Profile_Details extends Fragment {

    com.e.cellpaycrypto.databinding.LayoutProfileBinding binding;
    private BottomSheetDialog watchlist;
    Activity activity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = com.e.cellpaycrypto.databinding.LayoutProfileBinding.inflate(inflater, container, false);
        activity = getActivity();
        watchlist = new BottomSheetDialog(activity, R.style.videosheetDialogTheme);

        binding.coverWarningAddress.setOnClickListener(v -> {
            EditPaytmLayoutBinding binding;
            binding = EditPaytmLayoutBinding.inflate(getLayoutInflater());
            watchlist.setContentView(binding.getRoot());
            Objects.requireNonNull(watchlist.getWindow()).getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            watchlist.setCancelable(false);
            watchlist.setCanceledOnTouchOutside(true);
            binding.titleDialog.setText("Add your Address");
            binding.title.setText("Address");
            binding.inputBoxPayNumber.setHint("Enter Address");
            binding.btnCancel.setOnClickListener(v1 -> {
                watchlist.dismiss();
            });
            binding.btnSubmit.setOnClickListener(v1 -> {
                Toast.makeText(activity, "Remaining!", Toast.LENGTH_SHORT).show();
                watchlist.cancel();
            });
            watchlist.show();
        });

        binding.cvrBecomeMerchant.setOnClickListener(v -> Toast.makeText(activity, "Coming Shortly", Toast.LENGTH_SHORT).show());
//        return inflater.inflate(R.layout.fragment_new_profile, container, false);

        binding.cvrChangePass.setVisibility(View.GONE);
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


        binding.cvrLogout.setOnClickListener(v -> {
            DialogUtils.makeDialog(activity, "Logout", "Do you really want to logout?",
                    getResources().getString(R.string.yes), getResources().getString(R.string.no), true, this::logout, () -> {
                    });
        });
        return binding.getRoot();
    }

    private void logout() {
        Toast.makeText(getActivity(), "Logout Successfully", Toast.LENGTH_SHORT).show();
        activity.getSharedPreferences(CommonUtils.MyPREFERENCES, 0).edit().clear().commit();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
