package com.github.tianmu19.myaccessibilityservice;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.github.tianmu19.myaccessibilityservice.utils.BaseAccessibilityService;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private PackageManager mPackageManager;
    private String[] mPackages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BaseAccessibilityService.getInstance().init(this);
        mPackageManager = this.getPackageManager();
        mPackages = new String[]{"com.youtoo"};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.REQUEST_INSTALL_PACKAGES},
                    100);
        }else{
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.INSTALL_PACKAGES},
                    100);
        }
        findViewById(R.id.btn_clean).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleanProcess(v);
            }
        });
        findViewById(R.id.btn_goAccess).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goAccess(v);
            }
        });
        findViewById(R.id.btn_goApp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goApp(v);
            }
        });
        findViewById(R.id.btn_install).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoInstall(v);
            }
        });
        findViewById(R.id.btn_qutoutiao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processQutoutiao(v);
            }
        });


    }

    private final static  String QUTOUTIAO_PACKAGENAME = "com.jifen.qukan";
    private final static  String QUTOUTIAO_MAIN = "com.jifen.qukan.content.newsdetail.news.NewsDetailNewActivity";
    private void processQutoutiao(View v) {
        goApp(v);
    }

    public void goAccess(View view) {
        BaseAccessibilityService.getInstance().goAccess();
    }

    public void goApp(View view) {
        Intent intent = mPackageManager.getLaunchIntentForPackage(QUTOUTIAO_PACKAGENAME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void cleanProcess(View view) {
        for (String mPackage : mPackages) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", mPackage, null);
            intent.setData(uri);
            startActivity(intent);
        }
    }

    private static final String TAG = "main";
    public void autoInstall(View view) {
        String apkPath = Environment.getExternalStorageDirectory() + "/test.apk";
        File file = new File(apkPath);
        Log.e(TAG, "autoInstall: "+file.getAbsolutePath() );
        Intent localIntent = new Intent(Intent.ACTION_VIEW);
        Uri updateUri = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            localIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            updateUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileProvider", file);
        } else {
            updateUri = Uri.fromFile(file);
        }
        localIntent.setDataAndType(updateUri, "application/vnd.android.package-archive");
        startActivity(localIntent);
    }
}
