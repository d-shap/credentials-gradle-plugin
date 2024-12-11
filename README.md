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

## Configuration
Apply an appropriate version of a plugin:
```
plugins {
    id 'ru.d-shap.credentials' version '1.0'
}
```

Plugin provides a `read` function.
This function accepts the following parameters:
* `baseDir` - the directory with a keystore file and a credentials file, relative to the project's root directory
* `keystoreFileName` - the name of a keystore file within a base directory
* `credentialsFileName` - the name of a credentials file within a base directory
* `keystorePasswordProperty` - the property for a keystore password property
* `keyPasswordProperty` - the property for a key password property

Plugin exposes the following properties after a `read` function is called:
* `keystoreFile` - a file object for a keystore file
* `keystorePassword` - a keystore password
* `keyPassword` - a key password

## Example
```
android {
    signingConfigs {
        release {
            project.credentials.read('../keys/appname', 'keystore.jks', 'credentials.properties', 'KEYSTORE_PASSWORD', 'KEY_PASSWORD')
            storeFile project.ext.keystoreFile
            storePassword project.ext.keystorePassword
            keyAlias 'key'
            keyPassword project.ext.keyPassword
        }
    }
}
```
