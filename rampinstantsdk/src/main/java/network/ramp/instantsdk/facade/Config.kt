package network.ramp.instantsdk.facade

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Config(
    /**
     * 'ETH' or 'DAI'
     */
    val swapAsset: String,
    /**
     * int string - wei or token units
     * 1 Eth = 1 * 10 ** 18
     */
    val swapAmount: String,
    /**
     * 0x-prefixed ETH address of the buyer
     */
    val userAddress: String,
    /**
     * URL to your app's logo
     */
    val hostLogoUrl: String,
    /**
     * your app's name
     */
    val hostAppName: String,
    /**
     * allows to provide an alternative URL to load
     * a non-production version of the widget
     */
    val url: String? = "https://ri-widget-dev.firebaseapp.com",
    /**
     * your URL for webhook updates (optional)
     */
    val webhookStatusUrl: String?

) : Parcelable