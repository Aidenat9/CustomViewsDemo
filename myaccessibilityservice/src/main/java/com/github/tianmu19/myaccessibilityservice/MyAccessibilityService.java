package com.github.tianmu19.myaccessibilityservice;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

public class MyAccessibilityService extends AccessibilityService {
    private static final String TAG = "accessibility ";

    public MyAccessibilityService() {
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if(AccessibilityEvent.TYPE_VIEW_CLICKED==event.getEventType()){
//            Log.e(TAG, "onAccessibility: "+event.toString());
        }
    }

    @Override
    public void onInterrupt() {
//        Log.e(TAG, "onInterrupt: ");
    }


}
