package me.tmgg.viewsdemoapp.picpreview;

import kotlin.KotlinNullPointerException;

/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/8/14 10:04
 * package：me.tmgg.viewsdemoapp.picpreview
 * version：1.0
 * <p>description：              </p>
 */
public class Intrinsics {


    public static /* synthetic */ boolean b(String str, String str2, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return b(str, str2, z);
    }

    public static final boolean b(String str, String str2, boolean z) {
        b(str, "$receiver");
        b(str2, "prefix");
        if (!z) {
            return str.startsWith(str2);
        }
        return a(str, 0, str2, 0, str2.length(), z);
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



    public static final boolean a(String str, int i, String str2, int i2, int i3, boolean z) {
        Intrinsics.b(str, "$receiver");
        Intrinsics.b(str2, "other");
        if (!z) {
            return str.regionMatches(i, str2, i2, i3);
        }
        return str.regionMatches(z, i, str2, i2, i3);
    }


    public static void a(Object obj, String str) {
        if (obj == null) {
            throw ((IllegalStateException) new IllegalStateException(str + " must not be null"));
        }
    }

    public static void a() {
        throw ((KotlinNullPointerException) new KotlinNullPointerException());
    }





}
