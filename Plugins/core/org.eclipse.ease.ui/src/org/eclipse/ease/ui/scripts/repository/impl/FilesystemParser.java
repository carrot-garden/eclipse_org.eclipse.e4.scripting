package org.eclipse.ease.ui.scripts.repository.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import org.eclipse.ease.Logger;
import org.eclipse.ease.service.IScriptService;
import org.eclipse.ease.service.ScriptType;
import org.eclipse.ease.ui.repository.IEntry;
import org.eclipse.ease.ui.repository.IRepositoryFactory;
import org.eclipse.ease.ui.repository.IScript;
import org.eclipse.ui.PlatformUI;

public class FilesystemParser extends InputStreamParser {

	public FilesystemParser(RepositoryService repositoryService) {
		super(repositoryService);
	}

	public void parse(final File file, final IEntry entry) {
		if (file.isDirectory()) {
			// containment, parse children
			for (File child : file.listFiles()) {
				if ((child.isFile()) || (entry.isRecursive()))
					parse(child, entry);
			}

		} else {
			// try to locate registered script
			String location = file.toURI().toString();
			IScript script = getScriptByLocation(location);

			try {
				if (script == null) {
					// new script detected
					script = IRepositoryFactory.eINSTANCE.createScript();
					script.setEntry(entry);
					script.setLocation(location);

					ScriptType scriptType = getScriptType(file);
					Map<String, String> parameters = extractParameters(scriptType, new FileInputStream(file));
					script.getScriptParameters().clear();
					script.getScriptParameters().putAll(parameters);

					script.setTimestamp(file.lastModified());

					getRepositoryService().addScript(script);

				} else if (script.getTimestamp() != file.lastModified()) {
					// script needs updating
					ScriptType scriptType = getScriptType(file);
					Map<String, String> parameters = extractParameters(scriptType, new FileInputStream(file));

					script.setTimestamp(file.lastModified());

					getRepositoryService().updateScript(script, parameters);

				} else
					// script is up to date
					script.setUpdatePending(false);

			} catch (FileNotFoundException e) {
				// cannot find file
				Logger.logError("Cannot locate script file: " + file, e);
			}
		}
	}

	private ScriptType getScriptType(File file) throws FileNotFoundException {
		String name = file.getName();
		if (name.contains(".")) {
			String extension = name.substring(name.lastIndexOf('.') + 1);

			// locate header parser
			final IScriptService scriptService = (IScriptService) PlatformUI.getWorkbench().getService(IScriptService.class);
			for (ScriptType scriptType : scriptService.getAvailableScriptTypes().values()) {
				if (extension.equalsIgnoreCase(scriptType.getDefaultExtension()))
					return scriptType;
			}
		}

		return getScriptType(new FileInputStream(file));
	}
}
