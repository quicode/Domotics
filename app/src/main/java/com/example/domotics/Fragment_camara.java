package com.example.domotics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class Fragment_camara extends Fragment {

    private WebView webView;

    @Nullable
    @Override
    //override ctrl + c
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_camara,container,false);

        webView=v.findViewById(R.id.web);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://192.168.10.59:80/client");

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        return v;
    }
}