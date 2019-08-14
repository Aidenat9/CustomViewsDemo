package me.tmgg.viewsdemoapp.picpreview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListUtils {
    public static <T> List<T> getSubList(List<T> list, int i) {
        return (list == null || list.size() <= i) ? list : list.subList(0, i);
    }

    public static boolean notNull(List list) {
        return list != null && list.size() > 0;
    }

    public static boolean isNullOrEmpty(List list) {
        return list == null || list.size() <= 0;
    }

    public static String concat(List<String> list, String str) {
        if (isNullOrEmpty(list)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= list.size()) {
                return sb.toString();
            }
            if (i2 != 0) {
                sb.append(str);
            }
            sb.append((String) list.get(i2));
            i = i2 + 1;
        }
    }

    public static <T> ArrayList<T> getSingleArrayList(T t) {
        return new ArrayList<>(Collections.singletonList(t));
    }
}