package network.ramp.instantsdk.ui.bank

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_bank.*
import network.ramp.instantsdk.R
import network.ramp.instantsdk.events.RampInstantMobileInterface
import network.ramp.instantsdk.ui.rampinstant.RIWebViewChromeClient
import timber.log.Timber


internal class BankActivity : AppCompatActivity() {

    private var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(arg0: Context, intent: Intent) {
            when (intent.action) {
                FINISH_RECEIVER -> {
                    finish()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerReceiver(broadcastReceiver, IntentFilter(FINISH_RECEIVER))

        setContentView(R.layout.activity_bank)

        Log.d("ACTION", "action: $intent?.action")
        Log.d("DATA", "data: ${intent?.data}")

        val url = intent.getStringExtra(INTENT_URL) ?: intent?.data.toString()


        setupWebView(bankWebView)

        if (savedInstanceState == null) {
            bankWebView.loadUrl(url)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.data?.let {
            bankWebView.loadUrl(it.toString())
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
                onClose = { runOnUiThread { this.finish() } },
                onOpenUrl = {
                    Timber.d("onOpenUrl in BankActivity")
                }),
            RampInstantMobileInterface.RampInstantMobileInterfaceName
        )
        webView.webChromeClient = RIWebViewChromeClient(this)
    }

    override fun onBackPressed() {
        if (bankWebView.canGoBack()) {
            bankWebView.goBack()
        } else {
            super.onBackPressed()
        }
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
        const val FINISH_RECEIVER = "finish_activity"
        const val INTENT_URL = "url"
        val BROWSER_PACKAGE_NAMES = arrayOf(
            "com.android.chrome",
            "org.mozilla.firefox",
            "com.UCMobile.intl",
            "com.sec.android.app.sbrowser",
            "com.opera.browser",
            "com.opera.mini.native",
            "com.microsoft.emmx"
        )
        const val ACTION_VIEW_INTENT = "android.intent.action.VIEW"
    }

    private fun openUrl(
        context: Context,
        destinationUrl: String,
        view: WebView
    ) {
        var isAppOpened = false
        try {
            val intent = Intent(ACTION_VIEW_INTENT)
            intent.data = Uri.parse(destinationUrl)
            val activity = intent.resolveActivity(context.packageManager)
            if (activity != null && !BROWSER_PACKAGE_NAMES.contains(activity.packageName)) {
                context.startActivity(intent)
                isAppOpened = true
            }
        } catch (ignore: Exception) {
        }
        if (!isAppOpened) {
            Timber.d("isAppOpened : $isAppOpened  Load url $destinationUrl")
            view.loadUrl(destinationUrl)
        }
    }

    inner class BankWebViewClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            Timber.d("shouldOverrideUrlLoading ${request?.url}")
            request?.let { webResourceRequest ->
                view?.let {
                    openUrl(this@BankActivity, webResourceRequest.url.toString(), it)
                }
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
