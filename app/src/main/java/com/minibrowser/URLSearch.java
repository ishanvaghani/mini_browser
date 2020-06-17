package com.minibrowser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class URLSearch extends AppCompatActivity {

    WebView webView;
    Button search, cancle, backward, forward, home, reload;
    EditText url;
    String texturl;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urlsearch);

        search = findViewById(R.id.button12);
        url = findViewById(R.id.editText3);
        cancle = findViewById(R.id.button);
        webView = findViewById(R.id.webview);
        progressBar = findViewById(R.id.progressBar3);
        backward = findViewById(R.id.button13);
        forward = findViewById(R.id.button14);
        home = findViewById(R.id.button15);
        reload = findViewById(R.id.button17);


        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDescription,
                                        String mimetype, long contentLength) {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                String fileName = URLUtil.guessFileName(url,contentDescription,mimetype);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,fileName);
                DownloadManager dManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                dManager.enqueue(request);
                Toast.makeText(URLSearch.this, "Downloading File...", Toast.LENGTH_SHORT).show();
            }
        });

        texturl = getIntent().getExtras().get("url_address").toString();
        url.setText(texturl);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(texturl);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        webView.setWebViewClient(new WebViewClient());

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);

                if(newProgress == 100){
                    progressBar.setVisibility(View.INVISIBLE);
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url.setText("");
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(webView.canGoForward()) {
                    webView.goForward();
                }
            }
        });

        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(webView.canGoBack()) {
                    webView.goBack();
                }
                else {
                    startActivity(new Intent(URLSearch.this, MainActivity.class));
                }
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(URLSearch.this, MainActivity.class));
            }
        });

        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.reload();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebsite();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        }
        else {
            super.onBackPressed();
        }
    }

    private void openWebsite() {
        String url_address = url.getText().toString();
        String url_https = url_address.replaceAll("https://www.", "");
        String https = "https://";
        String www = "www.";
        String main_url = "https://www.google.com/search?source=hp&ei=H4oyXfXqEdzbz7sP6Py5iAg&q=";

        if(TextUtils.isEmpty(url_address)) {
            Toast.makeText(this, "Please Input web address", Toast.LENGTH_SHORT).show();
        }

        else if(url_address.startsWith(https)) {
            Intent intent = new Intent(URLSearch.this, URLSearch.class);
            intent.putExtra("url_address", url_address);
            startActivity(intent);
            url.setText("");
        }

        else if(url_address.startsWith(www)) {
            Intent intent = new Intent(URLSearch.this, URLSearch.class);
            intent.putExtra("url_address", https+url_address);
            startActivity(intent);
            url.setText("");
        }

        else if(url_address.endsWith(".com") || url_address.endsWith(".in") || url_address.endsWith(".edu")) {
            Intent intent = new Intent(URLSearch.this, URLSearch.class);
            intent.putExtra("url_address", https+www+url_https);
            startActivity(intent);
            url.setText("");
        }
        else {
            Intent intent = new Intent(URLSearch.this, URLSearch.class);
            intent.putExtra("url_address", main_url+url_address);
            startActivity(intent);
            url.setText("");
        }
    }
}
