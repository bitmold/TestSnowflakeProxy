Simple Kotlin app for testing IPtProxy's snowflake proxy on Android

Essentially a button for starting and stopping a Snowflake Proxy with the default values. The app hooks into IPtProxy in such a way where Android UI events can occur whenever IPtProxy has detected that a client has connected to your snowflake. This is probably the most barebones implementation of this feature, this will likely exist in Orbot at some point in the future. 

For testing it's easy to toggle between official releases of IPtProxy and a local aar file found in `app/libs/IPtProxy.aar`. Just commment out one or the other in `app/build.gradle`:

```groovy
//    implementation 'com.github.tladesignz:IPtProxy:1.2.0'
      implementation files('libs/IPtProxy.aar')
```
