package network.ramp.instantsdk.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_demo.*
import network.ramp.instantsdk.R
import network.ramp.instantsdk.facade.Config
import network.ramp.instantsdk.facade.RampInstantSDK

class DemoActivity : AppCompatActivity() {

    private val rampInstantSDK = RampInstantSDK()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

        demoButton.setOnClickListener {
            rampInstantSDK.show(
                this, Config(
                    swapAsset = swapAssetEditText.text.toString(),
                    swapAmount = swapAmountEditText.text.toString(),
                    userAddress = userAddressEditText.text.toString(),
                    hostLogoUrl = hostLogoUrlEditText.text.toString(),
                    hostAppName = "DemoRampInstantSDK"
                )
            )
        }
    }
}
