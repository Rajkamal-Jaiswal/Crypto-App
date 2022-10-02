package com.e.cellpaycrypto.menus.ui.futures;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.e.cellpaycrypto.Base.SharedPrefManagerAdmin;
import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.databinding.DialogConfirmRedeemBinding;
import com.e.cellpaycrypto.databinding.FragmentFuturesBinding;
import com.e.cellpaycrypto.databinding.SelectPaymentMethodToBuyBinding;
import com.e.cellpaycrypto.menus.DashboardActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Objects;


public class FuturesFragment extends Fragment {

    FragmentFuturesBinding binding;

    BottomSheetDialog watchlist;
    Activity activity;
    RadioButton radio1, radio2, radio3, radio4;
//    SharedPrefManagerAdmin sharedPrefManagerAdmin;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_futures, container, false);

        FuturesViewModel notificationsViewModel =
                new ViewModelProvider(this).get(FuturesViewModel.class);
        binding = FragmentFuturesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        activity = getActivity();

        binding.removeBtn1.setOnClickListener(v -> {
            SharedPrefManagerAdmin.getInstance(getContext()).clearSelectedMethods();
            doInVisible();

        });
        binding.removeBtn2.setOnClickListener(v -> {
            SharedPrefManagerAdmin.getInstance(getContext()).clearSelectedMethods();
            doInVisible();

        });
        binding.removeBtn3.setOnClickListener(v -> {
            SharedPrefManagerAdmin.getInstance(getContext()).clearSelectedMethods();
            doInVisible();

        });
        binding.removeBtn4.setOnClickListener(v -> {
            SharedPrefManagerAdmin.getInstance(getContext()).clearSelectedMethods();
            doInVisible();

        });


        String checkedValue = SharedPrefManagerAdmin.getInstance(getContext()).getPayMethods();
        if (checkedValue != null) {
            if (checkedValue.equalsIgnoreCase("1")) {
                doInVisible();
//                    SharedPrefManagerAdmin.getInstance(getContext()).saveSelectedMethod(requireContext(), "1");
                binding.cvrPaytm.setVisibility(View.VISIBLE);

            } else if (checkedValue.equalsIgnoreCase("2")) {
                doInVisible();
//                    SharedPrefManagerAdmin.getInstance(getContext()).saveSelectedMethod(requireContext(), "2");
                binding.cvrPhonePe.setVisibility(View.VISIBLE);
            } else if (checkedValue.equalsIgnoreCase("3")) {
                doInVisible();
//                    SharedPrefManagerAdmin.getInstance(getContext()).saveSelectedMethod(requireContext(), "3");

                binding.cvrUpi.setVisibility(View.VISIBLE);
            } else {
                doInVisible();
//                        checkedValue="4"
//                    SharedPrefManagerAdmin.getInstance(getContext()).saveSelectedMethod(requireContext(), "4");
                binding.cvrBankTransfer.setVisibility(View.VISIBLE);
            }
        }
//        sharedPrefManagerAdmin=new SharedPrefManagerAdmin(getContext())

        binding.btnSubmit.setOnClickListener(v -> {
            watchlist = new BottomSheetDialog(activity, R.style.videosheetDialogTheme);
            DialogConfirmRedeemBinding binding;
            binding = DialogConfirmRedeemBinding.inflate(getLayoutInflater());
            watchlist.setContentView(binding.getRoot());
            Objects.requireNonNull(watchlist.getWindow()).getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            watchlist.setCancelable(false);
            watchlist.setCanceledOnTouchOutside(true);
            binding.btnCancle.setOnClickListener(v1 -> {
                watchlist.dismiss();
            });
            binding.btnCnfrmSubmit.setOnClickListener(v1 -> {
                watchlist.cancel();


                final Dialog dialog = new Dialog(activity);
                dialog.setCancelable(false);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
                dialog.setContentView(R.layout.success_dialog_send);
                TextView titleDialog = (TextView) dialog.findViewById(R.id.titleDialog);
                TextView msgDialog = (TextView) dialog.findViewById(R.id.msgDialog);
                TextView descTv = (TextView) dialog.findViewById(R.id.nhbvh);
                Button btn_submit = (Button) dialog.findViewById(R.id.btn_submit);
                msgDialog.setVisibility(View.GONE);
                titleDialog.setText("Redeem Request Successful");
                descTv.setText("Your Redeem will be credited to your account within 24-48 hours.");
                btn_submit.setOnClickListener(v2 -> {
                    dialog.cancel();
                    startActivity(new Intent(activity, DashboardActivity.class));
                    activity.finishAffinity();
                });

                try {
                    dialog.show();
                } catch (Exception e) {
                    Log.d("Dialog", "makeDialog: " + e.getMessage());
                }

            });
            watchlist.show();
        });


        binding.cvrSelectPaymethods.setOnClickListener(v -> {
            watchlist = new BottomSheetDialog(activity, R.style.videosheetDialogTheme);
            SelectPaymentMethodToBuyBinding binding1;
            binding1 = SelectPaymentMethodToBuyBinding.inflate(getLayoutInflater());
            watchlist.setContentView(binding1.getRoot());
            Objects.requireNonNull(watchlist.getWindow()).getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            watchlist.setCancelable(false);
            watchlist.setCanceledOnTouchOutside(true);
            binding1.doneBtn.setOnClickListener(v1 -> {
                watchlist.dismiss();

                String checkedValue1 = SharedPrefManagerAdmin.getInstance(getContext()).getPayMethods();
                if (checkedValue1 != null) {
                    if (checkedValue1.equalsIgnoreCase("1")) {
                        doInVisible();
//                    SharedPrefManagerAdmin.getInstance(getContext()).saveSelectedMethod(requireContext(), "1");
                        binding.cvrPaytm.setVisibility(View.VISIBLE);

                    } else if (checkedValue1.equalsIgnoreCase("2")) {
                        doInVisible();
//                    SharedPrefManagerAdmin.getInstance(getContext()).saveSelectedMethod(requireContext(), "2");
                        binding.cvrPhonePe.setVisibility(View.VISIBLE);
                    } else if (checkedValue1.equalsIgnoreCase("3")) {
                        doInVisible();
//                    SharedPrefManagerAdmin.getInstance(getContext()).saveSelectedMethod(requireContext(), "3");

                        binding.cvrUpi.setVisibility(View.VISIBLE);
                    } else {
                        doInVisible();
//                        checkedValue="4"
//                    SharedPrefManagerAdmin.getInstance(getContext()).saveSelectedMethod(requireContext(), "4");
                        binding.cvrBankTransfer.setVisibility(View.VISIBLE);
                    }
                }
//                startActivity(new Intent(getActivity(), Buy3Activity.class));
            });

            radio1 = binding1.editPaytm;
            radio2 = binding1.editPhonePay;
            radio3 = binding1.editUpi;
            radio4 = binding1.editBankDetails;
            disableAllButton();
            radio1.setOnClickListener(view -> {
                disableAllButton();
                SharedPrefManagerAdmin.getInstance(getContext()).saveSelectedMethod(requireContext(), "1");

                radio1.setChecked(true);
            });
            radio2.setOnClickListener(view -> {
                disableAllButton();
                SharedPrefManagerAdmin.getInstance(getContext()).saveSelectedMethod(requireContext(), "2");

                radio2.setChecked(true);
            });
            radio3.setOnClickListener(view -> {
                disableAllButton();
                SharedPrefManagerAdmin.getInstance(getContext()).saveSelectedMethod(requireContext(), "3");

                radio3.setChecked(true);
            });
            radio4.setOnClickListener(view -> {
                disableAllButton();
                SharedPrefManagerAdmin.getInstance(getContext()).saveSelectedMethod(requireContext(), "4");

                radio4.setChecked(true);
            });
            watchlist.show();
        });

        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }

    private void doInVisible() {
        binding.cvrPaytm.setVisibility(View.GONE);
        binding.cvrPhonePe.setVisibility(View.GONE);
        binding.cvrUpi.setVisibility(View.GONE);
        binding.cvrBankTransfer.setVisibility(View.GONE);
    }

    private void disableAllButton() {
        radio1.setChecked(false);
        radio2.setChecked(false);
        radio3.setChecked(false);
        radio4.setChecked(false);
    }
}