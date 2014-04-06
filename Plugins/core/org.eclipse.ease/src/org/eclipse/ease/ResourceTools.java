package org.eclipse.ease;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.ease.service.IScriptService;
import org.eclipse.ease.service.ScriptType;
import org.eclipse.ease.urlhandler.WorkspaceURLConnection;
import org.eclipse.ui.PlatformUI;

public final class ResourceTools {

	/**
	 * @deprecated
	 */
	@Deprecated
	private ResourceTools() {
	}

	public static File getFile(final String uri) {
		try {
			// TODO find better way to encode URI correctly
			return new File(new URI(uri.replace(" ", "%20")));
		} catch (Exception e) {
		}

		return null;
	}

	public static IResource getResource(final String uri) {
		if (uri.startsWith(WorkspaceURLConnection.SCHEME + "://")) {
			Path path = new Path(uri.substring(WorkspaceURLConnection.SCHEME.length() + 3));
			if (!path.isEmpty()) {
				IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(path.segment(0));
				return project.findMember(path.removeFirstSegments(1));
			}
		}

		return null;
	}

	public static InputStream getInputStream(final String uri) {
		try {
			return new URL(uri).openStream();
		} catch (Exception e) {
		}

		return null;
	}

	public static Object getContent(final String uri) {
		IResource resource = getResource(uri);
		if (resource != null)
			return resource;

		File file = getFile(uri);
		if (file != null)
			return file;

		InputStream inputStream = getInputStream(uri);
		if (inputStream != null)
			return inputStream;

		return null;
	}

	public static boolean exists(final String uri) {
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

	public static String toLocation(final IResource resource) {
		return WorkspaceURLConnection.SCHEME + ":/" + resource.getFullPath().toPortableString();
	}

	public static ScriptType getScriptType(final IFile file) {
		// resolve by content type
		final IScriptService scriptService = (IScriptService) PlatformUI.getWorkbench().getService(IScriptService.class);
		try {
			IContentDescription contentDescription = file.getContentDescription();
			if (contentDescription != null)
				return scriptService.getScriptType(contentDescription.getContentType());

		} catch (CoreException e) {
		}

		// did not work, resolve by extension
		return scriptService.getScriptType(file.getFileExtension());
	}

	public static ScriptType getScriptType(final File file) {
		// resolve by extension
		String name = file.getName();
		if (name.contains(".")) {
			String extension = name.substring(name.lastIndexOf('.') + 1);

			final IScriptService scriptService = (IScriptService) PlatformUI.getWorkbench().getService(IScriptService.class);
			return scriptService.getScriptType(extension);
		}

		return null;
	}
}
