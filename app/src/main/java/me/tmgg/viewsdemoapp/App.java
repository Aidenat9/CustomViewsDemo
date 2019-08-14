package me.tmgg.viewsdemoapp;

import android.app.Application;
import android.util.Log;

import com.bumptech.glide.request.target.ViewTarget;

/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/4/25 16:50
 * package：me.tmgg.viewsdemoapp
 * version：1.0
 * <p>description：              </p>
 */
public class App extends Application {
    private static final String TAG = "app";
    private static App instance ;
    @Override
    public void onCreate() {
        super.onCreate();
        ViewTarget.setTagId(R.id.glideIndexTag);
    }

    public static App getInstance(){
        if(null==instance){
            instance = new App();
        }
        return instance;
    }
    public void exitCrash(Throwable ex){
        //保存上传 异常的逻辑
        Log.e(TAG, "exitCrash: "+ex.getMessage() );
        System.exit(0);
    }
}
