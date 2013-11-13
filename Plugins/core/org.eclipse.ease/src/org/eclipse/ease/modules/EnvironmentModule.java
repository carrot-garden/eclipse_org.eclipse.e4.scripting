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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ease.ExitException;
import org.eclipse.ease.IScriptEngine;
import org.eclipse.ease.Script;
import org.eclipse.ease.debug.ITracingConstant;
import org.eclipse.ease.log.Tracer;
import org.eclipse.ease.service.ScriptService;

/**
 * The Environment provides base functions for all script interpreters. It is automatically loaded by any interpreter upon startup.
 */
public class EnvironmentModule extends AbstractScriptModule {

	private static final String PROJECT = "project://";

	private static final String WORKSPACE = "workspace://";

	public static final String ENVIRONMENT_MODULE_NAME = "Environment";

	/** Stores ordering of wrapped elements. */
	private final List<Object> mModules = new ArrayList<Object>();

	/** Stores beautified names of loaded modules. */
	private final Map<String, Object> mModuleNames = new HashMap<String, Object>();

	private final ListenerList mModuleListeners = new ListenerList();

	/**
	 * Load a module. Loading a module generally enhances the JavaScript environment with new functions and variables. If a module was already loaded before, it
	 * gets refreshed and moved to the top of the module stack. When a module is loaded, all its dependencies are loaded too. So loading one module might change
	 * the whole module stack.
	 * 
	 * @param name
	 *            name of module to load
	 * @return loaded module instance
	 */
	@WrapToScript
	public final Object loadModule(final String identifier) {
		Object module = findModule(identifier);
		if (module == null) {
			// not loaded yet
			Map<String, ModuleDefinition> availableModules = ScriptService.getInstance().getAvailableModules();

			ModuleDefinition definition = availableModules.get(identifier);
			if (definition != null) {
				// module exists

				// load dependencies; always load to bring dependencies on top of modules stack
				for (String dependencyName : definition.getDependencies()) {
					if (loadModule(dependencyName) == null)
						// could not load dependency, bail out
						return null;
				}

				module = definition.getModuleInstance();
				if (module instanceof IScriptModule)
					((IScriptModule) module).initialize(getScriptEngine(), this);
			}
		}

		// create function wrappers
		wrap(module);

		return module;
	}

	/**
	 * Creates wrapper functions for a given java instance. Searches for members and methods annotated with {@link WrapToScript} and creates wrapping code in
	 * the target script language. A method named &lt;instance&gt;.myMethod() will be made available by calling myMethod().
	 * 
	 * @param toBeWrapped
	 *            instance to be wrapped
	 */
	@WrapToScript
	public void wrap(Object toBeWrapped) {
		// register new variable in script engine
		String identifier = getScriptEngine().getSaveVariableName(toBeWrapped.toString());

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

		// move module up to first position
		mModules.remove(toBeWrapped);
		mModules.add(0, toBeWrapped);

		// notify listeners
		fireModuleEvent(toBeWrapped, reloaded ? IModuleListener.RELOADED : IModuleListener.LOADED);
	}

	// public static String getRegisteredModuleName(final String moduleName) {
	// return "__MOD_" + moduleName;
	// }

	// /**
	// * Get a map of all available extension modules. This includes both loaded
	// and unloaded modules. The map contains of paisr
	// *
	// * @return list of extension modules
	// */
	// public static Map<String, Class<? extends IScriptModule>>
	// getAvailableModules(final boolean findHidden) {
	// final Map<String, Class<? extends IScriptModule>> modules = new
	// HashMap<String, Class<? extends IScriptModule>>();
	//
	// final IConfigurationElement[] elements =
	// Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_MODULE_ID);
	// for (final IConfigurationElement element : elements) {
	//
	// // only add items marked as visible
	// if (Boolean.parseBoolean(element.getAttribute("visible")) || findHidden)
	// {
	// try {
	// final Object o = element.createExecutableExtension("class");
	// if (o instanceof IScriptModule) {
	// final String name = element.getAttribute("name");
	// modules.put(name, ((IScriptModule) o).getClass());
	// }
	// } catch (final CoreException e) {
	// // FIXME add log message
	// // Logger.warning("Could not load module.", e);
	// }
	// }
	// }
	//
	// return modules;
	// }

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
	private void createWrappers(final Object instance, String identifier, final boolean reload) {
		// script code to inject
		StringBuilder scriptCode = new StringBuilder();

		// use reflection to access methods
		for (final Method method : instance.getClass().getMethods()) {
			if (useAutoLoader(method)) {

				String preExecutionCode = getPreExecutionCode(method);
				String postExecutionCode = getPostExecutionCode(method);

				Set<String> methodNames = new HashSet<String>();
				methodNames.add(method.getName());
				String alias = method.getAnnotation(WrapToScript.class).alias();
				methodNames.addAll(Arrays.asList(alias.split(WrapToScript.DELIMITER)));

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
		}

		// use reflection to access static members
		if (!reload) {
			Field[] declaredFields = instance.getClass().getDeclaredFields();
			for (Field field : declaredFields) {
				if (Modifier.isStatic(field.getModifiers())) {
					if (field.getAnnotation(WrapToScript.class) != null)
						scriptCode.append(getWrapper().getConstantDefinition(field.getName().toUpperCase(), field));
				}
			}
		}

		// execute code
		String codeToInject = scriptCode.toString();
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
		return (method.getAnnotation(WrapToScript.class) != null);
	}

	/**
	 * Resolves a loaded module and returns the Java instance. Will only query previously loaded modules.
	 * 
	 * @param name
	 *            name of the module to resolve
	 */
	@WrapToScript
	public final Object findModule(final String name) {
		return mModuleNames.get(name);
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
	public final void exit(final Object value) {
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
	 * file system.
	 * 
	 * @param filename
	 *            name of file to be included
	 * @return result of include operation
	 * @throws Throwable
	 */
	@WrapToScript
	public final Object include(final String filename) {
		if (filename.contains("://")) {
			// seems to be a URL

			if (filename.startsWith(PROJECT)) {
				// project relative link
				Object currentFile = getScriptEngine().getExecutedFile();
				if (currentFile instanceof IFile) {
					IFile file = ((IFile) currentFile).getProject().getFile(new Path(filename.substring(PROJECT.length())));
					if (file.exists())
						return getScriptEngine().inject(file);
				}

			} else if (filename.startsWith(WORKSPACE)) {
				// absolute path within workspace
				IFile workspaceFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(filename.substring(WORKSPACE.length())));
				if (workspaceFile.exists())
					return getScriptEngine().inject(workspaceFile);

			} else {
				// generic URL
				try {
					URL url = new URL(filename);

					return getScriptEngine().inject(url.openStream());
				} catch (MalformedURLException e) {
					// TODO handle this exception (but for now, at least know it
					// happened)
					throw new RuntimeException(e);
				} catch (IOException e) {
					// TODO handle this exception (but for now, at least know it
					// happened)
					throw new RuntimeException(e);
				}
			}

		} else {
			// no URL, try to resolve

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
		throw new RuntimeException("cannot resolve \"" + filename + "\"");
	}

	// /**
	// * List all available (visible) modules. Returns a list of visible
	// modules. Loaded modules are indicated.
	// *
	// * @return string containing module information
	// */
	// @WrapToScript
	// public final String listModules() {
	// final Map<String, Class<? extends IScriptModule>> modules =
	// getAvailableModules(false);
	//
	// final StringBuffer output = new StringBuffer();
	//
	// // add header
	// output.append("available modules\n=================\n\n");
	//
	// // add modules
	// for (final String moduleName : modules.keySet()) {
	// output.append('\t');
	//
	// output.append(moduleName);
	// if (findModule(moduleName) != null)
	// output.append(" [LOADED]");
	//
	// output.append('\n');
	// }
	//
	// // write to default output
	// getScriptEngine().getOutputStream().print(output);
	//
	// return output.toString();
	// }
	//
	private String getPreExecutionCode(final Method method) {
		final StringBuffer code = new StringBuffer();

		for (final Object module : mModules) {
			if (module instanceof IScriptFunctionModifier)
				code.append(((IScriptFunctionModifier) module).getPreExecutionCode(method));
		}

		return code.toString();
	}

	private String getPostExecutionCode(final Method method) {
		final StringBuffer code = new StringBuffer();

		for (final Object module : mModules) {
			if (module instanceof IScriptFunctionModifier)
				code.append(((IScriptFunctionModifier) module).getPostExecutionCode(method));
		}

		return code.toString();
	}

	// @SuppressWarnings("unchecked")
	// public static final Class<IScriptModule> getModuleClass(final String
	// moduleIdentifier) {
	// final IConfigurationElement[] config =
	// Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_MODULE_ID);
	//
	// try {
	// for (final IConfigurationElement e : config) {
	// if (moduleIdentifier.equals(e.getAttribute("name"))) {
	// final Object o = e.createExecutableExtension("class");
	// if (o instanceof IScriptModule) {
	// return ((Class<IScriptModule>) o.getClass());
	// }
	// }
	// }
	// } catch (final Exception e) {
	// }
	//
	// return null;
	// }

	/**
	 * Print to standard output.
	 * 
	 * @param text
	 *            text to write to standard output
	 */
	@WrapToScript
	public final void print(final Object text) {
		getScriptEngine().getOutputStream().println(text);
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

	public void addModuleListener(final IModuleListener listener) {
		mModuleListeners.add(listener);
	}

	public void removeModuleListener(final IModuleListener listener) {
		mModuleListeners.remove(listener);
	}

	private void fireModuleEvent(final Object module, final int type) {
		for (Object listener : mModuleListeners.getListeners())
			((IModuleListener) listener).notifyModule(module, type);
	}

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
