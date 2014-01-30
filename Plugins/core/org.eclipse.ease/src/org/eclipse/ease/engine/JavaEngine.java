package org.eclipse.ease.engine;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.ease.FileTrace;
import org.eclipse.ease.IExecutionListener;
import org.eclipse.ease.IScriptEngine;
import org.eclipse.ease.ScriptResult;
import org.eclipse.ease.modules.IEnvironment;
import org.eclipse.ease.modules.NativeEnvironment;

public class JavaEngine implements IScriptEngine {

	private PrintStream mOutStream;
	private PrintStream mErrorStream;
	private InputStream mInStream;

	private final Map<String, Object> mVariables = new HashMap<String, Object>();
	private final IEnvironment mEnvironment;

	public JavaEngine() {
		mEnvironment = new NativeEnvironment();
	}

	@Override
	public ScriptResult executeAsync(final Object content) {
		throw new RuntimeException("not supported");
	}

	@Override
	public ScriptResult executeSync(final Object content) throws InterruptedException {
		throw new RuntimeException("not supported");
	}

	@Override
	public Object inject(final Object content) {
		throw new RuntimeException("not supported");
	}

	@Override
	public Object getExecutedFile() {
		return null;
	}

	@Override
	public void setTerminateOnIdle(final boolean terminate) {
		// do nothing
	}

	@Override
	public boolean getTerminateOnIdle() {
		return false;
	}

	@Override
	public void schedule() {
		// do nothing
	}

	@Override
	public void terminate() {
		// do nothing
	}

	@Override
	public void terminateCurrent() {
		// do nothing
	}

	@Override
	public void addExecutionListener(final IExecutionListener listener) {
		// do nothing
	}

	@Override
	public void removeExecutionListener(final IExecutionListener listener) {
		// do nothing
	}

	@Override
	public void setIsUI(final boolean isUI) {
		// do nothing
	}

	@Override
	public boolean isUI() {
		throw new RuntimeException("not supported");
	}

	@Override
	public void reset() {
		// do nothing
	}

	@Override
	public boolean isIdle() {
		return true;
	}

	@Override
	public FileTrace getFileTrace() {
		return null;
	}

	@Override
	public String getID() {
		return null;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public void setVariable(final String name, final Object content) {
		mVariables.put(name, content);
	}

	@Override
	public Object getVariable(final String name) {
		return mVariables.get(name);
	}

	@Override
	public boolean hasVariable(final String name) {
		return mVariables.containsKey(name);
	}

	@Override
	public String getSaveVariableName(final String name) {
		return name;
	}

	@Override
	public PrintStream getOutputStream() {
		return (mOutStream != null) ? mOutStream : System.out;
	}

	@Override
	public void setOutputStream(final OutputStream outputStream) {
		if (outputStream instanceof PrintStream)
			mOutStream = (PrintStream) outputStream;

		else
			mOutStream = new PrintStream(outputStream);
	}

	@Override
	public InputStream getInputStream() {
		return (mInStream != null) ? mInStream : System.in;
	}

	@Override
	public void setInputStream(final InputStream inputStream) {
		mInStream = inputStream;
	}

	@Override
	public PrintStream getErrorStream() {
		return (mErrorStream != null) ? mErrorStream : System.err;
	}

	@Override
	public void setErrorStream(final OutputStream errorStream) {
		if (errorStream instanceof PrintStream)
			mErrorStream = (PrintStream) errorStream;

		else
			mErrorStream = new PrintStream(errorStream);
	}

	public IEnvironment getEnvironment() {
		return mEnvironment;
	}
}
