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
package org.eclipse.ease.modules;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.ease.Activator;
import org.eclipse.ease.service.IScriptService;
import org.eclipse.ui.PlatformUI;
import org.osgi.service.prefs.Preferences;

public class ModuleDefinition {

	/** Module name parameter. */
	private static final String NAME = "name";

	/** Module class parameter. */
	private static final String CLASS = "class";

	/** Module visibility parameter. */
	private static final String VISIBLE = "visible";

	/** Module category parameter. */
	private static final String CATEGORY = "category";

	/** Module dependency node. */
	private static final String DEPENDENCY = "dependency";

	/** Module dependency parameter name. */
	private static final String CONFIG_DEPENDENCY_ID = "module";

	/** Module id parameter name. */
	private static final String ID = "id";

	/** Main configuration element for module. */
	private final IConfigurationElement fConfig;

	private IPath fPath = null;

	public ModuleDefinition(final IConfigurationElement config) {
		fConfig = config;
	}

	public String getName() {
		return fConfig.getAttribute(NAME);
	}

	/**
	 * Get a collection of module dependencies. Collection contains ids of required modules.
	 * 
	 * @return collection of required module ids
	 */
	public Collection<String> getDependencies() {
		Set<String> dependencies = new HashSet<String>();

		for (final IConfigurationElement element : fConfig.getChildren(DEPENDENCY))
			dependencies.add(element.getAttribute(CONFIG_DEPENDENCY_ID));

		return dependencies;
	}

	public String getModuleClassName() {
		return fConfig.getAttribute(CLASS);
	}

	public Object createModuleInstance() {
		try {
			return fConfig.createExecutableExtension(CLASS);
		} catch (CoreException e) {
			// could not create class, ignore
		}

		return null;
	}

	public boolean isVisible() {
		IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(Activator.PLUGIN_ID);
		Preferences node = prefs.node("modules");
		return node.getBoolean(getPath().toString(), Boolean.parseBoolean(fConfig.getAttribute(VISIBLE)));
	}

	/**
	 * Get the full module name. The full name consists of optional parent categories and the module name itself.
	 * 
	 * @return absolute path of this module definition
	 */
	public IPath getPath() {
		if (fPath == null) {
			final IScriptService scriptService = (IScriptService) PlatformUI.getWorkbench().getService(IScriptService.class);

			fPath = new Path(getName());

			String categoryID = fConfig.getAttribute(CATEGORY);
			while (categoryID != null) {
				ModuleCategoryDefinition definition = scriptService.getAvailableModuleCategories().get(categoryID);
				if (definition != null) {
					fPath = new Path(definition.getName()).append(fPath);
					categoryID = definition.getParentId();
				}
			}

			fPath = fPath.makeAbsolute();
		}

		return fPath;
	}

	public String getId() {
		return (fConfig.getAttribute(ID) != null) ? fConfig.getAttribute(ID) : "";
	}
}
