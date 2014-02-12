package org.eclipse.ease.ui.macro;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import org.eclipse.ease.ui.repository.IRepositoryService;
import org.eclipse.ease.ui.repository.IScript;
import org.eclipse.ui.PlatformUI;
import org.osgi.service.url.AbstractURLStreamHandlerService;

public class ScriptURLStreamHandler extends AbstractURLStreamHandlerService {

	public ScriptURLStreamHandler() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public URLConnection openConnection(URL u) throws IOException {
		final IRepositoryService repositoryService = (IRepositoryService) PlatformUI.getWorkbench().getService(IRepositoryService.class);
		if (repositoryService != null) {
			IScript script = repositoryService.getScript(u.getHost() + u.getFile());

			if (script != null)
				return new ScriptURLConnection(u, script);

			throw new IOException("\"" + u.toString() + "\" not found");
		}

		return null;
	}
}
