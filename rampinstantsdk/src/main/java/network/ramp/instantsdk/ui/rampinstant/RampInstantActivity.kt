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
import network.ramp.instantsdk.events.model.InternalEvent
import network.ramp.instantsdk.facade.Config
import network.ramp.instantsdk.facade.RampInstantSDK.Companion.CONFIG_EXTRA
import network.ramp.instantsdk.ui.bank.BankActivity
import network.ramp.instantsdk.ui.bank.BankActivity.Companion.FINISH_RECEIVER
import org.greenrobot.eventbus.EventBus
import timber.log.Timber


internal class RampInstantActivity : AppCompatActivity() {

    private lateinit var config: Config

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupWebView(webView)

        intent.extras?.getParcelable<Config>(CONFIG_EXTRA)?.let {
            config = it
        } ?: returnOnError("Config object cannot be null")

        if (savedInstanceState == null) {
            Timber.d(buildUrl(config))
            webView.loadUrl(buildUrl(config))
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
            },
            onOpenUrl = { url ->
                Timber.d("onOpenUrl")
                val intent = Intent(this, BankActivity::class.java)
                intent.putExtra(BankActivity.INTENT_URL, url)
                startActivity(intent)
            }
        ), RampInstantMobileInterface.RampInstantMobileInterfaceName)

        webView.webChromeClient = RIWebViewChromeClient(this)
    }


    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        webView.saveState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        webView.restoreState(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        webView.removeJavascriptInterface(RampInstantMobileInterface.RampInstantMobileInterfaceName)
        EventBus.getDefault().post(InternalEvent.CLOSE)
    }

    private fun buildUrl(config: Config): String {
        return config.url +
                "?hostAppName=${config.hostAppName}" +
                "&hostLogoUrl=${config.hostLogoUrl}" +
                "&userAddress=${config.userAddress}" +
                concatenateIfNotBlank("&swapAsset=", config.swapAsset) +
                concatenateIfNotBlank("&swapAmount=", config.swapAmount) +
                concatenateIfNotBlank("&webhookStatusUrl=", config.webhookStatusUrl) +
                "&variant=mobile&" +
                "&hostUrl=*"
    }

    private fun concatenateIfNotBlank(str1: String, str2: String): String {
        return if (str1.isNotBlank() && str2.isNotBlank()) {
            str1 + str2
        } else ""
    }

    private fun returnOnError(message: String) {
        Timber.e(message)
        finish()
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
