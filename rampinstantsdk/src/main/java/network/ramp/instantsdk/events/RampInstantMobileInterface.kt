package network.ramp.instantsdk.events

import android.webkit.JavascriptInterface
import com.squareup.moshi.Moshi
import network.ramp.instantsdk.events.model.OpenLink
import network.ramp.instantsdk.events.model.RampInstantEvent
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import java.util.*

internal class RampInstantMobileInterface(
    val onSuccess: () -> Unit,
    val onError: () -> Unit,
    val onClose: () -> Unit,
    val onOpenUrl: (url: String) -> Unit
) {
    private val moshi = Moshi.Builder().build()

    @JavascriptInterface
    @Suppress("unused")
    fun postMessage(payloadJson: String) {
        Timber.d("JS INTERFACE postMessage $payloadJson")

        val payload = moshi
            .adapter<RampInstantEvent>(RampInstantEvent::class.java)
            .fromJson(payloadJson)

        //TODO() replace strings with types
        when (payload?.type?.toUpperCase(Locale.ENGLISH)) {
            SUCCESS -> {
                onSuccess()
            }
            ERROR -> {
                onError()
            }
            CLOSE -> {
                onClose()
            }
            OPEN_LINK -> {
                val openLinkAdapter = moshi.adapter<OpenLink>(OpenLink::class.java)
                payload.payload?.let {
                    openLinkAdapter.fromJson(it)?.let { openLink ->
                        onOpenUrl(openLink.url)
                    }
                }
            }
            WIDGET_CLOSE,
            PURCHASE_CREATED,
            PURCHASE_SUCCESSFUL,
            PURCHASE_FAILED,
            WIDGET_CONFIG_DONE,
            WIDGET_CLOSE_REQUEST,
            WIDGET_CLOSE_REQUEST_CANCELLED,
            WIDGET_CLOSE_REQUEST_CONFIRMED -> {
                EventBus.getDefault()
                    .post(RampInstantEvent(payload.type, payload.payload ?: ""))
            }
        }
    }

    companion object {
        internal const val RampInstantMobileInterfaceName = "RampInstantMobile"
        private const val SUCCESS = "SUCCESS"
        private const val ERROR = "ERROR"
        private const val CLOSE = "CLOSE"
        private const val OPEN_LINK = "OPEN_LINK"
        private const val WIDGET_CLOSE = "WIDGET_CLOSE"
        private const val PURCHASE_CREATED = "PURCHASE_CREATED"
        private const val PURCHASE_SUCCESSFUL = "PURCHASE_SUCCESSFUL"
        private const val PURCHASE_FAILED = "PURCHASE_FAILED"
        private const val WIDGET_CONFIG_DONE = "WIDGET_CONFIG_DONE"
        private const val WIDGET_CLOSE_REQUEST = "WIDGET_CLOSE_REQUEST"
        private const val WIDGET_CLOSE_REQUEST_CANCELLED = "WIDGET_CLOSE_REQUEST_CANCELLED"
        private const val WIDGET_CLOSE_REQUEST_CONFIRMED = "WIDGET_CLOSE_REQUEST_CONFIRMED"
    }
}