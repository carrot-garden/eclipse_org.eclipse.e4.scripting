/*******************************************************************************
 * Copyright (c) 2014 Bernhard Wedl and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bernhard Wedl - initial API and implementation
 *******************************************************************************/
package org.eclipse.ease.modules;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ModuleHelper {

	@Deprecated
	private ModuleHelper() {
	}

	/**
	 * Returns a list of exported methods. Any public method marked by a @WrapToScript annotation is exported. If no annotations are found all public methods
	 * are returned.
	 * 
	 * @param clazz
	 *            class to be evaluated
	 * @return list of methods
	 */
	public static List<Method> getMethods(final Class<?> clazz) {

		Method[] declaredMethods = clazz.getMethods();
		if (declaredMethods.length == 0)
			return Collections.emptyList();

		List<Method> methods = new ArrayList<Method>();
		boolean wrapping = ModuleHelper.hasWrapToScript(clazz);
		for (Method method : declaredMethods) {
			if ((Modifier.isPublic(method.getModifiers()) && (!wrapping || method.isAnnotationPresent(WrapToScript.class))))
				methods.add(method);
		}

		return methods;
	}

	/**
	 * Returns a List of exported fields. Any public static field marked by a @WrapToScript annotation is exported. If no annotations are found all public
	 * static fields are returned.
	 * 
	 * @param clazz
	 *            Class to be evaluated
	 * @return List of Fields
	 */
	public static List<Field> getFields(final Class<?> clazz) {

		Field[] declaredFields = clazz.getDeclaredFields();
		if (declaredFields.length == 0)
			return Collections.emptyList();

		List<Field> fields = new ArrayList<Field>();
		boolean wrapping = ModuleHelper.hasWrapToScript(clazz);
		for (Field field : declaredFields) {
			if ((Modifier.isStatic(field.getModifiers()))
					&& (Modifier.isPublic(field.getModifiers()) && (!wrapping || field.isAnnotationPresent(WrapToScript.class))))
				fields.add(field);
		}

		return fields;
	}

	/**
	 * Returns if the Class to be evaluated contains {@link WrapToScript} annotations.
	 * 
	 * @param clazz
	 *            class to be evaluated
	 * @return <code>true</code> when clazz contains {@link WrapToScript} annotations
	 */
	private static boolean hasWrapToScript(final Class<?> clazz) {

		for (Field field : clazz.getDeclaredFields()) {
			if (field.isAnnotationPresent(WrapToScript.class))
				return true;
		}

		for (Method method : clazz.getMethods()) {
			if (method.isAnnotationPresent(WrapToScript.class))
				return true;
		}

		return false;
	}
}
