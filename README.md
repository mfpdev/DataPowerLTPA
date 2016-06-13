# DataPower Samples

This sample demonstrates the blog post: http://ibm.biz/mobilefirstdatapower

This samples contains 4 artifacts:

- *pattern* contains the sample DataPower pattern to deploy on your DataPower instance.
- *DataPowerCordova* contains the Cordova project for the hybrid sample.
- *DataPowerSwift* contains the Xcode project for the native iOS sample.
- *DataPowerAndroid* contains the Android Studio project for the native android sample.

Regardless of the sample you wish you use (Hybrid, Native Android or Native iOS), you are first required to deploy the **ResourceAdapter** adapter from the [SecurityCheckAdapters](https://github.com/MobileFirst-Platform-Developer-Center/SecurityCheckAdapters/tree/release80/ResourceAdapter) repository.

Make sure to point your `mfpclient.properties` or `mfpclient.plist` to the IP/port of your DataPower machine.
