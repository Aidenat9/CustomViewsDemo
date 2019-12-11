package me.tmgg.viewsdemoapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;

import me.tmgg.viewsdemoapp.R;
import me.tmgg.viewsdemoapp.bean.TabEntity;
import me.tmgg.viewsdemoapp.ui.about.AboutActivity;
import me.tmgg.viewsdemoapp.widgets.LoadingText;
import me.tmgg.viewsdemoapp.widgets.ability.AbilityBean;
import me.tmgg.viewsdemoapp.widgets.ability.AbilityMapView;

public class MainActivity extends AppCompatActivity {

    /**
     * demo
     */
    private static final String TAG = "main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


        AutoCompleteTextView autoCompleteTv = findViewById(R.id.aptextview);
        autoCompleteTv.setThreshold(1);
        List<String> universities = new ArrayList<>();

// Print out the values to the log
        for (int i = 0; i < 10; i++) {
            universities.add("提示"+i);
        }
        autoCompleteTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                autoCompleteTv.showDropDown();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e(TAG, "afterTextChanged: "+s.toString() );
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, universities);
        autoCompleteTv.setAdapter(adapter);
        autoCompleteTv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Log.e(TAG, "onItemClick: " + arg0.getItemAtPosition(arg2).toString());
            }
        });
        findViewById(R.id.btn_behavior).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), CustomBehaviorActivity.class));
            }
        });
        findViewById(R.id.btn_demo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), DemoFragmentActivity.class));
            }
        });
        findViewById(R.id.btn_web).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), WebActivity.class));
            }
        });
        findViewById(R.id.btn_twofloor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), TwofloorDemoActivity.class));
            }
        });
        findViewById(R.id.btn_thread).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), ScheduledThreadpoolActivity.class));
            }
        });
        findViewById(R.id.btn_piccommon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), PicCommonActivity.class));
            }
        });
        findViewById(R.id.btn_views).setOnClickListener(view -> {
            startActivity(new Intent(getBaseContext(), ViewsActivity.class));
        });
        findViewById(R.id.sink).setOnClickListener(view -> {
        });
        findViewById(R.id.raise).setOnClickListener(view -> {
            Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this).toBundle();
            startActivity(new Intent(getBaseContext(), AboutActivity.class), bundle);
        });
        findViewById(R.id.transitions).setOnClickListener(view -> {
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, view, view.getTransitionName());
            startActivity(new Intent(getBaseContext(), TransitionActivity.class), optionsCompat.toBundle());
        });
        findViewById(R.id.btn_alertwindow).setOnClickListener(view -> {
            startActivity(new Intent(getBaseContext(), SystemWindowActivity.class));
        });
        findViewById(R.id.btn_scrollImageView).setOnClickListener(view -> {
            startActivity(new Intent(getBaseContext(), SimpleListActivity.class));
        });
        findViewById(R.id.foreground).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        AbilityMapView abilityMapView = findViewById(R.id.abilityView);

        AbilityBean 生存 = new AbilityBean(90f, "生存");
        AbilityBean 击杀 = new AbilityBean(20f, "击杀");
        AbilityBean 助攻 = new AbilityBean(50f, "助攻");
        AbilityBean 死亡 = new AbilityBean(80f, "死亡");
        AbilityBean 金钱 = new AbilityBean(50f, "金钱");
        AbilityBean 补兵 = new AbilityBean(30f, "补兵");
        AbilityBean 推塔 = new AbilityBean(80f, "推塔");
        AbilityBean 小龙 = new AbilityBean(100f, "小龙");
        ArrayList<AbilityBean> abilityBeans = new ArrayList<>();
        abilityBeans.add(生存);
        abilityBeans.add(击杀);
        abilityBeans.add(助攻);
        abilityBeans.add(死亡);
        abilityBeans.add(金钱);
        abilityBeans.add(补兵);
        abilityBeans.add(推塔);
        abilityBeans.add(小龙);
        abilityMapView.setData(abilityBeans);
    }

    private void initView() {
        LoadingText loadingText = findViewById(R.id.loadingText);
        loadingText.setBitmap(R.mipmap.icon_camera);
        loadingText.start();
        findViewById(R.id.btn_valueAnim).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ValueAnimActivity.class)));
    }
}
