package me.tmgg.viewsdemoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;

import me.tmgg.viewsdemoapp.widgets.LoadingText;
import me.tmgg.viewsdemoapp.widgets.ability.AbilityBean;
import me.tmgg.viewsdemoapp.widgets.ability.AbilityMapView;

public class MainActivity extends AppCompatActivity {

    /**
     * demo
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        findViewById(R.id.btn_behavior).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),CustomBehaviorActivity.class));
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
        findViewById(R.id.btn_valueAnim).setOnClickListener(v->startActivity(new Intent(MainActivity.this,ValueAnimActivity.class)));
    }
}
