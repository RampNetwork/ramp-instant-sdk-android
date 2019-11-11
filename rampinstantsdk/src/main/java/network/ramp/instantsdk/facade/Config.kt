package network.ramp.instantsdk.facade

data class Config(
    /**
     * 'ETH' or 'DAI'
     */
    val swapAsset: String,
    /**
     * int string - wei or token units
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
    val url: String?
)