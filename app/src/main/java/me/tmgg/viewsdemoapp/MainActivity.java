package me.tmgg.viewsdemoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.tmgg.viewsdemoapp.widgets.LoadingText;

public class MainActivity extends AppCompatActivity {

    /**
     * demo
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }
    private void initView() {
        LoadingText loadingText = findViewById(R.id.loadingText);
        loadingText.setBitmap(R.mipmap.icon_camera);
        loadingText.start();
        findViewById(R.id.btn_valueAnim).setOnClickListener(v->startActivity(new Intent(MainActivity.this,ValueAnimActivity.class)));
    }
}
