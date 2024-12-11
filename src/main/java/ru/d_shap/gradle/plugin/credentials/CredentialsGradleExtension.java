///////////////////////////////////////////////////////////////////////////////////////////////////
// Credentials Gradle Plugin is a plugin to obtain JKS keystore credentials.
// Copyright (C) 2024 Dmitry Shapovalov.
//
// This file is part of Credentials Gradle Plugin.
//
// Credentials Gradle Plugin is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// Credentials Gradle Plugin is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with this program. If not, see <http://www.gnu.org/licenses/>.
///////////////////////////////////////////////////////////////////////////////////////////////////
package ru.d_shap.gradle.plugin.credentials;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import javax.inject.Inject;

import org.gradle.api.InvalidUserDataException;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.plugins.ExtraPropertiesExtension;

/**
 * Credentials gradle extension.
 *
 * @author Dmitry Shapovalov
 */
public class CredentialsGradleExtension {

    private final Project _project;

    /**
     * Create new object.
     *
     * @param project the project.
     */
    @Inject
    public CredentialsGradleExtension(final Project project) {
        super();
        _project = project;
    }

    /**
     * Read the configuration.
     *
     * @param baseDir               the base directory.
     * @param storeFileName         the keystore file name.
     * @param credentialsFileName   the credentials file name.
     * @param storePasswordProperty the property name for the store password.
     * @param keyAliasProperty      the property name for the key alias.
     * @param keyPasswordProperty   the property name for the key password.
     */
    public void read(final String baseDir, final String storeFileName, final String credentialsFileName, final String storePasswordProperty, final String keyAliasProperty, final String keyPasswordProperty) {
        if (Logger.isInfoEnabled()) {
            Logger.info("Start processing credentials");
        }

        ExtensionContainer extensionContainer = _project.getExtensions();
        ExtraPropertiesExtension extraProperties = extensionContainer.getExtraProperties();

        File baseFile = getBaseFile(baseDir);

        File storeFile = getStoreFile(baseFile, storeFileName);
        extraProperties.set("storeFile", storeFile);

        File credentialsFile = getCredentialsFile(baseFile, credentialsFileName);
        Properties properties = readCredentialsFile(credentialsFile);

        String storePassword = getProperty(properties, storePasswordProperty, null);
        extraProperties.set("storePassword", storePassword);
        String keyAlias = getProperty(properties, keyAliasProperty, "key");
        extraProperties.set("keyAlias", keyAlias);
        String keyPassword = getProperty(properties, keyPasswordProperty, null);
        extraProperties.set("keyPassword", keyPassword);

        if (Logger.isInfoEnabled()) {
            Logger.info("Finish processing credentials");
        }
    }

    private File getBaseFile(final String baseDir) {
        File rootDir = _project.getRootDir();
        File rootFile = rootDir.getAbsoluteFile();
        Path rootPath = rootFile.toPath();
        Path basePath = rootPath.resolve(baseDir);
        File baseFile = basePath.toFile();
        baseFile = normalizeFilePath(baseFile);
        if (Logger.isDebugEnabled()) {
            Logger.debug("Base dir: " + baseFile.getAbsolutePath());
        }
        return baseFile;
    }

    private File getStoreFile(final File baseFile, final String keystoreFileName) {
        File keystoreFile = new File(baseFile, keystoreFileName);
        keystoreFile = normalizeFilePath(keystoreFile);
        if (Logger.isDebugEnabled()) {
            Logger.debug("Keystore file: " + keystoreFile.getAbsolutePath());
        }
        if (!keystoreFile.exists() || !keystoreFile.isFile()) {
            throw new InvalidUserDataException("Keystore file must be defined");
        }
        return keystoreFile;
    }

    private File getCredentialsFile(final File baseFile, final String credentialsFileName) {
        File credentialsFile = new File(baseFile, credentialsFileName);
        credentialsFile = normalizeFilePath(credentialsFile);
        if (Logger.isDebugEnabled()) {
            Logger.debug("Credentials file: " + credentialsFile.getAbsolutePath());
        }
        if (!credentialsFile.exists() || !credentialsFile.isFile()) {
            throw new InvalidUserDataException("Credentials file must be defined");
        }
        return credentialsFile;
    }

    private File normalizeFilePath(final File file) {
        Path path = file.toPath();
        path = path.normalize();
        return path.toFile();
    }

    private Properties readCredentialsFile(final File credentialsFile) {
        Properties properties = new Properties();
        try (InputStream inputStream = Files.newInputStream(credentialsFile.toPath())) {
            properties.load(inputStream);
        } catch (IOException ex) {
            throw new InvalidUserDataException("Failed to read credentials file", ex);
        }
        if (Logger.isDebugEnabled()) {
            Logger.debug("Credentials file is read");
        }
        return properties;
    }

    private String getProperty(final Properties properties, final String property, final String defaultValue) {
        String value = properties.getProperty(property);
        if (value == null) {
            throw new InvalidUserDataException("Property " + property + " must be defined");
        } else {
            if (Logger.isInfoEnabled()) {
                Logger.info("Property " + property + " is read");
            }
            return value;
        }
    }

}
