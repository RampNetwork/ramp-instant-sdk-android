package network.ramp.instantsdk.demo

import android.os.Bundle
import android.widget.EditText
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
                    swapAsset = swapAssetEditText.getContent(),
                    swapAmount = swapAmountEditText.getContent(),
                    userAddress = userAddressEditText.getContent(),
                    hostLogoUrl = hostLogoUrlEditText.getContent(),
                    hostAppName = hostAppNameEditText.getContent(),
                    webhookStatusUrl = webHookStatusUrlEditText.getContent()
                )
            )
        }
    }
}

fun EditText.getContent(): String {
    return when {
        this.text.isNotBlank() -> this.text.toString()
        this.hint != null && this.hint.isNotBlank() -> this.hint.toString()
        else -> ""
    }
}
