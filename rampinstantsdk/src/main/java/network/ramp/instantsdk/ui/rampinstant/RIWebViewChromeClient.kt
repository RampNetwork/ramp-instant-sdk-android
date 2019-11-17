package network.ramp.instantsdk.ui.rampinstant

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Message
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import network.ramp.instantsdk.ui.bank.BankActivity
import network.ramp.instantsdk.ui.bank.BankActivity.Companion.INTENT_URL


internal class RIWebViewChromeClient(val context: Context) : WebChromeClient() {

    override fun onCreateWindow(
        view: WebView?,
        isDialog: Boolean,
        isUserGesture: Boolean,
        resultMsg: Message?
    ): Boolean {
        when {
            isUserGesture -> {
                val newView = WebView(context)
                newView.webViewClient = object : WebViewClient() {
                    override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                        val intent = Intent(context, BankActivity::class.java)
                        intent.putExtra(INTENT_URL, url)
                        context.startActivity(intent)
                        newView.removeAllViews()
                        newView.destroy()
                    }
                }

                val transport = resultMsg?.obj as WebView.WebViewTransport
                transport.webView = newView
                resultMsg.sendToTarget()
                return true
            }
            else -> return false
        }
    }
}



