package me.tmgg.viewsdemoapp.ui.about;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import me.tmgg.viewsdemoapp.R;
import me.tmgg.viewsdemoapp.fragments.AboutFragment1;
import me.tmgg.viewsdemoapp.fragments.AboutFragment2;
import me.tmgg.viewsdemoapp.fragments.AboutFragment3;

/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/11/14 16:06
 * package：me.tmgg.viewsdemoapp.ui.about
 * version：1.0
 * <p>description：              </p>
 */
public class AboutActivity extends AppCompatActivity {
    private List<Fragment> fragments = new ArrayList<>(3);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ViewPager viewPager = findViewById(R.id.pager);
        InkPageIndicator indicator = findViewById(R.id.indicator);
        me.tmgg.viewsdemoapp.ui.about.ElasticDragDismissFrameLayout dismissFrameLayout = findViewById(R.id.dragFramelayout2);
        fragments.add(new AboutFragment1());
        fragments.add(new AboutFragment2());
        fragments.add(new AboutFragment3());
        AboutAdapter adapter = new AboutAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull View view, float v) {
                float dimension = getResources().getDimension(R.dimen.padding_normal);
                view.setTranslationX(v * dimension);
            }
        });
        indicator.setViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        dismissFrameLayout.addListener(new ElasticDragDismissFrameLayout.SystemChromeFader(AboutActivity.this){
            @Override
            public void onDragDismissed() {
                if (dismissFrameLayout.getTranslationY() > 0) {
                    Transition transition = TransitionInflater.from(AboutActivity.this).inflateTransition(R.transition.about_return_downward);
                    getWindow().setReturnTransition(transition);
                }
                finishAfterTransition();
            }
        });
    }

    private class AboutAdapter extends FragmentPagerAdapter {

        public AboutAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
