package network.ramp.instantsdk.facade

import android.content.Context
import android.content.Intent
import network.ramp.instantsdk.BuildConfig
import network.ramp.instantsdk.events.model.InternalEvent
import network.ramp.instantsdk.events.model.RampInstantEvent
import network.ramp.instantsdk.ui.rampinstant.RampInstantActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber

class RampInstantSDK(
    private val context: Context,
    private val userAddress: String,
    private val hostLogoUrl: String,
    private val hostAppName: String,
    private val swapAsset: String = "",
    private val swapAmount: String = "",
    private val webhookStatusUrl: String = "",
    private val url: String = ""
) {

    private lateinit var rampInstantEventHolder: (rampInstantEvent: RampInstantEvent) -> Unit

    init {
        if (BuildConfig.DEBUG) {
            initLogging()
        }
    }

    fun show() {
        val intent = Intent(context, RampInstantActivity::class.java)
        intent.putExtra(
            CONFIG_EXTRA, Config(
                swapAsset = swapAsset,
                swapAmount = swapAmount,
                userAddress = userAddress,
                hostAppName = hostAppName,
                hostLogoUrl = hostLogoUrl,
                url = url,
                webhookStatusUrl = webhookStatusUrl
            )
        )
        context.startActivity(intent)
    }

    fun on(onRampInstantEvent: (rampInstantEvent: RampInstantEvent) -> Unit) {
        EventBus.getDefault().register(this)
        rampInstantEventHolder = onRampInstantEvent
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    internal fun onWidgetEvent(rampInstantEvent: RampInstantEvent) {
        rampInstantEventHolder.invoke(rampInstantEvent)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    internal fun onInternalEvent(internalEvent: InternalEvent) {
        Timber.d("onInternalEvent: $internalEvent")
        when (internalEvent) {
            is InternalEvent.CLOSE -> EventBus.getDefault().unregister(this)
        }
    }

    private fun initLogging() {
        Timber.plant(Timber.DebugTree())
    }

    companion object {
        internal const val CONFIG_EXTRA = "config"
    }
}