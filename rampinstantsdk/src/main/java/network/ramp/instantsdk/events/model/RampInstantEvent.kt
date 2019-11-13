package network.ramp.instantsdk.events.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RampInstantEvent(val type: String, val payload: String?)