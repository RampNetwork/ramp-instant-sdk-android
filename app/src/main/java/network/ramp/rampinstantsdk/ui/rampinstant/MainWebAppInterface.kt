package network.ramp.rampinstantsdk.ui.rampinstant

import android.webkit.JavascriptInterface
import timber.log.Timber

class MainWebAppInterface(
    val onSuccess: () -> Unit,
    val onError: () -> Unit,
    val onClose: () -> Unit
) {
    @JavascriptInterface
    fun success() {
        Timber.d("JS INTERFACE Sucess")
        onSuccess()
    }

    @JavascriptInterface
    fun error() {
        Timber.d("JS INTERFACE Error")
        onError()
    }

    @JavascriptInterface
    fun close() {
        Timber.d("JS INTERFACE Close")
        onClose()
    }
}