package me.tmgg.viewsdemoapp.picpreview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import me.tmgg.viewsdemoapp.App;

public class FileUtils {
       public static String getFilePath(String str) {
        File file = new File(App.getInstance().getApplicationContext().getFilesDir(), str);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Log.e("file: ",(e.getMessage()));
                return "";
            }
        }
        return file.getAbsolutePath();
    }

    public static String getFileName(String str) {
        int lastIndexOf = str.lastIndexOf("/");
        return lastIndexOf < 0 ? str : str.substring(lastIndexOf + 1, str.length());
    }

    public static long getFileSize(String str) {
        if (TextUtils.isEmpty(str)) {
            return -1;
        }
        File file = new File(str);
        if (!file.exists() || !file.isFile()) {
            return -1;
        }
        return file.length();
    }

    public static boolean saveImage2Gallery(Context context, String str) {
        try {
            Media.insertImage(context.getContentResolver(), str, "title", "desc..");
            context.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.parse("file://" + str)));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void notifyGallery(Context context, String str) {
        context.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.parse("file://" + str)));
    }

    public static long getFolderSize(File file) {
        File[] listFiles;
        long j = 0;
        if (file != null && file.isDirectory()) {
            for (File file2 : file.listFiles()) {
                if (file2.isFile()) {
                    j += file2.length();
                } else if (file2.isDirectory()) {
                    j = j + file2.length() + getFolderSize(file2);
                }
            }
        }
        return j;
    }

    public static void clearFolder(File file) {
        File[] listFiles;
        if (file != null && file.isDirectory()) {
            for (File file2 : file.listFiles()) {
                if (file2.isFile()) {
                    file2.delete();
                } else if (file2.isDirectory()) {
                    clearFolder(file2);
                    file2.delete();
                }
            }
        }
    }

    public static boolean copyFile(String str, String str2) {
        try {
            return writeFile(str2, (InputStream) new FileInputStream(str));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("FileNotFoundException occurred. ", e);
        }
    }

    public static boolean writeFile(String str, InputStream inputStream) {
        return writeFile(str, inputStream, false);
    }

    public static boolean writeFile(String str, InputStream inputStream, boolean z) {
        return writeFile(str != null ? new File(str) : null, inputStream, z);
    }

    public static boolean writeFile(File file, InputStream inputStream) {
        return writeFile(file, inputStream, false);
    }

    /* JADX WARNING: Unknown top exception splitter block from list: {B:22:0x003c=Splitter:B:22:0x003c, B:10:0x001e=Splitter:B:10:0x001e} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean writeFile(java.io.File r4, java.io.InputStream r5, boolean r6) {
        /*
            r2 = 0
            java.lang.String r0 = r4.getAbsolutePath()     // Catch:{ FileNotFoundException -> 0x004a, IOException -> 0x003a, all -> 0x0045 }
            makeDirs(r0)     // Catch:{ FileNotFoundException -> 0x004a, IOException -> 0x003a, all -> 0x0045 }
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ FileNotFoundException -> 0x004a, IOException -> 0x003a, all -> 0x0045 }
            r1.<init>(r4, r6)     // Catch:{ FileNotFoundException -> 0x004a, IOException -> 0x003a, all -> 0x0045 }
            r0 = 1024(0x400, float:1.435E-42)
            byte[] r0 = new byte[r0]     // Catch:{ FileNotFoundException -> 0x001d, IOException -> 0x0048 }
        L_0x0011:
            int r2 = r5.read(r0)     // Catch:{ FileNotFoundException -> 0x001d, IOException -> 0x0048 }
            r3 = -1
            if (r2 == r3) goto L_0x002f
            r3 = 0
            r1.write(r0, r3, r2)     // Catch:{ FileNotFoundException -> 0x001d, IOException -> 0x0048 }
            goto L_0x0011
        L_0x001d:
            r0 = move-exception
        L_0x001e:
            java.lang.RuntimeException r2 = new java.lang.RuntimeException     // Catch:{ all -> 0x0027 }
            java.lang.String r3 = "FileNotFoundException occurred. "
            r2.<init>(r3, r0)     // Catch:{ all -> 0x0027 }
            throw r2     // Catch:{ all -> 0x0027 }
        L_0x0027:
            r0 = move-exception
        L_0x0028:
            im.juejin.android.common.utils.IOUtils.close(r1)
            im.juejin.android.common.utils.IOUtils.close(r5)
            throw r0
        L_0x002f:
            r1.flush()     // Catch:{ FileNotFoundException -> 0x001d, IOException -> 0x0048 }
            r0 = 1
            im.juejin.android.common.utils.IOUtils.close(r1)
            im.juejin.android.common.utils.IOUtils.close(r5)
            return r0
        L_0x003a:
            r0 = move-exception
            r1 = r2
        L_0x003c:
            java.lang.RuntimeException r2 = new java.lang.RuntimeException     // Catch:{ all -> 0x0027 }
            java.lang.String r3 = "IOException occurred. "
            r2.<init>(r3, r0)     // Catch:{ all -> 0x0027 }
            throw r2     // Catch:{ all -> 0x0027 }
        L_0x0045:
            r0 = move-exception
            r1 = r2
            goto L_0x0028
        L_0x0048:
            r0 = move-exception
            goto L_0x003c
        L_0x004a:
            r0 = move-exception
            r1 = r2
            goto L_0x001e
        */
        throw new UnsupportedOperationException("Method not decompiled: im.juejin.android.base.utils.FileUtils.writeFile(java.io.File, java.io.InputStream, boolean):boolean");
    }

    public static boolean makeDirs(String str) {
        String folderName = getFolderName(str);
        if (TextUtils.isEmpty(folderName)) {
            return false;
        }
        File file = new File(folderName);
        if ((!file.exists() || !file.isDirectory()) && !file.mkdirs()) {
            return false;
        }
        return true;
    }

    public static String getFolderName(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        int lastIndexOf = str.lastIndexOf(File.separator);
        return lastIndexOf == -1 ? "" : str.substring(0, lastIndexOf);
    }

    public static File getAlbumStorageDir() {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "JJ_IMAGE");
        if (!file.mkdirs()) {
            Log.e("file: ","Directory not created");
        }
        return file;
    }

    public boolean isExternalStorageWritable() {
        if ("mounted".equals(Environment.getExternalStorageState())) {
            return true;
        }
        return false;
    }

    public boolean isExternalStorageReadable() {
        String externalStorageState = Environment.getExternalStorageState();
        if ("mounted".equals(externalStorageState) || "mounted_ro".equals(externalStorageState)) {
            return true;
        }
        return false;
    }

}