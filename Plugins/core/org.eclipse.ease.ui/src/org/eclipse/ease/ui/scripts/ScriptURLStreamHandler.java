package org.eclipse.ease.ui.scripts;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import org.eclipse.core.runtime.Path;
import org.eclipse.ease.ui.repository.IScript;
import org.eclipse.ease.ui.scripts.repository.IRepositoryService;
import org.eclipse.ui.PlatformUI;
import org.osgi.service.url.AbstractURLStreamHandlerService;

public class ScriptURLStreamHandler extends AbstractURLStreamHandlerService {

	public ScriptURLStreamHandler() {
	}

	@Override
	public URLConnection openConnection(URL url) throws IOException {
		final IRepositoryService repositoryService = (IRepositoryService) PlatformUI.getWorkbench().getService(IRepositoryService.class);
		if (repositoryService != null) {
			IScript script = repositoryService.getScript(new Path(url.getHost() + url.getFile()).makeAbsolute().toString());

			if (script != null)
				return new ScriptURLConnection(url, script);

			throw new IOException("\"" + url + "\" not found");
		}

		return null;
	}
}
