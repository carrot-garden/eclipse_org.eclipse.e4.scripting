/*******************************************************************************
 * Copyright (c) 2013 Atos
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Arthur Daussy - initial implementation
 *******************************************************************************/
package org.eclipse.ease.injection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

import org.eclipse.ease.IScriptEngine;
import org.eclipse.ease.Script;
import org.eclipse.ease.debug.ITracingConstant;
import org.eclipse.ease.debug.Tracer;
import org.eclipse.ease.modules.BootStrapper;
import org.eclipse.ease.modules.IModuleWrapper;
import org.eclipse.swt.widgets.Display;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

/**
 * Helper class used to inject code into engines
 * 
 * @author adaussy
 * 
 */
public class CodeInjectorUtils {

	/**
	 * Instantiate an Java object from its class TODO Test this
	 * 
	 * @param classToInject
	 * @param engine
	 * @param paramaters
	 * @param variableName
	 * @return
	 */
	public static Object instancianteObject(final Class<?> classToInject, final IScriptEngine engine, final Object[] paramaters, final String variableName) {
		Object objectToInject = null;
		// Inject variable
		InstanciateClassRunnable tt = new InstanciateClassRunnable(classToInject, paramaters);
		if (engine.isUI()) {
			Display.getDefault().syncExec(tt);

		} else {
			tt.run();
		}
		objectToInject = tt.getResult();

		if (objectToInject != null) {
			((IScriptEngine) objectToInject).setVariable(variableName, objectToInject);
			if (ITracingConstant.ENVIRONEMENT_MODULE_WRAPPER_TRACING) {
				Tracer.logInfo("[Environement Module] Add variable to engine :\n " + variableName + " with value" + objectToInject);
			}
		}
		return objectToInject;
	}

	/**
	 * Inject code into a engine using reflection on a java class. The java object has to already be injected in the engine see
	 * {@link CodeInjectorUtils#injectJavaVariable(String, Object, IScriptEngine)}
	 * 
	 * @param classToInject
	 *            The class to inject
	 * @param methodToInjectPredicate
	 *            Predicate to filter the method to inject
	 * @param fieldToInjectPredicate
	 *            Predicate to filter filed to inject
	 * @param preExceutionCode
	 *            Function to add code before the execution of an injected method
	 * @param postExceutionCode
	 *            Function to add code after the execution of an injected method
	 * @param injectedVariableName
	 *            The name of the injected variable in the script
	 * @param engine
	 *            The {@link IScriptEngine}
	 * @param reload
	 *            Set to true if the class has already been loaded
	 */
	public static void injectClass(final Class<?> classToInject, final Predicate<Method> methodToInjectPredicate,
			final Predicate<Field> fieldToInjectPredicate, final Function<Method, String> preExceutionCode, final Function<Method, String> postExceutionCode,
			final String injectedVariableName, final IScriptEngine engine, final String injectionName) {
		// script code to inject
		StringBuffer scriptCode = new StringBuffer();
		Collection<Method> methodToInject = Collections2.filter(Lists.newArrayList(classToInject.getMethods()), methodToInjectPredicate);
		// use reflection to access methods
		for (final Method method : methodToInject) {

			String code = getWrapper(engine).createFunctionWrapper(null, injectedVariableName, method);
			if ((code != null) && !code.isEmpty()) {
				scriptCode.append(code);
				scriptCode.append('\n');
			}
		}

		// use reflection to access static members
		Collection<Field> declaredFields = Collections2.filter(Lists.newArrayList(classToInject.getDeclaredFields()), fieldToInjectPredicate);
		for (Field field : declaredFields) {
			scriptCode.append(getWrapper(engine).createStaticFieldWrapper(null, field));
		}

		// execute code
		String codeToInject = scriptCode.toString();
		if (ITracingConstant.ENVIRONEMENT_MODULE_WRAPPER_TRACING) {
			Tracer.logInfo("[Class injection] Injecting code:\n" + codeToInject);
		}
		engine.inject(new Script(injectionName, scriptCode.toString()));
	}

	/**
	 * Get the {@link IModuleWrapper} from a engine
	 * 
	 * @param scriptEngine
	 * @return
	 */
	public static IModuleWrapper getWrapper(final IScriptEngine scriptEngine) {
		return BootStrapper.getWrapper(scriptEngine.getDescription().getID());
	}

	/**
	 * Inject a module into an engine WRANING : This method is not use for now
	 * 
	 * @param module
	 * @param reload
	 * @param engine
	 */
	// public void injectModules(final IScriptModule module, final boolean reload, IScriptEngine engine) {
	// Predicate<Method> methodSelector = new Predicate<Method>() {
	//
	// @Override
	// public boolean apply(Method arg0) {
	// return arg0.getAnnotation(WrapToScript.class) != null;
	// }
	// };
	// Predicate<Field> fieldSelector = new Predicate<Field>() {
	//
	// @Override
	// public boolean apply(Field arg0) {
	// return Modifier.isStatic(arg0.getModifiers()) && arg0.getAnnotation(WrapToScript.class) != null;
	// }
	// };
	// injectClass(module.getClass(), methodSelector, fieldSelector, null, null, EnvironmentModule.getRegisteredModuleName(module.getModuleName()), engine,
	// "Injecting module " + module.getModuleName());
	// }

	/**
	 * Inject a Java varaible into an engine
	 * 
	 * @param variableName
	 * @param objectToInject
	 * @param engine
	 */
	public static void injectJavaVariable(final String variableName, final Object objectToInject, final IScriptEngine engine) {
		if (objectToInject != null) {
			String registeredModuleName = variableName;
			engine.setVariable(registeredModuleName, objectToInject);
			if (ITracingConstant.ENVIRONEMENT_MODULE_WRAPPER_TRACING) {
				Tracer.logInfo("[Environement Module] Add variable to engine :\n " + registeredModuleName + " with value" + objectToInject);
			}
		}

	}

	/**
	 * Predicate to use for preventing method injection
	 */
	public static final Predicate<Method> NO_METHOD_PREDICATE = new Predicate<Method>() {

		@Override
		public boolean apply(final Method arg0) {
			return false;
		}
	};

	/**
	 * Predication to use for preventing field injection
	 */
	public static final Predicate<Field> NO_FIELD_PREDICATE = new Predicate<Field>() {

		@Override
		public boolean apply(final Field arg0) {
			return false;
		}
	};

	/**
	 * Runnable to instantiate a class using a array of parameter
	 * 
	 * @author adaussy
	 * 
	 */
	protected static class InstanciateClassRunnable implements Runnable {

		private Object result;

		private final Class<?> toInstantiate;

		private final Object[] parameters;

		public InstanciateClassRunnable(final Class<?> toInstantiate, final Object[] parameters) {
			this.toInstantiate = toInstantiate;
			this.parameters = parameters;
		}

		@Override
		public void run() {
			Collection<Class> types = Collections2.transform(Lists.newArrayList(parameters), new Function<Object, Class>() {

				@Override
				public Class apply(final Object arg0) {
					return arg0.getClass();
				}
			});
			Constructor<?> constructor;
			try {
				constructor = toInstantiate.getConstructor(types.toArray(new Class[parameters.length]));
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
				return;
			} catch (SecurityException e) {
				e.printStackTrace();
				return;
			}
			if (constructor != null) {
				try {
					result = constructor.newInstance(parameters);
				} catch (InstantiationException e) {
					e.printStackTrace();
					return;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					return;
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					return;
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					return;
				}
			}

		}

		protected boolean isCorrectConstructor(final Constructor con) {
			Class[] paramTypes = con.getParameterTypes();
			if (paramTypes.length == parameters.length) {
				for (int index = 0; index < paramTypes.length; index++) {
					if (!paramTypes[index].isAssignableFrom(parameters[index].getClass())) {
						return false;
					}
				}

			}
			return true;
		}

		/**
		 * @return the result
		 */
		public Object getResult() {
			return result;
		}
	}
}
