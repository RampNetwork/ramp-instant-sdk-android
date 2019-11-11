package network.ramp.instantsdk.demo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_demo.*
import network.ramp.instantsdk.R
import network.ramp.instantsdk.ui.rampinstant.RampInstantActivity

class DemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

        demoButton.setOnClickListener {
            val intent = Intent(this, RampInstantActivity::class.java)
            startActivity(intent)
        }
    }
}
