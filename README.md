# Easy Screenshots
Android Instrumentation Test Screenshots made Easy.

# Getting Started

## 1) Add Gradle Dependencies:

`androidTestCompile 'com.capitalone.easyscreenshots:easyscreenshots:1.0.0'`

## 2) Take Screenshots

``` java
 EasyScreenshots.takeScreenshot("clicking_button");
```
or (if you use a static import)
``` java
 import static com.capitalone.easyscreenshots.EasyScreenshots.takeScreenshot;
 ...
 takeScreenshot("clicking_button");
```
Note: at this point you would have screenshots taken and stored locally on the device.

## 3a) Add the External Storage Permission to your Manifest
Add the `android.permission.WRITE_EXTERNAL_STORAGE` Permission to your app's `AndroidManifest.xml`.

This is required in order to save the screenshots to the device's SD Card.  You must declare the `WRITE_EXTERNAL_STORAGE` permission. If your app is not already using this permission, we recommend you add it to a non-release build variant's manifest (ex. `debug`).

**Note**: This cannot be added to your test application's manifest, it **must** be added to your normal app's manifest.
```
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```


## 3b) (API 23+ only) Granting the External Storage Permission during a Test

**[`GrantPermissionRule`](https://developer.android.com/reference/android/support/test/rule/GrantPermissionRule.html) - (Requires Android Testing Support Library 1.0.0 or Higher)**

In order to work around this bug: https://issuetracker.google.com/issues/64389280, you currently need to grant READ_EXTERNAL_STORAGE and WRITE_EXTERNAL_STORAGE.  So, you will need both of these in your application's manifest while this issues exists.

```
@Rule
public GrantPermissionRule permissionRule =
    GrantPermissionRule.grant(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
```

If you do NOT grant this permission, the following exception will be thrown.
```
java.lang.RuntimeException: Unable to capture screenshot.
Do you have the 'android.permission.WRITE_EXTERNAL_STORAGE' permission requested and granted?
```

## 4) View Screenshots

**Options:**
- Use the `Device File Explorer` in Android Studio and view the images.
- Use [Spoon](http://square.github.io/spoon/) to run your tests and get a generated test report with the screenshots you took.
- Use [Composer](https://github.com/gojuno/composer) to run your tests and get a generated test report with the screenshots you took.

Alternatively, you can also use the [Spoon Gradle Plugin](https://github.com/stanfy/spoon-gradle-plugin), but only works with the Android Gradle Plugin <3.

# Customizing Easy Screenshots
## Configure Screenshot Storage Location
Easy Screenshots uses a `FileProvider` to provide the filepath for a taken screenshot.

By default, we provide the `SpoonFileProvider` for [Spoon](http://square.github.io/spoon/) compatibility, but you can create your own which will specify where you want the screenshots to be saved on the device by either:
- Calling `screenshotFileProvider(...)` when constructing an `EasyScreenshots` instance with an `Initializer`.
- Calling `EasyScreenshots.setScreenshotFileProvider(...)`.

## Configure Screenshot Policy
Control when screenshots are taken by either:
- Calling `policy(...)` when constructing an `EasyScreenshots` instance with an `Initializer`.
- Calling `EasyScreenshots.setPolicy(...)`.
- Using the `screenshot-policy` instrumentation argument.

## Change Screenshot Library
By default, we use Falcon for <18 and UiAutomator for 18+, which should meet most use-cases. If you'd like to force a specific library, you can call `EasyScreenshots.setScreenshotLibrary(...)` with ex. `new FalconScreenshotLibrary()`

# Contributing
We welcome your interest in Capital One’s Open Source Project (the “Project”). Any Contributor to the Project must accept and sign a CLA indicating agreement to the CLA's terms. Except for the license granted in the CLA to Capital One and to recipients of software distributed by Capital One, you reserve all right, title, and interest in and to your contributions; this CLA does not impact your rights to use your own contributions for any other purpose.

[Link to Individual CLA](https://docs.google.com/forms/d/e/1FAIpQLSfwtl1s6KmpLhCY6CjiY8nFZshDwf_wrmNYx1ahpsNFXXmHKw/viewform)

[Link to Corporate CLA](https://docs.google.com/forms/d/e/1FAIpQLSeAbobIPLCVZD_ccgtMWBDAcN68oqbAJBQyDTSAQ1AkYuCp_g/viewform)

This project adheres to the Open Source Code of Conduct. By participating, you are expected to honor this code.
