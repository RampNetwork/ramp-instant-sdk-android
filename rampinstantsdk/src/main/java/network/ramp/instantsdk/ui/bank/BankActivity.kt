package network.ramp.instantsdk.ui.bank

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_bank.*
import network.ramp.instantsdk.R
import network.ramp.instantsdk.events.RampInstantMobileInterface
import timber.log.Timber

internal class BankActivity : AppCompatActivity() {

    private var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(arg0: Context, intent: Intent) {
            when (intent.action) {
                finishReceiver -> {
                    finish()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerReceiver(broadcastReceiver, IntentFilter(finishReceiver))

        setContentView(R.layout.activity_bank)

        val url = intent.getStringExtra(intentUrl)

        setupWebView(bankWebView)

        if (savedInstanceState == null) {
            bankWebView.loadUrl(url)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        unregisterReceiver(broadcastReceiver)
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun setupWebView(webView: WebView) {
        webView.settings.javaScriptEnabled = true
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.settings.setSupportMultipleWindows(true)
        webView.settings.domStorageEnabled = true
        webView.webViewClient = BankWebViewClient()
        webView.addJavascriptInterface(
            RampInstantMobileInterface(
                onSuccess = { runOnUiThread { this.finish() } },
                onError = { runOnUiThread { this.finish() } },
                onClose = { runOnUiThread { this.finish() } }),
            RampInstantMobileInterface.RampInstantMobileInterfaceName
        )
    }

    override fun onBackPressed() {
        if (bankWebView.copyBackForwardList().currentIndex > 0) {
            bankWebView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        bankWebView.saveState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        bankWebView.restoreState(savedInstanceState)
    }

    companion object {
        const val finishReceiver = "finish_activity"
        const val intentUrl = "URL"
    }


    inner class BankWebViewClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            Timber.d("shouldOverrideUrlLoading ${request?.url}")
            request?.let {
                view?.loadUrl(it.url.toString())
            }
            return false
        }

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
            Timber.d("onPageStarted $url")

            super.onPageStarted(view, url, favicon)

        }

        override fun onPageFinished(view: WebView, url: String) {
            Timber.d(" onPageFinished $url")
            bankProgressBar.visibility = View.GONE
            super.onPageFinished(view, url)
        }
    }
}
