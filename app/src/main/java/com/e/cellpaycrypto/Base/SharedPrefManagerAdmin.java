package com.e.cellpaycrypto.Base;


import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManagerAdmin {
    //the constants

    private static final String SHARED_PREF_NAME = "SellPay";
    private static final String USER_ID = "user_id";
    private static final String ASHRAM_ID = "ashram_id";
    private static final String USER_NAME = "user_name";
    private static final String USER_EMAIL = "user_email";
    private static final String USER_MOBILE = "user_mobile";
    private static final String USER_TYPE = "user_type";
    private static final String STATUS = "status";
    private static final String USER_PHOTO = "user_photo";
    private static final String TOKEN = "token";

    private static SharedPrefManagerAdmin mInstance;
    private static Context mCtx;

    private SharedPrefManagerAdmin(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManagerAdmin getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManagerAdmin(context);
        }
        return mInstance;
    }


    public void saveSelectedMethod(Context mCtx, String id) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("selectedPMethod", id);
        editor.apply();
    }

    public void clearSelectedMethods() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("selectedPMethod");
        editor.apply();
    }

    //method to let the user login
    //this method will store the user data in shared preferences

    public void userLogin(/*ModelUser.User user*/) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
     /*   editor.putString(USER_ID, user.getUser_id());
        editor.putString(ASHRAM_ID,user.getAshram_id());
        editor.putString(USER_NAME,user.getUser_name());
        editor.putString(USER_EMAIL,user.getUser_email());
        editor.putString(USER_MOBILE,user.getUser_mobile());
        editor.putString(USER_TYPE,user.getUser_type());
        editor.putString(STATUS,user.getStatus());
        editor.putString(USER_PHOTO,user.getUser_photo());
        editor.putString(TOKEN,user.getToken());*/
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_NAME, null) != null;
    }

    public String getPayMethods() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("selectedPMethod", null);
    }

    //this method will give the logged in user
   /* public ModelUser.User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new ModelUser.User(
                sharedPreferences.getString(USER_ID, null),
                sharedPreferences.getString(ASHRAM_ID, null),
                sharedPreferences.getString(USER_NAME, null),
                sharedPreferences.getString(USER_EMAIL, null),
                sharedPreferences.getString(USER_MOBILE, null),
                sharedPreferences.getString(USER_TYPE, null),
                sharedPreferences.getString(STATUS, null),
                sharedPreferences.getString(USER_PHOTO, null),
                sharedPreferences.getString(TOKEN, null)
        );
    }*/

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
//        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }

}
