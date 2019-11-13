package network.ramp.instantsdk.demo

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_demo.*
import network.ramp.instantsdk.R
import network.ramp.instantsdk.facade.RampInstantSDK

class DemoActivity : AppCompatActivity() {

    private lateinit var rampInstantSDK: RampInstantSDK

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

        demoButton.setOnClickListener {
            rampInstantSDK = RampInstantSDK(
                this,
                swapAsset = swapAssetEditText.getContent(),
                swapAmount = swapAmountEditText.getContent(),
                userAddress = userAddressEditText.getContent(),
                hostLogoUrl = hostLogoUrlEditText.getContent(),
                hostAppName = hostAppNameEditText.getContent(),
                webhookStatusUrl = webHookStatusUrlEditText.getContent()
            )
            if (eventSwitch.isChecked)
                rampInstantSDK.on { event ->
                    Toast.makeText(applicationContext, event.type, Toast.LENGTH_SHORT).show()
                    Log.d("Widget Event", event.type)
                }
            rampInstantSDK.show()
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
