package network.ramp.instantsdk.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_demo.*
import network.ramp.instantsdk.R
import network.ramp.instantsdk.facade.RampInstantSDK

class DemoActivity : AppCompatActivity() {

    private val rampInstantSDK = RampInstantSDK()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

        demoButton.setOnClickListener {
            rampInstantSDK.show(this)
        }
    }
}
