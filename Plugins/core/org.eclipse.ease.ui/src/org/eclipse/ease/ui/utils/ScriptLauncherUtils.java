/**
 *   Copyright (c) 2013 Atos
 *   All rights reserved. This program and the accompanying materials
 *   are made available under the terms of the Eclipse Public License v1.0
 *   which accompanies this distribution, and is available at
 *   http://www.eclipse.org/legal/epl-v10.html
 *  
 *   Contributors:
 *       Arthur Daussy - initial implementation
 */
package org.eclipse.ease.ui.utils;

import java.io.IOException;

import org.eclipse.ease.Activator;
import org.eclipse.ease.IScriptEngine;
import org.eclipse.ease.service.ScriptService;
import org.eclipse.ease.ui.console.ScriptConsole;
import org.eclipse.ease.ui.metadata.UIMetadataUtils;
import org.eclipse.ease.log.Logger;
import org.eclipse.ease.storedscript.storedscript.IStoredScript;
import org.eclipse.ease.storedscript.storedscript.ScriptType;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Display;


public class ScriptLauncherUtils {

	public static void launchStoredScript(IStoredScript script) {
		ScriptType scriptType = script.getScriptType();

		IScriptEngine engine = ScriptService.getInstance().createEngine(scriptType.getType());
		if(engine == null) {
			String message = "Unable to find a script engine for script " + script.getUri();
			ErrorDialog.openError(Display.getDefault().getActiveShell(), "No engine found", message, Logger.createErrorStatus(message, Activator.PLUGIN_ID));
			return;
		}
		if(UIMetadataUtils.hasToBeLaunchInUI(script)) {
			engine.setIsUI(true);
		}
		if(UIMetadataUtils.generateCodeInjectionFile(script)) {
			engine.addExecutionListener(new EffectiveScriptGenerator());
		}
		ScriptConsole console = ScriptConsole.create(engine.getName() + ": " + script.getUri(), engine);
		engine.setOutputStream(console.getOutputStream());
		engine.setErrorStream(console.getErrorStream());
		engine.setTerminateOnIdle(true);
		try {
			engine.executeAsync(script.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			Logger.logError(e.getMessage());
		}
		engine.schedule();
	}

}
