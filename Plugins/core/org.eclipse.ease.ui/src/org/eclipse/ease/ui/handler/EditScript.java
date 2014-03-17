package org.eclipse.ease.ui.handler;

import java.io.File;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.IFile;
import org.eclipse.ease.Logger;
import org.eclipse.ease.service.ScriptType;
import org.eclipse.ease.ui.ScriptEditorInput;
import org.eclipse.ease.ui.repository.IScript;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.IDE;

public class EditScript extends AbstractHandler implements IHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof IStructuredSelection) {
			for (Object element : ((IStructuredSelection) selection).toList()) {
				if (element instanceof IScript) {
					Object content = ((IScript) element).getContent();
					if ((content instanceof IFile) && (((IFile) content).exists())) {
						// open editor
						IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
						try {
							IDE.openEditor(page, (IFile) content);
						} catch (PartInitException e) {
							Logger.logError("Could not open editor for file " + content);
						}

					} else if ((content instanceof File) && (((File) content).exists())) {
						ScriptType type = ((IScript) element).getType();
						IEditorDescriptor editor = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor("foo." + type.getDefaultExtension());
						if (editor != null) {
							IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
							try {
								page.openEditor(new ScriptEditorInput((IScript) element), editor.getId());
							} catch (PartInitException e) {
								Logger.logError("Could not open editor for file " + content);
							}
						}
					}
				}
			}
		}

		return null;
	}
}
