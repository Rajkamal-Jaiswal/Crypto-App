package com.e.cellpaycrypto.kycPage;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.e.cellpaycrypto.AppPermissionsRunTime;
import com.e.cellpaycrypto.R;
import com.e.cellpaycrypto.databinding.ActivityDoKycBinding;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class DoKycActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_GALLERY = 1988;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_REQUEST = 1888;
    public final int REQUEST_CODE_PERMISSION_MULTIPLE = 123;
    public ArrayList<AppPermissionsRunTime.MyPermissionConstants> myPermissionConstantsArrayList;
    ActivityDoKycBinding binding;
    Activity activity;
    String str_imgTypeClick = "";
    String status = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoKycBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = DoKycActivity.this;
        myToolbar();

        binding.adharFrontBtn.setOnClickListener(v -> {
            status = "1";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {

                    imgClick();

                }
            }
        });
        binding.adharBackBtn.setOnClickListener(v -> {
            status = "2";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    imgClick();
                }
            }
        });
        binding.panBtn.setOnClickListener(v -> {
            status = "3";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    imgClick();
                }
            }
        });
        binding.profilePicBtn.setOnClickListener(v -> {
            status = "4";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    imgClick();
                }
            }
        });


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
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            if (status.equalsIgnoreCase("1")) {
                binding.adharFrontView.setImageBitmap(photo);
            } else if (status.equalsIgnoreCase("2")) {
                binding.adharBackView.setImageBitmap(photo);
            } else if (status.equalsIgnoreCase("3")) {
                binding.panView.setImageBitmap(photo);
            } else if (status.equalsIgnoreCase("4")) {
                binding.profileView.setImageBitmap(photo);
            }
        } else if (requestCode == REQUEST_CODE_GALLERY && resultCode == Activity.RESULT_OK) {
            final Uri imageUri = data.getData();

//            ivProfile.setImageURI(imageUri);
            InputStream imageStream = null;
            try {
                imageStream = activity.getContentResolver().openInputStream(imageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            final Bitmap photo = BitmapFactory.decodeStream(imageStream);
//            newProfileImageValue = Helper.encodeImage(selectedImage);
            if (status.equalsIgnoreCase("1")) {
                binding.adharFrontView.setImageBitmap(photo);
            } else if (status.equalsIgnoreCase("2")) {
                binding.adharBackView.setImageBitmap(photo);
            } else if (status.equalsIgnoreCase("3")) {
                binding.panView.setImageBitmap(photo);
            } else if (status.equalsIgnoreCase("4")) {
                binding.profileView.setImageBitmap(photo);
            }
        }
    }

    private void checkStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            // Marshmallow+
            myPermissionConstantsArrayList = new ArrayList<>();
            myPermissionConstantsArrayList.add(AppPermissionsRunTime.MyPermissionConstants.PERMISSION_READ_EXTERNAL_STORAGE);
            myPermissionConstantsArrayList.add(AppPermissionsRunTime.MyPermissionConstants.PERMISSION_WRITE_EXTERNAL_STORAGE);
            myPermissionConstantsArrayList.add(AppPermissionsRunTime.MyPermissionConstants.PERMISSION_CAMERA);
            if (AppPermissionsRunTime.checkPermission(DoKycActivity.this, myPermissionConstantsArrayList, REQUEST_CODE_PERMISSION_MULTIPLE)) {
                //takeImage.getImagePickerDialog(ProfileActivity.this, getString(R.string.app_name), getString(R.string.choose_image));
                imgClick();
            }
        } else {
            // Pre-Marshmallow
            //takeImage.getImagePickerDialog(ProfileActivity.this, getString(R.string.app_name), getString(R.string.choose_image));
            imgClick();
        }
    }

    private void imgClick() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(DoKycActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals("Take Photo")) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else if (options[item].equals("Choose from Gallery")) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, REQUEST_CODE_GALLERY);

            } else if (options[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }


    private void myToolbar() {
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        TextView title = findViewById(R.id.toolbarTitleTV);
        title.setText("KYC");
        TextView btn = findViewById(R.id.btn);
        toolbar.setNavigationIcon(R.drawable.ic_back);

//        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        btn.setVisibility(View.VISIBLE);
        btn.setOnClickListener(v -> {
            Toast.makeText(this, "Coming Shortly!", Toast.LENGTH_SHORT).show();
        });
    }

}