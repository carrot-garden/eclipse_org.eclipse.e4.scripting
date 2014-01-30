package org.eclipse.ease.modules;

import java.util.List;

import org.eclipse.ease.IScriptEngine;

public interface IEnvironment {

	IScriptEngine getScriptEngine();

	Object getModule(final String name);

	<T extends Object, U extends Class<T>> T getModule(final U clazz);

	/**
	 * Retrieve a list of loaded modules. The returned list is read only.
	 * 
	 * @return list of modules (might be empty)
	 */
	List<Object> getModules();

	/**
	 * Print to standard output.
	 * 
	 * @param text
	 *            text to write to standard output
	 */
	void print(final Object text);

	void addModuleListener(final IModuleListener listener);

	void removeModuleListener(final IModuleListener listener);

	/**
	 * Load a module. Loading a module generally enhances the JavaScript environment with new functions and variables. If a module was already loaded before, it
	 * gets refreshed and moved to the top of the module stack. When a module is loaded, all its dependencies are loaded too. So loading one module might change
	 * the whole module stack.
	 * 
	 * @param name
	 *            name of module to load
	 * @return loaded module instance
	 */
	Object loadModule(final String moduleIdentifier);

}
