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
package ru.d_shap.gradle.plugin.credentials.configuration;

import java.io.File;

import javax.inject.Inject;

import org.gradle.api.Project;

/**
 * The extension configuration.
 *
 * @author Dmitry Shapovalov
 */
public class ExtensionConfiguration {

    private final Project _project;

    private File _baseDir;

    private String _keystoreFileName;

    private String _credentialsFileName;

    /**
     * Create new object.
     *
     * @param project the project.
     */
    @Inject
    public ExtensionConfiguration(final Project project) {
        super();
        _project = project;
        _baseDir = null;
        _keystoreFileName = null;
        _credentialsFileName = null;
    }

    /**
     * Get the base directory.
     *
     * @return the base directory.
     */
    public File getBaseDir() {
        return _baseDir;
    }

    /**
     * Set the base directory.
     *
     * @param baseDir the base directory.
     */
    public void baseDir(final String baseDir) {
        File rootDir = _project.getRootDir();
        File rootFile = rootDir.getAbsoluteFile();
        _baseDir = new File(rootFile, baseDir);
    }

    /**
     * Get the keystore file name.
     *
     * @return the keystore file name.
     */
    public String getKeystoreFileName() {
        return _keystoreFileName;
    }

    /**
     * Set the keystore file name.
     *
     * @param keystoreFileName the keystore file name.
     */
    public void keystoreFileName(final String keystoreFileName) {
        _keystoreFileName = keystoreFileName;
    }

    /**
     * Get the credentials file name.
     *
     * @return the credentials file name.
     */
    public String getCredentialsFileName() {
        return _credentialsFileName;
    }

    /**
     * Set the credentials file name.
     *
     * @param credentialsFileName the credentials file name.
     */
    public void credentialsFileName(final String credentialsFileName) {
        _credentialsFileName = credentialsFileName;
    }

}
