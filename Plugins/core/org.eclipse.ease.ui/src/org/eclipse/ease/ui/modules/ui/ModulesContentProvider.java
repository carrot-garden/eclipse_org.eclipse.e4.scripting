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
package org.eclipse.ease.ui.modules.ui;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.ease.modules.ModuleDefinition;
import org.eclipse.ease.modules.ModuleHelper;
import org.eclipse.ease.ui.tools.AbstractVirtualTreeProvider;

public class ModulesContentProvider extends AbstractVirtualTreeProvider {

	private final boolean mModulesOnly;

	public ModulesContentProvider(boolean modulesOnly) {
		mModulesOnly = modulesOnly;
	}

	@Override
	public Object getParent(Object element) {

		if (element instanceof IPath)
			return ((IPath) element).removeLastSegments(1);
		if (element instanceof ModuleDefinition)
			return ((ModuleDefinition) element).getPath().removeLastSegments(1);
		if (element instanceof Field)
			return ModulesTools.getDeclaringModule((Field) element);
		if (element instanceof Method)
			return ModulesTools.getDeclaringModule((Method) element);

		return null;

	}

	@Override
	public Object[] getChildren(Object parentElement) {

		if ((parentElement instanceof ModuleDefinition) && !mModulesOnly) {
			List<Object> children = new ArrayList<Object>();

			// TODO wait for getModuleClass()
			children.addAll(ModuleHelper
					.getFields(((ModuleDefinition) parentElement)
							.getModuleClass()));

			children.addAll(ModuleHelper
					.getMethods(((ModuleDefinition) parentElement)
							.getModuleClass()));

			return children.toArray();

		}

		return super.getChildren(parentElement);

	};

	@Override
	protected void populateElements(Object inputElement) {

		if (inputElement instanceof Collection<?>) {

			for (Object module : (Collection<?>) inputElement) {

				if (module instanceof ModuleDefinition) {
					registerElement(((ModuleDefinition) module).getPath()
							.removeLastSegments(1), module);
				}
			}
		}

	}

	@Override
	public boolean hasChildren(Object element) {

		if ((element instanceof ModuleDefinition) && !mModulesOnly) {
			boolean hasChildren = false;

			Class<?> clazz = ((ModuleDefinition) element).getModuleClass();
			if (clazz == null)
				return false;

			hasChildren |= !ModuleHelper.getMethods(clazz).isEmpty();
			hasChildren |= !ModuleHelper.getFields(clazz).isEmpty();
			return hasChildren;
		}

		return super.hasChildren(element);
	}
}
