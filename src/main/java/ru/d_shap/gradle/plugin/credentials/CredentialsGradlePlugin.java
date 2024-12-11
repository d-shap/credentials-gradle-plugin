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

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionContainer;

import ru.d_shap.gradle.plugin.credentials.configuration.ExtensionConfiguration;

/**
 * Credentials gradle plugin.
 *
 * @author Dmitry Shapovalov
 */
public class CredentialsGradlePlugin implements Plugin<Project> {

    static final String TASK_NAME = "сredentials";

    static final String EXTENSION_NAME = "сredentials";

    /**
     * Create new object.
     */
    public CredentialsGradlePlugin() {
        super();
    }

    @Override
    public void apply(final Project project) {
        ExtensionConfiguration extensionConfiguration = getExtensionConfiguration(project);

        addProjectAction(project, extensionConfiguration);
    }

    private ExtensionConfiguration getExtensionConfiguration(final Project project) {
        ExtensionContainer extensions = project.getExtensions();
        return extensions.create(EXTENSION_NAME, ExtensionConfiguration.class, project);
    }

    private void addProjectAction(final Project project, final ExtensionConfiguration extensionConfiguration) {
        Action<Project> action = new CredentialsGradleConfiguration(extensionConfiguration);
        project.afterEvaluate(action);
    }

}
