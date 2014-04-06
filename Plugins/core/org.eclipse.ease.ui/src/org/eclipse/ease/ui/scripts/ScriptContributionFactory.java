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
package org.eclipse.ease.ui.scripts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.ease.ui.repository.IScript;
import org.eclipse.ease.ui.scripts.repository.IRepositoryService;
import org.eclipse.ease.ui.scripts.ui.ScriptPopup;
import org.eclipse.ease.ui.scripts.ui.ScriptPopupMenu;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.menus.AbstractContributionFactory;
import org.eclipse.ui.menus.IContributionRoot;
import org.eclipse.ui.menus.IMenuService;
import org.eclipse.ui.services.IServiceLocator;

/**
 * Factory adding scripts to dynamically populated menu.
 */
public final class ScriptContributionFactory extends AbstractContributionFactory {

	/**
	 * Add a context menu to a given element.
	 * 
	 * @param menuID
	 *            ID of menu to create (typically: popup:view.id)
	 */
	public static void addContextMenu(final String menuID) {
		final IMenuService menuService = (IMenuService) PlatformUI.getWorkbench().getService(IMenuService.class);
		menuService.addContributionFactory(new ScriptContributionFactory(menuID));
	}

	/**
	 * Private constructor.
	 * 
	 * @param menuID
	 *            menu ID to create contribution factory for.
	 */
	private ScriptContributionFactory(final String menuID) {
		super("menu:" + menuID, null);
	}

	@Override
	public void createContributionItems(final IServiceLocator serviceLocator, final IContributionRoot additions) {

		final IRepositoryService repositoryService = (IRepositoryService) PlatformUI.getWorkbench().getService(IRepositoryService.class);

		if (repositoryService != null) {
			final List<IScript> scripts = new ArrayList<IScript>(repositoryService.getScripts());

			Collections.sort(scripts, new Comparator<IScript>() {

				@Override
				public int compare(final IScript o1, final IScript o2) {
					IPath path1 = o1.getPath();
					IPath path2 = o2.getPath();
					if (path1.isEmpty() && !path2.isEmpty())
						return 1;

					else if (!path1.isEmpty() && path2.isEmpty())
						return -1;

					else
						return o1.getName().compareToIgnoreCase(o2.getName());
				}
			});

			final Map<IPath, ScriptPopupMenu> dynamicMenus = new HashMap<IPath, ScriptPopupMenu>();

			List<IContributionItem> rootItems = new ArrayList<IContributionItem>();
			for (final IScript script : scripts) {
				final ScriptPopup popup = new ScriptPopup(script);

				final IPath path = script.getPath().removeLastSegments(1);
				if (path.lastSegment() == null) {
					// script in root folder
					rootItems.add(popup.getContribution(serviceLocator));

				} else {
					// script in sub menu
					ScriptPopupMenu menu = registerPath(dynamicMenus, path);
					menu.addItem(popup);
				}
			}

			// add root menus to additions
			for (IPath path : dynamicMenus.keySet()) {
				if (path.segmentCount() == 1)
					additions.addContributionItem(dynamicMenus.get(path).getContribution(serviceLocator), null);
			}

			// add root elements to additions
			for (IContributionItem item : rootItems)
				additions.addContributionItem(item, null);
		}
	}

	private ScriptPopupMenu registerPath(final Map<IPath, ScriptPopupMenu> dynamicMenus, final IPath path) {
		if (!dynamicMenus.containsKey(path)) {
			dynamicMenus.put(path, new ScriptPopupMenu(path.lastSegment()));

			if (path.segmentCount() > 1) {
				IPath parent = path.removeLastSegments(1);
				ScriptPopupMenu parentMenu = registerPath(dynamicMenus, parent);
				parentMenu.addItem(dynamicMenus.get(path));
			}
		}

		return dynamicMenus.get(path);
	}
}
