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

import org.slf4j.LoggerFactory;

/**
 * The logger.
 *
 * @author Dmitry Shapovalov
 */
public final class Logger {

    private static final org.slf4j.Logger LOGGER_INSTANCE = LoggerFactory.getLogger(CredentialsGradlePlugin.TASK_NAME);

    private Logger() {
        super();
    }

    /**
     * Check if debug level is enabled.
     *
     * @return true, if debug level is enabled.
     */
    public static boolean isDebugEnabled() {
        return LOGGER_INSTANCE.isDebugEnabled();
    }

    /**
     * Log the message with debug level.
     *
     * @param message the message.
     */
    public static void debug(final String message) {
        LOGGER_INSTANCE.debug(message);
    }

    /**
     * Check if info level is enabled.
     *
     * @return true, if info level is enabled.
     */
    public static boolean isInfoEnabled() {
        return LOGGER_INSTANCE.isInfoEnabled();
    }

    /**
     * Log the message with info level.
     *
     * @param message the message.
     */
    public static void info(final String message) {
        LOGGER_INSTANCE.info(message);
    }

    /**
     * Check if error level is enabled.
     *
     * @return true, if error level is enabled.
     */
    public static boolean isErrorEnabled() {
        return LOGGER_INSTANCE.isErrorEnabled();
    }

    /**
     * Log the message with error level.
     *
     * @param message the message.
     */
    public static void error(final String message) {
        LOGGER_INSTANCE.error(message);
    }

    /**
     * Log the message with error level.
     *
     * @param message   the message.
     * @param throwable the throwable.
     */
    public static void error(final String message, final Throwable throwable) {
        LOGGER_INSTANCE.error(message, throwable);
    }

}
