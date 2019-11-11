package network.ramp.instantsdk.facade

import android.content.Context
import android.content.Intent
import network.ramp.instantsdk.BuildConfig
import network.ramp.instantsdk.ui.rampinstant.RampInstantActivity
import timber.log.Timber

class RampInstantSDK {

    init {
        if (BuildConfig.DEBUG) {
            initLogging()
        }
    }

    fun show(context: Context, config: Config) {
        val intent = Intent(context, RampInstantActivity::class.java)
        intent.putExtra(CONFIG_EXTRA, config)

        context.startActivity(intent)
    }

    private fun initLogging() {
        Timber.plant(Timber.DebugTree())
    }

    companion object {
        internal const val CONFIG_EXTRA = "config"
    }
}