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

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ease.FileTrace.Trace;
import org.eclipse.ease.debug.ITracingConstant;
import org.eclipse.ease.debug.Tracer;
import org.eclipse.ease.service.EngineDescription;
import org.eclipse.ease.service.IScriptService;
import org.eclipse.ui.PlatformUI;

/**
 * Base implementation for a script engine. Handles Job implementation of script engine, adding script code for execution, module loading support and a basic
 * online help system.
 */
public abstract class AbstractScriptEngine extends Job implements IScriptEngine {

	/** List of code junks to be executed. */
	private final List<Script> fCodePieces = Collections.synchronizedList(new ArrayList<Script>());

	private final ListenerList fExecutionListeners = new ListenerList();

	/** Indicator to terminate once this Job gets IDLE. */
	private boolean fTerminateOnIdle = true;

	private PrintStream fOutputStream = null;

	private PrintStream fErrorStream = null;

	private InputStream fInputStream = null;

	private FileTrace fFileTrace = new FileTrace();

	private EngineDescription fDescription;

	/**
	 * Constructor. Contains a name for the underlying job and an indicator for the presence of online help.
	 * 
	 * @param name
	 *            name of script engine job
	 * @param helpAvailable
	 *            <code>true</code> when help code shall be generated on the fly
	 */
	public AbstractScriptEngine(final String name) {
		super(name);

		// make this a system job (not visible to the user)
		setSystem(true);
	}

	@Override
	public EngineDescription getDescription() {
		return fDescription;
	}

	@Override
	public final ScriptResult executeAsync(final Object content) {
		final Script piece;
		if (content instanceof Script)
			piece = (Script) content;
		else
			piece = new Script(content);

		fCodePieces.add(piece);
		synchronized (this) {
			notifyAll();
		}

		return piece.getResult();
	}

	@Override
	public final ScriptResult executeSync(final Object content) throws InterruptedException {

		if (getState() == NONE)
			throw new RuntimeException("Engine is not started yet, cannot run code");

		final ScriptResult result = executeAsync(content);
		synchronized (result) {
			while (!result.isReady())
				result.wait();
		}

		return result;
	}

	@Override
	public final Object inject(final Object content) {
		return internalInject(content, false);
	}

	@Override
	public final Object injectUI(final Object content) {
		return internalInject(content, true);
	}

	private final Object internalInject(final Object content, final boolean uiThread) {
		// injected code shall not trigger a new event, therefore notifyListerners needs to be false
		ScriptResult result;
		if (content instanceof Script)
			result = inject((Script) content, false, uiThread);
		else
			result = inject(new Script(content), false, uiThread);

		if (result.hasException()) {
			// re-throw previous exception

			if (result.getException() instanceof RuntimeException)
				throw (RuntimeException) result.getException();

			throw new RuntimeException(result.getException().getMessage(), result.getException());
		}

		return result.getResult();
	}

	/**
	 * Inject script code to the script engine. Injected code is processed synchronous by the current thread unless <i>uiThread</i> is set to <code>true</code>.
	 * Nevertheless this is a blocking call.
	 * 
	 * @param script
	 *            script to be executed
	 * @param notifyListeners
	 *            <code>true</code> when listeners should be informed of code fragment
	 * @param uiThread
	 *            when set to <code>true</code> run injected code in UI thread
	 * @return script execution result
	 */
	private ScriptResult inject(final Script script, final boolean notifyListeners, final boolean uiThread) {

		// FIXME make UI access optional by using preferences
		synchronized (script.getResult()) {

			try {
				if (ITracingConstant.MODULE_WRAPPER_TRACING)
					Tracer.logInfo("Executing (" + script.getTitle() + "):\n" + script.getCode());

				fFileTrace.push(script.getFile());

				// execution
				if (notifyListeners)
					notifyExecutionListeners(script, IExecutionListener.SCRIPT_START);
				else
					notifyExecutionListeners(script, IExecutionListener.SCRIPT_INJECTION_START);

				script.setResult(execute(script, script.getFile(), fFileTrace.peek().getFileName(), uiThread));

			} catch (final ExitException e) {
				script.setResult(e.getCondition());

			} catch (final BreakException e) {
				script.setResult(e.getCondition());

			} catch (final Exception e) {
				script.setException(e);
				e.printStackTrace(getErrorStream());

			} finally {
				if (notifyListeners)
					notifyExecutionListeners(script, IExecutionListener.SCRIPT_END);
				else
					notifyExecutionListeners(script, IExecutionListener.SCRIPT_INJECTION_END);

				fFileTrace.pop();
			}
		}

		return script.getResult();
	}

	@Override
	protected IStatus run(final IProgressMonitor monitor) {
		if (setupEngine()) {
			fFileTrace = new FileTrace();

			notifyExecutionListeners(null, IExecutionListener.ENGINE_START);

			// main loop
			while ((!monitor.isCanceled()) && (!isTerminated())) {

				// execute code
				if (!fCodePieces.isEmpty()) {
					final Script piece = fCodePieces.remove(0);
					inject(piece, true, false);

				} else {

					synchronized (this) {
						if (!isTerminated()) {
							try {
								wait();
							} catch (final InterruptedException e) {
							}
						}
					}
				}
			}

			// discard pending code pieces
			synchronized (fCodePieces) {
				for (final Script script : fCodePieces)
					script.setException(new ExitException());
			}

			fCodePieces.clear();

			notifyExecutionListeners(null, IExecutionListener.ENGINE_END);

			teardownEngine();
		}

		if (isTerminated())
			return Status.OK_STATUS;

		return Status.CANCEL_STATUS;
	}

	@Override
	public PrintStream getOutputStream() {
		return (fOutputStream != null) ? fOutputStream : System.out;
	}

	@Override
	public void setOutputStream(final OutputStream outputStream) {
		if (outputStream instanceof PrintStream)
			fOutputStream = (PrintStream) outputStream;

		else
			fOutputStream = new PrintStream(outputStream);
	}

	@Override
	public InputStream getInputStream() {
		return (fInputStream != null) ? fInputStream : System.in;
	}

	@Override
	public void setInputStream(final InputStream inputStream) {
		fInputStream = inputStream;
	}

	@Override
	public PrintStream getErrorStream() {
		return (fErrorStream != null) ? fErrorStream : System.err;
	}

	@Override
	public void setErrorStream(final OutputStream errorStream) {
		if (errorStream instanceof PrintStream)
			fErrorStream = (PrintStream) errorStream;

		else
			fErrorStream = new PrintStream(errorStream);
	}

	@Override
	public final void setTerminateOnIdle(final boolean terminate) {
		fTerminateOnIdle = terminate;
	}

	@Override
	public boolean getTerminateOnIdle() {
		return fTerminateOnIdle;
	}

	/**
	 * Get termination status of the interpreter. A terminated interpreter cannot be restarted.
	 * 
	 * @return true if interpreter is terminated.
	 */
	private boolean isTerminated() {
		return fTerminateOnIdle && fCodePieces.isEmpty();
	}

	/**
	 * Get idle status of the interpreter. The interpreter is IDLE if there are no pending execution requests and the interpreter is not terminated.
	 * 
	 * @return true if interpreter is IDLE
	 */
	@Override
	public boolean isIdle() {
		return fCodePieces.isEmpty();
	}

	@Override
	public void addExecutionListener(final IExecutionListener listener) {
		fExecutionListeners.add(listener);
	}

	@Override
	public void removeExecutionListener(final IExecutionListener listener) {
		fExecutionListeners.remove(listener);
	}

	protected void notifyExecutionListeners(final Script script, final int status) {
		for (final Object listener : fExecutionListeners.getListeners())
			((IExecutionListener) listener).notify(this, script, status);
	}

	@Override
	public void terminate() {
		setTerminateOnIdle(true);
		fCodePieces.clear();
		terminateCurrent();

		// ask thread to terminate
		cancel();
		if (getThread() != null)
			getThread().interrupt();
	}

	@Override
	public void reset() {
		// make sure that everybody gets notified that script engine got a reset
		for (final Script script : fCodePieces)
			script.setException(new ExitException("Script engine got resetted."));

		fCodePieces.clear();

		// re-enable launch extensions to register themselves
		final IScriptService scriptService = (IScriptService) PlatformUI.getWorkbench().getService(IScriptService.class);
		for (final IScriptEngineLaunchExtension extension : scriptService.getLaunchExtensions(getDescription().getID()))
			extension.createEngine(this);
	}

	@Override
	public FileTrace getFileTrace() {
		return fFileTrace;
	}

	@Override
	public Object getExecutedFile() {
		for (Trace trace : getFileTrace()) {
			Object file = trace.getFile();
			if (file != null)
				return file;
		}

		return null;
	}

	public void setEngineDescription(final EngineDescription description) {
		fDescription = description;
	}

	/**
	 * Setup method for script engine. Run directly after the engine is activated. Needs to return <code>true</code>. Otherwise the engine will terminate
	 * instantly.
	 * 
	 * @return <code>true</code> when setup succeeds
	 */
	protected abstract boolean setupEngine();

	/**
	 * Teardown engine. Called immediately before the engine terminates. This method is not called when {@link #setupEngine()} fails.
	 * 
	 * @return teardown result
	 */
	protected abstract boolean teardownEngine();

	/**
	 * Execute script code.
	 * 
	 * @param fileName
	 *            name of file executed
	 * @param uiThread
	 * @param reader
	 *            reader for script data to be executed
	 * @param uiThread
	 *            when set to <code>true</code> run code in UI thread
	 * @return execution result
	 * @throws Exception
	 *             any exception thrown during script execution
	 */
	protected abstract Object execute(Script script, Object reference, String fileName, boolean uiThread) throws Exception;
}
