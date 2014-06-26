/*******************************************************************************
 * Copyright (c) 2013 Christian Pontesegger and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Christian Pontesegger - initial API and implementation
 *******************************************************************************/
package org.eclipse.ease;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Platform;

/**
 * Scriptable object. Consists of scriptable data and a result container.
 */
public class Script {

	/** command to be executed. */
	private final Object fCommand;

	/** script result returned from command. */
	private final ScriptResult fResult;

	private String fCodeBuffer = null;

	private final String fTitle;

	/**
	 * Constructor.
	 * 
	 * @param title
	 *            name of script object
	 * @param command
	 *            command (sequence) to be executed
	 */
	public Script(final String title, final Object command) {
		fTitle = title;
		fCommand = command;
		fResult = new ScriptResult();
	}

	/**
	 * Constructor.
	 * 
	 * @param command
	 *            command (sequence) to be executed
	 */
	public Script(final Object command) {
		this(null, command);
	}

	/**
	 * Get the scriptable data as {@link InputStream}. The caller needs to close the stream when it is not used anymore. Calling this method multiple times will
	 * return different streams with the same text content.
	 * 
	 * @return scriptable data
	 * @throws Exception
	 */
	public InputStream getCodeStream() throws Exception {
		return new ByteArrayInputStream(getCode().getBytes());
	}

	/**
	 * Get the scriptable data as {@link InputStream}. The caller needs to close the stream when it is not used anymore.
	 * 
	 * @return scriptable data
	 * @throws Exception
	 */
	public String getCode() throws Exception {
		if (fCodeBuffer != null)
			return fCodeBuffer;

		if (fCommand instanceof String)
			return (String) fCommand;

		if (fCommand instanceof InputStream)
			// streams can only be read once, therefore buffer
			return bufferStream((InputStream) fCommand);

		if (fCommand instanceof Reader)
			// readers can only be read once, therefore buffer
			return bufferReader((Reader) fCommand);

		// if we already have a scriptable
		if (fCommand instanceof IScriptable)
			return bufferStream(((IScriptable) fCommand).getSourceCode());

		// try to adapt to scriptable
		final Object scriptable = Platform.getAdapterManager().getAdapter(fCommand, IScriptable.class);
		if (scriptable != null)
			return bufferStream(((IScriptable) scriptable).getSourceCode());

		// last resort, convert to String
		if (fCommand != null) {
			// better buffer stuff, we do not know if toString() remains constant
			fCodeBuffer = fCommand.toString();
			return fCodeBuffer;
		}

		return null;

	}

	private String bufferReader(final Reader command) throws IOException {
		fCodeBuffer = toString(command);
		return fCodeBuffer;
	}

	private String bufferStream(final InputStream command) throws IOException {
		fCodeBuffer = toString(command);
		return fCodeBuffer;
	}

	public final Object getCommand() {
		return fCommand;
	}

	/**
	 * Get execution result.
	 * 
	 * @return execution result.
	 */
	public final ScriptResult getResult() {
		return fResult;
	}

	/**
	 * Set the execution result.
	 * 
	 * @param result
	 *            execution result
	 */
	public final void setResult(final Object result) {
		fResult.setResult(result);

		// gracefully close input streams & readers
		if (fCommand instanceof InputStream) {
			try {
				((InputStream) fCommand).close();
			} catch (final IOException e) {
			}

		} else if (fCommand instanceof Reader) {
			try {
				((Reader) fCommand).close();
			} catch (final IOException e) {
			}
		}
	}

	/**
	 * Set an execution exception.
	 * 
	 * @param e
	 *            exception
	 */
	public final void setException(final Exception e) {
		fResult.setException(e);

		// gracefully close input streams & readers
		if (fCommand instanceof InputStream) {
			try {
				((InputStream) fCommand).close();
			} catch (final IOException ex) {
			}

		} else if (fCommand instanceof Reader) {
			try {
				((Reader) fCommand).close();
			} catch (final IOException ex) {
			}
		}
	}

	public Object getFile() {
		if ((fCommand instanceof IFile) || (fCommand instanceof File))
			return fCommand;

		return null;
	}

	/**
	 * Convert an input stream to a string.
	 * 
	 * @param stream
	 *            input string to read from
	 * @return string containing stream data
	 * @throws IOException
	 *             thrown on problems with input stream
	 */
	private static String toString(final InputStream stream) throws IOException {
		return toString(new InputStreamReader(stream));
	}

	/**
	 * Read characters from a {@link Reader} and return its string representation. Can be used to convert an {@link InputStream} to a string.
	 * 
	 * @param reader
	 *            reader to read from
	 * @return string content of reader
	 * @throws IOException
	 *             when reader is not accessible
	 */
	private static String toString(final Reader reader) throws IOException {
		final StringBuffer out = new StringBuffer();

		final char[] buffer = new char[1024];
		int bytes = 0;
		do {
			bytes = reader.read(buffer);
			if (bytes > 0)
				out.append(buffer, 0, bytes);
		} while (bytes != -1);

		return out.toString();
	}

	@Override
	public String toString() {
		if (fCommand instanceof IFile)
			return ((IFile) fCommand).getName();

		if (fCommand instanceof File)
			return ((File) fCommand).getName();

		return "(unknown script source)";
	}

	public String getTitle() {
		return fTitle;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((fCodeBuffer == null) ? 0 : fCodeBuffer.hashCode());
		result = (prime * result) + ((fCommand == null) ? 0 : fCommand.hashCode());
		result = (prime * result) + ((fResult == null) ? 0 : fResult.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Script other = (Script) obj;
		if (fCodeBuffer == null) {
			if (other.fCodeBuffer != null)
				return false;
		} else if (!fCodeBuffer.equals(other.fCodeBuffer))
			return false;
		if (fCommand == null) {
			if (other.fCommand != null)
				return false;
		} else if (!fCommand.equals(other.fCommand))
			return false;
		if (fResult == null) {
			if (other.fResult != null)
				return false;
		} else if (!fResult.equals(other.fResult))
			return false;
		return true;
	}

}
