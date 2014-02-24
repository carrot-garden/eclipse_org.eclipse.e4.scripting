package org.eclipse.ease;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.ease.urlhandler.WorkspaceURLConnection;

public final class ResourceTools {

	/**
	 * @deprecated
	 */
	@Deprecated
	private ResourceTools() {
	}

	public static File getFile(String uri) {
		try {
			return new File(new URI(uri));
		} catch (Exception e) {
		}

		return null;
	}

	public static IResource getResource(String uri) {
		if (uri.startsWith(WorkspaceURLConnection.SCHEME + "://")) {
			Path path = new Path(uri.substring(WorkspaceURLConnection.SCHEME.length() + 3));
			if (!path.isEmpty()) {
				IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(path.segment(0));
				return project.findMember(path.removeFirstSegments(1));
			}
		}

		return null;
	}

	public static InputStream getInputStream(String uri) {
		try {
			return new URL(uri).openStream();
		} catch (Exception e) {
		}

		return null;
	}

	public static boolean exists(String uri) {
		IResource resource = getResource(uri);
		if (resource != null)
			return resource.exists();

		File file = getFile(uri);
		if (file != null)
			return file.exists();

		InputStream inputStream = getInputStream(uri);
		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException e) {
			}
			return true;
		}

		return false;
	}

	public static String toLocation(IResource resource) {
		return WorkspaceURLConnection.SCHEME + ":/" + resource.getFullPath().toPortableString();
	}
}
