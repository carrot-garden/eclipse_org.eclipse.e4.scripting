package org.eclipse.ease.module.platform;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ease.modules.AbstractScriptModule;
import org.eclipse.ease.modules.WrapToScript;

public class ResourcesModule extends AbstractScriptModule {

	@WrapToScript
	public static final int READ = IFileHandle.READ;

	@WrapToScript
	public static final int WRITE = IFileHandle.WRITE;

	@WrapToScript
	public static final int APPEND = IFileHandle.APPEND;

	@WrapToScript
	public IWorkspaceRoot getWorkspace() {
		return ResourcesPlugin.getWorkspace().getRoot();
	}

	public IProject getProject(final String name) {
		return getWorkspace().getProject(name);
	}

	/**
	 * Retrieve a file from the workspace or the file system.
	 * 
	 * @param location
	 *            file location to open from
	 * @return {@link File} or {@link IFile} object when file is found, <code>null</code> otherwise
	 */
	// @WrapToScript
	// public Object getFile(final String location) {
	// return ResourceTools.getFile(location, getScriptEngine().getFileTrace().getTopMostFile());
	// }
	//
	// @WrapToScript
	// public IFileHandle openFile(final String location, final int mode) {
	// Object file = getFile(location);
	// if (file instanceof IFile)
	// return new ResourceHandle((IFile) file, mode);
	//
	// else if (file instanceof File)
	// return new FilesystemHandle((File) file, mode);
	//
	// return null;
	// }
	//
	// @WrapToScript
	// public String readLine(final IFileHandle handle) {
	// return handle.readLine();
	// }
	//
	// @WrapToScript
	// public boolean writeFile(final IFileHandle handle, final String data) {
	// return handle.write(data);
	// }
	//
	// @WrapToScript
	// public boolean existsFile(final IFileHandle handle) {
	// return handle.exists();
	// }
	//
	// @WrapToScript
	// public boolean createFile(final IFileHandle handle, final Object createHierarchy) throws Exception {
	// return handle.createFile(Boolean.parseBoolean(createHierarchy.toString()));
	// }
}
