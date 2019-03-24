package com.example.bob.health_helper.News.activity;

import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.bob.health_helper.Base.BaseActivity;
import com.example.bob.health_helper.MyApplication;
import com.example.bob.health_helper.R;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDetailActivity extends BaseActivity {
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private String url="https://mp.weixin.qq.com/s/NiOp11NIaPXKw04j5U8FNA";
    private ActionBar actionBar;
    private String newsTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
        //url=getIntent().getStringExtra("news_url");
        setSupportActionBar(toolbar);
        actionBar=getSupportActionBar();
        if(actionBar!=null)
            actionBar.setDisplayHomeAsUpEnabled(true);
        showNews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.qq_share:
                qqShare();
                break;
            case R.id.qq_qzone_share:
                qqQzoneShare();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private ShareUiListener shareUiListener=new ShareUiListener();

    private void qqShare() {
        Bundle bundle=new Bundle();
        bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL,url);
        bundle.putString(QQShare.SHARE_TO_QQ_TITLE, newsTitle);
        bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY,newsTitle);
        bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME,getString(R.string.app_name));
        MyApplication.getTencent().shareToQQ(this, bundle , shareUiListener );
    }


    private void qqQzoneShare() {
        Bundle bundle=new Bundle();
        bundle.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,QzoneShare.SHARE_TO_QZONE_TYPE_NO_TYPE);
        bundle.putString(QzoneShare.SHARE_TO_QQ_TITLE,newsTitle);
        bundle.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL,url);
        bundle.putString(QzoneShare.SHARE_TO_QQ_APP_NAME,getString(R.string.app_name));
        MyApplication.getTencent().shareToQQ(this, bundle , shareUiListener );
    }


    private class ShareUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            showTips(getString(R.string.share_successful));
        }
        @Override
        public void onError(UiError uiError) {
            showTips(getString(R.string.share_fail));
        }
        @Override
        public void onCancel() {
            showTips(getString(R.string.share_cancel));
        }
    }

    private void showNews() {
        webView.loadUrl(url);
        webView.getSettings().setJavaScriptEnabled(true);//js交互
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//允许通过js打开新窗口
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress==100)
                    progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                newsTitle=title;
                if(actionBar!=null)
                    actionBar.setTitle(title);
            }
        });
    }

    //拦截返回键，控制网页后退而不退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK&&webView.canGoBack()){
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
