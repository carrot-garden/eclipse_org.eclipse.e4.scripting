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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ease.AbstractScriptEngine;
import org.eclipse.ease.EngineDescription;
import org.eclipse.ease.IScriptEngine;
import org.eclipse.ease.IScriptEngineLaunchExtension;
import org.eclipse.ease.IScriptService;
import org.eclipse.ease.ScriptType;
import org.eclipse.ease.modules.IModuleWrapper;
import org.eclipse.ease.modules.ModuleDefinition;

public class ScriptService implements IScriptService {

	private static final String ENGINE = "engine";

	private static final String ENGINE_ID = "engineID";

	private static final String EXTENSION_FILE = "extension_file";

	private static final Object EXTENSION_MODULE = "module";

	private static final String EXTENSION_LANGUAGE_ID = "org.eclipse.ease.language";

	private static final String EXTENSION_MODULES_ID = "org.eclipse.ease.modules";

	private static final String LAUNCH_EXTENSION = "launchExtension";

	private static ScriptService mInstance = null;

	private static final String MODULE_WRAPPER = "moduleWrapper";

	private static final String TYPE = "type";

	public static ScriptService getInstance() {
		if (mInstance == null)
			mInstance = new ScriptService();

		return mInstance;
	}

	public static List<IScriptEngineLaunchExtension> getLaunchExtensions(final String engineID) {
		final List<IScriptEngineLaunchExtension> extensions = new LinkedList<IScriptEngineLaunchExtension>();

		final IConfigurationElement[] config = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_LANGUAGE_ID);

		for (final IConfigurationElement e : config) {
			try {
				if (LAUNCH_EXTENSION.equals(e.getName())) {
					if (e.getAttribute(ENGINE_ID).equals(engineID)) {
						final Object extension = e.createExecutableExtension("class");
						if (extension instanceof IScriptEngineLaunchExtension)
							extensions.add((IScriptEngineLaunchExtension) extension);
					}
				}
			} catch (final InvalidRegistryObjectException e1) {
			} catch (final CoreException e1) {
			}
		}

		return extensions;
	}

	protected Map<String, ModuleDefinition> mAvailableModules = null;

	private Map<String, EngineDescription> mEngineDescriptions = null;

	protected Map<String, IModuleWrapper> mModuleWrappers = null;

	protected Map<String, ScriptType> mScriptTypes = null;

	private ScriptService() {
	}

	private IScriptEngine createEngine(final EngineDescription description) {
		try {
			Object engine = description.createEngine();

			if (engine instanceof IScriptEngine) {
				// configure engine
				if (engine instanceof AbstractScriptEngine)
					((AbstractScriptEngine) engine).setEngineDescription(description);

				// engine loaded, now load launch extensions
				for (final IScriptEngineLaunchExtension extension : getLaunchExtensions(((IScriptEngine) engine).getID()))
					extension.createEngine((IScriptEngine) engine);

				return (IScriptEngine) engine;
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public IScriptEngine createEngine(final String scriptType) {
		List<EngineDescription> engines = new ArrayList<EngineDescription>(getEngines());

		// sort by priority (highest first)
		Collections.sort(engines, new Comparator<EngineDescription>() {

			@Override
			public int compare(final EngineDescription o1, final EngineDescription o2) {
				return o2.getPriority() - o1.getPriority();
			}
		});

		// return first engine where ID matches or (in case no ID is provided)
		// scriptType matches
		for (EngineDescription description : engines) {
			if (description.getSupportedScriptTypesNames().contains(scriptType)) {
				// engine found, launch
				IScriptEngine engine = createEngine(description);
				if (engine != null)
					return engine;

				// we could not create engine for some reason, try next one
			}
		}

		return null;
	}

	@Override
	public IScriptEngine createEngineByID(final String engineID) {
		EngineDescription engineDescription = getEngineDescriptions().get(engineID);
		if (engineDescription != null) {
			return createEngine(engineDescription);
		}

		return null;
	}

	protected Map<String, IModuleWrapper> getAllModuleWrapper() {
		if (mModuleWrappers == null) {
			mModuleWrappers = new HashMap<String, IModuleWrapper>();
			final IConfigurationElement[] config = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_LANGUAGE_ID);

			for (final IConfigurationElement e : config) {
				try {
					if (MODULE_WRAPPER.equals(e.getName())) {
						final Object extension = e.createExecutableExtension("class");
						String engineID = e.getAttribute(ENGINE_ID);
						if ((extension instanceof IModuleWrapper) && (engineID != null)) {
							if (mModuleWrappers.containsKey(engineID)) {
								/*
								 * TODO should log an error instead of print err. Need activator
								 */
								System.err.println("The engine id " + engineID + " is already used");
							}
							mModuleWrappers.put(engineID, (IModuleWrapper) extension);
						}
					}
				} catch (final InvalidRegistryObjectException e1) {
				} catch (final CoreException e1) {
				}
			}
		}
		return mModuleWrappers;
	}

	@Override
	public Map<String, ModuleDefinition> getAvailableModules() {
		if (mAvailableModules == null) {
			mAvailableModules = new HashMap<String, ModuleDefinition>();
			final IConfigurationElement[] config = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_MODULES_ID);
			for (final IConfigurationElement e : config) {
				if (e.getName().equals(EXTENSION_MODULE)) {
					// module extension detected
					ModuleDefinition definition = new ModuleDefinition(e);
					mAvailableModules.put(definition.getName(), definition);
				}
			}
		}
		return mAvailableModules;
	}

	@Override
	public Collection<EngineDescription> getEngines() {
		return getEngineDescriptions().values();
	}

	protected Map<String, EngineDescription> getEngineDescriptions() {
		if (mEngineDescriptions == null) {
			mEngineDescriptions = new HashMap<String, EngineDescription>();
			final IConfigurationElement[] config = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_LANGUAGE_ID);

			for (final IConfigurationElement e : config) {
				if (ENGINE.equals(e.getName())) {
					EngineDescription engine = new EngineDescription(e);
					mEngineDescriptions.put(engine.getID(), engine);
				}
			}
		}
		return mEngineDescriptions;
	}

	public Set<ScriptType> getHandleScriptType() {
		Set<ScriptType> result = new HashSet<ScriptType>();
		for (EngineDescription desc : getEngineDescriptions().values()) {
			for (ScriptType scriptType : desc.getSupportedScriptTypes()) {
				result.add(scriptType);
			}
		}
		return result;
	}

	public Map<String, ScriptType> getKownSwriptType() {
		if (mScriptTypes == null) {
			mScriptTypes = new HashMap<String, ScriptType>();
			final IConfigurationElement[] config = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_LANGUAGE_ID);

			for (final IConfigurationElement e : config) {
				if ("scriptType".equals(e.getName())) {
					String typeAttr = e.getAttribute(TYPE);
					if (typeAttr != null) {
						ScriptType type = new ScriptType();
						type.setScritpType(typeAttr);
						String extension = e.getAttribute(EXTENSION_FILE);
						if (extension != null) {
							type.setExtension(extension);
						}
						mScriptTypes.put(type.getScritpType(), type);
					}
				}
			}
		}
		return mScriptTypes;
	}

	@Override
	public IModuleWrapper getModuleWrapper(final String engineID) {
		return getAllModuleWrapper().get(engineID);
	}
}
