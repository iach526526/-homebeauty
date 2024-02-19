package tw.com.homebeauty;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.view.KeyEvent;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    WebView myweb;
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.6099.225 Safari/537.36";;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myweb = findViewById(R.id.myWeb);
        myweb.getSettings().setJavaScriptEnabled(true);
        myweb.getSettings().setUserAgentString(USER_AGENT);
        // 設置自定義的 WebViewClient
        myweb.setWebViewClient(new MyWebViewClient());

        myweb.loadUrl("https://homebeauty.com.tw");
    }

    // 自定義 WebViewClient
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            Uri url = request.getUrl();
            String host = url.getHost();
            // 如果連結指向指定的網站，就在 WebView 中打開
            if ("homebeauty.com.tw".equals(host)) {
                return false;
            } else if (url.toString().startsWith("https://pay.line.me/")) {
                // 如果連結指向 Line Pay，就使用 Intent 打開外部瀏覽器
                Intent intent = new Intent(Intent.ACTION_VIEW, url);
                startActivity(intent);
                return true;
            }

            // 其他情況，使用 WebView 處理
            return false;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check whether the key event is the Back button and if there's history.
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myweb.canGoBack()) {
            myweb.goBack();
            return true;
        }
        // If it isn't the Back button or there's no web page history, bubble up to
        // the default system behavior. Probably exit the activity.
        return super.onKeyDown(keyCode, event);
    }
}