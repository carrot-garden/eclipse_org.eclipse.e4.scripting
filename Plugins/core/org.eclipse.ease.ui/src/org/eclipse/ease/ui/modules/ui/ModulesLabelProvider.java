/*******************************************************************************
 * Copyright (c) 2014 Bernhard Wedl and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * code taken from
 * shttp://git.eclipse.org/c/platform/eclipse.platform.ui.git/tree/examples/org.eclipse.jface.snippets/Eclipse%20JFace%20Snippets/org/eclipse/jface/snippets/viewers/Snippet015CustomTooltipsForTree.java
 *
 * Contributors:
 *     Bernhard Wedl - initial API and implementation
 *******************************************************************************/

package org.eclipse.ease.ui.modules.ui;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.eclipse.core.runtime.IPath;
import org.eclipse.ease.modules.ModuleDefinition;
import org.eclipse.ease.ui.Activator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class ModulesLabelProvider extends LabelProvider {

	@Override
	public String getText(Object element) {

		if (element instanceof ModuleDefinition)
			return ((ModuleDefinition) element).getName();

		if (element instanceof IPath)
			return ((IPath) element).lastSegment();

		if (element instanceof Field)
			return ((Field) element).getName();

		if (element instanceof Method)
			return ModulesTools.getSignature((Method) element);

		return super.getText(element);
	}

	@Override
	public Image getImage(Object element) {

		if (element instanceof IPath)
			return Activator.getImage("org.eclipse.ui",
					"/icons/full/obj16/fldr_obj.gif", true);

		if (element instanceof ModuleDefinition) {
			ImageDescriptor icon = ((ModuleDefinition) element)
					.getImageDescriptor();
			if (icon == null)
				return Activator.getImage("org.eclipse.ease.ui",
						"/icons/full/obj16/Module_16x16.png", true);

			return icon.createImage();
		}

		if (element instanceof Method)
			return Activator.getImage("org.eclipse.ease.ui",
					"/icons/full/obj16/Method_16x16.png", true);

		if (element instanceof Field)
			return Activator.getImage("org.eclipse.ease.ui",
					"/icons/full/obj16/Field_16x16.png", true);

		return null;

	}

}
