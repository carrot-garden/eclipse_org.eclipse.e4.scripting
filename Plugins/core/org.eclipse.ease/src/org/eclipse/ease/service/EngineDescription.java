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
package org.eclipse.ease.service;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.ease.IScriptEngine;
import org.eclipse.ease.Logger;
import org.eclipse.ui.PlatformUI;

public class EngineDescription {

	private static final String CLASS = "class";

	private static final String PRIORITY = "priority";

	private static final String BINDING = "binding";

	private static final String TYPE = "scriptType";

	private static final String ID = "id";

	private static final String NAME = "name";

	private final IConfigurationElement mConfigurationElement;

	private List<ScriptType> types = null;

	public EngineDescription(IConfigurationElement configurationElement) {
		mConfigurationElement = configurationElement;
	}

	// public Collection<String> getSupportedScriptTypesNames() {
	// return Collections2.transform(getSupportedScriptTypes(), new ScriptType.ToScriptType());
	// }
	//
	public List<ScriptType> getSupportedScriptTypes() {
		if (types == null) {
			types = new ArrayList<ScriptType>();
			final IScriptService scriptService = (IScriptService) PlatformUI.getWorkbench().getService(IScriptService.class);

			for (final IConfigurationElement child : mConfigurationElement.getChildren(BINDING)) {
				String scriptTypeID = child.getAttribute(TYPE);

				if (scriptTypeID != null) {
					ScriptType scriptType = scriptService.getAvailableScriptTypes().get(scriptTypeID);
					if (scriptType == null)
						Logger.logError("Unknow scriptType " + scriptTypeID);
					else
						types.add(scriptType);
				}
			}
		}

		return types;
	}

	public int getPriority() {
		try {
			return Integer.parseInt(mConfigurationElement.getAttribute(PRIORITY));
		} catch (Throwable e) {
			// ignore
		}

		return 0;
	}

	public IScriptEngine createEngine() {
		try {
			Object object = mConfigurationElement.createExecutableExtension(CLASS);
			if (object instanceof IScriptEngine)
				return (IScriptEngine) object;
		} catch (CoreException e) {
			Logger.logError("Could not create script engine: " + getID(), e);
		}

		return null;
	}

	public String getID() {
		return mConfigurationElement.getAttribute(ID);
	}

	public String getName() {
		String name = mConfigurationElement.getAttribute(NAME);
		return (name != null) ? name : getID();
	}

	public boolean supports(String scriptType) {
		for (ScriptType type : getSupportedScriptTypes()) {
			if (type.getName().equals(scriptType))
				return true;
		}
		return false;
	}
}
