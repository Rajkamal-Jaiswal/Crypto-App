package com.e.cellpaycrypto;

import android.content.Context;
import android.os.StrictMode;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.e.cellpaycrypto.Base.Helper;
import com.e.cellpaycrypto.Base.LocalFileUncaughtExceptionHandler;

public class MyApplication extends MultiDexApplication {
    private static Context appContext;

    public static Context getAppContext() {
        return appContext;
    }

    //nightMode
    public static final String NIGHT_MODE = "NIGHT_MODE";
    private boolean isNightModeEnabled = false;

    private static MyApplication singleton = null;

    public static MyApplication getInstance() {

        if (singleton == null) {
            singleton = new MyApplication();
        }
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(new LocalFileUncaughtExceptionHandler(this,
                Thread.getDefaultUncaughtExceptionHandler()));
        appContext = this;

        Helper.setCanTakeScreenShot(true);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

     /*   CrashReporter.initialize(this);
        FirebaseApp.initializeApp(appContext);*/
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }


    @Override
    protected void attachBaseContext(Context base) {
        MultiDex.install(this);

        super.attachBaseContext(base);
    }

}
