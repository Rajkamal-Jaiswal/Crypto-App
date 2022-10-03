package com.e.cellpaycrypto;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.e.cellpaycrypto.Adaptor.BankNamesAdapter;
import com.e.cellpaycrypto.Adaptor.BankTypeAdapter;
import com.e.cellpaycrypto.Base.CommonUtils;
import com.e.cellpaycrypto.Base.GlobalConstants;
import com.e.cellpaycrypto.Base.Helper;
import com.e.cellpaycrypto.Base.VolleySingleton;
import com.e.cellpaycrypto.apimodels.ModelResponse;
import com.e.cellpaycrypto.apimodels.accountTypes.AccountTypeListItem;
import com.e.cellpaycrypto.apimodels.accountTypes.ModelBankAccountTypes;
import com.e.cellpaycrypto.apimodels.bankList.BankListItem;
import com.e.cellpaycrypto.apimodels.bankList.ModelBankNames;
import com.e.cellpaycrypto.databinding.ActivitySelectPamentMethodBinding;
import com.e.cellpaycrypto.databinding.EditBankDetailsLayoutBinding;
import com.e.cellpaycrypto.databinding.EditPaytmLayoutBinding;
import com.e.cellpaycrypto.databinding.EditUpiLayoutBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SelectPaymentMethodActivity extends AppCompatActivity {

    private BottomSheetDialog watchlist;
    private Activity activity;
    private ActivitySelectPamentMethodBinding bindingMain;
    private String userid;
    private SharedPreferences sharedpreferences;
    private Gson gson = new Gson();
    List<AccountTypeListItem> modelAccountTypes = new ArrayList<>();
    List<BankListItem> modelBankNameList = new ArrayList<BankListItem>();
    RecyclerView searchRecyclerview;
    private BankTypeAdapter stateCityAdapter;
    private BankNamesAdapter stateCityAdapter11;
    private String type = "";
    private String TAG = "SelectPay";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingMain = ActivitySelectPamentMethodBinding.inflate(getLayoutInflater());
        setContentView(bindingMain.getRoot());
        activity = SelectPaymentMethodActivity.this;
        sharedpreferences = getSharedPreferences(CommonUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        userid = sharedpreferences.getString(CommonUtils.shared_USER_ID, "");
        watchlist = new BottomSheetDialog(activity, R.style.videosheetDialogTheme);
        loadBankAccountsType();
        loadBankNameList();

        bindingMain.banklistadd.setOnClickListener(view -> {
            EditBankDetailsLayoutBinding binding;
            binding = EditBankDetailsLayoutBinding.inflate(getLayoutInflater());
            watchlist.setContentView(binding.getRoot());
            Objects.requireNonNull(watchlist.getWindow()).getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            watchlist.setCancelable(false);
            watchlist.setCanceledOnTouchOutside(true);
            binding.titleDialog.setText("Add Bank Details");
            binding.btnCancel.setOnClickListener(v1 -> {
                watchlist.dismiss();
            });

            binding.accountType.setOnClickListener(v -> {
                if (modelAccountTypes == null || modelAccountTypes.size() == 0) {
                    Toast.makeText(activity, "No bank types available", Toast.LENGTH_SHORT).show();
                } else {
                    bankTypesList(binding.accountType, modelAccountTypes);
                }

            });


            binding.bankName.setOnClickListener(v -> {
                if (modelBankNameList == null || modelBankNameList.size() == 0) {
                    Toast.makeText(activity, "No Bank names available", Toast.LENGTH_SHORT).show();
                } else {
                    bankNameListDialog(binding.bankName, modelBankNameList);
                }

            });
            binding.btnSubmit.setOnClickListener(v1 -> {
                if (binding.accountHolderName.getText().toString().trim().isEmpty()) {
                    Helper.showToast("Account holder name can't be empty", activity);
                    return;
                }
                if (binding.accountNumber.getText().toString().trim().equalsIgnoreCase("")) {
                    Helper.showToast("Account Number can't be empty", activity);
                    return;
                }
                if (binding.ifscCode.getText().toString().trim().equalsIgnoreCase("")) {
                    Helper.showToast("IFSC code can't be empty", activity);
                    return;
                }
                if (binding.accountType.getText().toString().trim().isEmpty() || !(Helper.isNullChek(binding.accountType.getText().toString().trim()))) {
                    Helper.showToast("Please select account type", activity);
                    return;
                }
                if (binding.bankName.getText().toString().trim().isEmpty() || !(Helper.isNullChek(binding.accountType.getText().toString().trim()))) {
                    Helper.showToast("Bank name can't be empty", activity);
                    return;
                }
                if (binding.branchName.getText().toString().trim().equalsIgnoreCase("")) {
                    Helper.showToast("Branch name can't be empty", activity);
                    return;
                }

                type = "BANK";
                hitApiToSubmitBankDetails(
                        watchlist,
                        binding.accountHolderName.getText().toString().trim(),
                        binding.accountNumber.getText().toString().trim(),
                        binding.ifscCode.getText().toString().trim(),
                        binding.accountType.getText().toString().trim(),
                        binding.bankName.getText().toString().trim(),
                        binding.branchName.getText().toString().trim(), type
                );
//                    Toast.makeText(SelectPamentMethodActivity.this, "Remaining!", Toast.LENGTH_SHORT).show();
            });
            watchlist.show();
        });
        bindingMain.llImps.setOnClickListener(view -> {
            EditBankDetailsLayoutBinding binding;
            binding = EditBankDetailsLayoutBinding.inflate(getLayoutInflater());
            watchlist.setContentView(binding.getRoot());
            Objects.requireNonNull(watchlist.getWindow()).getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            watchlist.setCancelable(false);
            watchlist.setCanceledOnTouchOutside(true);
            binding.titleDialog.setText("Add IMPS Details");
            binding.btnCancel.setOnClickListener(v1 -> {
                watchlist.dismiss();
            });


            binding.accountType.setOnClickListener(v -> {
                if (modelAccountTypes == null || modelAccountTypes.size() == 0) {
                    Toast.makeText(activity, "No bank types available", Toast.LENGTH_SHORT).show();
                } else {
                    bankTypesList(binding.accountType, modelAccountTypes);
                }

            });


            binding.bankName.setOnClickListener(v -> {
                if (modelBankNameList == null || modelBankNameList.size() == 0) {
                    Toast.makeText(activity, "No Bank names available", Toast.LENGTH_SHORT).show();
                } else {
                    bankNameListDialog(binding.bankName, modelBankNameList);
                }

            });
            binding.btnSubmit.setOnClickListener(v1 -> {
                if (binding.accountHolderName.getText().toString().trim().isEmpty()) {
                    Helper.showToast("Account holder name can't be empty", activity);
                    return;
                }
                if (binding.accountNumber.getText().toString().trim().equalsIgnoreCase("")) {
                    Helper.showToast("Account Number can't be empty", activity);
                    return;
                }
                if (binding.ifscCode.getText().toString().trim().equalsIgnoreCase("")) {
                    Helper.showToast("IFSC code can't be empty", activity);
                    return;
                }
                if (binding.accountType.getText().toString().trim().isEmpty() || !(Helper.isNullChek(binding.accountType.getText().toString().trim()))) {
                    Helper.showToast("Please select account type", activity);
                    return;
                }
                if (binding.bankName.getText().toString().trim().isEmpty() || !(Helper.isNullChek(binding.accountType.getText().toString().trim()))) {
                    Helper.showToast("Bank name can't be empty", activity);
                    return;
                }
                if (binding.branchName.getText().toString().trim().equalsIgnoreCase("")) {
                    Helper.showToast("Branch name can't be empty", activity);
                    return;
                }

                type = "IMPS";
                hitApiToSubmitBankDetails(
                        watchlist,
                        binding.accountHolderName.getText().toString().trim(),
                        binding.accountNumber.getText().toString().trim(),
                        binding.ifscCode.getText().toString().trim(),
                        binding.accountType.getText().toString().trim(),
                        binding.bankName.getText().toString().trim(),
                        binding.branchName.getText().toString().trim()
                        , type
                );
//                    Toast.makeText(SelectPamentMethodActivity.this, "Remaining!", Toast.LENGTH_SHORT).show();
            });
            watchlist.show();
        });
        bindingMain.llPaytm.setOnClickListener(view -> {
            EditPaytmLayoutBinding binding;
            binding = EditPaytmLayoutBinding.inflate(getLayoutInflater());
            watchlist.setContentView(binding.getRoot());
            Objects.requireNonNull(watchlist.getWindow()).getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            watchlist.setCancelable(false);
            watchlist.setCanceledOnTouchOutside(true);
            binding.titleDialog.setText("Add Other Payment Details");
            binding.btnCancel.setOnClickListener(v1 -> {
                watchlist.dismiss();
            });
            binding.btnSubmit.setOnClickListener(v1 -> {

                if (TextUtils.isEmpty(binding.upiHolderName.getText().toString())) {
                    Helper.showToast("Please enter name", this);
                    binding.upiHolderName.requestFocus();
                } else if (TextUtils.isEmpty(binding.upiId.getText().toString())) {
                    Helper.showToast("Please enter Upi id", this);
                    binding.upiId.requestFocus();
                } else if (TextUtils.isEmpty(binding.inputBoxPayNumber.getText().toString())) {
                    Helper.showToast("Please enter paytm id", this);
                    binding.inputBoxPayNumber.requestFocus();
                } else if (TextUtils.isEmpty(binding.inputBoxPhonePayNumber.getText().toString())) {
                    Helper.showToast("Please enter phone pe id", this);
                    binding.inputBoxPhonePayNumber.requestFocus();
                } else {
                    hitApiToUpdatePaytmDetails(watchlist, binding.upiHolderName.getText().toString().trim()
                            , binding.upiId.getText().toString().trim()
                            , binding.inputBoxPayNumber.getText().toString().trim()
                            , binding.inputBoxPhonePayNumber.getText().toString().trim()
                    );
                }

            });
            watchlist.show();
        });
        bindingMain.llUpi.setVisibility(View.GONE);
//        bindingMain.llUpi.setOnClickListener(view -> {
////            watchlist = new BottomSheetDialog(activity, R.style.videosheetDialogTheme);
//            EditUpiLayoutBinding binding;
//            binding = EditUpiLayoutBinding.inflate(getLayoutInflater());
//            watchlist.setContentView(binding.getRoot());
//            Objects.requireNonNull(watchlist.getWindow()).getAttributes().windowAnimations = R.style.PauseDialogAnimation;
//            watchlist.setCancelable(false);
//            watchlist.setCanceledOnTouchOutside(true);
//            binding.titleDialog.setText("Add UPI Details");
//            binding.btnCancel.setOnClickListener(v1 -> {
//                watchlist.dismiss();
//            });
//            binding.btnSubmit.setOnClickListener(v1 -> {
//                if (binding.upiHolderName.getText().toString().trim().isEmpty()) {
//                    Helper.showToast("Name can't be empty", activity);
//                    return;
//                }
//                if (binding.upiId.getText().toString().trim().isEmpty()) {
//                    Helper.showToast("Name can't be empty", activity);
//                    return;
//                }
//                hitApiAddUpiDetails(watchlist, binding.upiHolderName.getText().toString().trim(), binding.upiId.getText().toString().trim());
//            });
//            watchlist.show();
//        });


    }

    private void hitApiToUpdatePaytmDetails(BottomSheetDialog watchlist, String name, String upiId, String paytmNo, String phonePeNo) {
        Helper.showLoadingDialog(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GlobalConstants.BASE_URL, response -> {
            Helper.watchLog("EditUpdateUPiApi: ", response);
            Helper.hideLoadingDialog();
            try {
                ModelResponse modelResponse = gson.fromJson(response, ModelResponse.class);
                if (modelResponse.getResultCode().equalsIgnoreCase("200")) {
                    watchlist.dismiss();
                    Helper.showToast(modelResponse.getMessage(), activity);
                } else {
                    Helper.showToast(modelResponse.getMessage(), activity);
                }
            } catch (Exception e1) {
                Helper.watchLog("Excep", e1.getLocalizedMessage());
                watchlist.dismiss();
                Helper.showToast("Something went wrong", activity);
            }
        }, error -> {
            Helper.hideLoadingDialog();
            watchlist.dismiss();
            Helper.showToast(error.getLocalizedMessage(), activity);
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("module", WebURLS.URL_ADD_EDIT_UPI);
                map.put("user_id", userid);
                map.put("default_bank_id", WebURLS.default_bank_id);
                map.put("upi_id", upiId);
                map.put("upi_name", name);
                map.put("paytm_no", paytmNo);
                map.put("phonepe_no", phonePeNo);
                Helper.watchLog("ParamsAddUpi", map.toString());
                return map;
            }
        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private void bankNameListDialog(TextView bankNameTv, List<BankListItem> bankListItems) {
        Dialog searchDialog = new Dialog(activity);
        searchDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        searchDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        searchDialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        searchDialog.setContentView(R.layout.state_city_dialog);
        searchDialog.setCancelable(true);
        TextView tvCancel = searchDialog.findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(v -> searchDialog.cancel());
        searchRecyclerview = searchDialog.findViewById(R.id.search_recyclerview);
        searchRecyclerview.setHasFixedSize(true);
        searchRecyclerview.setLayoutManager(new LinearLayoutManager(activity));
        stateCityAdapter11 = new BankNamesAdapter(activity, bankListItems, searchDialog, bankNameTv);
        searchRecyclerview.setAdapter(stateCityAdapter11);

        searchDialog.show();
    }

    private void loadBankNameList() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GlobalConstants.BASE_URL, response -> {
            Helper.watchLog("bankList", response.toString());
            ModelBankNames modelResponse = gson.fromJson(response, ModelBankNames.class);
            if (modelResponse.getResultCode().equalsIgnoreCase("200")) {
                modelBankNameList = modelResponse.getBankList();
            } else {
                Helper.showToast(modelResponse.getMessage(), activity);
            }

        }, error -> {
            watchlist.dismiss();
            Helper.showToast(error.getLocalizedMessage(), activity);
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("module", WebURLS.URL_SHOW_BANK_LIST);
                return map;
            }
        };

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }

    private void bankTypesList(TextView accountTypeTV, List<AccountTypeListItem> modelAccountTypes) {
        Dialog searchDialog = new Dialog(activity);
        searchDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        searchDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        searchDialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        searchDialog.setContentView(R.layout.state_city_dialog);
        searchDialog.setCancelable(true);
        TextView tvCancel = searchDialog.findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(v -> searchDialog.cancel());
        searchRecyclerview = searchDialog.findViewById(R.id.search_recyclerview);
        searchRecyclerview.setHasFixedSize(true);
        searchRecyclerview.setLayoutManager(new LinearLayoutManager(activity));
        stateCityAdapter = new BankTypeAdapter(activity, modelAccountTypes, searchDialog, accountTypeTV);
        searchRecyclerview.setAdapter(stateCityAdapter);
        searchDialog.show();
    }

    private void loadBankAccountsType() {
        Helper.showLoadingDialog(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GlobalConstants.BASE_URL, response -> {
            Helper.watchLog("SelectPayMethods", response.toString());
            Helper.hideLoadingDialog();
            ModelBankAccountTypes modelResponse = gson.fromJson(response, ModelBankAccountTypes.class);
            if (modelResponse.getResultCode().equalsIgnoreCase("200")) {
                modelAccountTypes = modelResponse.getAccountTypeList();
            } else {
                Helper.showToast(modelResponse.getMessage(), activity);
            }

        }, error -> {
            Helper.hideLoadingDialog();

            watchlist.dismiss();
            Helper.showToast(error.getLocalizedMessage(), activity);
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("module", WebURLS.URL_BANK_ACCOUNTS_TYPE);
                return map;
            }
        };

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }

    private void hitApiToSubmitBankDetails(BottomSheetDialog watchlist, String bankHolderName, String accountNumber, String ifscCode, String bankType, String bankName, String branchName, String type) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GlobalConstants.BASE_URL, response -> {
            ModelResponse modelResponse = gson.fromJson(response, ModelResponse.class);
            if (modelResponse.getResultCode().equalsIgnoreCase("200")) {
                watchlist.dismiss();
                Helper.showToast(modelResponse.getMessage(), activity);
            } else {
                Helper.showToast(modelResponse.getMessage(), activity);
            }
        }, error -> {
            watchlist.dismiss();
            Helper.showToast(error.getLocalizedMessage(), activity);
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("module", WebURLS.URL_ADD_BANK_DETAILS);
                map.put("user_id", userid);
                map.put("account_holder_name", bankHolderName);
                map.put("account_number", accountNumber);
                map.put("ifsc_code", ifscCode);
                map.put("account_type", bankType);
                map.put("bank_name", bankName);
                map.put("branch_name", branchName);
                map.put("transfer_mode", type);
                return map;
            }
        };

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }

    private void hitApiAddUpiDetails(BottomSheetDialog watchlist, String upiHolderName, String upiId) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GlobalConstants.BASE_URL, response -> {
            Helper.watchLog("EditUpdateUPiApi: ", response);
            try {
                ModelResponse modelResponse = gson.fromJson(response, ModelResponse.class);
                if (modelResponse.getResultCode().equalsIgnoreCase("200")) {
                    watchlist.dismiss();
                    Helper.showToast(modelResponse.getMessage(), activity);
                } else {
                    Helper.showToast(modelResponse.getMessage(), activity);
                }
            } catch (Exception e1) {
                Helper.watchLog("Excep", e1.getLocalizedMessage());
                watchlist.dismiss();
                Helper.showToast("Something went wrong", activity);
            }
        }, error -> {
            watchlist.dismiss();
            Helper.showToast(error.getLocalizedMessage(), activity);
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("module", WebURLS.URL_ADD_EDIT_UPI);
                map.put("user_id", userid);
//                map.put("upi_name", upiHolderName);
                map.put("upi_id", upiId);
                map.put("qr_code", "qr_code");
                map.put("default_bank_id", WebURLS.default_bank_id);
                map.put("paytm_no", "");
                map.put("phonepe_no", "");
                Helper.watchLog("ParamsAddUpi", map.toString());
                return map;
            }
        };

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }

    public void bsck(View view) {
       /* Intent intent = new Intent(SelectPamentMethodActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);*/
        onBackPressed();
    }
}