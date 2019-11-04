package network.ramp.rampinstantsdk

import android.app.Application
import android.content.Context
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initLogging()
    }

    private fun initLogging() {
        Timber.plant(Timber.DebugTree())
    }

    companion object {
        fun get(context: Context): App = (context.applicationContext as App)
    }
}