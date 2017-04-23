package com.example.administrator.myapplication.ui.comm;

import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;


import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.Utill.MarqueeTextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class WebActivity extends Activity
{

    Toolbar toobar;
    WebView webView;
    String tittle;
    MarqueeTextView textView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }

        webView = (WebView) findViewById(R.id.webView);
        toobar = (Toolbar) findViewById(R.id.toolbar);
        textView = (MarqueeTextView) findViewById(R.id.tittle);
        String url = getIntent().getStringExtra("url");
        tittle = getIntent().getStringExtra("tittle");

        if(tittle!=null&&tittle.toString().length()!=0)
        {
            toobar.setTitle(null);
            textView.setText(tittle);
        }


        WebSettings webSettings = webView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

        toobar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        webView.loadUrl(url);
        webView.setOnKeyListener(new View.OnKeyListener()
        { // webview can
            // go back
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack())
                {
                    webView.goBack();
                    return true;
                }
                return false;
            }
        });
    }


}
