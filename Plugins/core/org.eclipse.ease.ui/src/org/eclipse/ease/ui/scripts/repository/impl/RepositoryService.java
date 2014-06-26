/*******************************************************************************
 * Copyright (c) 2014 Christian Pontesegger and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Christian Pontesegger - initial API and implementation
 *******************************************************************************/package org.eclipse.ease.ui.scripts.repository.impl;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IPreferenceNodeVisitor;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.ease.Logger;
import org.eclipse.ease.ui.Activator;
import org.eclipse.ease.ui.preferences.IPreferenceConstants;
import org.eclipse.ease.ui.repository.IEntry;
import org.eclipse.ease.ui.repository.IRepository;
import org.eclipse.ease.ui.repository.IRepositoryFactory;
import org.eclipse.ease.ui.repository.IScript;
import org.eclipse.ease.ui.repository.impl.RepositoryFactoryImpl;
import org.eclipse.ease.ui.scripts.ScriptStorage;
import org.eclipse.ease.ui.scripts.repository.IRepositoryService;
import org.eclipse.ease.ui.scripts.repository.IScriptListener;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.osgi.service.prefs.BackingStoreException;

public class RepositoryService implements IRepositoryService {

	// TODO find a nice delay value here
	static final long UPDATE_URI_INTERVAL = 1000; // update daily
	// TODO find a nice delay value here
	static final long UPDATE_SCRIPT_INTERVAL = 1000; // update hourly

	private static RepositoryService fInstance;

	private static final String CACHE_FILE_NAME = "script.repository";

	// TODO find a nice delay value here
	private static final long DEFAULT_DELAY = 0; // 1 minute
	public static final long UPDATE_STREAM_INTERVAL = 0;

	/**
	 * Get the repository service singleton.
	 * 
	 * @return repository service
	 */
	public static RepositoryService getInstance() {
		if (fInstance == null)
			fInstance = new RepositoryService();

		return fInstance;
	}

	private IRepository fRepository = null;

	private final UpdateRepositoryJob fUpdateJob;
	private final Job fSaveJob = new Job("Save Script Repositories") {

		@Override
		protected IStatus run(final IProgressMonitor monitor) {
			IPath path = Activator.getDefault().getStateLocation().append(CACHE_FILE_NAME);
			File file = path.toFile();

			// Obtain a new resource set
			ResourceSet resSet = new ResourceSetImpl();
			Resource resource = resSet.createResource(URI.createFileURI(file.getAbsolutePath()));
			resource.getContents().add(fRepository);
			try {
				resource.save(Collections.emptyMap());
			} catch (IOException e) {
				Logger.logError("Could not store script repositories");
			}

			return Status.OK_STATUS;
		}
	};

	private final ListenerList fListeners = new ListenerList();
	private final UIIntegrationJob fUiIntegration;

	/**
	 * Initialize the repository service.
	 */
	private RepositoryService() {
		RepositoryFactoryImpl.init();

		// load stored data
		IPath path = Activator.getDefault().getStateLocation().append(CACHE_FILE_NAME);
		File file = path.toFile();
		if (file != null) {

			ResourceSet resourceSet = new ResourceSetImpl();
			Resource resource = resourceSet.createResource(URI.createURI(file.toURI().toString()));
			try {
				resource.load(null);
				fRepository = (IRepository) resource.getContents().get(0);

			} catch (IOException e) {
				// we could not load an existing model, but we will refresh it in a second
			}
		}

		// create repository if empty
		long updateDelay = 0;
		if (fRepository == null) {
			// create an empty repository to start with
			fRepository = IRepositoryFactory.eINSTANCE.createRepository();

			// add default location for stored scripts
			IEntry entry = IRepositoryFactory.eINSTANCE.createEntry();
			entry.setLocation(ScriptStorage.createStorage().getLocation());
			entry.setRecursive(true);
			entry.setDefault(true);
			fRepository.getEntries().add(entry);

		} else {
			// wait for the workspace to be loaded before updating, we have cached data anyway
			updateDelay = DEFAULT_DELAY;
		}

		// apply UI integrations
		fUiIntegration = new UIIntegrationJob(this);
		fUiIntegration.addScripts(getScripts());

		// update repository
		fUpdateJob = new UpdateRepositoryJob(this);
		fUpdateJob.schedule(updateDelay);
	}

	@Override
	public void update(final boolean force) {

		if (force) {
			for (IScript script : getScripts())
				script.setTimestamp(0);
		}

		fUpdateJob.scheduleUpdate(0);
	}

	@Override
	public void updateLocations() {
		// update locations from preferences
		for (IEntry entry : getLocations())
			entry.setUpdatePending(true);

		final IEclipsePreferences rootNode = InstanceScope.INSTANCE.getNode(Activator.PLUGIN_ID);
		try {
			rootNode.accept(new IPreferenceNodeVisitor() {

				@Override
				public boolean visit(final IEclipsePreferences node) throws BackingStoreException {
					if (rootNode.equals(node))
						return true;

					else {
						String location = node.get(IPreferenceConstants.SCRIPT_STORAGE_LOCATION, "");

						boolean found = false;
						for (IEntry entry : getLocations()) {
							if (entry.getLocation().equals(location)) {
								entry.setDefault(node.getBoolean(IPreferenceConstants.SCRIPT_STORAGE_DEFAULT, false));
								entry.setRecursive(node.getBoolean(IPreferenceConstants.SCRIPT_STORAGE_RECURSIVE, false));
								entry.setUpdatePending(false);

								found = true;
								break;
							}
						}

						if (!found) {
							IEntry entry = IRepositoryFactory.eINSTANCE.createEntry();
							entry.setLocation(location);
							entry.setDefault(node.getBoolean(IPreferenceConstants.SCRIPT_STORAGE_DEFAULT, false));
							entry.setRecursive(node.getBoolean(IPreferenceConstants.SCRIPT_STORAGE_RECURSIVE, false));

							fRepository.getEntries().add(entry);
						}

						return false;
					}
				}
			});

			// remove all repositories where update is pending as they are not stored in the preferences
			for (IEntry entry : new HashSet<IEntry>(getLocations())) {
				if (entry.isUpdatePending())
					fRepository.getEntries().remove(entry);
			}

			// we updated our locations, now update all scripts
			update(false);

		} catch (BackingStoreException e) {
			Logger.logError("Could not update script repository.", e);
		}
	}

	IRepository getRepository() {
		return fRepository;
	}

	@Override
	public IScript getScript(final String name) {
		for (IScript script : getRepository().getScripts()) {
			if (name.equals(script.getPath().toString()))
				return script;
		}

		return null;
	}

	/**
	 * Trigger delayed save action. Store the repository to disk after a given delay.
	 */
	void save() {
		fSaveJob.cancel();
		fSaveJob.schedule(5000);
	}

	private void notifyListeners(final ScriptRepositoryEvent scriptRepositoryEvent) {
		for (Object listener : fListeners.getListeners())
			((IScriptListener) listener).notify(scriptRepositoryEvent);
	}

	@Override
	public Collection<IScript> getScripts() {
		return Collections.unmodifiableCollection(getRepository().getScripts());
	}

	@Override
	public void addScriptListener(final IScriptListener listener) {
		fListeners.add(listener);
	}

	@Override
	public void removeScriptListener(final IScriptListener listener) {
		fListeners.remove(listener);
	}

	@Override
	public Collection<IEntry> getLocations() {
		return Collections.unmodifiableCollection(fRepository.getEntries());
	}

	void removeScript(final IScript script) {
		fRepository.getScripts().remove(script);
		notifyListeners(new ScriptRepositoryEvent(script, ScriptRepositoryEvent.DELETE, null));
	}

	void updateScript(final IScript script, final Map<String, String> parameters) {
		// TODO update listeners (only if parameters changed)

		// store current parameters
		Map<String, String> oldParameters = script.getParameters();

		script.getScriptParameters().clear();
		script.getScriptParameters().putAll(parameters);

		// get new parameters
		Map<String, String> newParameters = script.getParameters();

		// now look for changes

		// first find removed parameters
		HashSet<String> deletedKeys = new HashSet<String>(oldParameters.keySet());
		deletedKeys.removeAll(newParameters.keySet());

		// now remove parameters that were not changed
		for (String key : oldParameters.keySet()) {
			if (oldParameters.get(key).equals(newParameters.get(key)))
				newParameters.remove(key);
		}

		// re-add deleted keys with value set to null
		for (String key : deletedKeys)
			newParameters.put(key, null);

		if (!newParameters.isEmpty()) {
			// some parameters changed
			notifyListeners(new ScriptRepositoryEvent(script, ScriptRepositoryEvent.PARAMETER_CHANGE, newParameters));
		}

		script.setUpdatePending(false);

		System.out.println("RepositoryService.updateScript() -> scheduling UI");
		fUiIntegration.schedule();
	}

	void addScript(final IScript script) {
		script.setUpdatePending(false);

		notifyListeners(new ScriptRepositoryEvent(script, ScriptRepositoryEvent.ADD, null));
	}

	@Override
	public IEntry getDefaultLocation() {
		for (IEntry entry : getLocations())
			if (entry.isDefault())
				return entry;

		return null;
	}
}
