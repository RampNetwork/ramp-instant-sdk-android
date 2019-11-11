package network.ramp.instantsdk.events.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class Event(val type: String, val payload: String?)