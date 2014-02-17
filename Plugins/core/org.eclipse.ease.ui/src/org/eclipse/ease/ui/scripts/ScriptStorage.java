package org.eclipse.ease.ui.scripts;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.ease.ui.Activator;
import org.eclipse.ease.ui.preferences.PreferenceConstants;
import org.eclipse.ease.ui.scripts.repository.IRepositoryService;
import org.eclipse.ui.PlatformUI;

public abstract class ScriptStorage {

	public static ScriptStorage createStorage() {
		IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(Activator.PLUGIN_ID);
		String location = prefs.get(PreferenceConstants.SCRIPT_STORAGE_LOCATION, PreferenceConstants.DEFAULT_SCRIPT_STORAGE_LOCATION);

		if (location.startsWith("workspace://"))
			return new WorkspaceScriptStorage(location);

		return new FileScriptStorage(location);
	}

	private final String fLocation;

	protected ScriptStorage(String location) {
		fLocation = location;
	}

	public boolean exists(String name) {
		final IRepositoryService repositoryService = (IRepositoryService) PlatformUI.getWorkbench().getService(IRepositoryService.class);
		return repositoryService.getScript(name) != null;
	}

	public boolean store(String name, String content) {
		Path path = new Path(name);
		if (createPath(path.removeLastSegments(1))) {
			if (createFile(path, content)) {
				// trigger repository update
				// TODO trigger update on changed location only
				final IRepositoryService repositoryService = (IRepositoryService) PlatformUI.getWorkbench().getService(IRepositoryService.class);
				repositoryService.update(false);

				return true;
			}
		}

		return false;
	}

	public String getLocation() {
		return fLocation;
	}

	protected abstract boolean createFile(Path path, String content);

	protected abstract boolean createPath(IPath path);
}
