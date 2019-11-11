package network.ramp.instantsdk.ui.rampinstant


import android.annotation.SuppressLint
import android.content.Intent
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
import network.ramp.instantsdk.facade.Config
import network.ramp.instantsdk.facade.RampInstantSDK.Companion.CONFIG_EXTRA
import network.ramp.instantsdk.ui.bank.BankActivity.Companion.FINISH_RECEIVER
import timber.log.Timber


internal class RampInstantActivity : AppCompatActivity() {

    lateinit var config: Config

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupWebView(webview)

        config = intent.extras?.getParcelable(CONFIG_EXTRA)!! //TODO replace it

        if (savedInstanceState == null) {
            webview.loadUrl(buildUrl(config))
        }
    }


    @SuppressLint("SetJavaScriptEnabled")
    fun setupWebView(webView: WebView) {
        webView.settings.javaScriptEnabled = true
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.settings.setSupportMultipleWindows(true)
        webView.webViewClient = RampInstantWebViewClient()

        webView.addJavascriptInterface(RampInstantMobileInterface(
            onSuccess = {
                val intent = Intent(FINISH_RECEIVER)
                sendBroadcast(intent)
            },
            onError = {
                val intent = Intent(FINISH_RECEIVER)
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        webview.saveState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        webview.restoreState(savedInstanceState)
    }

    private fun buildUrl(config: Config): String {
        return config.url +
                "?hostAppName=${config.hostAppName}" +
                "&hostLogoUrl=${config.hostLogoUrl}" +
                "&userAddress=${config.userAddress}" +
                "&swapAsset=${config.swapAsset}" +
                "&swapAmount=${config.swapAmount}" +
                "&variant=mobile&" +
                "&hostUrl=*"
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
