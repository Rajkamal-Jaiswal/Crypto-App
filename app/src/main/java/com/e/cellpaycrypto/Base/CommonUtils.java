package com.e.cellpaycrypto.Base;

/**
 * Created by DRD on 11/14/2017.
 */


import android.content.Context;
import android.widget.Toast;


public class CommonUtils {
    public static final String MyPREFERENCES = "com.moneymova";
    public static final String shared_USER_ID = "user_id";
    public static final String shared_Name = "name";
    public static final String shared_USER_Name = "username";
    public static final String shared_password = "password";
    public static final String shared_Phone_number = "phone";
    public static final String shared_Email = "email";
    public static final String shared_Rank = "rank";
    public static final String paytm = "paytm";


    public static void showToast(Context c,String msg) {
        Toast.makeText(c, msg, Toast.LENGTH_SHORT).show();
    }


}
