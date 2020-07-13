package dev.ashish.newsapp.activities

import android.graphics.Bitmap
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import dev.ashish.newsapp.R
import dev.ashish.newsapp.utils.Constants

class WebViewActivity : AppCompatActivity() {
    var webView: WebView? = null
    var progressBar: ProgressBar? = null
    var URL: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        val receivedIntent = intent
        URL = receivedIntent.getStringExtra(Constants.URL)
        if (URL == null) {
            finish()
        }
        progressBar = findViewById(R.id.progressBarWebView)
        progressBar?.setIndeterminate(true)
        webView = findViewById(R.id.webview)
        webView?.setWebViewClient(MyWebClient())
        webView?.getSettings()?.loadsImagesAutomatically = true
        webView?.getSettings()?.javaScriptEnabled = true
        webView?.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY)
        webView?.loadUrl(URL)
    }

    private inner class MyWebClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
            super.onPageStarted(view, url, favicon)
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            progressBar!!.visibility = View.GONE
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN) {
            when (keyCode) {
                KeyEvent.KEYCODE_BACK -> {
                    if (webView!!.canGoBack()) {
                        webView!!.goBack()
                    } else {
                        onBackPressed()
                    }
                    return true
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onBackPressed() {
        if (webView!!.canGoBack()) {
            webView!!.goBack()
        } else {
            super.onBackPressed()
        }
    }
}