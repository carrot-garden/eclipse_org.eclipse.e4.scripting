package org.eclipse.ease.modules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.ease.service.IScriptService;
import org.eclipse.ui.PlatformUI;

public abstract class AbstractEnvironment extends AbstractScriptModule implements IEnvironment {

	/** Stores ordering of wrapped elements. */
	private final List<Object> mModules = new ArrayList<Object>();

	/** Stores beautified names of loaded modules. */
	private final Map<String, Object> mModuleNames = new HashMap<String, Object>();

	private final ListenerList mModuleListeners = new ListenerList();

	/**
	 * Load a module. Loading a module generally enhances the script environment with new functions and variables. If a module was already loaded before, it
	 * gets refreshed and moved to the top of the module stack. When a module is loaded, all its dependencies are loaded too. So loading one module might change
	 * the whole module stack.
	 * 
	 * @param name
	 *            name of module to load
	 * @return loaded module instance
	 */
	@Override
	@WrapToScript
	public final Object loadModule(final String identifier) {
		Object module = getModule(identifier);
		if (module == null) {
			// not loaded yet
			final IScriptService scriptService = (IScriptService) PlatformUI.getWorkbench().getService(IScriptService.class);
			Map<String, ModuleDefinition> availableModules = scriptService.getAvailableModules();

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

				mModuleNames.put(identifier, module);
			}
		}
		if (module == null) {
			getScriptEngine().getErrorStream().append("Unable to find module with id " + identifier);
		}
		// create function wrappers
		wrap(module);

		// move module up to first position
		mModules.remove(module);
		mModules.add(0, module);

		return module;
	}

	/**
	 * Resolves a loaded module and returns the Java instance. Will only query previously loaded modules.
	 * 
	 * @param name
	 *            name of the module to resolve
	 * @return resolved module instance or <code>null</code>
	 */
	@Override
	@WrapToScript
	public final Object getModule(final String name) {
		return mModuleNames.get(name);
	}

	/**
	 * Resolves a loaded module by its class.
	 * 
	 * @param clazz
	 *            module class to look resolve
	 * @return resolved module instance or <code>null</code>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Object, U extends Class<T>> T getModule(final U clazz) {
		for (final Object module : getModules()) {
			if (clazz.isAssignableFrom(module.getClass()))
				return (T) module;
		}

		return null;
	}

	@Override
	public List<Object> getModules() {
		return Collections.unmodifiableList(mModules);
	}

	@Override
	@WrapToScript
	public final void print(final Object text) {
		getScriptEngine().getOutputStream().println(text);
	}

	@Override
	public void addModuleListener(final IModuleListener listener) {
		mModuleListeners.add(listener);
	}

	@Override
	public void removeModuleListener(final IModuleListener listener) {
		mModuleListeners.remove(listener);
	}

	protected void fireModuleEvent(final Object module, final int type) {
		for (Object listener : mModuleListeners.getListeners())
			((IModuleListener) listener).notifyModule(module, type);
	}

	protected abstract void wrap(Object module);
}
