package me.tmgg.viewsdemoapp.picpreview;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Locale;

import me.tmgg.viewsdemoapp.App;
import me.tmgg.viewsdemoapp.R;

public class ImageUtils {
    private static final String JPG_SUFFIX_FOR_SINGLE_IMG_PNG = "?imageView2/0/w/%s/h/%s/format/png";
    private static final String JPG_SUFFIX_FOR_SINGLE_IMG_WEBP = "?imageView2/0/w/%s/h/%s/format/webp";
    private static final String JPG_SUFFIX_FOR_SQUARE_IMG_PNG = "?imageView2/1/w/%s/h/%s/format/png";
    private static final String JPG_SUFFIX_FOR_SQUARE_IMG_WEBP = "?imageView2/1/w/%s/h/%s/format/webp";
    private static final int MAX_IMG_WIDTH = 400;
    private static final int MIN_IMG_WIDTH = 240;

    public static String getFileType(String str) {
        String fileType = getFileHeader(str);
        return fileType;
    }

    private static String getFileHeader(String str) {
        String[] split = str.split(",");
        String last = split[split.length - 1];
        if(!TextUtils.isEmpty(last)){
            return last;
        }
        return "";
    }


    public static void loadSingleThumbImg(ImageView imageView, String str, boolean z) {
        if (imageView != null) {
            if (TextUtils.isEmpty(str)) {
                imageView.setImageResource(R.drawable.ic_loading);
                return;
            }
            String pureUrl = getPureUrl(str);
            int[] sourceWidthHeightFromUrl = getSourceWidthHeightFromUrl(str);
            boolean isLongImg = isLongImg(str);
            if (isLongImg) {
                changeImageWidth(sourceWidthHeightFromUrl);
            }
            int i = z ? sourceWidthHeightFromUrl[1] : sourceWidthHeightFromUrl[0];
            boolean isGif = isGif(str);
//            String str2 = (!z || isLongImg) ? isGif ? JPG_SUFFIX_FOR_SQUARE_IMG_PNG : JPG_SUFFIX_FOR_SQUARE_IMG_WEBP : isGif ? JPG_SUFFIX_FOR_SINGLE_IMG_PNG : JPG_SUFFIX_FOR_SINGLE_IMG_WEBP;
            RequestOptions requestOptions = new RequestOptions().override(sourceWidthHeightFromUrl[0], sourceWidthHeightFromUrl[1]).diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .dontAnimate();
            if(isGif){
                Glide.with(imageView.getContext()).asGif().load(pureUrl)
                        .apply(requestOptions)
                        .into(imageView);
            }else{
                Glide.with(imageView.getContext()).load(pureUrl)
                        .apply(requestOptions)
                        .into(imageView);
            }

        }
    }
    public static String getQiniuThumbUrl(String str) {
        return (TextUtils.isEmpty(str) || !isQiniuUrl(str)) ? str : getThumbnailUrl(getPureUrl(str));
    }


    public static String getQiNiuImgThumbUrl(String str, int i) {
        if (!isQiniuUrl(str)) {
            return str;
        }
        if (str.contains("?")) {
            str = getPureUrl(str);
        }
        return str + "?imageView2/0/h/" + i + "/q/80";
    }

    public static String getThumbnailUrl(String str, boolean z, int i, int i2, int i3, String str2) {
        int i4;
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if (!isQiniuUrl(str)) {
            return str;
        }
        if (i < 0 || i2 < 0) {
            throw new IllegalArgumentException("Invalid width or height.");
        } else if (i3 < 1 || i3 > 100) {
            throw new IllegalArgumentException("Invalid quality,valid range is 0-100.");
        } else {
            if (TextUtils.isEmpty(str2)) {
                str2 = "png";
            }
            if (z) {
                i4 = 2;
            } else {
                i4 = 1;
            }
            return getPureUrl(str) + String.format(Locale.getDefault(), "?imageView/%d/w/%d/h/%d/q/%d/format/%s/ignore-error/1", new Object[]{Integer.valueOf(i4), Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), str2});
        }
    }

    public static String getThumbnailUrlLimitSize(String str, int i) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if (!str.startsWith("http") || str.contains("imageView2/")) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        sb.append(str.contains("?") ? "&imageView2/0" : "?imageView2/0");
        if (i > 0) {
            sb.append("/h/").append(i);
        }
        sb.append("/format/webp/ignore-error/1");
        return sb.toString();
    }

    public static String getGifThumbnailUrlLimitSize(String str, int i) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if (!str.startsWith("http") || str.contains("imageView2/")) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        sb.append(str.contains("?") ? "&imageView2/0" : "?imageView2/0");
        if (i > 0) {
            sb.append("/w/").append(i);
        }
        return sb.toString();
    }

    public static boolean isQiniuUrl(String str) {
        return str.contains("-cdn.xitu.io") || str.contains(".qbox.me");
    }

    public static String getThumbnailUrlLimitLongEdge(String str, int i) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if (!str.startsWith("http") || str.contains("imageView2/")) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        sb.append(str.contains("?") ? "&imageView2/0" : "?imageView2/0");
        if (i > 0) {
            sb.append("/w/").append(i);
        }
        sb.append("/format/webp/ignore-error/1");
        return sb.toString();
    }

    public static String getThumbnailAvatarUrl(String str) {
        return getThumbnailUrl(str, true, ScreenUtil.dip2px(40.0f), ScreenUtil.dip2px(40.0f), 80, "webp");
    }

    public static String getThumbnailUrl(String str) {
        return getThumbnailUrl(str, true, ScreenUtil.dip2px(80.0f), ScreenUtil.dip2px(80.0f), 80, "webp");
    }

    public static String getThumbnailUrl(String str, int i, int i2) {
        return getThumbnailUrl(str, true, i, i2, 80, "webp");
    }

    public static Drawable getTintedDrawable(Resources resources, @DrawableRes int i, @ColorRes int i2) {
        Drawable drawable = resources.getDrawable(i);
        int color = resources.getColor(i2);
        if (drawable != null) {
            drawable.setColorFilter(color, Mode.SRC_IN);
        }
        return drawable;
    }

    public static String addMetaToUrl(String str, int i, int i2) {
        if (str.contains("?")) {
            return String.format(Locale.getDefault(), "%s&w=%d&h=%d", new Object[]{str, Integer.valueOf(i), Integer.valueOf(i2)});
        }
        return String.format(Locale.getDefault(), "%s?w=%d&h=%d", new Object[]{str, Integer.valueOf(i), Integer.valueOf(i2)});
    }

    public static String getMetaOfUrl(String str, int[] iArr) {
        try {
            Uri parse = Uri.parse(str);
            iArr[0] = Integer.parseInt(parse.getQueryParameter("w"));
            iArr[1] = Integer.parseInt(parse.getQueryParameter("h"));
            int indexOf = str.indexOf("?");
            if (indexOf == -1) {
                indexOf = str.length();
            }
            return str.substring(0, indexOf);
        } catch (Exception e) {
            return str;
        }
    }

    public static String getPureUrl(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        int indexOf = str.indexOf("?");
        if (indexOf == -1) {
            indexOf = str.length();
        }
        return str.substring(0, indexOf);
    }

    public static int[] getWidthHeightFromUrl(String str) {
        int[] iArr = new int[2];
        try {
            Uri parse = Uri.parse(str);
            iArr[0] = Integer.parseInt(parse.getQueryParameter("w"));
            iArr[1] = Integer.parseInt(parse.getQueryParameter("h"));
        } catch (Exception e) {
        }
        return iArr;
    }

    private static int[] getSourceWidthHeightFromUrl(String str) {
        return getSourceWidthHeightFromUrl(getWidthHeightFromUrl(str));
    }

    private static int[] getSourceWidthHeightFromUrl(int[] iArr) {
        int i = iArr[0];
        int i2 = iArr[1];
        if (i2 > 400 || i > 400) {
            if (i > i2) {
                i2 = (int) ((((float) i2) / ((float) i)) * 400.0f);
                i = 400;
            } else {
                i = (int) ((((float) i) / ((float) i2)) * 400.0f);
                i2 = 400;
            }
        }
        iArr[0] = i;
        iArr[1] = i2;
        return iArr;
    }

    public static boolean isLongImg(String str) {
        int[] widthHeightFromUrl = getWidthHeightFromUrl(str);
        int i = widthHeightFromUrl[1];
        float f = ((float) widthHeightFromUrl[1]) / ((float) widthHeightFromUrl[0]);
        if (i <= 1200 ) {
            return false;
        }
        return true;
    }

    public static boolean isLargeLongImg(String str) {
        if (getWidthHeightFromUrl(str)[1] > 5000) {
            return true;
        }
        return false;
    }

    private static int[] changeImageWidth(int[] iArr) {
        int i = MIN_IMG_WIDTH;
        if (iArr[0] >= MIN_IMG_WIDTH) {
            i = iArr[0];
        }
        iArr[0] = i;
        return iArr;
    }

    public static boolean isGif(String str) {
        try {
            return "gif".equals(Uri.parse(str).getQueryParameter("f"));
        } catch (Exception e) {
            return false;
        }
    }

    public static Uri getImageUri(Context context, Bitmap bitmap) {
        boolean z = false;
        try {
            bitmap.compress(CompressFormat.JPEG, 100, new ByteArrayOutputStream());
            return Uri.parse(Media.insertImage(context.getContentResolver(), bitmap, "GoldShareImage", null));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isUri(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.startsWith("content://");
    }

    public static boolean isLocalFile(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.startsWith("file://");
    }

    public static int[] getWidthHeightFromFile(File file) {
        try {
            Options options = new Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(file.getAbsolutePath(), options);
            return new int[]{options.outWidth, options.outHeight};
        } catch (Exception e) {
            return new int[2];
        }
    }

    public static String getImagePath(Uri uri) {
        Cursor query = App.getInstance().getApplicationContext().getContentResolver().query(uri, new String[]{"_data"}, null, null, null);
        if (query == null) {
            return "";
        }
        int columnIndexOrThrow = query.getColumnIndexOrThrow("_data");
        query.moveToFirst();
        String string = query.getString(columnIndexOrThrow);
        query.close();
        return string;
    }

    public static File getImageFile(String str) {
        if (!str.startsWith("/")) {
            str = getImagePath(Uri.parse(str));
        }
        return new File(str);
    }
}