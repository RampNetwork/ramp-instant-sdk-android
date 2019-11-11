package network.ramp.instantsdk.facade

import android.content.Context
import android.content.Intent
import network.ramp.instantsdk.ui.rampinstant.RampInstantActivity
import timber.log.Timber

class RampInstantSDK {

    init {
        initLogging()
    }

    fun show(context: Context) {
        val intent = Intent(context, RampInstantActivity::class.java)
        context.startActivity(intent)
    }


    private fun initLogging() {
        Timber.plant(Timber.DebugTree())
    }
}