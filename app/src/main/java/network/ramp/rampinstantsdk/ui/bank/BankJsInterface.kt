package network.ramp.rampinstantsdk.ui.bank

import android.webkit.JavascriptInterface
import timber.log.Timber

class BankJsInterface(val onSuccess: () -> Unit, val onError: () -> Unit) {

    @JavascriptInterface
    fun success() {
        Timber.d("JS INTERFACE Success")
        onSuccess()
    }

    @JavascriptInterface
    fun error() {
        Timber.d("JS INTERFACE Error")
        onError()
    }

    companion object {
        const val bankJsInterfaceName = "RampInstantMobile"
    }
}