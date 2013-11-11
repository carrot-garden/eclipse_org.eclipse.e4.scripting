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
package org.eclipse.ease.ui.actions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ease.ui.Activator;
import org.eclipse.ease.log.Logger;
import org.eclipse.ease.storedscript.service.IStoredScriptService;
import org.eclipse.jface.action.Action;


/**
 * Action use to reset all the registry of stored scrip
 * @author adaussy
 *
 */
public class RefreshStoredScriptAction extends Action {

	public RefreshStoredScriptAction(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		Job job = new Job("Refresh script registry") {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					IStoredScriptService.INSTANCE.rescanAllFiles();
				} catch (CoreException e) {
					e.printStackTrace();
					return Logger.createErrorStatus("Unable to refresh script registry", Activator.PLUGIN_ID);
				}
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.schedule();
		super.run();
	}

}
