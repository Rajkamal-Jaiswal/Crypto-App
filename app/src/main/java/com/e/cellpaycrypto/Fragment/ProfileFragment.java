package com.e.cellpaycrypto.Fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.bumptech.glide.Glide;
import com.e.cellpaycrypto.Activity.ChangePasswordActivity;
import com.e.cellpaycrypto.Activity.FileUtil;
import com.e.cellpaycrypto.Activity.LoginActivity;
import com.e.cellpaycrypto.Activity.NetWorkClass;
import com.e.cellpaycrypto.Base.CommonUtils;
import com.e.cellpaycrypto.Base.GlobalConstants;
import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.SelectPaymentMethodActivity;
import com.e.cellpaycrypto.mypack.P2PMethodsActivity;
import com.e.cellpaycrypto.kycPage.DoKycActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProfileFragment extends Fragment {
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 7;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    View view;
    EditText etName;
    TextView tvAvlBal;
    EditText etPhone;
    EditText etEmail;
    EditText etAddress;
    EditText etReferal, etUpiName, etUpiId, etPaytmNumber, etPhonePe;
    Button btn_update, kycBtn, choosePayBtn;
    ProgressDialog dialog;
    SharedPreferences sharedPreferences;
    SharedPreferences sharedpreferences;
    RequestQueue queue;
    String userid;
    Spinner spinBankName;
    List<Map<String, Object>> membershipTitle1 = new ArrayList<>();
    String bankname;
    ImageView qrimage;
    Uri contentURI;
    File file;
    String image = "null";
    String encodedImage;
    Context context;
    ProgressDialog dialog1;
    private HashMap<String, String> map = new HashMap<>();
    private int GALLERY = 1, CAMERA = 2;


    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        etName = view.findViewById(R.id.etName);
        kycBtn = view.findViewById(R.id.kycBtn);
        choosePayBtn = view.findViewById(R.id.choosePayBtn);
        context = getActivity();
        etPhone = view.findViewById(R.id.etPhone);
        etEmail = view.findViewById(R.id.etEmail);
        etAddress = view.findViewById(R.id.etAddress);
        etUpiName = view.findViewById(R.id.etUpiName);
        etUpiId = view.findViewById(R.id.etUpiId);
        etPaytmNumber = view.findViewById(R.id.etPaytmNumber);
        etPhonePe = view.findViewById(R.id.etPhonePe);
        etReferal = view.findViewById(R.id.etReferelCode);
        tvAvlBal = view.findViewById(R.id.tvAvlBal);
        btn_update = view.findViewById(R.id.btn_update);
        qrimage = view.findViewById(R.id.qrimage);
        spinBankName = view.findViewById(R.id.spinnBankLIst);
        queue = Volley.newRequestQueue(getActivity());
        view.findViewById(R.id.selectpayment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SelectPaymentMethodActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(true);
                builder.setMessage("Are You Sure To Logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Logout();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog1, int which) {
                        //if user select "No", just cancel this dialog and continue with app
                        dialog1.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        view.findViewById(R.id.changepaaword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        sharedpreferences = getActivity().getSharedPreferences(CommonUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        userid = sharedpreferences.getString(CommonUtils.shared_USER_ID, "");

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    dialog = new ProgressDialog(getActivity());
                    dialog.setMessage("Please wait.");
                    dialog.setCancelable(false);
                    dialog.show();
                    Map<String, Object> selectedItem1 = membershipTitle1.get(spinBankName.getSelectedItemPosition());
                    String name = selectedItem1.get("name").toString();
                    String id = selectedItem1.get("id").toString();
                    System.out.println("dkhkjhgjkdjkd" + name + id);

                    if (contentURI != null) {
                        try {
                            file = FileUtil.from(getActivity(), contentURI);
                            System.out.println("jkkjhkjhkjh" + file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (NetWorkClass.isConnectingToInternet(getActivity())) {
                        AndroidNetworking.upload(GlobalConstants.BASE_URL)
                                .addMultipartParameter("module", "editUserprofile")
                                .addMultipartParameter("user_id", userid)
                                .addMultipartParameter("upi_name", etUpiName.getText().toString())
                                .addMultipartParameter("upi_id", etUpiId.getText().toString())
                                .addMultipartParameter("paytm_no", etPaytmNumber.getText().toString())
                                .addMultipartParameter("phonepe_no", etPhonePe.getText().toString())
                                .addMultipartParameter("default_bank_id", id)
                                .addMultipartParameter("name", etName.getText().toString())
                                .addMultipartParameter("address", etAddress.getText().toString())
                                .addMultipartFile("qr_code", file)
                                .setTag(this)
                                .setPriority(Priority.LOW)
                                .build()
                                .getAsString(new StringRequestListener() {
                                    @Override
                                    public void onResponse(String response) {
                                        dialog.dismiss();
                                        System.out.println("ghghjghjhj" + response);
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            String resultCode = jsonObject.getString("resultCode");
                                            String message = jsonObject.getString("message");

                                            if (resultCode.equalsIgnoreCase("200")) {
                                                Intent intent = getActivity().getIntent();
                                                getActivity().finish();
                                                startActivity(intent);

                                            } else if (resultCode.equalsIgnoreCase("400")) {
                                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }

                                    @Override
                                    public void onError(ANError anError) {
                                        dialog.dismiss();
                                    }
                                });

                    } else {
                        Toast.makeText(getActivity(), "no network", Toast.LENGTH_SHORT).show();
                        return;
                    }


                } catch (NullPointerException e) {
                    e.fillInStackTrace();
                }


            }
        });
        kycBtn.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), DoKycActivity.class));
        });
        choosePayBtn.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), P2PMethodsActivity.class));
        });
        availableBal();
        setLognTask(userid);
        qrimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAndRequestPermissions()) {
                    showPictureDialog();
                }
            }
        });
        profileInfo();
        spinBankName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> selectedItem1 = membershipTitle1.get(spinBankName.getSelectedItemPosition());
                bankname = selectedItem1.get("name").toString();
                String id1 = selectedItem1.get("id").toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    private boolean checkAndRequestPermissions() {
        int camera = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
        int wtite = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int read = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (wtite != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (read != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            System.out.println("lkdlkjf");
            showPictureDialog();
            return false;
        }
        return true;
    }

    private void showPictureDialog() {

        android.app.AlertDialog.Builder pictureDialog = new android.app.AlertDialog.Builder(getActivity());
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ProfileFragment.this.choosePhotoFromGallary();
                                break;
                            case 1:
                                ProfileFragment.this.takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }
        System.out.println("kjjljkll" + requestCode);
        if (requestCode == GALLERY) {
            if (data != null) {
                contentURI = data.getData();

                try {
                    Bitmap bitmap2 = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
                    //Bitmap bitmap2 = (Bitmap) Bitmap.createScaledBitmap(bitmap, 200,201 , true);
                    String path = saveImage(bitmap2);
                    qrimage.setImageBitmap(bitmap2);
                    image = "image";
                    encodedImage = getStringImage(bitmap2);

                    contentURI = getImageUri(getActivity(), bitmap2);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                }
                //    EditProfile(userid);
            }

        } else if (requestCode == CAMERA) {

            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            qrimage.setImageBitmap(thumbnail);
            image = "image";
            encodedImage = getStringImage(thumbnail);
            contentURI = getImageUri(getActivity(), thumbnail);

            // saveImage(thumbnail);
            //  Toast.makeText(getActivity(), "Image Saved!", Toast.LENGTH_SHORT).show();

            //      EditProfile(userid);
        } else {
            contentURI = Uri.parse("sim");
        }
    }

    public String saveImage(Bitmap myBitmap) {
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
            MediaScannerConnection.scanFile(getActivity(),
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        Log.w("path of image from .", encodedImage + "");
        return encodedImage;
    }

    private void Logout() {
        Toast.makeText(getActivity(), "Logout Successfully", Toast.LENGTH_SHORT).show();
        getActivity().getSharedPreferences(CommonUtils.MyPREFERENCES, 0).edit().clear().commit();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void setLognTask(String id) {
//        dialog = new ProgressDialog(Customers_Entry.this);
//        dialog.setMessage("Please wait.");
//        dialog.show();
        HashMap<String, String> map = new HashMap<>();
        map.put("module", "userbankList");
        map.put("id", id);
        readPostSgnin(GlobalConstants.BASE_URL, map);
        System.out.println("jjkhkhjh" + map);
    }

    public void readPostSgnin(String url, final HashMap<String, String> map) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("rtrtyrtyty" + response);

                        try {
                            //dialog.dismiss();
                            JSONObject jsonObject = new JSONObject(response);
                            String resultCode = jsonObject.getString("resultCode");
                            String message = jsonObject.getString("message");
                            JSONArray jsonArray = jsonObject.getJSONArray("user_bank_list");
                            if (resultCode.equalsIgnoreCase("200")) {
                                // dialog.dismiss();

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String id = object.getString("id");
                                    String title = object.getString("bank_name");
                                    System.out.println("bhgfcfxfdfc");
                                    Map<String, Object> item = new HashMap<>();
                                    item.put("id", id);
                                    item.put("name", title);
                                    membershipTitle1.add(item);
                                }

                                SimpleAdapter arrayAdapter = new SimpleAdapter(getActivity(), membershipTitle1,
                                        R.layout.spinneritemback,
                                        new String[]{"name"}, new int[]{R.id.text1});
                                arrayAdapter.setDropDownViewResource(R.layout.spinnershowitemdropdown);
                                spinBankName.setAdapter(arrayAdapter);

                                Map<String, Object> selectedItem1 = membershipTitle1.get(spinBankName.getSelectedItemPosition());
                                String name = selectedItem1.get("name").toString();
                                String id = selectedItem1.get("id").toString();
                                System.out.println("dkhkjhgjkdjkd" + name + id);

                            } else {
                                // dialog.dismiss();
                                //  boo.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            //boo.setVisibility(View.GONE);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(getActivity(), "Server Error! Try again after some time.", Toast.LENGTH_LONG);
                        View view1 = toast.getView();
                        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);

                        TextView text = (TextView) view1.findViewById(android.R.id.message);
                        text.setGravity(View.TEXT_ALIGNMENT_CENTER);
                        text.setTextSize(12);

                        toast.show();

                        try {
                            //  dialog.dismiss();
                            JSONObject jsonObject = new JSONObject(new String(error.networkResponse.data));
                            String errormsg = jsonObject.getString("message");
                            Toast.makeText(getActivity(), errormsg, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.d("logger - SignInActivity", "Error while parsing Login Error Response: " + e.toString());
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                return map;
            }
        };
        queue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void profileInfo() {
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Please wait.");
        dialog.show();
        map.put("module", "userProfile");
        map.put("user_id", userid);
        System.out.println("jlkjljlkjl0" + map);
        readPostSignUp(GlobalConstants.BASE_URL, map);
    }

    public void readPostSignUp(String url, final HashMap<String, String> map) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onResponse(String response) {
                        System.out.println("hfghghgjghj" + response);
                        try {
                            dialog.dismiss();
                            JSONObject jsonObject = new JSONObject(response);
                            String resultCode = jsonObject.getString("resultCode");
                            String message = jsonObject.getString("message");
                            JSONArray jsonArray = jsonObject.getJSONArray("user_details");
                            if (resultCode.equalsIgnoreCase("200")) {
                                dialog.dismiss();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String id = jsonObject1.getString("user_id");
                                    String name = jsonObject1.getString("name");
                                    String phone = jsonObject1.getString("phone");
                                    String email = jsonObject1.getString("email");
                                    String upi_name = jsonObject1.getString("upi_name");
                                    String upi_id = jsonObject1.getString("upi_id");
                                    String qr_code = jsonObject1.getString("qr_code");
                                    String paytm_no = jsonObject1.getString("paytm_no");
                                    String phonepe_no = jsonObject1.getString("phonepe_no");
                                    String default_bank_id = jsonObject1.getString("default_bank_id");
                                    String referral_code = jsonObject1.getString("referral_code");
                                    etAddress.setText(referral_code);
                                    etReferal.setText(referral_code);
                                    etName.setText(name);
                                    etPhone.setText(phone);
                                    etEmail.setText(email);
                                    etPhonePe.setText(phonepe_no);
                                    etPaytmNumber.setText(paytm_no);
                                    etUpiId.setText(upi_id);
                                    etUpiName.setText(upi_name);
                                    if (!qr_code.equalsIgnoreCase("")) {
                                        Glide.with(context)
                                                .load(qr_code)
                                                .into(qrimage);

                                    }
                                }

                            } else {
                                dialog.dismiss();
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        NetworkResponse response = error.networkResponse;
                        String errorMsg = "";
                        if (response != null && response.data != null) {
                            String errorString = new String(response.data);
                            Log.i("log error", errorString);
                        }
                        try {
                            dialog.dismiss();
                            JSONObject jsonObject = new JSONObject(new String(error.networkResponse.data));
                            String errormsg = jsonObject.getString("message");
                            Toast.makeText(getActivity(), errormsg, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.d("logger - SignInActivity", "Error while parsing Login Error Response: " + e.toString());
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                return map;
            }
        };
        queue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void availableBal() {

        map.put("module", "walletBalance");
        map.put("user_id", userid);
        System.out.println("jkhkjhkjhjk0" + map);
        readPost(GlobalConstants.BASE_URL, map);


    }

    public void readPost(String url, final HashMap<String, String> map) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onResponse(String response) {
                        System.out.println("lsghldhlkdh" + response);
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String resultCode = jsonObject.getString("resultCode");
                            String message = jsonObject.getString("message");
                            JSONObject jsonArray = jsonObject.getJSONObject("wallet_data");
                            if (resultCode.equalsIgnoreCase("200")) {

                                String avilable_balance = jsonArray.getString("avilable_balance");

                                tvAvlBal.setText("Avl. bal:Rs " + avilable_balance);


                            } else {
                                dialog.dismiss();
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        NetworkResponse response = error.networkResponse;
                        String errorMsg = "";
                        if (response != null && response.data != null) {
                            String errorString = new String(response.data);
                            Log.i("log error", errorString);
                        }
                        try {
                            dialog.dismiss();
                            JSONObject jsonObject = new JSONObject(new String(error.networkResponse.data));
                            String errormsg = jsonObject.getString("message");
                            Toast.makeText(getActivity(), errormsg, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.d("logger - SignInActivity", "Error while parsing Login Error Response: " + e.toString());
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                return map;
            }
        };
        queue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


}