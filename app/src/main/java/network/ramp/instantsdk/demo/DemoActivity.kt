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

//"0xab5801a7d398351b8be11c439e05c5b3259aec9b"
//"https%3A%2F%2Fcdn-images-1.medium.com%2Fmax%2F2600%2F1*nqtMwugX7TtpcS-5c3lRjw.png"