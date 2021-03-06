/*******************************************************************************
 * Copyright (c) 2013 Atos
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Arthur Daussy - initial implementation
 *******************************************************************************/
package org.eclipse.ease.ui.e4.handler;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.ease.storedscript.storedscript.IStoredScript;
import org.eclipse.ease.ui.ScriptGraphService;
import org.eclipse.ease.ui.scriptuigraph.Node;
import org.eclipse.ease.ui.scriptuigraph.StoredScriptUI;
import org.eclipse.ease.ui.utils.ScriptLauncherUtils;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Display;

public class ExecuteScriptHandler {

	@Execute
	public void execute(@Named("org.eclipse.ease.ui.e4.commandparameter.storedScript") String id, @Optional @Named(IServiceConstants.ACTIVE_SELECTION) Object selection) {
		System.out.println(id);
		Node script = ScriptGraphService.getInstance().getNodeFromFragment(id);
		if(!(script instanceof StoredScriptUI)) {
			ErrorDialog.openError(Display.getDefault().getActiveShell(), "Error dinding stored script", "Unable to retreive the stored script UI", null);
			return;
		}
		StoredScriptUI scriptUI = (StoredScriptUI)script;
		IStoredScript storedScript = scriptUI.getScript();
		if(storedScript == null) {
			ErrorDialog.openError(Display.getDefault().getActiveShell(), "Error dinding stored script", "Unable to retreive the stored script", null);
			return;
		}

		ScriptLauncherUtils.launchStoredScript(storedScript);

	}


}
