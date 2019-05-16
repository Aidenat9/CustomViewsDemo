package com.aiden.po.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/5/11 14:34
 * package：com.aiden.po.utils
 * version：1.0
 * <p>description：              </p>
 */
public class DrawablePOUtil {
    private static final DrawablePOUtil ourInstance = new DrawablePOUtil();

    public static DrawablePOUtil getInstance() {
        return ourInstance;
    }

    private DrawablePOUtil() {
    }


    /**
     * 直接使用res/drawable中的图
     * @param context
     * @param idName
     * @param packageName
     * @return
     */
    public Drawable getDrawable(Context context,String idName, String packageName){
        Context applicationContext = context.getApplicationContext();
        int resID = applicationContext.getResources().getIdentifier(idName, "drawable", packageName);
        InputStream inputStream = applicationContext.getResources().openRawResource(resID);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Drawable drawable = Drawable.createFromStream(bufferedInputStream,"src");
        return drawable;
    }

    /**
     * 获取指定resId的drawable
     * @param context
     * @param resID
     * @return
     */
    public Drawable getDrawable(Context context,int resID){
        Context applicationContext = context.getApplicationContext();
        InputStream inputStream = applicationContext.getResources().openRawResource(resID);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Drawable drawable = Drawable.createFromStream(bufferedInputStream,"src");
        return drawable;
    }


    /**
     *获取本地图片为drawable
     * @param context
     * @param filePath
     * @return
     */
    public Drawable getDrawable(Context context,String filePath){
        BufferedInputStream bufferedInputStream;
        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//只读取图片，不加载到内存中
        BitmapFactory.decodeFile(filePath, options);
        Drawable drawable = Drawable.createFromStream(bufferedInputStream, "drawable");
        return drawable;
    }

    /**
     * drawable转bitmap
     * @param drawable
     * @return
     */
    public Bitmap drawable2Bitmap(Drawable drawable)
    {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }


}
