# Credentials Gradle Plugin
Credentials Gradle Plugin is a plugin to obtain JKS keystore credentials.

Plugin is intended to help in signing Android applications.
But it can be used in other use-cases.

Android sign configuration looks like this:
```
android {
    signingConfigs {
        release {
            storeFile file('path')
            storePassword 'pass1'
            keyAlias 'key'
            keyPassword 'pass2'
        }
    }
}
```

## Prerequisites
To use plugin, credentials data should be stored separately in a file system.
Two files are required - a keystore file and a properties file with a keystore password, key alias and a key password.
For example:
```
\some\dir
    keystore.jks
    credentials.properties
```
The content of the `credentials.properties` file is the following:
```
STORE_PASSWORD=pass1
KEY_ALIAS=key
KEY_PASSWORD=pass2
```

## Configuration
Apply an appropriate version of a plugin:
```
plugins {
    id 'ru.d-shap.credentials' version '1.0'
}
```

Plugin provides a `read` function.
This function accepts the following parameters:
* `baseDir` - the directory with a keystore file and a properties file, relative to the project's root directory
* `storeFileName` - the name of a keystore file within a `baseDir`
* `credentialsFileName` - the name of a properties file within a `baseDir`
* `storePasswordProperty` - the property name for a store password
* `keyAliasProperty` - the property name for a key alias
* `keyPasswordProperty` - the property name for a key password

Plugin exposes the following properties after a `read` function is called:
* `storeFile` - a file object for a keystore file
* `storePassword` - a store password
* `keyAlias` - a key alias
* `keyPassword` - a key password

## Example
```
android {
    signingConfigs {
        release {
            project.credentials.read('../keys/appname', 'keystore.jks', 'credentials.properties', 'STORE_PASSWORD', 'KEY_ALIAS', 'KEY_PASSWORD')
            storeFile project.ext.storeFile
            storePassword project.ext.storePassword
            keyAlias project.ext.keyAlias
            keyPassword project.ext.keyPassword
        }
    }
}
```
