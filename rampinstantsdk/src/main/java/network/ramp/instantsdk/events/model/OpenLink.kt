package network.ramp.instantsdk.events.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OpenLink(val linkType: String, val url: String)