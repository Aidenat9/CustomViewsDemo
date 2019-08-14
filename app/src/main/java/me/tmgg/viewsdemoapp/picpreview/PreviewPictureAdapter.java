package me.tmgg.viewsdemoapp.picpreview;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/* compiled from: PreviewPictureAdapter.kt */
public final class PreviewPictureAdapter extends FragmentPagerAdapter {
    private final int adapterPosition;
    private final int currentIndex;
    private final List<String> imgLocalList;
    private final PreviewPictureActivity.StartPostTransitionListener mStartPostTransitionListener;
    private final List<String> urlList;

    public PreviewPictureAdapter(FragmentManager fragmentManager, int i, int i2, List<String> list, List<String> list2, PreviewPictureActivity.StartPostTransitionListener startPostTransitionListener) {
        super(fragmentManager);
        b(fragmentManager, "fm");
        b(list, "urlList");
        b(startPostTransitionListener, "mStartPostTransitionListener");
        this.currentIndex = i;
        this.adapterPosition = i2;
        this.urlList = list;
        this.imgLocalList = list2;
        this.mStartPostTransitionListener = startPostTransitionListener;
    }

    public static void b(Object obj, String str) {
        if (obj == null) {
            c(str);
        }
    }

    private static void c(String str) {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
        String className = stackTraceElement.getClassName();
        throw ((IllegalArgumentException)  new IllegalArgumentException("Parameter specified as non-null is null: method " + className + "." + stackTraceElement.getMethodName() + ", parameter " + str));
    }


    public Fragment getItem(int i) {
        String str = "";
        if (ListUtils.notNull(this.imgLocalList)) {
            List<String> list = this.imgLocalList;
            if (list == null) {
                throw new NullPointerException();
            }
            if (list.size() > i) {
                str = (String) this.imgLocalList.get(i);
            }
        }
        PreviewPictureFragment newInstance = PreviewPictureFragment.newInstance(i, this.currentIndex, this.adapterPosition, (String) this.urlList.get(i), str);
        newInstance.setStartPostTransitionListener(this.mStartPostTransitionListener);
        return newInstance;
    }

    public int getCount() {
        return this.urlList.size();
    }
}