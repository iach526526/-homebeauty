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
    public static final String USER_AGENT = "Mozilla/5.0 (Linux; Android 10; Redmi Note 8 Pro) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.88 Mobile Safari/537.36";;

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
//            // 如果連結指向指定的網站，就在 WebView 中打開
////
            if ("homebeauty.com.tw".equals(request.getUrl().getHost())) {
                return false;
            }
            else if (url.toString().contains("line")) {//目前的判斷方式是連結中有line就跳，不知道會不會有什麼奇怪的問題，但目前狀況良好
                // 如果連結指向 Line，就使用 Intent 打開外部瀏覽器跳轉到line app
                Intent intent = new Intent(Intent.ACTION_VIEW, request.getUrl());
                startActivity(intent);
                return true;
            }
//            // 其他情況，繼續使用 WebView 處理
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