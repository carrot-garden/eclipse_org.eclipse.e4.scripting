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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ease.IExecutionListener;
import org.eclipse.ease.IScriptEngine;
import org.eclipse.ease.IScriptEngineProvider;
import org.eclipse.ease.Script;
import org.eclipse.ease.service.EngineDescription;
import org.eclipse.ease.service.IScriptService;
import org.eclipse.ease.service.ScriptType;
import org.eclipse.ease.ui.Activator;
import org.eclipse.ease.ui.scripts.ScriptStorage;
import org.eclipse.ease.ui.tools.StringTools;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.menus.UIElement;

/**
 * Toggle script recording command. Start/stop script recording.
 */
public class ToggleScriptRecording extends ToggleHandler implements IHandler, IElementUpdater, IExecutionListener {

	private boolean fChecked = false;

	private static final Map<IScriptEngine, StringBuffer> fRecordings = new HashMap<IScriptEngine, StringBuffer>();

	@Override
	protected final void executeToggle(final ExecutionEvent event, final boolean checked) {
		final IWorkbenchPart part = HandlerUtil.getActivePart(event);
		if (part instanceof IScriptEngineProvider) {
			final IScriptEngine engine = ((IScriptEngineProvider) part).getScriptEngine();
			if (engine != null) {
				if (checked) {
					// start recording, eventually overrides a running recording of the same provider
					fRecordings.put(engine, new StringBuffer());
					engine.addExecutionListener(this);

				} else {
					// stop recording
					final ScriptStorage storage = ScriptStorage.createStorage();
					final StringBuffer buffer = fRecordings.get(engine);

					if (buffer.length() > 0) {
						final InputDialog dialog = new InputDialog(HandlerUtil.getActiveShell(event), "Save Script",
								"Enter a unique name for your script (use '/' as path delimiter)", "", new IInputValidator() {

									@Override
									public String isValid(final String name) {
										if (storage.exists(name))
											return "Script name <" + name + "> is already in use. Choose a different one.";

										return null;
									}
								});

						if (dialog.open() == Window.OK) {
							final IScriptService scriptService = (IScriptService) PlatformUI.getWorkbench().getService(IScriptService.class);
							EngineDescription description = engine.getDescription();
							ScriptType scriptType = description.getSupportedScriptTypes().iterator().next();

							String name = dialog.getValue() + "." + scriptType.getDefaultExtension();

							// TODO write script header

							if (!storage.store(name, buffer.toString()))
								// could not store script
								MessageDialog.openError(HandlerUtil.getActiveShell(event), "Save error", "Could not store script data");
						}
					}
				}
			}
		}

		fChecked = checked;
	}

	@Override
	public final void updateElement(final UIElement element, @SuppressWarnings("rawtypes") final Map parameters) {
		super.updateElement(element, parameters);

		if (fChecked)
			element.setIcon(Activator.getImageDescriptor(Activator.PLUGIN_ID, "/images/stop_record_macro.gif"));

		else
			element.setIcon(Activator.getImageDescriptor(Activator.PLUGIN_ID, "/images/start_record_macro.gif"));
	}

	@Override
	public void notify(final IScriptEngine engine, final Script script, final int status) {
		if (IExecutionListener.SCRIPT_END == status) {
			try {
				final StringBuffer buffer = fRecordings.get(engine);
				if (buffer != null) {
					// TODO add support to add trailing returns and ;
					buffer.append(script.getCode());
					buffer.append(StringTools.LINE_DELIMITER);
				} else
					engine.removeExecutionListener(this);

			} catch (final FileNotFoundException e) {
				// cannot record / execute macro when file is not found
			} catch (final CoreException e) {
				// cannot record / execute macro when file is not found
			} catch (final IOException e) {
				// cannot extract string from getCode()
			} catch (final Exception e) {
				// TODO handle this exception (but for now, at least know it happened)
				throw new RuntimeException(e);
			}
		}
	}
}
