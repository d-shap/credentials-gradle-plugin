# Credentials Gradle Plugin
Credentials Gradle Plugin is a plugin to obtain JKS keystore credentials.

Plugin is intended to help in signing Android applications.
But it can be used in other use-cases.

Android sign configuration looks like this:
```
signingConfigs {
    release {
        storeFile file('path')
        storePassword 'pass1'
        keyAlias 'key'
        keyPassword 'pass2'
    }
}
```

To use plugin, this data should be stored separatly in a file system.
Two files are required - a keystore file and a properties file with keystore and key passwords.
For example:
```
\some\dir
    keystore.jks
    credentials.properties
```
The content for the `credentials.properties` file is the following:
```
KEYSTORE_PASSWORD=pass1
KEY_PASSWORD=pass2
```
