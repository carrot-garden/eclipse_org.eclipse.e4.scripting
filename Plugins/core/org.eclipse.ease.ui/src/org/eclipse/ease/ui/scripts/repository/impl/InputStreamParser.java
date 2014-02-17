package org.eclipse.ease.ui.scripts.repository.impl;

import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

import org.eclipse.ease.IHeaderParser;
import org.eclipse.ease.service.ScriptType;
import org.eclipse.ease.ui.repository.IScript;

public class InputStreamParser {

	private final RepositoryService fRepositoryService;

	public InputStreamParser(RepositoryService repositoryService) {
		fRepositoryService = repositoryService;
	}

	// public void parse(InputStream stream, IEntry entry) {
	// // check if entry directly links to a script
	// String fileExtension = new Path(entry.getUri()).getFileExtension();
	// if (fileExtension != null) {
	// final IScriptService scriptService = (IScriptService) PlatformUI.getWorkbench().getService(IScriptService.class);
	// ScriptType type = scriptService.getScriptType(fileExtension);
	// if (type != null) {
	// // this is very likely a script file
	//
	// // create uri
	// String uri = entry.getUri();
	// IScript script = fRepositoryService.getScript(URI.createURI(uri));
	//
	// if (script == null) {
	// script = IRepositoryFactory.eINSTANCE.createScript();
	// script.setEntry(entry);
	// script.setUri(uri);
	// }
	//
	// if ((script.getTimestamp() + RepositoryService.UPDATE_STREAM_INTERVAL) < System.currentTimeMillis()) {
	// // we need to update this location
	// }
	//
	// // parse entry
	// // TODO continue here
	//
	// }
	// }
	// }

	protected RepositoryService getRepositoryService() {
		return fRepositoryService;
	}

	protected IScript getScriptByLocation(final String location) {
		for (IScript script : getRepositoryService().getScripts()) {
			if (script.getLocation().equals(location))
				return script;
		}

		return null;
	}

	protected Map<String, String> extractParameters(ScriptType type, InputStream stream) {
		IHeaderParser parser = type.getHeaderParser();
		if (parser != null)
			return parser.parser(stream);

		return Collections.emptyMap();
	}

	protected ScriptType getScriptType(InputStream contents) {
		// TODO Auto-generated method stub
		return null;
	}
}
