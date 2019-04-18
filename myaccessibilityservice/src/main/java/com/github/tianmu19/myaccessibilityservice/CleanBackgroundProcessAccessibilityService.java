package com.github.tianmu19.myaccessibilityservice;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.github.tianmu19.myaccessibilityservice.utils.BaseAccessibilityService;


public class CleanBackgroundProcessAccessibilityService extends BaseAccessibilityService {
    private static final String TAG = "accessibility";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED &&
                event.getPackageName().equals("com.android.settings")) {
            CharSequence className = event.getClassName();
            String name = className.toString();
            if (className.equals("com.android.settings.applications.InstalledAppDetailsTop")) {
                AccessibilityNodeInfo info = findViewByText("强行停止",true);
                Log.e(TAG, "onAccessibilityEvent: " + info);
                if (null != info&&info.isEnabled()) {
                    performViewClick(info);
                } else {
                    performBackClick();
                }
            }
            if (name.endsWith(".AlertDialog")) {
                clickTextViewByText("确定");
                performBackClick();
            }
        }
    }
}
