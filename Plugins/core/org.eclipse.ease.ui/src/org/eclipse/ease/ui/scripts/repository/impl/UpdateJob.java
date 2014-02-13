package org.eclipse.ease.ui.scripts.repository.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ease.IHeaderParser;
import org.eclipse.ease.service.IScriptService;
import org.eclipse.ease.service.ScriptType;
import org.eclipse.ease.ui.repository.IEntry;
import org.eclipse.ease.ui.repository.IParameter;
import org.eclipse.ease.ui.repository.IRepositoryFactory;
import org.eclipse.ease.ui.repository.IScript;
import org.eclipse.ui.PlatformUI;

public class UpdateJob extends Job {

	private final RepositoryService fRepositoryService;

	public UpdateJob(final RepositoryService repositoryService) {
		super("Updating script repository");

		fRepositoryService = repositoryService;
	}

	@Override
	protected IStatus run(final IProgressMonitor monitor) {

		// mark script to be verified
		for (IScript script : fRepositoryService.getScripts())
			script.setUpdatePending(true);

		// verify locations
		for (IEntry entry : new ArrayList<IEntry>(fRepositoryService.getRepository().getEntries())) {

			if ((entry.getTimestamp() + RepositoryService.UPDATE_URI_INTERVAL) < System.currentTimeMillis()) {
				// entry outdated

				// check for workspace resources
				IResource resource = entry.getIResource();
				if (resource != null) {
					// this is a valid workspace resource
					parse(resource, entry);
					entry.setTimestamp(System.currentTimeMillis());
				} else {
					// check for file system resource
					File file = entry.getFile();
					if (file != null) {
						// this is a valid file system resource
						parse(file, entry);
						entry.setTimestamp(System.currentTimeMillis());
					}
				}
			}
		}

		// remove scripts that were not verified
		for (IScript script : new HashSet<IScript>(fRepositoryService.getScripts())) {
			if (script.isUpdatePending())
				fRepositoryService.removeScript(script);
		}

		fRepositoryService.save();

		// re schedule job
		// TODO make this editable by preferences
		schedule(1000 * 60 * 30);
		return Status.OK_STATUS;
	}

	private void parse(final File file, final IEntry entry) {
		if (file.isDirectory()) {
			// containment, parse children
			for (File child : file.listFiles()) {
				if ((child.isFile()) || (entry.isRecursive()))
					parse(child, entry);
			}

		} else {
			// try to locate registered script
			String uri = file.toURI().toString();
			IScript script = fRepositoryService.getScript(uri);

			if (script == null) {
				script = IRepositoryFactory.eINSTANCE.createScript();
				script.setEntry(entry);
				script.setUri(uri);
			}

			if (script.getTimestamp() != file.lastModified()) {
				// file altered in file system

				try {
					// parse script headers
					String name = file.getName();
					if (name.contains(".")) {
						String extension = name.substring(name.lastIndexOf('.') + 1);

						// locate header parser
						final IScriptService scriptService = (IScriptService) PlatformUI.getWorkbench().getService(IScriptService.class);
						IHeaderParser parser = null;
						for (ScriptType scriptType : scriptService.getAvailableScriptTypes().values()) {
							if (extension.equalsIgnoreCase(scriptType.getDefaultExtension())) {
								parser = scriptType.getHeaderParser();
								break;
							}
						}

						// parse header
						if (parser != null) {
							Map<String, String> parameters = parser.parser(new FileInputStream(file));
							for (String key : parameters.keySet()) {
								IParameter parameter = IRepositoryFactory.eINSTANCE.createParameter();
								parameter.setKey(key);
								parameter.setValue(parameters.get(key));
								parameter.setScriptOrigin(true);

								script.getParameter().add(parameter);
							}
						}

						// set name
						IParameter nameParameter = script.getParameter("Name");
						if (nameParameter != null)
							// name parameter contained in script
							script.setFullName(nameParameter.getValue());

						else {
							// use path information relative to base folder for name
							String filePath = file.getAbsolutePath();
							String locationPath = entry.getFile().getAbsolutePath();
							String relativePath = filePath.substring(locationPath.length());

							IPath finalPath = new Path(relativePath).makeRelative();

							script.setFullName(finalPath.removeFileExtension().toString());
						}

						// update timestamp
						script.setTimestamp(file.lastModified());
						fRepositoryService.addScript(script);
					}
				} catch (FileNotFoundException e) {
					// TODO handle this exception (but for now, at least know it happened)
					throw new RuntimeException(e);
				}
			}
		}
	}

	private void parse(final IResource resource, final IEntry entry) {
		if (resource instanceof IContainer) {
			// containment, parse children
			try {
				resource.accept(new IResourceVisitor() {

					@Override
					public boolean visit(final IResource resource) throws CoreException {

						if (resource instanceof IFile)
							parse(resource, entry);

						return entry.isRecursive();
					}

				}, entry.isRecursive() ? IResource.DEPTH_INFINITE : IResource.DEPTH_ONE, 0);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (resource instanceof IFile) {
			// try to locate registered script
			String uri = "workspace:/" + resource.getFullPath();
			IScript script = fRepositoryService.getScript(uri);

			if (script == null) {
				script = IRepositoryFactory.eINSTANCE.createScript();
				script.setEntry(entry);
				script.setUri(uri);
			}

			if (script.getTimestamp() != resource.getModificationStamp()) {
				// file altered in file system

				// parse script headers
				try {

					// extract content type
					IContentDescription contentDescription = ((IFile) resource).getContentDescription();
					if (contentDescription != null) {
						final IScriptService scriptService = (IScriptService) PlatformUI.getWorkbench().getService(IScriptService.class);

						// locate header parser
						ScriptType type = scriptService.getScriptType(contentDescription.getContentType());
						if (type != null) {
							IHeaderParser parser = type.getHeaderParser();

							if (parser != null) {
								Map<String, String> parameters = parser.parser(((IFile) resource).getContents());
								for (String key : parameters.keySet()) {
									IParameter parameter = IRepositoryFactory.eINSTANCE.createParameter();
									parameter.setKey(key);
									parameter.setValue(parameters.get(key));
									parameter.setScriptOrigin(true);

									script.getParameter().add(parameter);
								}
							}

							// set name
							IParameter nameParameter = script.getParameter("Name");
							if (nameParameter != null)
								// name parameter contained in script
								script.setFullName(nameParameter.getValue());

							else {
								// use path information relative to base folder for name
								IPath filePath = resource.getFullPath();
								IPath locationPath = entry.getIResource().getFullPath();
								IPath relativePath = filePath.makeRelativeTo(locationPath);

								script.setFullName(relativePath.removeFileExtension().toString());
							}

							// update timestamp
							script.setTimestamp(resource.getModificationStamp());
							fRepositoryService.addScript(script);
						}
					}
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					throw new RuntimeException(e);
				}
			}
		}
	}

	public void scheduleUpdate(final long delay) {
		cancel();
		schedule(delay);
	}
}