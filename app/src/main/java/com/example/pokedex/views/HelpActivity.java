package com.example.pokedex.views;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.pokedex.R;

public class HelpActivity extends AppCompatActivity {

    private WebView mwb;
    private String aux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //mwb = new WebView(this);
        mwb= findViewById(R.id.web);
        aux = getIntent().getStringExtra("aux");
        mwb.getSettings().setJavaScriptEnabled(true);
        final Activity activity = this;
        mwb.setWebViewClient(new WebViewClient(){
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl){
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }
            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }
        });

        if(aux.equals("list")){
            mwb.loadUrl("https://pedromfuente.github.io/Pokedex/list.html?");
        }else if(aux.equals("form")){
            mwb.loadUrl("https://pedromfuente.github.io/Pokedex/form.html?");
        }else if(aux.equals("search")){
            mwb.loadUrl("https://pedromfuente.github.io/Pokedex/search.html?");
        }
    }
}