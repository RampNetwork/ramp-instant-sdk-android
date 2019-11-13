package network.ramp.instantsdk.facade

import android.content.Context
import android.content.Intent
import network.ramp.instantsdk.BuildConfig
import network.ramp.instantsdk.events.model.Event
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
    private val webhookStatusUrl: String = ""
) {

    private lateinit var holder: (event: Event) -> Unit

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
                webhookStatusUrl = webhookStatusUrl
            )
        )

        EventBus.getDefault().register(this)
        context.startActivity(intent)
    }

    fun on(onEvent: (event: Event) -> Unit) {
        holder = onEvent
    }

    fun unsubscribeFromEvents() {
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: Event) {
        holder.invoke(event)
    }

    private fun initLogging() {
        Timber.plant(Timber.DebugTree())
    }

    companion object {
        internal const val CONFIG_EXTRA = "config"
    }
}