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
import java.nio.file.Path;

import org.gradle.api.Action;
import org.gradle.api.InvalidUserDataException;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.plugins.ExtraPropertiesExtension;

import ru.d_shap.gradle.plugin.credentials.configuration.ExtensionConfiguration;

/**
 * Credentials gradle configuration.
 *
 * @author Dmitry Shapovalov
 */
final class CredentialsGradleConfiguration implements Action<Project> {

    private final ExtensionConfiguration _extensionConfiguration;

    CredentialsGradleConfiguration(final ExtensionConfiguration extensionConfiguration) {
        super();
        _extensionConfiguration = extensionConfiguration;
    }

    @Override
    public void execute(final Project project) {
        if (Logger.isInfoEnabled()) {
            Logger.info("Start processing credentials");
        }

        ExtensionContainer extensionContainer = project.getExtensions();
        ExtraPropertiesExtension extraProperties = extensionContainer.getExtraProperties();

        File keystoreFile = getKeystoreFile();
        extraProperties.set("keystoreFile", keystoreFile);

        File credentialsFile = getCredentialsFile();
        extraProperties.set("credentialsFile", credentialsFile);

        if (Logger.isInfoEnabled()) {
            Logger.info("Finish processing credentials");
        }
    }

    private File getKeystoreFile() {
        File baseDir = _extensionConfiguration.getBaseDir();
        String keystoreFileName = _extensionConfiguration.getKeystoreFileName();
        File keystoreFile = new File(baseDir, keystoreFileName);
        keystoreFile = normalize(keystoreFile);
        if (Logger.isDebugEnabled()) {
            Logger.debug("Keystore file: " + keystoreFile.getAbsolutePath());
        }
        if (!keystoreFile.exists() || !keystoreFile.isFile()) {
            throw new InvalidUserDataException("Keystore file must be defined");
        }
        return keystoreFile;
    }

    private File getCredentialsFile() {
        File baseDir = _extensionConfiguration.getBaseDir();
        String credentialsFileName = _extensionConfiguration.getCredentialsFileName();
        File credentialsFile = new File(baseDir, credentialsFileName);
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

}
