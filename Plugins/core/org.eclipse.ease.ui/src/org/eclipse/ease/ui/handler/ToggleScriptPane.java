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
package org.eclipse.ease.ui.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.ease.ui.scripts.IScriptSupport;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Toggle display of the macro manager.
 */
public class ToggleScriptPane extends AbstractHandler implements IHandler {

    @Override
    public final Object execute(final ExecutionEvent event) throws ExecutionException {

        final IWorkbenchPart part = HandlerUtil.getActivePart(event);
        if (part instanceof IScriptSupport)
            ((IScriptSupport) part).toggleMacroManager();

        return null;
    }
}
