package me.tmgg.viewsdemoapp.picpreview;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.io.File;
import java.util.HashMap;

import kotlin.TypeCastException;
import me.tmgg.viewsdemoapp.R;

/* compiled from: PreviewPictureFragment.kt */
public final class PreviewPictureFragment extends BaseFragment {
    /* access modifiers changed from: private */
    public static final String ADAPTER_POSITION = "ADAPTER_POSITION";
    /* access modifiers changed from: private */
    public static final String CURRENT_INDEX = "CURRENT_INDEX";
    /* access modifiers changed from: private */
    public static final String IMG_LOCAL_PATH = "IMG_LOCAL_PATH";
    private static final int IMG_MAX_WIDTH = 1080;
    private static final int IMG_MAX_WIDTH_SMALL = 480;
    /* access modifiers changed from: private */
    public static final String IMG_POSITION = "IMG_POSITION";
    /* access modifiers changed from: private */
    public static final String IMG_URL = "IMG_URL";
    private HashMap _$_findViewCache;
    private int adapterPosition;
    private Bitmap bitmap;
    private int currentIndex;
    private File imgFile;
    private int[] imgInfo = new int[2];
    private String imgLocalPath;
    private boolean isLongImg;
    /* access modifiers changed from: private */
    public boolean isVisibleToUser;
    private int position;
    private PreviewPictureActivity.StartPostTransitionListener startPostTransitionListener;
    private String transitionName;
    private String url;


    public static PreviewPictureFragment newInstance(int i, int i2, int i3, String str, String str2) {
        b(str, "url");
        b(str2, "imgLocalPath");
        Bundle bundle = new Bundle();
        bundle.putString(PreviewPictureFragment.IMG_URL, str);
        bundle.putString(PreviewPictureFragment.IMG_LOCAL_PATH, str2);
        bundle.putInt(PreviewPictureFragment.IMG_POSITION, i);
        bundle.putInt(PreviewPictureFragment.ADAPTER_POSITION, i3);
        bundle.putInt(PreviewPictureFragment.CURRENT_INDEX, i2);
        PreviewPictureFragment previewPictureFragment = new PreviewPictureFragment();
        previewPictureFragment.setArguments(bundle);
        return previewPictureFragment;
    }


    public static void b(Object obj, String str) {
        if (obj == null) {
            c(str);
        }
    }

    private static void c(String str) {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
        String className = stackTraceElement.getClassName();
        throw ((IllegalArgumentException) new IllegalArgumentException("Parameter specified as non-null is null: method " + className + "." + stackTraceElement.getMethodName() + ", parameter " + str));
    }

    public void _$_clearFindViewByIdCache() {
        if (this._$_findViewCache != null) {
            this._$_findViewCache.clear();
        }
    }

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view != null) {
            return view;
        }
        View view2 = getView();
        if (view2 == null) {
            return null;
        }
        View findViewById = view2.findViewById(i);
        this._$_findViewCache.put(Integer.valueOf(i), findViewById);
        return findViewById;
    }

    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    /* access modifiers changed from: private */
    public final void exitActivity() {
        if (getActivity() == null) {
            return;
        }
        if (getActivity() instanceof PreviewPictureActivity) {
            FragmentActivity activity = getActivity();
            if (activity == null) {
                throw new TypeCastException("null cannot be cast to non-null type im.juejin.android.base.activity.PreviewPictureActivity");
            }
            if (!((PreviewPictureActivity) activity).withShareView()) {
                ElasticDragDismissFrameLayout elasticDragDismissFrameLayout = (ElasticDragDismissFrameLayout) _$_findCachedViewById(R.id.dragdismiss_drag_dismiss_layout);
                if (elasticDragDismissFrameLayout != null) {
                    elasticDragDismissFrameLayout.setBackgroundColor(0);
                }
            }
            FragmentActivity activity2 = getActivity();
            if (activity2 == null) {
                throw new TypeCastException("null cannot be cast to non-null type im.juejin.android.base.activity.PreviewPictureActivity");
            }
            ((PreviewPictureActivity) activity2).exitActivity();
            return;
        }
        FragmentActivity activity3 = getActivity();
        if (activity3 != null) {
            activity3.supportFinishAfterTransition();
        }
    }

    public void onCreate(Bundle bundle) {
        String thumbnailUrlLimitSize;
        super.onCreate(bundle);
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.url = arguments.getString(IMG_URL);
            this.imgLocalPath = arguments.getString(IMG_LOCAL_PATH);
            this.position = arguments.getInt(IMG_POSITION);
            this.adapterPosition = arguments.getInt(ADAPTER_POSITION);
            this.currentIndex = arguments.getInt(CURRENT_INDEX);
        }
        String str = this.url;
        if (str != null) {
            if (me.tmgg.viewsdemoapp.picpreview.Intrinsics.b(str, "content://", false, 2, null)) {
                this.url = ImageUtils.getImagePath(Uri.parse(str));
            }
            if (ImageUtils.isGif(this.url)) {
                thumbnailUrlLimitSize = ImageUtils.getGifThumbnailUrlLimitSize(str, IMG_MAX_WIDTH);
            } else {
                this.isLongImg = ImageUtils.isLongImg(str);
                int[] widthHeightFromUrl = ImageUtils.getWidthHeightFromUrl(str);
                me.tmgg.viewsdemoapp.picpreview.Intrinsics.a(widthHeightFromUrl, "ImageUtils.getWidthHeightFromUrl(it)");
                this.imgInfo = widthHeightFromUrl;
                thumbnailUrlLimitSize = ImageUtils.getThumbnailUrlLimitSize(str, IMG_MAX_WIDTH);
            }
            this.url = thumbnailUrlLimitSize;
        }
    }

    public int getLayoutId() {
        return R.layout.fragment_preview_picture;
    }

    public final File getImgFile() {
        return this.imgFile;
    }

    public final void setImgFile(File file) {
        this.imgFile = file;
    }

    public final Bitmap getBitmap() {
        return this.bitmap;
    }

    public final void setBitmap(Bitmap bitmap2) {
        this.bitmap = bitmap2;
    }

    public void initView() {
        String str;
        String str2;
        ElasticDragDismissFrameLayout elasticDragDismissFrameLayout = (ElasticDragDismissFrameLayout) _$_findCachedViewById(R.id.dragdismiss_drag_dismiss_layout);
        if (elasticDragDismissFrameLayout != null) {
            elasticDragDismissFrameLayout.addListener(new ElasticDragDismissFrameLayout.ElasticDragDismissCallback() {
                @Override
                public void onDragDismissed() {
                    super.onDragDismissed();
                    exitActivity();
                }
            });
            elasticDragDismissFrameLayout.setDragElasticity(2.0f);
            elasticDragDismissFrameLayout.halfDistanceRequired();
        }
        setUpSSIV();
        if (VERSION.SDK_INT >= 21) {
            this.transitionName = getString(R.string.transition_name, Integer.valueOf(this.adapterPosition), Integer.valueOf(this.position));
            if (this.adapterPosition == -1) {
                this.transitionName = "shareImage";
            }
            SubsamplingScaleImageView subsamplingScaleImageView = (SubsamplingScaleImageView) _$_findCachedViewById(R.id.iv_picture);
            me.tmgg.viewsdemoapp.picpreview.Intrinsics.a((Object) subsamplingScaleImageView, "iv_picture");
            subsamplingScaleImageView.setTransitionName(this.transitionName);
            SubsamplingScaleImageView subsamplingScaleImageView2 = (SubsamplingScaleImageView) _$_findCachedViewById(R.id.iv_picture);
            me.tmgg.viewsdemoapp.picpreview.Intrinsics.a((Object) subsamplingScaleImageView2, "iv_picture");
            subsamplingScaleImageView2.setTag(this.transitionName);
            if (this.position == this.currentIndex) {
                PreviewPictureActivity.StartPostTransitionListener startPostTransitionListener2 = this.startPostTransitionListener;
                if (startPostTransitionListener2 != null) {
                    startPostTransitionListener2.onStartPostTransition((SubsamplingScaleImageView) _$_findCachedViewById(R.id.iv_picture));
                }
            }
        }
        if (!TextUtils.isEmpty(this.imgLocalPath)) {
            str = this.imgLocalPath;
        } else {
            str = this.url;
        }
        if (isFilePath(str)) {
            str2 = "file://" + str;
        } else {
            str2 = str;
        }
        this.imgFile = getImgFile(str2);
        if (this.imgFile != null) {
            File file = this.imgFile;
            if (file == null) {
                me.tmgg.viewsdemoapp.picpreview.Intrinsics.a();
            }
            if (file.exists()) {
                hideLoadingBar();
                loadFile(this.imgFile);
                return;
            }
        }
        if (ImageUtils.isLocalFile(str2)) {
            hideLoadingBar();
            if (str2 == null) {
                me.tmgg.viewsdemoapp.picpreview.Intrinsics.a();
            }
            if (str2 == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            this.imgFile = new File(str2);
            loadFile(this.imgFile);
        } else if (ImageUtils.isUri(str2)) {
            hideLoadingBar();
            Uri parse = Uri.parse(str2);
            me.tmgg.viewsdemoapp.picpreview.Intrinsics.a((Object) parse, "Uri.parse(imgUrl)");
            this.imgFile = new File(parse.getPath());
            ((SubsamplingScaleImageView) _$_findCachedViewById(R.id.iv_picture)).setImage(ImageSource.uri(Uri.parse(str2)));
        }
    }

    public void setUserVisibleHint(boolean z) {
        super.setUserVisibleHint(z);
        this.isVisibleToUser = z;
    }

    public final File getImgFile(String str) {
        Context context = getContext();
        return new File(context != null ? context.getFilesDir() : null, str);
    }

    public final boolean isFilePath(String str) {
        if (str == null || me.tmgg.viewsdemoapp.picpreview.Intrinsics.b(str, "http://", false, 2, null) || me.tmgg.viewsdemoapp.picpreview.Intrinsics.b(str, "https://", false, 2, null) || Intrinsics.b(str, "content://", false, 2, null) || Intrinsics.b(str, "file://", false, 2, null)) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public final void loadFile(File file) {
        if (((SubsamplingScaleImageView) _$_findCachedViewById(R.id.iv_picture)) != null && file != null && file.exists()) {
            if (!TextUtils.equals("gif", ImageUtils.getFileType(file.getAbsolutePath()))) {
                hideLoadingBar();
                String absolutePath = file.getAbsolutePath();
                setFitWidthScale(absolutePath);
            } else if (((ImageView) _$_findCachedViewById(R.id.gifView)) != null) {
                if (VERSION.SDK_INT >= 21) {
                    ImageView imageView = (ImageView) _$_findCachedViewById(R.id.gifView);
                    imageView.setTransitionName(this.transitionName);
                    ((ImageView) _$_findCachedViewById(R.id.gifView)).setTag(R.id.IMAGE_TAG, this.transitionName);
                }
                SubsamplingScaleImageView subsamplingScaleImageView = (SubsamplingScaleImageView) _$_findCachedViewById(R.id.iv_picture);
                ImageView imageView2 = (ImageView) _$_findCachedViewById(R.id.gifView);
                ((ImageView) _$_findCachedViewById(R.id.gifView)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        exitActivity();
                    }
                });
                int[] widthHeightFromFile = ImageUtils.getWidthHeightFromFile(file);
                ImageView imageView3 = (ImageView) _$_findCachedViewById(R.id.gifView);
                Glide.with(imageView3.getContext()).asGif()
                        .load(file).diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .apply(new RequestOptions().override(widthHeightFromFile[0],widthHeightFromFile[1])
                        .dontAnimate()).listener(new RequestListener<GifDrawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                        hideLoadingBar();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                        hideLoadingBar();
                        return false;
                    }
                }).into(imageView3);

            }
        }
    }

    private final void setFitWidthScale(String str) {
        ((SubsamplingScaleImageView) _$_findCachedViewById(R.id.iv_picture)).setImage(ImageSource.uri(Uri.fromFile(new File(str))));
    }

    private final void setUpSSIV() {
        ((SubsamplingScaleImageView) _$_findCachedViewById(R.id.iv_picture)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitActivity();
            }
        });
        SubsamplingScaleImageView subsamplingScaleImageView = (SubsamplingScaleImageView) _$_findCachedViewById(R.id.iv_picture);
        subsamplingScaleImageView.setMaxScale(6.0f);
        int i = this.imgInfo[0];
        int i2 = this.imgInfo[1];
        if (i > 0 && i2 > 0) {
            Context context = getContext();
            int i3 = context != null ? ScreenUtil.getScreenWidth() : 1080;
            if (i2 > i && i < i3 && ImageUtils.isLongImg(this.url)) {
                ((SubsamplingScaleImageView) _$_findCachedViewById(R.id.iv_picture)).setMinimumScaleType(4);
            }
        }
    }

    /* access modifiers changed from: private */
    public final void hideLoadingBar() {
        if (((ContentLoadingProgressBar) _$_findCachedViewById(R.id.loading_bar)) != null) {
            ((ContentLoadingProgressBar) _$_findCachedViewById(R.id.loading_bar)).hide();
        }
    }

    public final void setStartPostTransitionListener(PreviewPictureActivity.StartPostTransitionListener startPostTransitionListener2) {
        Intrinsics.b(startPostTransitionListener2, "startPostTransitionListener");
        this.startPostTransitionListener = startPostTransitionListener2;
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.imgFile != null) {
            this.imgFile = null;
        }
        Bitmap bitmap2 = this.bitmap;
        if (bitmap2 != null) {
            bitmap2.recycle();
            this.bitmap = null;
        }
        this.startPostTransitionListener = null;
    }
}