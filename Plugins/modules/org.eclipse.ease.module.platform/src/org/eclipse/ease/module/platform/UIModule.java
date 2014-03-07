package org.eclipse.ease.module.platform;

import org.eclipse.core.resources.IFile;
import org.eclipse.ease.common.RunnableWithResult;
import org.eclipse.ease.modules.ScriptParameter;
import org.eclipse.ease.modules.WrapToScript;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.IViewDescriptor;
import org.eclipse.ui.views.IViewRegistry;

public class UIModule {
	/**
	 * Displays an info dialog. Needs UI to be available.
	 * 
	 * @param title
	 *            dialog title
	 * @param message
	 *            dialog message
	 */
	@WrapToScript
	public void showInfoDialog(final String title, final String message) {
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				MessageDialog.openInformation(Display.getDefault().getActiveShell(), title, message);
			}
		});
	}

	/**
	 * Displays a question dialog. Contains yes/no buttons. Needs UI to be available.
	 * 
	 * @param title
	 *            dialog title
	 * @param message
	 *            dialog message
	 * @return <code>true</code> when 'yes' was pressed, <code>false</code> otherwise
	 */
	@WrapToScript
	public boolean showQuestionDialog(final String title, final String message) {

		final RunnableWithResult<Boolean> runnable = new RunnableWithResult<Boolean>() {

			private boolean fResult;

			@Override
			public void run() {
				fResult = MessageDialog.openQuestion(Display.getDefault().getActiveShell(), title, message);
			}

			@Override
			public Boolean getResult() {
				return fResult;
			}
		};

		Display.getDefault().syncExec(runnable);

		return runnable.getResult();
	}

	/**
	 * Displays an input dialog. Contains yes/no buttons. Needs UI to be available.
	 * 
	 * @param title
	 *            dialog title
	 * @param message
	 *            dialog message
	 * @return <code>true</code> when 'yes' was pressed, <code>false</code> otherwise
	 */
	@WrapToScript
	public String showInputDialog(final String title, final String message, @ScriptParameter(optional = true, defaultValue = "") final String initialValue) {

		final RunnableWithResult<String> runnable = new RunnableWithResult<String>() {

			private String fResult = null;

			@Override
			public void run() {
				InputDialog dialog = new InputDialog(Display.getDefault().getActiveShell(), title, message, initialValue, null);
				if (dialog.open() == Window.OK)
					fResult = dialog.getValue();
			}

			@Override
			public String getResult() {
				return fResult;
			}
		};

		Display.getDefault().syncExec(runnable);

		return runnable.getResult();
	}

	/**
	 * Displays a confirmation dialog.
	 * 
	 * @param title
	 *            dialog title
	 * @param message
	 *            dialog message
	 * @return <code>true</code> when accepted
	 */
	@WrapToScript
	public boolean showConfirmDialog(final String title, final String message) {
		RunnableWithResult<Boolean> runnable = new RunnableWithResult<Boolean>() {

			private boolean fResult;

			@Override
			public void run() {
				fResult = MessageDialog.openConfirm(Display.getDefault().getActiveShell(), title, message);
			}

			@Override
			public Boolean getResult() {
				return fResult;
			}
		};
		Display.getDefault().syncExec(runnable);

		return runnable.getResult();
	}

	/**
	 * Displays a warning dialog.
	 * 
	 * @param title
	 *            dialog title
	 * @param message
	 *            dialog message
	 */
	@WrapToScript
	public void showWarningDialog(final String title, final String message) {
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				MessageDialog.openWarning(Display.getDefault().getActiveShell(), title, message);
			}
		});
	}

	/**
	 * Displays an error dialog.
	 * 
	 * @param title
	 *            dialog title
	 * @param message
	 *            dialog message
	 */
	@WrapToScript
	public void showErrorDialog(final String title, final String message) {
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				MessageDialog.openError(Display.getDefault().getActiveShell(), title, message);
			}
		});
	}

	/**
	 * Close the application. On unsaved editors user will be asked to save before closing.
	 */
	@WrapToScript
	public void exitApplication() {
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				PlatformUI.getWorkbench().close();
			}
		});
	}

	/**
	 * Opens a view by given Name or id. When <i>name</i> does not match any known view id we try to match it with a view title. When found the view is opened.
	 * If the view is already visible it will be given focus.
	 * 
	 * @param name
	 *            name or id of viw to open
	 * @return view instance or <code>null</code>
	 * @throws PartInitException
	 *             when view cannot be created
	 */
	@WrapToScript(alias = "openView")
	public IViewPart showView(final String name) throws PartInitException {
		// find view ID
		final String viewID = getIDForName(name);

		if (viewID != null) {
			RunnableWithResult<IViewPart> runnable = new RunnableWithResult<IViewPart>() {

				private IViewPart fResult = null;

				@Override
				public void run() {
					try {
						try {
							fResult = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(viewID);
						} catch (final NullPointerException e) {
							if (PlatformUI.getWorkbench().getWorkbenchWindowCount() > 0)
								fResult = PlatformUI.getWorkbench().getWorkbenchWindows()[0].getActivePage().showView(viewID);
						}
					} catch (PartInitException e) {
					}
				}

				@Override
				public IViewPart getResult() {
					return fResult;
				}
			};

			Display.getDefault().syncExec(runnable);
			return runnable.getResult();
		}

		return null;
	}

	/**
	 * Opens a file in an editor.
	 * 
	 * @param file
	 *            workspace file to open
	 * @return editor instance or <code>null</code>
	 * @throws PartInitException
	 *             when editor cannot be created
	 */
	@WrapToScript(alias = "openEditor")
	public IEditorPart showEditor(final IFile file) throws PartInitException {
		final IEditorDescriptor descriptor = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(file.getName());

		try {
			return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new FileEditorInput(file), descriptor.getId());
		} catch (final NullPointerException e) {
			if (PlatformUI.getWorkbench().getWorkbenchWindowCount() > 0)
				return PlatformUI.getWorkbench().getWorkbenchWindows()[0].getActivePage().openEditor(new FileEditorInput(file), descriptor.getId());
		}

		return null;
	}

	/**
	 * Get the current selection. If <i>partID</i> is provided, the selection of the given part is returned. Otherwise the selection of the current active part
	 * is returned.
	 * 
	 * @param name
	 *            name or ID of part to get selection from
	 * @return current selection
	 */
	@WrapToScript
	public ISelection getSelection(@ScriptParameter(optional = true, defaultValue = ScriptParameter.NULL) final String name) {
		final ISelectionService selectionService = PlatformUI.getWorkbench().getWorkbenchWindows()[0].getSelectionService();

		if ((name != null) && (!name.isEmpty())) {
			String partID = getIDForName(name);
			if (partID != null)
				return selectionService.getSelection(partID);

			return null;
		}

		return selectionService.getSelection();
	}

	/**
	 * Converts selection to a consumable form. Table/Tree selections are transformed into Object[], Text selections into the selected String.
	 * 
	 * @param selection
	 *            selection to convert
	 * @return converted elements
	 */
	@WrapToScript
	public Object convertSelection(final ISelection selection) {
		if (selection instanceof IStructuredSelection)
			return ((IStructuredSelection) selection).toArray();

		if (selection instanceof ITextSelection)
			return ((ITextSelection) selection).getText();

		return null;
	}

	/**
	 * Find ID for a given view name. If <i>name</i> already contains a valid id, it will be returned.
	 * 
	 * @param name
	 *            name of view
	 * @return view ID or <code>null</code>
	 */
	private String getIDForName(final String name) {
		String id = null;

		IViewRegistry viewRegistry = PlatformUI.getWorkbench().getViewRegistry();
		for (IViewDescriptor descriptor : viewRegistry.getViews()) {
			if (descriptor.getId().equals(name)) {
				id = descriptor.getId();
				break;
			} else if (descriptor.getLabel().equals(name)) {
				id = descriptor.getId();
				// continue as we might have a match with an ID later
			}
		}

		return id;
	}
}
