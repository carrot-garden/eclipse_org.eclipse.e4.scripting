package org.eclipse.ease.ui.scripts;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.ease.Logger;

public class FileScriptStorage extends ScriptStorage {

	public FileScriptStorage(String location) {
		super(location);
	}

	@Override
	protected boolean createFile(Path path, String content) {
		File file = new File(getLocation() + "/" + path);
		if (!file.exists()) {
			try {
				if (file.createNewFile()) {

					FileOutputStream outputStream = null;
					try {
						outputStream = new FileOutputStream(file);
						outputStream.write(content.getBytes());
						return true;

					} catch (Exception e) {
						Logger.logError("Could not store recorded script.", e);
					} finally {
						if (outputStream != null) {
							try {
								outputStream.close();
							} catch (IOException e) {
								// giving up
							}
						}
					}
				}
			} catch (IOException e) {
				Logger.logError("Could not create file", e);
			}
		}

		return false;
	}

	@Override
	protected boolean createPath(IPath path) {
		File file = new File(getLocation() + "/" + path);
		if (!file.exists())
			return file.mkdirs();

		return true;
	}
}
