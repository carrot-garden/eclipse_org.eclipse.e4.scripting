package org.eclipse.ease.tools.helpgenerator.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ease.tools.helpgenerator.ModuleDocumentationBuilder;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class AddModuleDocBuilder extends AbstractHandler implements IHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof IStructuredSelection) {
			final Object element = ((IStructuredSelection) selection).getFirstElement();

			final IProject project = (IProject) Platform.getAdapterManager().getAdapter(element, IProject.class);

			if (project != null) {
				try {
					final IProjectDescription description = project.getDescription();
					for (final ICommand buildSpec : description.getBuildSpec()) {
						if (ModuleDocumentationBuilder.BUILDER_ID.equals(buildSpec.getBuilderName()))
							// already enabled
							return null;
					}

					// add builder to project properties
					final ICommand newCommand = description.newCommand();
					newCommand.setBuilderName(ModuleDocumentationBuilder.BUILDER_ID);

					final List<ICommand> commands = new ArrayList<ICommand>();
					commands.addAll(Arrays.asList(description.getBuildSpec()));
					commands.add(newCommand);

					description.setBuildSpec(commands.toArray(new ICommand[commands.size()]));
					project.setDescription(description, null);
				} catch (final CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return null;
	}
}
