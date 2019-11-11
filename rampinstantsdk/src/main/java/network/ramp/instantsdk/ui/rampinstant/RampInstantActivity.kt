package network.ramp.instantsdk.ui.rampinstant


import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import network.ramp.instantsdk.R
import network.ramp.instantsdk.events.RampInstantMobileInterface
import network.ramp.instantsdk.ui.bank.BankActivity.Companion.finishReceiver
import timber.log.Timber


class RampInstantActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initLogging()
        setupWebView(webview)
        if (savedInstanceState == null) {
            webview.loadUrl(rampUrl)
        }
    }

    private fun initLogging() {
        Timber.plant(Timber.DebugTree())
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun setupWebView(webView: WebView) {
        webView.settings.javaScriptEnabled = true
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.settings.setSupportMultipleWindows(true)
        webView.webViewClient = RampInstantWebViewClient()

        webView.addJavascriptInterface(RampInstantMobileInterface(
            onSuccess = {
                val intent = Intent(finishReceiver)
                sendBroadcast(intent)
            },
            onError = {
                val intent = Intent(finishReceiver)
                sendBroadcast(intent)
            },
            onClose = {
                finish()
            }
        ), RampInstantMobileInterface.RampInstantMobileInterfaceName)

        webView.webChromeClient = RIWebViewChromeClient(this)
    }


    override fun onBackPressed() {
        if (webview.copyBackForwardList().currentIndex > 0) {
            webview.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        webview.saveState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        webview.restoreState(savedInstanceState)
    }

    companion object {
        const val rampUrl =
            "https://ri-widget-dev.firebaseapp.com/?hostAppName=Maker+DAO&hostLogoUrl=https%3A%2F%2Fcdn-images-1.medium.com%2Fmax%2F2600%2F1*nqtMwugX7TtpcS-5c3lRjw.png&userAddress=0xe2E0256d6785d49eC7BadCD1D44aDBD3F6B0Ab58&variant=mobile&hostUrl=*"
    }

    inner class RampInstantWebViewClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            Timber.d("shouldOverrideUrlLoading ${request?.url}")
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
            Timber.d("onPageStarted $url")
            super.onPageStarted(view, url, favicon)
        }

        override fun onPageFinished(view: WebView, url: String) {
            Timber.d("onPageFinished $url")
            progressBar.visibility = View.GONE
            super.onPageFinished(view, url)
        }
    }
}
