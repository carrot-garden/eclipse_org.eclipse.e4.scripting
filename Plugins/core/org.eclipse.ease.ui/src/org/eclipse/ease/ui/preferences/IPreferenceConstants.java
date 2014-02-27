/*******************************************************************************
 * Copyright (c) 2013 Christian Pontesegger and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Christian Pontesegger - initial API and implementation
 *******************************************************************************/
package org.eclipse.ease.ui.preferences;

import org.eclipse.ease.ui.Activator;

/**
 * Constant definitions for plug-in preferences.
 */
public interface IPreferenceConstants {

	String VALUE_OUTPUT_CONSOLE = "Console";
	String VALUE_OUTPUT_SHELL = "Shell";
	String VALUE_OUTPUT_NONE = "None";

	String SHELL_BASE = "org.eclipse.ease.shell.prefs";

	String INIT_COMMANDS = "org.eclipse.ease.shell.prefs.initCommands";

	String CONSOLE_BASE = "org.eclipse.ease.console.prefs";
	String CONSOLE_OPEN_ON_OUT = "consoleOpenOnOut";
	String CONSOLE_OPEN_ON_ERR = "consoleOpenOnErr";

	String LOCATIONS = "constants";
	String SCRIPT_STORAGE_LOCATION = "scriptStorageLocation";
	String DEFAULT_SCRIPT_STORAGE_LOCATION = Activator.getDefault().getStateLocation().append("recordedScripts").toFile().toURI().toString();

	String NODE_SHELL = "shell";

	String SHELL_HISTORY_LENGTH = "shellHistoryLength";
	int DEFAULT_SHELL_HISTORY_LENGTH = 20;
	String SHELL_AUTOFOCUS = "shellAutoFocus";
	boolean DEFAULT_SHELL_AUTOFOCUS = true;
	String SHELL_KEEP_COMMAND = "shellKeepCommand";
	boolean DEFAULT_SHELL_KEEP_COMMAND = false;
	String SHELL_STARTUP = "shellStartup";
}
