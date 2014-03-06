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
package org.eclipse.ease.modules;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ease.ExitException;
import org.eclipse.ease.IScriptEngine;
import org.eclipse.ease.Logger;
import org.eclipse.ease.Script;
import org.eclipse.ease.debug.ITracingConstant;
import org.eclipse.ease.debug.Tracer;

/**
 * The Environment provides base functions for all script interpreters. It is automatically loaded by any interpreter upon startup.
 */
public class EnvironmentModule extends AbstractEnvironment {

	private static final String PROJECT_SCHEME = "project://";

	public static final String MODULE_NAME = "/System/Environment";

	public static final String MODULE_PREFIX = "__MOD_";

	public EnvironmentModule() {
	}

	/**
	 * Creates wrapper functions for a given java instance. Searches for members and methods annotated with {@link WrapToScript} and creates wrapping code in
	 * the target script language. A method named &lt;instance&gt;.myMethod() will be made available by calling myMethod().
	 * 
	 * @param toBeWrapped
	 *            instance to be wrapped
	 */
	@Override
	@WrapToScript
	public void wrap(final Object toBeWrapped) {
		// register new variable in script engine
		String identifier = getScriptEngine().getSaveVariableName(MODULE_PREFIX + toBeWrapped.toString());

		// FIXME either remove or move to script engine
		// if (getScriptEngine().isUI()) {
		// InstaciateModuleRunnble tt = new InstaciateModuleRunnble(
		// definition);
		// Display.getDefault().syncExec(tt);
		// module = tt.getResult();

		boolean reloaded = getScriptEngine().hasVariable(identifier);
		getScriptEngine().setVariable(identifier, toBeWrapped);

		// FIXME move to script engine
		if (ITracingConstant.ENVIRONEMENT_MODULE_WRAPPER_TRACING) {
			Tracer.logInfo("[Environment Module] Add variable to engine :\n " + toBeWrapped.toString() + " with value" + toBeWrapped);
		}

		// create function wrappers
		createWrappers(toBeWrapped, identifier, reloaded);

		// notify listeners
		fireModuleEvent(toBeWrapped, reloaded ? IModuleListener.RELOADED : IModuleListener.LOADED);
	}

	/**
	 * Create JavaScript wrapper functions for autoload methods. Adds code of following style: <code>function {name} (a, b, c, ...) {
	 * __module.{name}(a, b, c, ...);
	 * }</code>
	 * 
	 * @param instance
	 *            module instance to create wrappers for
	 * @param reload
	 *            flag indicating that the module was already loaded
	 */
	private void createWrappers(final Object instance, final String identifier, final boolean reload) {
		// script code to inject
		StringBuilder scriptCode = new StringBuilder();

		// get methods with annotation
		List<Method> methods = new ArrayList<Method>();
		for (Method method : instance.getClass().getMethods()) {
			if (useAutoLoader(method))
				methods.add(method);
		}

		if (methods.isEmpty()) {
			// no annotated methods, use all declared public methods
			for (Method method : instance.getClass().getDeclaredMethods()) {
				if (Modifier.isPublic(method.getModifiers()))
					methods.add(method);
			}
		}

		// use reflection to access methods
		for (final Method method : methods) {
			String preExecutionCode = getPreExecutionCode(instance, method);
			String postExecutionCode = getPostExecutionCode(instance, method);

			Set<String> methodNames = new HashSet<String>();
			methodNames.add(method.getName());

			WrapToScript wrapAnnotation = method.getAnnotation(WrapToScript.class);
			if (wrapAnnotation != null)
				methodNames.addAll(Arrays.asList(wrapAnnotation.alias().split(WrapToScript.DELIMITER)));

			// prevent from null and empty string to pass to module wrapper
			methodNames.remove(null);
			for (String name : new HashSet<String>(methodNames))
				if (name.trim().isEmpty())
					methodNames.remove(name);

			String code = getWrapper().createFunctionWrapper(identifier, method, methodNames, IScriptFunctionModifier.RESULT_NAME, preExecutionCode,
					postExecutionCode);
			if ((code != null) && !code.isEmpty()) {
				scriptCode.append(code);
				scriptCode.append('\n');
			}
		}

		// use reflection to access static members
		if (!reload) {
			Field[] declaredFields = instance.getClass().getDeclaredFields();
			for (Field field : declaredFields) {
				try {
					if ((Modifier.isStatic(field.getModifiers())) && (Modifier.isPublic(field.getModifiers()))) {
						if (field.getAnnotation(WrapToScript.class) != null)
							// do not generate code here as this would require the module to be exported in manifest.mf
							getScriptEngine().setVariable(getWrapper().getSaveVariableName(field.getName()), field.get(instance));
					}
				} catch (IllegalArgumentException e) {
					Logger.logError("Could not wrap field \"" + field.getName() + " \" of module \"" + instance.getClass() + "\"");
				} catch (IllegalAccessException e) {
					// should not happen as field is public. But in case just ignore this field and continue with the next one
					Logger.logError("Could not wrap field \"" + field.getName() + " \" of module \"" + instance.getClass() + "\"");
				}
			}
		}

		// execute code
		String codeToInject = scriptCode.toString();
		// FIXME move log to script engine
		if (ITracingConstant.ENVIRONEMENT_MODULE_WRAPPER_TRACING) {
			Tracer.logInfo("[Environement Module] Injecting code:\n" + codeToInject);
		}
		getScriptEngine().inject(new Script("Wrapping " + instance.toString(), scriptCode.toString()));
	}

	/**
	 * Verify that a method is treated by the autoloader. This is the case when the method is marked by an @WrapToJavaScript annotation.
	 * 
	 * @param method
	 *            method to be evaluated
	 * @return true when autoloader should handle this method
	 */
	private static boolean useAutoLoader(final Method method) {
		return (Modifier.isPublic(method.getModifiers())) && (method.getAnnotation(WrapToScript.class) != null);
	}

	/**
	 * Execute script code. This method executes script code directly in the running interpreter. Execution is done in the same thread as the caller thread.
	 * 
	 * @param data
	 *            code to be interpreted
	 * @return result of code execution
	 */
	@WrapToScript
	public final Object eval(final Object data) {
		return getScriptEngine().inject(data);
	}

	/**
	 * Terminates script execution immediately. Code following this command will not be executed anymore.
	 * 
	 * @param value
	 *            return code
	 */
	@WrapToScript
	public final void exit(final @ScriptParameter(optional = true, defaultValue = ScriptParameter.NULL) Object value) {
		throw new ExitException(value);
	}

	// /**
	// * Terminates execution of the current piece of code. Eg forces an include
	// command to return.
	// *
	// * @param value
	// * return value
	// */
	// @WrapToScript
	// public final void stepUp(final Object value) {
	// throw new BreakException(value);
	// }
	//
	/**
	 * Include and execute a script file. Quite similar to eval(Object) a source file is opened and its content is executed. Multiple sources are available:
	 * "workspace://" opens a file relative to the workspace root, "project://" opens a file relative to the current project, "file://" opens a file from the
	 * file system. All other types of URIs are supported too (like http:// ...). You may also use absolute and relative paths as defined by your local file
	 * system.
	 * 
	 * @param filename
	 *            name of file to be included
	 * @return result of include operation
	 * @throws Throwable
	 */
	@WrapToScript
	public final Object include(final String filename) {
		if (filename.startsWith(PROJECT_SCHEME)) {
			// project relative link, we cannot resolve this via URL as we need a relative file in the project
			Object currentFile = getScriptEngine().getExecutedFile();
			if (currentFile instanceof IFile) {
				IFile file = ((IFile) currentFile).getProject().getFile(new Path(filename.substring(PROJECT_SCHEME.length())));
				if (file.exists())
					return getScriptEngine().inject(file);
			}

		} else {

			// maybe this is a URI
			try {
				URL url = new URL(filename);
				return getScriptEngine().inject(url.openStream());

			} catch (MalformedURLException e) {
			} catch (IOException e) {
			}

			// maybe this is an absolute path within the file system
			File systemFile = new File(filename);
			if (systemFile.exists())
				return getScriptEngine().inject(systemFile);

			// maybe this is an absolute path within the workspace
			IFile workspaceFile;
			try {
				workspaceFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(filename));
				if (workspaceFile.exists())
					return getScriptEngine().inject(workspaceFile);
			} catch (IllegalArgumentException e) {
				// invalid path detected
			}

			// maybe a relative filename
			Object currentFile = getScriptEngine().getExecutedFile();
			if (currentFile instanceof IFile) {
				workspaceFile = ((IFile) currentFile).getParent().getFile(new Path(filename));
				if (workspaceFile.exists())
					return getScriptEngine().inject(workspaceFile);

			} else if (currentFile instanceof File) {
				systemFile = new File(((File) currentFile).getParentFile().getAbsolutePath() + File.pathSeparator + filename);
				if (systemFile.exists())
					return getScriptEngine().inject(systemFile);
			}
		}

		// giving up
		throw new RuntimeException("Cannot locate '" + filename + "'");
	}

	private String getPreExecutionCode(final Object instance, final Method method) {
		final StringBuffer code = new StringBuffer();

		for (final Object module : getModules()) {
			if (module instanceof IScriptFunctionModifier)
				code.append(((IScriptFunctionModifier) module).getPreExecutionCode(instance, method));
		}

		return code.toString();
	}

	private String getPostExecutionCode(final Object instance, final Method method) {
		final StringBuffer code = new StringBuffer();

		for (final Object module : getModules()) {
			if (module instanceof IScriptFunctionModifier)
				code.append(((IScriptFunctionModifier) module).getPostExecutionCode(instance, method));
		}

		return code.toString();
	}

	// // FIXME move to rhino bundle
	// /**
	// * Resolves a jar file and makes its classes available for the
	// ClassLoader.
	// *
	// * @param location
	// * location of jar file to register
	// */
	// // @WrapToJavaScript
	// // public final void registerJar(final String location) {
	// // final Object currentFile =
	// getScriptEngine().getFileTrace().getTopMostFile();
	// // final Object file = ResourceTools.getFile(location, currentFile);
	// //
	// // final IScriptEngine engine = getScriptEngine();
	// // if (engine instanceof RhinoScriptEngine) {
	// // if (file != null) {
	// // try {
	// // if (file instanceof IFile) {
	// // final URL url = ((IFile) file).getRawLocationURI().toURL();
	// // ((RhinoScriptEngine) engine).registerJar(url);
	// // return;
	// //
	// // } else if (file instanceof File) {
	// // final URL url = ((File) file).toURI().toURL();
	// // ((RhinoScriptEngine) engine).registerJar(url);
	// // return;
	// //
	// // }
	// // } catch (final MalformedURLException e) {
	// // // nothing to do
	// // }
	// // }
	// // }
	// //
	// // throw new RuntimeException("Jar file \"" + location + "\" not found");
	// // }

	/**
	 * Re-implementation as we might not have initialized the engine on the first module load.
	 */
	@Override
	public IScriptEngine getScriptEngine() {

		IScriptEngine engine = super.getScriptEngine();
		if (engine == null) {
			final Job currentJob = Job.getJobManager().currentJob();
			if (currentJob instanceof IScriptEngine)
				return (IScriptEngine) currentJob;
		}

		return engine;
	}
}
