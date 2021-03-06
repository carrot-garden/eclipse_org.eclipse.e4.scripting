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
package org.eclipse.ease.ui.utils;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ease.storedscript.EASEProjectNature;

/**
 * Helper to handle script resources
 * 
 * @author adaussy
 * 
 */
public class ScriptResourceUtils {

	protected ScriptResourceUtils() {
	}

	public static boolean isScriptMonkeyProject(IProject project) {
		if(project != null && project.exists()) {
			try {
				return project.hasNature(EASEProjectNature.ESCRIPT_MONKEY_NATURE);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static boolean isEclipseMonkeyResource(IResource resource) {
		if(resource != null) {
			IProject project = resource.getProject();
			if(project.exists()) {
				return isScriptMonkeyProject(project) && isCorrectFileExtension(resource);
			} else {
				//Handle case when file is outside eclipse or the IProject has been deleted
				return isCorrectFileExtension(resource);
			}
		}
		return false;
	}

	public static boolean isCorrectFileExtension(IResource resource) {
		return true;
		/*
		 * TODO
		 */
		//		return ScriptService.getInstance().getLanguageStore().keySet().contains(resource.getFileExtension());
	}

	public static void addEScriptMoneyNature(IProject project) throws CoreException {
		if(project != null) {
			if(!isScriptMonkeyProject(project)) {
				IProjectDescription description = project.getDescription();
				String[] natures = description.getNatureIds();
				String[] newNatures = new String[natures.length + 1];
				System.arraycopy(natures, 0, newNatures, 0, natures.length);
				newNatures[natures.length] = EASEProjectNature.ESCRIPT_MONKEY_NATURE;
				description.setNatureIds(newNatures);
				project.setDescription(description, new NullProgressMonitor());
			}
		}
	}

}
