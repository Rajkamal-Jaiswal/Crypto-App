package com.e.cellpaycrypto.mypack;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.e.cellpaycrypto.AppPermissionsRunTime;
import com.e.cellpaycrypto.Base.CommonUtils;
import com.e.cellpaycrypto.Base.FileUtil;
import com.e.cellpaycrypto.Base.GlobalConstants;
import com.e.cellpaycrypto.Base.Helper;
import com.e.cellpaycrypto.Base.NetWorkClass;
import com.e.cellpaycrypto.Base.SettingsManager;
import com.e.cellpaycrypto.Base.VolleySingleton;
import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.WebURLS;
import com.e.cellpaycrypto.apimodels.ModelResponse;
import com.e.cellpaycrypto.apimodels.ModelSettings;
import com.e.cellpaycrypto.databinding.ActivityDepositBinding;
import com.e.cellpaycrypto.databinding.DepositDialogBinding;
import com.e.cellpaycrypto.menus.DashboardActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DepositActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityDepositBinding binding;
    private BottomSheetDialog watchlist;
    private Activity activity;
    private Gson gson;
    private ModelSettings.Root modelResponse;
    private ModelResponse modelSubmitResponse;
    private SettingsManager settingsManager;
    private Uri contentURI = null;
    private File file;
    private DepositDialogBinding bindingDepoDial;


    private static final int REQUEST_CODE_GALLERY = 1988;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_REQUEST = 1888;

    public ArrayList<AppPermissionsRunTime.MyPermissionConstants> myPermissionConstantsArrayList;

    private SharedPreferences sharedpreferences;
    private String user_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDepositBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = DepositActivity.this;
        myToolbar();
        gson = new Gson();
        settingsManager = new SettingsManager(activity);
        sharedpreferences = getSharedPreferences(CommonUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        user_id = sharedpreferences.getString(CommonUtils.shared_USER_ID, "");

        hitApiToLoadSettingsData();


        binding.copyBtnUpiId.setOnClickListener(this);
        binding.copyBtnBankName.setOnClickListener(this);
        binding.copyBtnAccNumber.setOnClickListener(this);
        binding.copyBtnIfsc.setOnClickListener(this);
        binding.copyBtnBranchName.setOnClickListener(this);
        binding.copyBtnHolderName.setOnClickListener(this);

    }

    private void hitApiToLoadSettingsData() {
        Helper.showLoadingDialog(activity);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GlobalConstants.BASE_URL, response -> {
            Helper.watchLog("EditUpdateUPiApi: ", response);
            Helper.hideLoadingDialog();
            try {
                modelResponse = gson.fromJson(response, ModelSettings
                        .Root.class);
                if (modelResponse.resultCode.equalsIgnoreCase("200")) {
                    settingsManager.saveSettings(modelResponse.settings);
                    binding.upiIdTxt.setText(modelResponse.settings.getUpi_id());
                    binding.bankNameTv.setText("Bank: " + modelResponse.settings.getBank_name());
                    binding.accNumberTxt.setText("Acc: " + modelResponse.settings.getAccount_number());
                    binding.ifscCodeTxt.setText("IFSC: " + modelResponse.settings.getIfsc_code());
                    binding.branchNameTxt.setText("Branch: " + modelResponse.settings.getBranch_name());
                    binding.accHolderNameTxt.setText("Holder: " + modelResponse.settings.getAccount_holder_name());
                } else {
                    Helper.showToast(modelResponse.message, activity);
                }
            } catch (Exception e1) {
                Helper.watchLog("Excep", e1.getLocalizedMessage());
                Helper.showToast("Something went wrong", activity);
            }
        }, error -> {
            Helper.hideLoadingDialog();
            Helper.showToast(error.getLocalizedMessage(), activity);
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("module", WebURLS.settings);
                Helper.watchLog("ParamsAddUpi", map.toString());
                return map;
            }
        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private void myToolbar() {
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        TextView title = findViewById(R.id.toolbarTitleTV);
        title.setText("Deposit");
        TextView btn = findViewById(R.id.btn);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        btn.setVisibility(View.VISIBLE);
        btn.setText("Next");
        btn.setOnClickListener(v -> {

            if (!binding.payConfirmation.isChecked()) {
                CommonUtils.showToast(activity, "Please confirm your payment");
                return;
            }
            watchlist = new BottomSheetDialog(activity, R.style.videosheetDialogTheme);

            bindingDepoDial = DepositDialogBinding.inflate(getLayoutInflater());
            watchlist.setContentView(bindingDepoDial.getRoot());
            Objects.requireNonNull(watchlist.getWindow()).getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            watchlist.setCancelable(false);
            watchlist.setCanceledOnTouchOutside(true);
            bindingDepoDial.btnPickImage.setOnClickListener(v3 -> {
                Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, REQUEST_CODE_GALLERY);
            });
            bindingDepoDial.btnCancel.setOnClickListener(v1 -> {
                watchlist.dismiss();
            });
            bindingDepoDial.btnSubmit.setOnClickListener(v1 -> {
                if (TextUtils.isEmpty(bindingDepoDial.nameTxt.getText().toString())) {
                    Helper.showToast("Please enter name", activity);
                    bindingDepoDial.nameTxt.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(bindingDepoDial.txtAmt.getText().toString())) {
                    Helper.showToast("Please enter amount", activity);
                    bindingDepoDial.txtAmt.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(bindingDepoDial.utrNumbTxt.getText().toString())) {
                    Helper.showToast("Please enter Transaction/UTR number", activity);
                    bindingDepoDial.utrNumbTxt.requestFocus();
                    return;
                }
                if (contentURI == null) {
                    Helper.showToast("Please upload payment proof", activity);
                    return;
                }
                try {
                    hitApiToSubmitDepositRequest(watchlist, bindingDepoDial);
                } catch (IOException e) {
                    e.printStackTrace();
                    Helper.showToast("Something went wrong,please try again", activity);
                }

           /*     watchlist.cancel();

                openAlertDialog();*/


            });
            watchlist.show();
        });
    }


    private void hitApiToSubmitDepositRequest(BottomSheetDialog watchlist, DepositDialogBinding bindingDepoDial) throws IOException {
        Helper.showLoadingDialog(activity);
        if (NetWorkClass.isConnectingToInternet(activity)) {
            if (contentURI != null) {
                file = FileUtil.from(activity, contentURI);
            }
            AndroidNetworking.upload(GlobalConstants.BASE_URL)
                    .addMultipartParameter("module", WebURLS.depositRequest)
                    .addMultipartParameter("user_id", user_id)
                    .addMultipartParameter("name", this.bindingDepoDial.nameTxt.getText().toString().trim())
                    .addMultipartParameter("amount", this.bindingDepoDial.txtAmt.getText().toString().trim())
                    .addMultipartParameter("utr_no", this.bindingDepoDial.utrNumbTxt.getText().toString().trim())
                    .addMultipartFile("image", file)
                    .setTag(this)
                    .build()
                    .getAsString(new StringRequestListener() {
                        @Override
                        public void onResponse(String response) {

                            Helper.watchLog("+++", response);
                            Helper.hideLoadingDialog();

                            try {
                                modelSubmitResponse = gson.fromJson(response, ModelResponse.class);
                                if (modelSubmitResponse.getResultCode().equalsIgnoreCase("200")) {
                                    Toast.makeText(activity, modelSubmitResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                    String amt = bindingDepoDial.txtAmt.getText().toString().trim();
                                    watchlist.dismiss();
                                    openAlertDialog(amt);
                                } else if (modelSubmitResponse.getResultCode().equalsIgnoreCase("400")) {
                                    Toast.makeText(activity, modelSubmitResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JsonSyntaxException e) {
                                e.printStackTrace();
                                Helper.showToast("Something went wrong!", activity);
                            }

                        }

                        @Override
                        public void onError(ANError anError) {
                            Helper.hideLoadingDialog();
                            Helper.showToast(anError.getErrorDetail().toString(), activity);
                        }
                    });

        } else {
            Helper.showToast("No internet", activity);
        }
    }

    private void openAlertDialog(String amt) {
        final Dialog dialog = new Dialog(DepositActivity.this);
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        dialog.setContentView(R.layout.success_dialog_send);
        TextView titleDialog = (TextView) dialog.findViewById(R.id.titleDialog);
        titleDialog.setText("Successfully Deposit!");
        TextView msgDialog = (TextView) dialog.findViewById(R.id.msgDialog);
        msgDialog.setText(amt + " INR");
        TextView nhbvh = (TextView) dialog.findViewById(R.id.nhbvh);
        nhbvh.setVisibility(View.INVISIBLE);
        Button btn_submit = (Button) dialog.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
            finishAffinity();
        });

        try {
            dialog.show();
        } catch (Exception e) {
            Log.d("Dialog", "makeDialog: " + e.getMessage());
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.copyBtnUpiId:
                Helper.copyToClipBoard(activity, "UPI id", modelResponse.settings.getUpi_id().trim());
                break;
            case R.id.copyBtnBankName:
                Helper.copyToClipBoard(activity, "Bank name", modelResponse.settings.getBank_name().trim());

                break;
            case R.id.copyBtnAccNumber:
                Helper.copyToClipBoard(activity, "Account number", modelResponse.settings.getAccount_number().trim());

                break;
            case R.id.copyBtnIfsc:
                Helper.copyToClipBoard(activity, "IFSC code", modelResponse.settings.getIfsc_code().trim());

                break;
            case R.id.copyBtnBranchName:
                Helper.copyToClipBoard(activity, "Branch Name", modelResponse.settings.getBranch_name().trim());

                break;
            case R.id.copyBtnHolderName:
                Helper.copyToClipBoard(activity, "Account Holder Name", modelResponse.settings.getAccount_holder_name().trim());
                break;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == Activity.RESULT_OK) {
        /*    final Uri imageUri = data.getData();

            InputStream imageStream = null;
            try {
                imageStream = activity.getContentResolver().openInputStream(imageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            final Bitmap photo = BitmapFactory.decodeStream(imageStream);
            bindingDepoDial.btnPickImage.setImageBitmap(photo);*/

            contentURI = data.getData();

            /* Bitmap bitmap2 = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), contentURI);
             contentURI = getImageUri(activity, bitmap2);*/
            bindingDepoDial.btnPickImage.setImageURI(contentURI);
        }
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "IMG_" + Calendar.getInstance().getTime(), null);
        return Uri.parse(path);
    }

}

   /* private String saveImage(Bitmap bitmap2) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(context,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }*/