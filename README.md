# ramp-instant-sdk-android
[![platform](https://img.shields.io/badge/platform-Android-yellow.svg)](https://www.android.com)
[![API](https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=24)


Ramp Instant is a library to integrate easy cryptocurrency buy with Ramp.


## Prerequisites

Add this in your root `build.gradle` file (**not** your module `build.gradle` file):

```gradle
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```

Add this in your `AndroidManifest.xml` file:

```xml
    <uses-permission android:name="android.permission.INTERNET" />
    
    <application
	...
 	>
	    ...
        <activity
            android:name="network.ramp.instantsdk.ui.bank.BankActivity"
            android:configChanges="keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize"
            android:theme="@style/RampInstant.NoActionBar" />
        <activity
            android:name="network.ramp.instantsdk.ui.rampinstant.RampInstantActivity"
            android:configChanges="keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize"
            android:theme="@style/RampInstant.NoActionBar"></activity>
	    ...
     </application>
```

## Dependency

Add this to your module's `build.gradle` file (make sure the version matches the JitPack badge above):

```gradle
dependencies {
	...
	implementation 'com.github.RampNetwork:ramp-instant-sdk-android:$version'
}
```

## Usage

Ramp Instant Android SDK consists of two main types: Config and RampInstantSDK.

Config data class contains all the information necessary for Ramp to initialize. Its parameters are as follows:

**hostLogoUrl**: URL to your app's logo

**hostAppName**: your app's name

**userAddress**: 0x-prefixed ETH address of the buyer

**swapAsset**: 'ETH' or 'DAI' (optional)

**swapAmount**: int string - wei or token units (optional)

**webhookStatusUrl**: your URL for webhook updates (optional)

**url**: allows to provide an alternative URL to load a non-production version of the widget (optional)

RampInstantSDK is facade for the sdk. It starts Ramp widget, which allows to buy crypto. 
To start using RampInstant in your app, simply create Config instance with parameters of your choice, then instantiate RampInstantSDK using Config instance and call 'show' method.

```kotlin
private val rampInstantSDK = RampInstantSDK()

rampInstantSDK.show(context, config)

```

## License
TODO()
