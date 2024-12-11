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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Properties;

import javax.inject.Inject;

import org.gradle.api.InvalidUserDataException;
import org.gradle.api.Project;

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
     * @param baseDir             the base directory.
     * @param keystoreFileName    the keystore file name.
     * @param credentialsFileName the credentials file name.
     */
    public void read(final String baseDir, final String keystoreFileName, final String credentialsFileName) {
        if (Logger.isErrorEnabled()) {
            Logger.error("Start processing credentials");
        }

        File baseFile = getBaseFile(baseDir);

        File keystoreFile = getKeystoreFile(baseFile, keystoreFileName);
        if (Logger.isErrorEnabled()) {
            Logger.error("keystoreFile: " + keystoreFile.getAbsolutePath());
        }

        File credentialsFile = getCredentialsFile(baseFile, credentialsFileName);
        if (Logger.isErrorEnabled()) {
            Logger.error("credentialsFile: " + credentialsFile.getAbsolutePath());
        }

        Properties properties = readCredentialsFile(credentialsFile);
        if (Logger.isErrorEnabled()) {
            Logger.error("properties: " + properties);
        }

        String storePassword = properties.getProperty("STORE_PASSWORD");
        if (Logger.isErrorEnabled()) {
            Logger.error("storePassword: " + storePassword);
        }
        String keyPassword = properties.getProperty("KEY_PASSWORD");
        if (Logger.isErrorEnabled()) {
            Logger.error("keyPassword: " + keyPassword);
        }

        if (Logger.isErrorEnabled()) {
            Logger.error("Finish processing credentials");
        }
    }

    private File getBaseFile(final String baseDir) {
        File rootDir = _project.getRootDir();
        File rootFile = rootDir.getAbsoluteFile();
        Path rootPath = rootFile.toPath();
        Path basePath = rootPath.resolve(baseDir);
        return basePath.toFile();
    }

    private File getKeystoreFile(final File baseFile, final String keystoreFileName) {
        File keystoreFile = new File(baseFile, keystoreFileName);
        keystoreFile = normalize(keystoreFile);
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
        credentialsFile = normalize(credentialsFile);
        if (Logger.isDebugEnabled()) {
            Logger.debug("Credentials file: " + credentialsFile.getAbsolutePath());
        }
        if (!credentialsFile.exists() || !credentialsFile.isFile()) {
            throw new InvalidUserDataException("Credentials file must be defined");
        }
        return credentialsFile;
    }

    private File normalize(final File file) {
        Path path = file.toPath();
        path = path.normalize();
        return path.toFile();
    }

    private Properties readCredentialsFile(final File credentialsFile) {
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream(credentialsFile)) {
            properties.load(inputStream);
        } catch (IOException ex) {
            throw new InvalidUserDataException("Failed to read credentials file", ex);
        }
        return properties;
    }

}
