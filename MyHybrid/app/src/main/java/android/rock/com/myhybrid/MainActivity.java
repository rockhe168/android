package android.rock.com.myhybrid;

import android.os.Bundle;
import android.rock.com.mylibrary.BaseActivity;
import android.rock.com.mylibrary.core.JsBridgeWebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by xhhe on 2016/3/28.
 */
public class MainActivity extends BaseActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView = (WebView) findViewById(R.id.myWebView);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);

        mWebView.setWebChromeClient(new JsBridgeWebChromeClient());
        mWebView.loadUrl("http://10.32.229.125:5389/hybird.html");
    }
}
