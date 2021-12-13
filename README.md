Simple Kotlin app for testing IPtProxy's snowflake proxy on Android

For testing it's easy to toggle between official releases of IPtProxy and a local aar file found in `app/libs/IPtProxy.aar`. Just commment out one or the other in `app/build.gradle`:

```groovy
//    implementation 'com.github.tladesignz:IPtProxy:1.2.0'
      implementation files('libs/IPtProxy.aar')
```
