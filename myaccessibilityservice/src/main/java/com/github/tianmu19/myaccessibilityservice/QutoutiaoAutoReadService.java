package com.github.tianmu19.myaccessibilityservice;

import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.github.tianmu19.myaccessibilityservice.utils.BaseAccessibilityService;

/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/4/18 14:09
 * package：com.github.tianmu19.myaccessibilityservice
 * version：1.0
 * <p>description：    趣头条自动阅读          </p>
 */
public class QutoutiaoAutoReadService extends BaseAccessibilityService {
    private final static String QUTOUTIAO_PACKAGENAME = "com.jifen.qukan";
    private final static String TAOBAO_PACKAGENAME = "com.taobao.taobao";
    private static final String TAG = "accessibility";
    private String mPackageName;
    private String text;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        super.onAccessibilityEvent(event);
        CharSequence packageName = event.getPackageName();
        if (!TextUtils.isEmpty(packageName)) {
            mPackageName = packageName.toString();
        }
        if (QUTOUTIAO_PACKAGENAME.equals(mPackageName)) {
            String s = "";
            AccessibilityNodeInfo rootNode = getRootInActiveWindow();
            if (null == rootNode) return;
            CharSequence className = rootNode.getClassName();
            if(!TextUtils.isEmpty(className)){
                s = className.toString();
            }
            if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_SCROLLED) {
                Log.e(TAG, "goThrough: " + event.getClassName());
                goThrough(rootNode);
            }
        } else if (mPackageName.equals(TAOBAO_PACKAGENAME)) {
            performBackClick();
        }
    }

    private void goThrough(AccessibilityNodeInfo info) {
        if (info.getChildCount() == 0) {
            if (info.getText() != null) {
//                Log.e(TAG, "onAccessibilityEvent: " + info.getClassName()+"   "+info.getText());
                String s = info.getClassName().toString();
                CharSequence te = info.getText();
                if (!TextUtils.isEmpty(te)) {
                    text = te.toString();
                }
                if (s.equals("android.widget.TextView") && text.endsWith("评") && !text.endsWith("图")) {
                    AccessibilityNodeInfo parent = info;
                    while (parent != null) {
                        if (parent.isClickable()) {
                            parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            break;
                        }
                        parent = parent.getParent();
                    }
                }
                //详情页
                else if (s.equals("android.webkit.WebView") || s.equals("android.support.v7.widget.RecyclerView")) {
                    while (true) {
                        Log.e(TAG, "true: " );
                        info.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
                    }
                }

            }
        } else {
            for (int i = 0; i < info.getChildCount(); i++) {
                if (info.getChild(i) != null) {
                    goThrough(info.getChild(i));
                }
            }
        }
    }
}
