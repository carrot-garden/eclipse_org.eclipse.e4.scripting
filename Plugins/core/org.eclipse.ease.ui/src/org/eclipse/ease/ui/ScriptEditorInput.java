/*******************************************************************************
 * Copyright (c) 2014 Christian Pontesegger and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Christian Pontesegger - initial API and implementation
 *******************************************************************************/package org.eclipse.ease.ui;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.ease.ui.repository.IScript;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.IStorageEditorInput;

public class ScriptEditorInput implements IStorageEditorInput {

	private final String fName;
	private final InputStream fContent;
	private final IScript fScript;

	public ScriptEditorInput(final IScript script) {
		fScript = script;
		fName = script.getName();
		fContent = null;
	}

	public ScriptEditorInput(final String name, final String content) {
		fName = name;
		fContent = new ByteArrayInputStream(content.getBytes());
		fScript = null;
	}

	@Override
	public final boolean equals(final Object other) {
		return (other instanceof ScriptEditorInput) && fName.equals(((ScriptEditorInput) other).fName);
	}

	@Override
	public final Object getAdapter(final Class adapter) {
		return null;
	}

	@Override
	public final IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public final boolean exists() {
		return true;
	}

	@Override
	public final IStorage getStorage() {
		return new IStorage() {
			@Override
			public Object getAdapter(final Class adapter) {
				return null;
			}

			@Override
			public boolean isReadOnly() {
				return false;
			}

			@Override
			public String getName() {
				return fName;
			}

			@Override
			public IPath getFullPath() {
				if (fScript != null) {
					Object content = fScript.getContent();
					if (content instanceof File)
						return new Path(((File) content).getPath());
				}

				return null;
			}

			@Override
			public InputStream getContents() throws CoreException {
				if (fScript != null)
					return fScript.getInputStream();

				return fContent;
			}
		};
	}

	@Override
	public final ImageDescriptor getImageDescriptor() {
		return Activator.getImageDescriptor("/images/script.gif");
	}

	@Override
	public final String getName() {
		return "Script: " + fName;
	}

	@Override
	public final String getToolTipText() {
		return "Script editor";
	}
}
