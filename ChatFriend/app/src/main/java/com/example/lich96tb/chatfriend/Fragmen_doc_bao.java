package com.example.lich96tb.chatfriend;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import static com.example.lich96tb.chatfriend.R.id.ViewVeb;

/**
 * Created by lich96tb on 11/27/2017.
 */

public class Fragmen_doc_bao extends Fragment {
    WebView viewWeb;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fm_docbao2,container,false);
        viewWeb = (WebView)view.findViewById(ViewVeb);
        Bundle bundle=getArguments();
        String url=bundle.getString("link");
      //  String url = getIntent().getStringExtra("link");
        if (url != null) {
            viewWeb.setWebViewClient(onWebViewClient);
            viewWeb.loadUrl(url);
        }
        //khong che chi su dụng phan mem ko cho dung trinh duytet khac
        viewWeb.setWebViewClient(new WebViewClient());

        return view;
    }
    private WebViewClient onWebViewClient=new WebViewClient(){

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            Toast.makeText(getActivity(), "loi roi", Toast.LENGTH_SHORT).show();

        }
    };

}
