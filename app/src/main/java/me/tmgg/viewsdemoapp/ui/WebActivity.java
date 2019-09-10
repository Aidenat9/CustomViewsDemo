package me.tmgg.viewsdemoapp.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.UnsupportedEncodingException;

import me.tmgg.viewsdemoapp.R;

public class WebActivity extends AppCompatActivity {

    private WebView pdfViewerWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initWeb();

    }

    String docPath ="http://bmob-cdn-24582.b0.upaiyun.com/2019/04/02/2d85f3864052b99380bffac87283dd09.pdf";

    private void initWeb() {
        pdfViewerWeb = findViewById(R.id.webview);
        WebSettings settings = pdfViewerWeb.getSettings();
        settings.setAllowFileAccess(true);

        settings.setSavePassword(false);

        settings.setJavaScriptEnabled(true);

        settings.setAllowFileAccessFromFileURLs(true);

        settings.setAllowUniversalAccessFromFileURLs(true);

        settings.setBuiltInZoomControls(true);
//        pdfViewerWeb.loadUrl(docPath);
//        docPath = "file:///android_asset/xy.pdf";
        if(!TextUtils.isEmpty(docPath)&&docPath.endsWith("pdf")){
            loadPdf();
        }

        pdfViewerWeb.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if(!TextUtils.isEmpty(docPath)&&docPath.endsWith("pdf")){
                    loadPdf();
                }else{
                    view.loadUrl(url);
                }
                return true;

            }

        });

        pdfViewerWeb.setWebChromeClient(new WebChromeClient());
    }

    private void loadPdf() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//api >= 19
            pdfViewerWeb.loadUrl("file:///android_asset/pdfjs/web/viewer.html?file=" + docPath);

        } else {

            if (!TextUtils.isEmpty(docPath)) {

                byte[] bytes = null;

                try {// 获取以字符编码为utf-8的字符

                    bytes = docPath.getBytes("UTF-8");

                } catch (UnsupportedEncodingException e) {

                    e.printStackTrace();

                }

                if (bytes != null) {
                    docPath = new String(Base64.encode(bytes,Base64.DEFAULT));// BASE64转码

                }

            }

            pdfViewerWeb.loadUrl("file:///android_asset/pdfjs_compatibility/web/viewer.html?file=" + docPath);

        }
    }

    @Override
    public void onBackPressed() {
        if(pdfViewerWeb.canGoBack()){
            pdfViewerWeb.goBack();
        }else{
            super.onBackPressed();
        }
    }
}
