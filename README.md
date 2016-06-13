# DataPower Samples

This sample demonstrates the blog post: https://mobilefirstplatform.ibmcloud.com/blog/2016/02/20/datapower-integration/

This samples contains 4 artifacts:

- *pattern* contains the sample DataPower pattern to deploy on your DataPower instance.
- *DataPower* contains the MobileFirst Platform project to import in MobileFirst Studio, as well as the Hybrid sample.
- *DataPowerSwift* contains the Xcode project for the native iOS sample.
- *DataPowerAndroid* contains the Android Studio project for the native android sample.

Regardless of the sample you wish you use (Hybrid, Native Android or Native iOS), you are first required to deploy the adapter and authenticationConfig included in the common *DataPower* project.

Make sure to point your `wlclient.properties` or `worklight.plist` to the IP/port of your DataPower machine.
