package org.eclipse.ease.tools.helpgenerator;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.jar.JarFile;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;

public class Tools {

	/**
	 * Returns an {@link InputStream} for a given resource within a bundle. The caller is responsible for closing the stream after the operation is finished.
	 * 
	 * @param bundle
	 *        qualified name of the bundle to resolve
	 * @param path
	 *        full path of the file to load
	 * @return input stream to resource
	 * @throws IOException
	 */
	public static InputStream getResourceStream(final String bundle, final String path) throws IOException {
		String location = Platform.getBundle(bundle).getLocation();
		if (location.toLowerCase().endsWith(".jar")) {
			// we need to open a jar file
			final int pos = location.indexOf("file:");
			if (pos != -1) {
				location = location.substring(pos + 5);

				final JarFile file = new JarFile(location);
				if (path.startsWith("/"))
					return file.getInputStream(file.getEntry(path.substring(1)));
				else
					return file.getInputStream(file.getEntry(path));
			}

		} else {
			final URL url = Platform.getBundle(bundle).getResource(path);
			return FileLocator.resolve(url).openStream();
		}

		return null;
	}

	/**
	 * Create a new folder on the workbench. Will recursively create a whole folder structure, creating parent folders if necessary.
	 * 
	 * @param folder
	 *        folder to create
	 * @param updateFlags
	 *        internal resource flags (see {@link IResource})
	 * @param local
	 *        local flag for files
	 * @return <code>true</code> on success
	 * @throws CoreException
	 *         thrown when folder cannot be created
	 */
	public static boolean createFolder(final IFolder folder, final int updateFlags, final boolean local) throws CoreException {

		if (!folder.isAccessible()) {
			final IContainer parent = folder.getParent();

			if (!parent.isAccessible()) {
				if (parent instanceof IFolder)
					createFolder((IFolder)parent, updateFlags, local);
				else
					return false;
			}

			folder.create(updateFlags, local, null);
		}

		return true;
	}
}
