package com.e.cellpaycrypto.Base;

import android.content.Context;
import android.content.SharedPreferences;

import com.e.cellpaycrypto.apimodels.ModelSettings;

public class SettingsManager {

    private static final String SHARED_PREF_NAME = "Settings";

    private static final String coin_today_cost = "coin_today_cost";
    private static final String min_deposit_coin = "min_deposit_coin";
    private static final String min_deposit_coin_online = "min_deposit_coin_online";
    private static final String withdrawal_limit = "withdrawal_limit";
    private static final String p2p_sell_limit = "p2p_sell_limit";
    private static final String trade_limit = "trade_limit";
    private static final String admin_fee_onsell = "admin_fee_onsell";
    private static final String account_holder_name = "account_holder_name";
    private static final String account_number = "account_number";
    private static final String ifsc_code = "ifsc_code";
    private static final String bank_name = "bank_name";
    private static final String branch_name = "branch_name";
    private static final String upi_id = "upi_id";
    private static final String binance_id = "binance_id";


    private static SettingsManager mInstance;
    private static Context mCtx;

    public SettingsManager(Context context) {
        mCtx = context;
    }

    public static synchronized SettingsManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SettingsManager(context);
        }
        return mInstance;
    }


    public void saveSettings(ModelSettings.Settings settings) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        editor.putString(coin_today_cost, settings.getCoin_today_cost());
        editor.putString(min_deposit_coin, settings.getMin_deposit_coin());
        editor.putString(min_deposit_coin_online, settings.getMin_deposit_coin_online());
        editor.putString(withdrawal_limit, settings.getWithdrawal_limit());
        editor.putString(p2p_sell_limit, settings.getP2p_sell_limit());
        editor.putString(trade_limit, settings.getTrade_limit());
        editor.putString(admin_fee_onsell, settings.getAdmin_fee_onsell());
        editor.putString(account_holder_name, settings.getAccount_holder_name());
        editor.putString(account_number, settings.getAccount_number());
        editor.putString(ifsc_code, settings.getIfsc_code());
        editor.putString(bank_name, settings.getBank_name());
        editor.putString(branch_name, settings.getBranch_name());
        editor.putString(upi_id, settings.getUpi_id());
        editor.putString(binance_id, settings.getBinance_id());
        editor.apply();
    }

    public ModelSettings.Settings getSettings() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new ModelSettings.Settings(sharedPreferences.getString(coin_today_cost, null),
                sharedPreferences.getString(min_deposit_coin, null),
                sharedPreferences.getString(min_deposit_coin_online, null),
                sharedPreferences.getString(withdrawal_limit, null),
                sharedPreferences.getString(p2p_sell_limit, null),
                sharedPreferences.getString(trade_limit, null),
                sharedPreferences.getString(admin_fee_onsell, null),
                sharedPreferences.getString(account_holder_name, null),
                sharedPreferences.getString(account_number, null),
                sharedPreferences.getString(ifsc_code, null),
                sharedPreferences.getString(bank_name, null),
                sharedPreferences.getString(branch_name, null),
                sharedPreferences.getString(upi_id, null),
                sharedPreferences.getString(binance_id, null));
    }

    public void clearSettings() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
