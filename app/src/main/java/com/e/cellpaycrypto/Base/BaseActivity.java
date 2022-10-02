package com.e.cellpaycrypto.Base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class BaseActivity extends AppCompatActivity {
    private ProgressDialog mProgressDialog;

    public void setToolbar(Toolbar toolbar, boolean titleEnabled) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(titleEnabled);
    }

    public void setToolbarTitle(int resId) {
        getSupportActionBar().setTitle(resId);
    }


    /*  @Override
        public void onBackPressed() {
            super.onBackPressed();
            finishActivity();
        }
    */
    public void startActivity(Context context, Class name) {
        startActivity(new Intent(context, name));
    }

    public void finishActivity() {
        this.finish();
    }


    /**
     * @return boolean value
     * return true if Progress Dialog show else return false
     */
    public boolean isShowingProgressDialog() {
        return !(mProgressDialog == null) && mProgressDialog.isShowing();
    }

    /***
     * hide the Progress Dialog
     */
    public void hideLoadingDialog() {
        if (isDestroyingActivity())
            return;
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    public boolean isDestroyingActivity() {

        return isFinishing() || Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && isDestroyed();
    }

    public void exitDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finishedActivity();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }


    public void paydailoge() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(BaseActivity.this);
        builder1.setMessage("Please Select Your Name For Pay Your Amount. ");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void requestdailoge() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(BaseActivity.this);
        builder1.setMessage("Please Add Your Amount For REQUEST. ");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void requestdailoge1() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(BaseActivity.this);
        builder1.setMessage("Please Select Your Contact For REQUEST Your Amount. ");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void requestdailoge2() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(BaseActivity.this);
        builder1.setMessage("Please Fill Reason for Request Money. ");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
          /*((EditText)findViewById(R.id.inputReason)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
              @Override
              public void onFocusChange(View v, boolean hasFocus) {
                  if (!hasFocus) {
                      TextView tvNo;
                      LayoutInflater inflater = LayoutInflater.from(context);
                      final Dialog mDialog = new Dialog(context,android.R.style.Theme_Translucent_NoTitleBar);
                      mDialog.setCanceledOnTouchOutside(true);
                      mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                      mDialog.getWindow().setGravity(Gravity.BOTTOM);
                      WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
                      lp.dimAmount = 0.75f;
                      mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                      mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                      mDialog.getWindow();
                      View dialoglayout = inflater.inflate(R.layout.pay_dialog, null);
                      mDialog.setContentView(dialoglayout);
                      tvNo = (TextView) mDialog.findViewById(R.id.btnpay);
                      tvNo.setText("&"+amount + "" + " Pay ");
                      tvNo.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {
                              startActivity(new Intent(context,MainActivity.class));
                              finishActivity();
                              mDialog.dismiss();
                          }
                      });
                      mDialog.show();
                  }
              }
          });*/

    public void finishedActivity() {

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }


}
