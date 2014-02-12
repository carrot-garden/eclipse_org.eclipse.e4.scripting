package org.eclipse.ease.ui.scripts;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.eclipse.ease.ui.repository.IScript;

public class ScriptURLConnection extends URLConnection {

	private final IScript fScript;

	public ScriptURLConnection(final URL url, final IScript script) {
		super(url);

		fScript = script;
	}

	@Override
	public void connect() throws IOException {
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return fScript.getInputStream();
	}
}
