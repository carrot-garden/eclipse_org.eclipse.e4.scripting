package org.eclipse.ease.ui.view;

import java.util.Collection;

import org.eclipse.ease.ui.repository.IRepositoryService;
import org.eclipse.ease.ui.repository.IScript;

public class ScriptContentProvider extends AbstractVirtualTreeProvider {

	@Override
	protected void populateElements(Object inputElement) {
		if (inputElement instanceof IRepositoryService) {
			Collection<IScript> scripts = ((IRepositoryService) inputElement).getScripts();

			for (IScript script : scripts)
				registerElement(script.getPath(), script);
		}
	}
}
