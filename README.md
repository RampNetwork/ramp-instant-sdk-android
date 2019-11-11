# ramp-instant-sdk-android
[![platform](https://img.shields.io/badge/platform-Android-yellow.svg)](https://www.android.com)
[![API](https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=24)



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
        <activity
            android:name="network.ramp.instantsdk.ui.bank.BankActivity"
            android:configChanges="keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize"
            android:theme="@style/RampInstant.NoActionBar" />
        <activity
            android:name="network.ramp.instantsdk.ui.rampinstant.RampInstantActivity"
            android:configChanges="keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize"
            android:theme="@style/RampInstant.NoActionBar"></activity>
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

```kotlin
private val rampInstantSDK = RampInstantSDK()

rampInstantSDK.show(context, config)

```


