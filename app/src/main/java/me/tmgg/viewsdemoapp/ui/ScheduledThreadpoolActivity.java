package me.tmgg.viewsdemoapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import me.tmgg.viewsdemoapp.R;

public class ScheduledThreadpoolActivity extends AppCompatActivity {

    private ScheduledThreadPoolExecutor threadPoolExecutor;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduled_threadpool);
        shutDownThread();
        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startThread();
            }
        });
        findViewById(R.id.btn_shutDown).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shutDownThread();
            }
        });
    }

    private void startThread() {

        threadPoolExecutor = new ScheduledThreadPoolExecutor(2);
        threadPoolExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "--->run: " + i);
                i++;
            }
        }, 3, 5, TimeUnit.SECONDS);
    }

    @Override
    protected void onStop() {
        shutDownThread();
        super.onStop();
    }

    private static final String TAG = "ScheduledThreadpool";

    private void shutDownThread() {
        if (null != threadPoolExecutor) {
            Log.e(TAG, "shutDownThread:  ");
            threadPoolExecutor.shutdown();
            threadPoolExecutor = null;
        }
    }
}
