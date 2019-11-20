package network.ramp.instantsdk.webview

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView
import android.webkit.WebViewClient
import network.ramp.instantsdk.events.RampInstantMobileInterface
import network.ramp.instantsdk.events.RampInstantMobileInterface.Companion.RampInstantMobileInterfaceName


class RampInstantWebView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : WebView(context, attrs, defStyleAttr) {

    @SuppressLint("SetJavaScriptEnabled")
    fun setupWebView(wvClient: WebViewClient, jsInterface: RampInstantMobileInterface?) {
        settings.javaScriptEnabled = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.domStorageEnabled = true
        settings.setSupportMultipleWindows(true)
        webViewClient = wvClient
        jsInterface?.let {
            addJavascriptInterface(it, RampInstantMobileInterfaceName)
        }
        webChromeClient = RampInstantWebViewChromeClient(context)
    }
}