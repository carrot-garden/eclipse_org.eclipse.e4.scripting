/*******************************************************************************
 * Copyright (c) 2014 Christian Pontesegger and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Christian Pontesegger - initial API and implementation
 *******************************************************************************/package org.eclipse.ease.ui.scripts;

import java.io.ByteArrayInputStream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.ease.Logger;

public class WorkspaceScriptStorage extends ScriptStorage {

	public WorkspaceScriptStorage(final String location) {
		super(location);
	}

	@Override
	protected boolean createFile(final Path path, final String content) {
		Path locationPath = new Path(getLocation().substring(12));
		IPath fullPath = locationPath.append(path.removeLastSegments(1));

		IContainer container;
		if (fullPath.segmentCount() > 1)
			container = ResourcesPlugin.getWorkspace().getRoot().getFolder(fullPath);
		else
			container = ResourcesPlugin.getWorkspace().getRoot().getProject(fullPath.toString());

		IFile file = container.getFile(new Path(path.lastSegment()));
		if (!file.exists()) {
			try {
				file.create(new ByteArrayInputStream(content.getBytes()), false, null);
				return true;
			} catch (CoreException e) {
				Logger.logError("Could not create file " + file, e);
			}
		}

		return false;
	}

	@Override
	protected boolean createPath(final IPath path) {
		Path locationPath = new Path(getLocation().substring(12));
		IPath fullPath = locationPath.append(path);

		IProject project = createProject(fullPath.segment(0));

		if (project != null) {
			// project exists
			if (fullPath.segmentCount() > 1)
				// create subfolders
				return createFolders(project, fullPath.removeFirstSegments(1));

			return true;
		}

		return false;
	}

	private boolean createFolders(final IContainer container, final IPath folderPath) {
		IFolder folder = container.getFolder(folderPath);

		// create parent if needed
		if (!folder.getParent().exists()) {
			if (!createFolders(container, folderPath.removeLastSegments(1)))
				return false;
		}

		// create folder
		if (!folder.exists()) {
			try {
				folder.create(false, true, null);
				return true;
			} catch (CoreException e) {
				Logger.logError("Could not create folder " + folder, e);
			}
		} else
			return true;

		return false;
	}

	private IProject createProject(final String name) {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
		if (!project.exists()) {
			try {
				project.create(null);
				project.open(null);
				return project;
			} catch (CoreException e) {
				Logger.logError("Could not create project " + project, e);
			}
		} else
			return project;

		return null;
	}
}
