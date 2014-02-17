package org.eclipse.ease.ui.scripts.repository.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ease.ui.repository.IScript;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.menus.IMenuService;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.progress.UIJob;

public class UIIntegration extends UIJob {
	private final Map<String, ScriptContributionFactory> fContributionFactories = new HashMap<String, ScriptContributionFactory>();

	private final RepositoryService fRepositoryService;

	public UIIntegration(RepositoryService repositoryService) {
		super("Update script UI components");

		fRepositoryService = repositoryService;
	}

	@Override
	public IStatus runInUIThread(IProgressMonitor monitor) {
		if (PlatformUI.getWorkbench().getActiveWorkbenchWindow() == null) {
			// we might get called before the workbench is loaded.
			// in that case delay execution until the workbench is ready
			PlatformUI.getWorkbench().addWindowListener(new IWindowListener() {

				@Override
				public void windowOpened(IWorkbenchWindow window) {
				}

				@Override
				public void windowDeactivated(IWorkbenchWindow window) {
				}

				@Override
				public void windowClosed(IWorkbenchWindow window) {
				}

				@Override
				public void windowActivated(IWorkbenchWindow window) {
					PlatformUI.getWorkbench().removeWindowListener(this);
					schedule();
				}
			});

		} else {
			for (IScript script : new HashSet<IScript>(fRepositoryService.getScripts())) {
				String toolbarLocation = script.getParameters().get("toolbar");
				if (toolbarLocation != null) {
					// map to a real location
					toolbarLocation = LocationHelper.toLocation(toolbarLocation);
					if (toolbarLocation != null) {

						// get contribution factory for toolbar
						ScriptContributionFactory contributionFactory = fContributionFactories.get("toolbar:" + toolbarLocation);
						if (contributionFactory == null) {
							// create a contribution factory
							contributionFactory = new ScriptContributionFactory("toolbar:" + toolbarLocation, null);
							fContributionFactories.put("toolbar:" + toolbarLocation, contributionFactory);

							final IMenuService menuService = (IMenuService) PlatformUI.getWorkbench().getService(IMenuService.class);
							menuService.addContributionFactory(contributionFactory);
						}
						// add script
						contributionFactory.addScript(script);

						// views already visible (not in the background of some part stack) will not use the contribution factory
						IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(toolbarLocation);
						if (view instanceof ViewPart) {
							for (IContributionItem item : contributionFactory.createContributions(ScriptContributionFactory.STATIC_ID, PlatformUI
									.getWorkbench().getActiveWorkbenchWindow())) {
								view.getViewSite().getActionBars().getToolBarManager().add(item);
								System.out.println("added static toolbar entry");
							}

							// refresh view

							view.getSite().getPage().addPartListener(new IPartListener() {

								@Override
								public void partActivated(IWorkbenchPart part) {
									if (part instanceof ViewPart) {
										IToolBarManager toolBarManager = ((ViewPart) part).getViewSite().getActionBars().getToolBarManager();
										if (toolBarManager.find(ScriptContributionFactory.DYNAMIC_ID) != null) {
											// this toolbar contains script elements from the registered contribution factory

											// remove static contributions
											IContributionItem item = toolBarManager.find(ScriptContributionFactory.STATIC_ID);
											while (item != null) {
												toolBarManager.remove(item);
												System.out.println("removed static toolbar entry");
												item = toolBarManager.find(ScriptContributionFactory.STATIC_ID);
											}

											((ViewPart) part).getViewSite().getActionBars().updateActionBars();
										}
									}
								}

								@Override
								public void partBroughtToTop(IWorkbenchPart part) {
									// TODO Auto-generated method stub

								}

								@Override
								public void partClosed(IWorkbenchPart part) {
									// TODO Auto-generated method stub

								}

								@Override
								public void partDeactivated(IWorkbenchPart part) {
									// TODO Auto-generated method stub

								}

								@Override
								public void partOpened(IWorkbenchPart part) {
									// TODO Auto-generated method stub

								}

							});

							view.getViewSite().getActionBars().updateActionBars();
						}
					}

				}

				// String menuLocation = script.getParameter("menu");
			}
		}

		return Status.OK_STATUS;
	}
}
