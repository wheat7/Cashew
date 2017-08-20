package com.wheat7.cashew.utils;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by wheat7 on 10/05/2017.
 */

public class App extends Application{

    public static final String SDCARD= Environment.getExternalStorageDirectory()+"";
    public static String CACHE_FILE;
    public static String CACHE_IMAGES;
    public static String CACHE_DOWNLOADS;
    public static String CACHE_DATASETS;
    public static String CACHE_ASSETS;
    public static String CACHE_SCREENCAPS;
    public static String CACHE_SCREENSHOTS;
    public static String CACHE_VIDEO;
    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        intDIR();
        appContext = getApplicationContext();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
    public static Context getAppContext() {
        return appContext;
    }

    private void intDIR(){
        CACHE_FILE=getExternalFilesDir(null)+"/";
        CACHE_IMAGES=getExternalFilesDir("images")+"/";
        CACHE_DOWNLOADS=getExternalFilesDir("downloads")+"/";
        CACHE_SCREENCAPS=getExternalFilesDir("ScreenCaps")+"/";
        CACHE_SCREENSHOTS=getExternalFilesDir("ScreenShots")+"/";
        CACHE_DATASETS=getExternalFilesDir("DataSets")+"/";
        CACHE_ASSETS=getExternalFilesDir("AssetsBundles")+"/";
        CACHE_VIDEO=getExternalFilesDir("video")+"/";
    }


}
