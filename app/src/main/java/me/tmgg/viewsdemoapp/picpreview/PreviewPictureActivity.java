package me.tmgg.viewsdemoapp.picpreview;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.SharedElementCallback;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.transition.TransitionManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.jvm.internal.markers.KMutableMap;
import me.tmgg.viewsdemoapp.App;
import me.tmgg.viewsdemoapp.R;

public final class PreviewPictureActivity extends AppCompatActivity {
    public static final String ADAPTER_POSITION = " adapter_position";
    public static final String SHOW_DEL_BUTTON = "show_del_button";
    public static final String TYPE_INDEX = "index";
    public static final String TYPE_ORIGIN_URLS = "origin_urls";
    public static final String TYPE_URLS = "urls";
    public static final String WITH_SHARE_VIEW = "with_share_view";
    private HashMap<Integer, View> _$_findViewCache;
    private int adapterPosition;
    private int currentIndex;
    private ArrayList<String> imgLocalPathList = new ArrayList<>();
    private StartPostTransitionListener mStartPostTransitionListener = new StartPostTransitionListener() {
        @Override
        public void onStartPostTransition(View view) {
            setStartPostTransition(view);
        }
    };
    /* access modifiers changed from: private */
    public final ArrayList<String> urlList = new ArrayList<>();

    public static void start(Context context, View view, List<String> list) {
        start(context, view, list, null, 0, false, 0);
    }

    public static void start(Context context, View view, List<String> list, List<String> list2) {
        start(context, view, list, list2, 0, false, 0);
    }

    public static void start(Context context, View view, List<String> list, List<String> list2, int i) {
        start(context, view, list, list2, i, false, 0);
    }

    public static void start(Context context, View view, List<String> list, List<String> list2, int i, boolean z) {
        start(context, view, list, list2, i, z, 0);
    }

    public static void start(Context context, List<String> list) {
        start(context, null, list, null, 0, false, 0);
    }

    public static void start(Context context, View view, List<String> list, List<String> list2, int i, boolean z, int i2) {
        Intrinsics.checkNotNull(context, "context");
        Intent intent = new Intent(context, PreviewPictureActivity.class);
        String str = PreviewPictureActivity.TYPE_URLS;
        if (list == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.ArrayList<kotlin.String> /* = java.util.ArrayList<kotlin.String> */");
        }
        intent.putStringArrayListExtra(str, (ArrayList<String>) list);
        intent.putStringArrayListExtra(PreviewPictureActivity.TYPE_ORIGIN_URLS, (ArrayList<String>) list2);
        intent.putExtra(PreviewPictureActivity.TYPE_INDEX, i);
        intent.putExtra(PreviewPictureActivity.ADAPTER_POSITION, i2);
        intent.putExtra(PreviewPictureActivity.SHOW_DEL_BUTTON, z);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        if (VERSION.SDK_INT < 21 || view == null || !(context instanceof Activity)) {
            context.startActivity(intent);
            return;
        }
        intent.putExtra(PreviewPictureActivity.WITH_SHARE_VIEW, true);
        ActivityCompat.startActivity(context, intent, ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, view, TextUtils.isEmpty(view.getTransitionName()) ? "shareImage" : view.getTransitionName()).toBundle());
    }

    /* compiled from: PreviewPictureActivity.kt */
    public interface StartPostTransitionListener {
        void onStartPostTransition(View view);
    }

    public void _$_clearFindViewByIdCache() {
        if (this._$_findViewCache != null) {
            this._$_findViewCache.clear();
        }
    }

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap<>();
        }
        View view = this._$_findViewCache.get(Integer.valueOf(i));
        if (view != null) {
            return view;
        }
        View findViewById = findViewById(i);
        this._$_findViewCache.put(Integer.valueOf(i), findViewById);
        return findViewById;
    }

    public final void setStatusBarColor(int i) {
        if (VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            Intrinsics.checkNotNull((Object) window, "window");
            View decorView = window.getDecorView();
            Intrinsics.checkNotNull((Object) decorView, "decorView");
            decorView.setSystemUiVisibility(3328);
            Window window2 = getWindow();
            Intrinsics.checkNotNull((Object) window2, "window");
            window2.setStatusBarColor(i);
        }
    }

    /* access modifiers changed from: protected */
    @SuppressLint({"SetTextI18n"})
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setStatusBarColor(0);
        getWindow().setBackgroundDrawableResource(17170445);
        Window window = getWindow();
        Intrinsics.checkNotNull((Object) window, "window");
        window.getDecorView().setBackgroundColor(0);
        if (VERSION.SDK_INT >= 21) {
            postponeEnterTransition();
        }
        setContentView(R.layout.activity_preview_picture);
        if (getIntent().getBooleanExtra(SHOW_DEL_BUTTON, false)) {
            TextView textView = (TextView) _$_findCachedViewById(R.id.tv_download);
            if (textView != null) {
                textView.setVisibility(View.GONE);
            }
        }
        TextView textView2 = (TextView) _$_findCachedViewById(R.id.tv_download);
        if (textView2 != null) {
            textView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickDownload();
                }
            });
        }
        this.currentIndex = getIntent().getIntExtra(TYPE_INDEX, 0);
        this.currentIndex = this.currentIndex == -1 ? 0 : this.currentIndex;
        this.adapterPosition = getIntent().getIntExtra(ADAPTER_POSITION, -1);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Intrinsics.checkNotNull((Object) supportFragmentManager, "supportFragmentManager");
        PreviewPictureAdapter previewPictureAdapter = new PreviewPictureAdapter(supportFragmentManager, this.currentIndex, this.adapterPosition, this.urlList, this.imgLocalPathList, this.mStartPostTransitionListener);
        HackyViewPager hackyViewPager = (HackyViewPager) _$_findCachedViewById(R.id.view_pager);
        if (hackyViewPager != null) {
            hackyViewPager.setAdapter(previewPictureAdapter);
        }
        HackyViewPager hackyViewPager2 = (HackyViewPager) _$_findCachedViewById(R.id.view_pager);
        if (hackyViewPager2 != null) {
            hackyViewPager2.setOffscreenPageLimit(3);
        }
        if (getIntent().hasExtra(TYPE_URLS)) {
            ArrayList<String> stringArrayListExtra = getIntent().getStringArrayListExtra(TYPE_URLS);
            this.urlList.clear();
            this.urlList.addAll(stringArrayListExtra);
            previewPictureAdapter.notifyDataSetChanged();
            if (this.currentIndex < this.urlList.size()) {
                HackyViewPager hackyViewPager3 = (HackyViewPager) _$_findCachedViewById(R.id.view_pager);
                if (hackyViewPager3 != null) {
                    hackyViewPager3.setCurrentItem(this.currentIndex, false);
                }
                TextView textView3 = (TextView) _$_findCachedViewById(R.id.tv_pager_indicator);
                if (textView3 != null) {
                    textView3.setText(String.valueOf(this.currentIndex + 1) + "/" + this.urlList.size());
                }
            }
        }
        if (getIntent().hasExtra(TYPE_ORIGIN_URLS)) {
            this.imgLocalPathList = getIntent().getStringArrayListExtra(TYPE_ORIGIN_URLS);
        }
        HackyViewPager hackyViewPager4 = (HackyViewPager) _$_findCachedViewById(R.id.view_pager);
        if (hackyViewPager4 != null) {
            hackyViewPager4.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int i) {
                    TextView textView = (TextView) _$_findCachedViewById(R.id.tv_pager_indicator);
                    if (textView != null) {
                        textView.setText(String.valueOf(i + 1) + "/" + urlList.size());
                    }
                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });
        }
    }

    public void finishAfterTransition() {
        View view = null;
        HackyViewPager hackyViewPager = (HackyViewPager) _$_findCachedViewById(R.id.view_pager);
        Integer num = hackyViewPager != null ? Integer.valueOf(hackyViewPager.getCurrentItem()) : null;
        setResult(-1, new Intent().putExtra("exit_position", num));
        int i = this.currentIndex;
        if (num == null || i != num.intValue()) {
            HackyViewPager hackyViewPager2 = (HackyViewPager) _$_findCachedViewById(R.id.view_pager);
            if (hackyViewPager2 != null) {
                view = hackyViewPager2.findViewWithTag(getString(R.string.transition_name, new Object[]{Integer.valueOf(this.adapterPosition), num}));
            }
            if (view != null) {
                setSharedElementCallback(view);
            }
        }
        super.finishAfterTransition();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        removeActivityFromTransitionManager(this);
    }

    private final void removeActivityFromTransitionManager(Activity activity) {
        if (VERSION.SDK_INT >= 21) {
            Class<TransitionManager> cls = TransitionManager.class;
            try {
                Field declaredField = cls.getDeclaredField("sRunningTransitions");
                declaredField.setAccessible(true);
                Object obj = declaredField.get(cls);
                if (obj == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.ThreadLocal<java.lang.ref.WeakReference<android.support.v4.util.ArrayMap<android.view.ViewGroup, kotlin.collections.ArrayList<android.support.transition.Transition> /* = java.util.ArrayList<android.support.transition.Transition> */>>>");
                }
                ThreadLocal threadLocal = (ThreadLocal) obj;
                if (threadLocal.get() != null && ((WeakReference) threadLocal.get()).get() != null) {
                    ArrayMap arrayMap = (ArrayMap) ((WeakReference) threadLocal.get()).get();
                    Window window = activity.getWindow();
                    Intrinsics.checkNotNull((Object) window, "activity.window");
                    View decorView = window.getDecorView();
                    if (arrayMap != null) {
                        Map map = arrayMap;
                        if (map == null) {
                            throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.Map<K, *>");
                        } else if (map.containsKey(decorView)) {
                            c(arrayMap).remove(decorView);
                        }
                    }
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
            }
        }


    }

    public static Map c(Object obj) {
        if ((obj instanceof KMappedMarker) && !(obj instanceof KMutableMap)) {
            Intrinsics.checkNotNull(obj, "kotlin.collections.MutableMap");
        }
        try {
            return (Map) obj;
        } catch (ClassCastException e) {
            return null;
        }
    }

    @TargetApi(21)
    private final void setSharedElementCallback(View view) {
        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                if (names != null) {
                    names.clear();
                }
                if (sharedElements != null) {
                    sharedElements.clear();
                }
                if (names != null) {
                    String transitionName = view.getTransitionName();
                    Intrinsics.checkNotNull((Object) transitionName, "view.transitionName");
                    names.add(transitionName);
                }
                if (sharedElements != null) {
                    String transitionName2 = view.getTransitionName();
                    Intrinsics.checkNotNull((Object) transitionName2, "view.transitionName");
                    sharedElements.put(transitionName2, view);
                }

            }
        });
    }

    /* access modifiers changed from: private */
    @TargetApi(21)
    public final void setStartPostTransition(View view) {
        if (view != null) {
            ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
            if (viewTreeObserver != null) {
                viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        view.getViewTreeObserver().removeOnPreDrawListener(this);
                        startPostponedEnterTransition();
                        return false;
                    }
                });
            }
        }
    }

    public final void clickDownload() {
        int i;
        HackyViewPager hackyViewPager = (HackyViewPager) _$_findCachedViewById(R.id.view_pager);
        if (hackyViewPager != null) {
            i = hackyViewPager.getCurrentItem();
        } else {
            i = 0;
        }
        Toast.makeText(this, "正在保存图片...", Toast.LENGTH_LONG).show();
        if (i < this.urlList.size()) {
            String str = (String) this.urlList.get(i);
            Intrinsics.checkNotNull((Object) str, "imagePath");
            if (b(str, "http://", false, 2, null) || b(str, "https://", false, 2, null)) {
                Toast.makeText(this, "正在下载图片...", Toast.LENGTH_LONG).show();
            } else {
                FileUtils.saveImage2Gallery(App.getInstance().getApplicationContext(), str);
            }
        }
    }

    public static /* synthetic */ boolean b(String str, String str2, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return b(str, str2, z);
    }

    public static final boolean b(String str, String str2, boolean z) {
        if (!z) {
            return str.startsWith(str2);
        }
        return a(str, 0, str2, 0, str2.length(), z);
    }

    public static final boolean a(String str, int i, String str2, int i2, int i3, boolean z) {
        if (!z) {
            return str.regionMatches(i, str2, i2, i3);
        }
        return str.regionMatches(z, i, str2, i2, i3);
    }


    public final boolean withShareView() {
        return getIntent().getBooleanExtra(WITH_SHARE_VIEW, false);
    }

    public final void exitActivity() {
        if (!withShareView() || VERSION.SDK_INT < 21) {
            finish();
            overridePendingTransition(0, R.anim.slide_out_down_fade);
            return;
        }
        finishAfterTransition();
    }
}