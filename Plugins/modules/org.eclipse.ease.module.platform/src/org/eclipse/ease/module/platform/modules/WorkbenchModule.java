/*******************************************************************************
F * Copyright (c) 2013 Atos
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Arthur Daussy - initial implementation
 *******************************************************************************/
package org.eclipse.ease.module.platform.modules;

import org.eclipse.ease.common.RunnableWithResult;
import org.eclipse.ease.log.Logger;
import org.eclipse.ease.modules.AbstractScriptModule;
import org.eclipse.ease.modules.ScriptParameter;
import org.eclipse.ease.modules.WrapToScript;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.dialogs.ShowViewDialog;
import org.eclipse.ui.views.IViewDescriptor;

/**
 * Module used to interact with the workbench
 * 
 * @author adaussy
 * 
 */
public class WorkbenchModule extends AbstractScriptModule {

	public WorkbenchModule() {
	}

	/**
	 * Return the active workbench
	 * 
	 * @return
	 */
	@WrapToScript
	public static IWorkbench getActiveWorkbench() {
		return PlatformUI.getWorkbench();
	}

	/**
	 * Return the Active Window {@link IWorkbench#getActiveWorkbenchWindow()}
	 * 
	 * @return
	 */
	@WrapToScript
	public static IWorkbenchWindow getActiveWindow() {
		RunnableWithResult<IWorkbenchWindow> runnable = new RunnableWithResult<IWorkbenchWindow>() {

			private IWorkbenchWindow result;

			@Override
			public void run() {
				this.result = getActiveWorkbench().getActiveWorkbenchWindow();
			}

			@Override
			public IWorkbenchWindow getResult() {
				return result;
			}
		};
		Display.getDefault().syncExec(runnable);
		return runnable.getResult();
	}

	/**
	 * Return the active shell
	 * 
	 * @return The active shell
	 */
	@WrapToScript
	public static Shell getActiveShell() {
		return getActiveWindow().getShell();
	}

	/**
	 * Return the active page {@link IWorkbenchWindow#getActivePage()}
	 * 
	 * @return
	 */
	@WrapToScript
	public static IWorkbenchPage getActivePage() {
		return getActiveWindow().getActivePage();
	}

	/**
	 * Return the current editor
	 * 
	 * @return The current editor
	 */
	@WrapToScript
	public static IEditorPart getActiveEditor() {
		return getActiveWindow().getActivePage().getActiveEditor();
	}

	/**
	 * Display the view with the specific in the workbench
	 * 
	 * @param viewID
	 *        The id of the view to show
	 * @return
	 */
	@WrapToScript
	public IViewPart showView(@ScriptParameter(name = "viewID", optional = true) String viewID) {
		if((viewID == null) || (viewID.trim().isEmpty())) {
			ShowViewDialog dialog = new ShowViewDialog(getActiveWindow(), WorkbenchPlugin.getDefault().getViewRegistry());
			if(dialog.open() != Window.OK) {
				return null;
			}
			IViewDescriptor[] result = dialog.getSelection();
			if(result == null || result.length == 0) {
				return null;
			}
			viewID = result[0].getId();
		}
		RunnableWithResult<IViewPart> showViewRunnable = new ShowViewRunnable(viewID);
		Display.getDefault().asyncExec(showViewRunnable);
		return showViewRunnable.getResult();
	}

	private class ShowViewRunnable implements RunnableWithResult<IViewPart> {

		private final String id;

		private IViewPart result;

		public ShowViewRunnable(String id) {
			super();
			this.id = id;
		}

		@Override
		public void run() {
			try {
				this.result = getActivePage().showView(id);
			} catch (PartInitException e) {
				e.printStackTrace();
				Logger.logError("Unable to show view " + id + " cause of :\n" + e.getMessage());
			}

		}

		@Override
		public IViewPart getResult() {
			return result;
		}
	}
}
