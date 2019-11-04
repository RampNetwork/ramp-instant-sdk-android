package network.ramp.rampinstantsdk.ui.rampinstant

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Message
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import network.ramp.rampinstantsdk.ui.bank.BankActivity
import network.ramp.rampinstantsdk.ui.bank.BankActivity.Companion.intentUrl


class RIWebViewChromeClient(val context: Context) : WebChromeClient() {

    override fun onCreateWindow(
        view: WebView?,
        isDialog: Boolean,
        isUserGesture: Boolean,
        resultMsg: Message?
    ): Boolean {
        val newView = WebView(context)
        newView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                val intent = Intent(context, BankActivity::class.java)
                intent.putExtra(intentUrl, url)
                context.startActivity(intent)
                newView.destroy()
            }
        }

        val transport = resultMsg?.obj as WebView.WebViewTransport
        transport.webView = newView
        resultMsg.sendToTarget()
        return true

    }
}



