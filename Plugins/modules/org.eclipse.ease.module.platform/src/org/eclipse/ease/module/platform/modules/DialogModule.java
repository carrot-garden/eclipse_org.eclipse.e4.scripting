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
package org.eclipse.ease.module.platform.modules;

import org.eclipse.ease.common.RunnableWithResult;
import org.eclipse.ease.log.Logger;
import org.eclipse.ease.modules.AbstractScriptModule;
import org.eclipse.ease.modules.WrapToScript;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;


/**
 * Module to display dialogs
 * 
 * @author adaussy
 * 
 */
public class DialogModule extends AbstractScriptModule {

	public DialogModule() {
		super();
	}

	/**
	 * Return the active {@link Shell}
	 * 
	 * @return
	 */
	@WrapToScript
	public static Shell getActiveShell() {
		return Display.getDefault().getActiveShell();
	}

	/**
	 * Open a dialog
	 * 
	 * @param window
	 *        A Window to open
	 * @return window.open().
	 */
	@WrapToScript
	public static int openDialog(final Window window) {
		RunnableWithResult<Integer> run = new RunnableWithResult<Integer>() {

			private int result;

			@Override
			public void run() {
				this.result = window.open();

			}

			@Override
			public Integer getResult() {
				return result;
			}
		};
		Display.getDefault().syncExec(run);
		return run.getResult();
	}

	/**
	 * Display a message to the user
	 * 
	 * @param message
	 *        to display
	 */
	@WrapToScript
	public static void info(final String message) {
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				MessageDialog.openInformation(Display.getDefault().getActiveShell(), "Information", message);
			}
		});

	}



	/**
	 * Display a confirm dialog
	 * 
	 * @param title
	 *        of dialog
	 * @param message
	 *        Question to ask
	 * @return
	 */
	@WrapToScript
	public static boolean confirm(final String title, final String message) {
		RunnableWithResult<Boolean> runnable = new RunnableWithResult<Boolean>() {

			private boolean result;

			@Override
			public void run() {
				this.result = MessageDialog.openConfirm(Display.getDefault().getActiveShell(), title, message);
			}

			@Override
			public Boolean getResult() {
				return result;
			}
		};
		Display.getDefault().syncExec(runnable);
		return runnable.getResult();

	}

	/**
	 * Open a question dialog to the user
	 * 
	 * @param title
	 *        of dialog
	 * @param message
	 *        Question to ask
	 * @return
	 */
	@WrapToScript
	public static boolean question(final String title, final String message) {
		RunnableWithResult<Boolean> runnable = new RunnableWithResult<Boolean>() {

			private boolean result;

			@Override
			public void run() {
				this.result = MessageDialog.openQuestion(Display.getDefault().getActiveShell(), title, message);
			}

			@Override
			public Boolean getResult() {
				return result;
			}
		};
		Display.getDefault().syncExec(runnable);
		return runnable.getResult();

	}


	/**
	 * Display a error dialog
	 * 
	 * @param message
	 *        Error message
	 */
	@WrapToScript
	public static void error(final String message) {
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				ErrorDialog.openError(Display.getDefault().getActiveShell(), "Error", message, Logger.createErrorStatus(message, org.eclipse.ease.module.platform.Activator.PLUGIN_ID));
			}
		});

	}

	/**
	 * Open a dialog and ask to the user to select from a list of element
	 * 
	 * @param selectionOption
	 *        The list element from which the user shall choose
	 * @param labelProvider
	 *        The label provider used to display the elements
	 * @return An array of the selected objects
	 */
	@WrapToScript
	public static Object[] selectFromList(final Object[] selectionOption, final ILabelProvider labelProvider) {
		RunnableWithResult<Object[]> runnable = new RunnableWithResult<Object[]>() {

			private Object[] result;

			@Override
			public void run() {
				ElementListSelectionDialog dialog = new ElementListSelectionDialog(Display.getDefault().getActiveShell(), labelProvider);
				dialog.setElements(selectionOption);
				if(dialog.open() == ElementListSelectionDialog.OK) {
					this.result = dialog.getResult();
				}

			}

			@Override
			public Object[] getResult() {
				return result;
			}
		};
		Display.getDefault().syncExec(runnable);
		return runnable.getResult();

	}

	public static void openWindow(final Window window) {
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				window.open();
			}
		};
		Display.getDefault().syncExec(runnable);
	}




}
