package network.ramp.instantsdk

import android.app.Application
import android.content.Context

class App : Application() {

    override fun onCreate() {
        super.onCreate()

    }



    companion object {
        fun get(context: Context): App = (context.applicationContext as App)
    }
}