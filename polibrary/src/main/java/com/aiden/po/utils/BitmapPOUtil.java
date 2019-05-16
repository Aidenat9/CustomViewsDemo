package com.aiden.po.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/5/11 14:52
 * package：com.aiden.po.utils
 * version：1.0
 * <p>description：              </p>
 */
public class BitmapPOUtil {
    private static final BitmapPOUtil ourInstance = new BitmapPOUtil();

    public static BitmapPOUtil getInstance() {
        return ourInstance;
    }

    private BitmapPOUtil() {
    }

    /**
     * bytes = 原始图片宽*(options.inTargetDensity/options.inDensity)*原始图片长
     * *(options.inTargetDensity/options.inDensity)*每个像素点位数
     */

    /**
     * 用流的形式生成缩略Bitmap  80*80
     **/
    public Bitmap compressBitmapIntoThumbnailPic(InputStream is) {
        int sampleBitmap = 0;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 最终返回的Bitmap
        Bitmap finalBitmap = null;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            //设置inJustDecodeBounds在native decode时候只返回尺寸等，Bitmap为空
            // 或者将inputStream -> BufferedInputStream 保证某些情况reset不支持
            BitmapFactory.decodeStream(is, null, options);
            sampleBitmap = calculateInSampleSize(options, 80, 80);
            options.inSampleSize = sampleBitmap;
            options.inJustDecodeBounds = false;
            try {
                is.reset();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finalBitmap = BitmapFactory.decodeStream(is, null, options);
        } else {// 4.4包含以后就没有这个is.mark(1024)的大小限制问题了。不会出现OOM
            if (is.markSupported()) {
                try {
                    BitmapFactory.decodeStream(is, null, options);
                    sampleBitmap = calculateInSampleSize(options, 80, 80);
                    options.inSampleSize = sampleBitmap;
                    options.inJustDecodeBounds = false;
                    is.reset();
                    finalBitmap = BitmapFactory.decodeStream(is, null, options);
                    Log.d("tag", "final == " + finalBitmap.getByteCount() +
                            " target real density  " + options.inTargetDensity + " folder density " + options.inDensity);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return finalBitmap;
    }


    /**
     * 如果是放大图片，filter决定是否平滑，如果是缩小图片，filter无影响
     *
     * @param src
     * @param dstWidth  目标宽
     * @param dstHeight 高
     * @return
     */
    private static Bitmap createScaleBitmap(Bitmap src, int dstWidth,
                                            int dstHeight) {
        Bitmap dst = Bitmap.createScaledBitmap(src, dstWidth, dstHeight, false);
        if (src != dst) { // 如果没有缩放，那么不回收
            src.recycle(); // 释放Bitmap的native像素数组
        }
        return dst;
    }

    /**
     * 从Resources中加载图片
     *
     * @param res
     * @param resId     资源名id
     * @param reqWidth  目标宽
     * @param reqHeight 高
     * @return 目标bitmap
     */
    public static Bitmap decodeBitmapFromResource(Resources res,
                                                  int resId, int reqWidth, int reqHeight) {
        if (null == res || resId < 0 || reqWidth <= 0 || reqHeight <= 0) {
            return null;
        }
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//只读取图片，不加载到内存中
        BitmapFactory.decodeResource(res, resId, options); // 读取图片长款
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight); // 计算inSampleSize
        options.inJustDecodeBounds = false;//加载到内存中
        InputStream inputStream = res.openRawResource(resId);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Bitmap src = BitmapFactory.decodeStream(bufferedInputStream, null, options); // 载入一个稍大的缩略图
        return createScaleBitmap(src, reqWidth, reqHeight); // 进一步得到目标大小的缩略图
    }

    /**
     * 从sd卡上加载图片
     *
     * @param pathName  图片地址
     * @param reqWidth  目标宽
     * @param reqHeight 高
     * @return
     */
    public static Bitmap decodeBitmapFromSD(String pathName,
                                            int reqWidth, int reqHeight) {
        if (TextUtils.isEmpty(pathName) || !new File(pathName).exists() || reqWidth <= 0 || reqHeight <= 0) {
            return null;
        }
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//只读取图片，不加载到内存中
        BitmapFactory.decodeFile(pathName, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        BufferedInputStream bufferedInputStream = null;
        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(pathName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        Bitmap src = BitmapFactory.decodeStream(bufferedInputStream, null, options);
        return createScaleBitmap(src, reqWidth, reqHeight);
    }


    /**
     * 将view转为bitmap
     *
     * @param view
     * @return
     */
    public static Bitmap getBitmapFromView(View view) {
        // Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.RGB_565);
        // Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        // Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            // has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            // does not have background drawable, then draw white background on
            // the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        // return the bitmap
        return returnedBitmap;
    }

    /**
     * 将view转为bitmap
     *
     * @param view
     * @return
     */
    public static Bitmap viewToBitmap(View view) {
        if (null == view) {
            return null;
        }
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bm = view.getDrawingCache();
        return bm;
    }

    /**
     * 图片转为文件
     * @param bmp
     * @param filename 文件名称
     * @return
     */
    public static boolean saveBitmap2file(Bitmap bmp, String filename) {
        Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
        int quality = 98;
        OutputStream stream = null;
        try {
            // 判断SDcard状态
            if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
            {
                // 错误提示
                return false;
            }

            // 检查SDcard空间
            File SDCardRoot = Environment.getExternalStorageDirectory();
            if (SDCardRoot.getFreeSpace() < 10000)
            {
                // 弹出对话框提示用户空间不够
                Log.e("Utils", "存储空间不够");
                return false;
            }
            stream = new FileOutputStream(Environment.getExternalStorageDirectory() + filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(stream);

        return bmp.compress(format, quality, bufferedOutputStream);
    }


    /**
     * 计算采样率大小
     *
     * @param options   配置
     * @param reqWidth  目标宽
     * @param reqHeight 高
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

}
