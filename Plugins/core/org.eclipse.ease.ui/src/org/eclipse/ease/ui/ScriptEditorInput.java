package org.eclipse.ease.ui;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.ease.ui.repository.IScript;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.IStorageEditorInput;

public class ScriptEditorInput implements IEditorInput, IStorageEditorInput {

	private final String fName;
	private final InputStream fContent;

	public ScriptEditorInput(final IScript script) {
		fName = script.getName();
		fContent = script.getInputStream();
	}

	public ScriptEditorInput(final String name, final String content) {
		fName = name;
		fContent = new ByteArrayInputStream(content.getBytes());
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
				return null;
			}

			@Override
			public InputStream getContents() throws CoreException {
				return fContent;
			}
		};
	}

	@Override
	public final ImageDescriptor getImageDescriptor() {
		return null;
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
