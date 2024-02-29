package com.example.webviewapp

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.URLUtil
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.widget.SearchView
import com.example.webviewapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object{
        private const val BASE_URL = "https://google.com"
        private const val SEARCH_URL = "/search?q="
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSearchView()
        setWebView()
    }

    private fun setSearchView(){
        binding.searchViewMain.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {

                p0?.let {
                    if (URLUtil.isValidUrl(it)) {
                        binding.webViewMain.loadUrl(it)
                    } else {
                        binding.webViewMain.loadUrl("$BASE_URL$SEARCH_URL$it")
                    }
                }
                return false
            }

        })
    }


    private fun setWebView() {
        binding.webViewMain.webChromeClient = object : WebChromeClient() {}

        binding.webViewMain.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return false
            }
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                binding.searchViewMain.setQuery(url, false)
                //swipeRefresh.isRefreshing = true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                //swipeRefresh.isRefreshing = false
            }

        }

        val settings = binding.webViewMain.settings
        settings.javaScriptEnabled = true
        binding.webViewMain.loadUrl(BASE_URL)
    }
}