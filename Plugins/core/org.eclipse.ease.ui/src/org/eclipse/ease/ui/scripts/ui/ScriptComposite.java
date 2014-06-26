/*******************************************************************************
 * Copyright (c) 2013 Christian Pontesegger and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Christian Pontesegger - initial API and implementation
 *******************************************************************************/
package org.eclipse.ease.ui.scripts.ui;

import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.ease.IScriptEngine;
import org.eclipse.ease.IScriptEngineProvider;
import org.eclipse.ease.ui.Activator;
import org.eclipse.ease.ui.repository.IScript;
import org.eclipse.ease.ui.scripts.repository.IRepositoryService;
import org.eclipse.ease.ui.scripts.repository.IScriptListener;
import org.eclipse.ease.ui.scripts.repository.impl.ScriptRepositoryEvent;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.menus.IMenuService;

/**
 * SWT Composite that displays available macros. Implemented as a tree viewer.
 */
public class ScriptComposite extends Composite implements IScriptListener {
	private final TreeViewer treeViewer;

	private IDoubleClickListener fDoubleClickListener = new IDoubleClickListener() {

		@Override
		public void doubleClick(final DoubleClickEvent event) {

			Object element = ((IStructuredSelection) event.getSelection()).getFirstElement();

			if ((element instanceof IScript) && (fEngineProvider != null)) {
				final IScriptEngine scriptEngine = fEngineProvider.getScriptEngine();
				if (scriptEngine != null)
					scriptEngine.executeAsync("include('script:/" + ((IScript) element).getPath() + "');");
			}
		}
	};

	private IScriptEngineProvider fEngineProvider = null;

	/**
	 * Constructor creating the script tree viewer.
	 * 
	 * @param engineProvider
	 *            component providing script support
	 * @param site
	 *            site to implement this component on
	 * @param parent
	 *            parent SWT element
	 * @param style
	 *            composite style flags
	 */
	public ScriptComposite(final IScriptEngineProvider engineProvider, final IWorkbenchPartSite site, final Composite parent, final int style) {
		super(parent, style);
		fEngineProvider = engineProvider;

		setLayout(new FillLayout(SWT.HORIZONTAL));

		treeViewer = new TreeViewer(this, SWT.BORDER);

		treeViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof IPath)
					return ((IPath) element).lastSegment();

				if (element instanceof IScript)
					return ((IScript) element).getName();

				return super.getText(element);
			}

			@Override
			public Image getImage(final Object element) {
				if (element instanceof IPath)
					return Activator.getImage("org.eclipse.ui", "/icons/full/obj16/fldr_obj.gif", true);

				if (element instanceof IScript)
					return Activator.getImage("org.eclipse.ease.ui", "/images/script.gif", true);

				return super.getImage(element);
			}
		});
		treeViewer.setContentProvider(new ScriptContentProvider());

		treeViewer.setComparator(new ViewerComparator() {
			@Override
			public int category(final Object element) {
				return (element instanceof IPath) ? 0 : 1;
			}

			// @Override
			// public int compare(Viewer viewer, Object e1, Object e2) {
			// super.compare(viewer, e1, e2)
			// return e1.toString().compareTo(e2.toString());
			// }
		});

		final IRepositoryService repositoryService = (IRepositoryService) PlatformUI.getWorkbench().getService(IRepositoryService.class);
		treeViewer.setInput(repositoryService);

		if (fDoubleClickListener != null)
			treeViewer.addDoubleClickListener(fDoubleClickListener);

		// add listener for script repository changes
		repositoryService.addScriptListener(this);

		// add context menu support
		final MenuManager menuManager = new MenuManager();
		final Menu menu = menuManager.createContextMenu(treeViewer.getTree());
		treeViewer.getTree().setMenu(menu);
		site.registerContextMenu(menuManager, treeViewer);
		site.setSelectionProvider(treeViewer);

		// add dynamic context menu entries
		final IMenuService menuService = (IMenuService) PlatformUI.getWorkbench().getService(IMenuService.class);
		menuService.addContributionFactory(new ScriptContextMenuEntries("popup:" + site.getId()));
		menuManager.setRemoveAllWhenShown(true);

		// add DND support
		ScriptDragSource.addDragSupport(treeViewer);

		// register for change events
		repositoryService.addScriptListener(this);
	}

	// TODO change this filter to scripttype
	public void setEngine(final String engineID) {
		treeViewer.setFilters(new ViewerFilter[] { new ScriptEngineFilter(engineID) });
	}

	@Override
	public void dispose() {
		final IRepositoryService repositoryService = (IRepositoryService) PlatformUI.getWorkbench().getService(IRepositoryService.class);
		repositoryService.removeScriptListener(this);

		super.dispose();
	}

	public void setDoubleClickListener(final IDoubleClickListener doubleClickListener) {
		if ((fDoubleClickListener != null) && (treeViewer != null))
			treeViewer.removeDoubleClickListener(fDoubleClickListener);

		fDoubleClickListener = doubleClickListener;

		if ((fDoubleClickListener != null) && (treeViewer != null))
			treeViewer.addDoubleClickListener(fDoubleClickListener);
	}

	@Override
	public void notify(final ScriptRepositoryEvent event) {
		switch (event.getType()) {
		case ScriptRepositoryEvent.PARAMETER_CHANGE:
			Map<String, String> eventData = (Map<String, String>) event.getEventData();
			if (!eventData.containsKey("name"))
				return;

			// name changed, fall through

		case ScriptRepositoryEvent.DELETE:
			// fall through

		case ScriptRepositoryEvent.ADD:
			// FIXME needs some performance improvements on multiple script updates
			Display.getDefault().asyncExec(new Runnable() {

				@Override
				public void run() {
					treeViewer.refresh();
				}
			});
		}
	}
}
