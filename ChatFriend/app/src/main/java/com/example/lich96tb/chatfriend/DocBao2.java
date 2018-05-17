package com.example.lich96tb.chatfriend;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static com.example.lich96tb.chatfriend.R.id.ViewVeb;

public class DocBao2 extends AppCompatActivity {
private WebView viewWeb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_bao2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewWeb = (WebView) findViewById(ViewVeb);
         String url = getIntent().getStringExtra("link");
        if (url != null) {
           // viewWeb.setWebViewClient(onWebViewClient);
            viewWeb.loadUrl(url);
        }
        //khong che chi su dụng phan mem ko cho dung trinh duytet khac
        viewWeb.setWebViewClient(new WebViewClient());
    }
    // sự kiện ấn vòa nut back trên màn hình ddienj thoại
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
               finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
