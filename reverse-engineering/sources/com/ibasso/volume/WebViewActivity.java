package com.ibasso.volume;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
public class WebViewActivity extends AppCompatActivity {
    private String httpUrl;
    private ProgressBar progressBar;
    private WebSettings webSettings;
    private WebView webView;
    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageFinished(WebView webView, String str) {
            WebViewActivity.this.progressBar.setVisibility(8);
        }

        @Override
        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            WebViewActivity.this.progressBar.setVisibility(0);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            Log.i("ansen", "拦截url:" + str);
            if (str.equals("http://www.google.com/")) {
                Toast.makeText(WebViewActivity.this, "国内不能访问google,拦截该url", 1).show();
                return true;
            }
            return super.shouldOverrideUrlLoading(webView, str);
        }
    };
    private WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public boolean onJsAlert(WebView webView, String str, String str2, JsResult jsResult) {
            AlertDialog.Builder builder = new AlertDialog.Builder(webView.getContext());
            builder.setMessage(str2).setPositiveButton("确定", (DialogInterface.OnClickListener) null);
            builder.setCancelable(false);
            builder.create().show();
            jsResult.confirm();
            return true;
        }

        @Override
        public void onProgressChanged(WebView webView, int i) {
            WebViewActivity.this.progressBar.setProgress(i);
        }

        @Override
        public void onReceivedTitle(WebView webView, String str) {
            super.onReceivedTitle(webView, str);
            Log.i("ansen", "网页标题:" + str);
        }
    };

    @JavascriptInterface
    public void getClient(String str) {
        Log.i("ansen", "html调用客户端:" + str);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.webview);
        this.httpUrl = getIntent().getStringExtra("httpUrl");
        this.progressBar = (ProgressBar) findViewById(R.id.progressbar);
        WebView webView = (WebView) findViewById(R.id.webview);
        this.webView = webView;
        webView.loadUrl(this.httpUrl);
        this.webView.addJavascriptInterface(this, "android");
        this.webView.setWebChromeClient(this.webChromeClient);
        this.webView.setWebViewClient(this.webViewClient);
        WebSettings settings = this.webView.getSettings();
        this.webSettings = settings;
        settings.setJavaScriptEnabled(true);
        this.webSettings.setCacheMode(2);
        this.webSettings.setSupportZoom(true);
        this.webSettings.setBuiltInZoomControls(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.webView.destroy();
        this.webView = null;
    }

    @Override
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        Log.i("ansen", "是否有上一个页面:" + this.webView.canGoBack());
        if (this.webView.canGoBack() && i == 4) {
            this.webView.goBack();
            return true;
        } else if (i == 4 && keyEvent.getRepeatCount() == 0) {
            startActivity(new Intent(this, MainActivity.class));
            overridePendingTransition(17432576, 17432577);
            return true;
        } else {
            return super.onKeyDown(i, keyEvent);
        }
    }
}
