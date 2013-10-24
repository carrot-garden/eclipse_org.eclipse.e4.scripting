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
package org.eclipse.ease.ui.macro.ui;

import org.eclipse.ease.ui.IMacroService;
import org.eclipse.ease.ui.macro.Macro;
import org.eclipse.ease.ui.tools.AdvancedTreeNode;
import org.eclipse.ease.ui.tools.AdvancedTreeNodeContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.PlatformUI;

/**
 * Content provider for macro tree view.
 */
public class MacroContentProvider extends AdvancedTreeNodeContentProvider<Macro> implements ITreeContentProvider {

    @Override
    public final void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
        getRoot().clear();

        IMacroService macroService = (IMacroService) PlatformUI.getWorkbench().getService(IMacroService.class);

        if (macroService != null) {
            for (final Macro macro : macroService.getMacros()) {
                final AdvancedTreeNode<Macro> node = getRoot().findNode(macro.getPath(), true);
                node.addLeaf(macro);
            }
        }
    }
}
