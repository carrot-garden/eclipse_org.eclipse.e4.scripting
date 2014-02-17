package org.eclipse.ease.ui.scripts.repository.impl;

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
import org.eclipse.ease.Logger;
import org.eclipse.ease.ui.Activator;
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

public class RepositoryService implements IRepositoryService {

	// TODO find a nice delay value here
	static final long UPDATE_URI_INTERVAL = 1000; // update daily
	// TODO find a nice delay value here
	static final long UPDATE_SCRIPT_INTERVAL = 1000; // update hourly

	private static RepositoryService fInstance;

	private static final String CACHE_FILE_NAME = "script.repository";

	// TODO find a nice delay value here
	private static final long DEFAULT_DELAY = 600; // 1 minute
	public static final long UPDATE_STREAM_INTERVAL = 0;

	public static RepositoryService getInstance() {
		if (fInstance == null)
			fInstance = new RepositoryService();

		return fInstance;
	}

	private IRepository fRepository = null;

	private final UpdateJob fUpdateJob;
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
	private final UIIntegration fUiIntegration;

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
			entry.setHidden(true);
			fRepository.getEntries().add(entry);

		} else {
			// wait for the workspace to be loaded before updating, we have cached data anyway
			updateDelay = DEFAULT_DELAY;
		}

		// apply UI integrations
		fUiIntegration = new UIIntegration(this);
		System.out.println("RepositoryService() -> scheduling UI");
		fUiIntegration.schedule();

		// update repository
		fUpdateJob = new UpdateJob(this);
		fUpdateJob.schedule(updateDelay);
	}

	@Override
	public void update(boolean force) {
		if (force) {
			for (IScript script : getScripts())
				script.setTimestamp(0);
		}

		fUpdateJob.scheduleUpdate(0);
	}

	IRepository getRepository() {
		return fRepository;
	}

	@Override
	public IScript getScript(final String name) {
		for (IScript script : getRepository().getScripts()) {
			if (name.equals(script.getFullName()))
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

	@Override
	public void removeLocation(final IEntry entry) {
		fRepository.getEntries().remove(entry);
		// TODO use better interval
		fUpdateJob.scheduleUpdate(1000);
		save();
	}

	@Override
	public void addLocation(final IEntry entry) {
		fRepository.getEntries().add(entry);
		// TODO use better interval
		fUpdateJob.scheduleUpdate(1000);
		save();
	}

	void removeScript(final IScript script) {
		fRepository.getScripts().remove(script);
		notifyListeners(new ScriptRepositoryEvent(script, ScriptRepositoryEvent.DELETE, null));
	}

	void updateScript(IScript script, Map<String, String> parameters) {
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

		if (!getRepository().getScripts().contains(script)) {
			getRepository().getScripts().add(script);
			notifyListeners(new ScriptRepositoryEvent(script, ScriptRepositoryEvent.ADD, null));
		}
	}

}
