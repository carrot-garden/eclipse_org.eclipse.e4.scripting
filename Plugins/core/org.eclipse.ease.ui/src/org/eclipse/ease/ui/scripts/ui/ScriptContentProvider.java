package org.eclipse.ease.ui.scripts.ui;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.ease.ui.repository.IScript;
import org.eclipse.ease.ui.scripts.repository.IRepositoryService;

public class ScriptContentProvider extends AbstractVirtualTreeProvider {

	@Override
	protected void populateElements(Object inputElement) {
		if (inputElement instanceof IRepositoryService) {
			Collection<IScript> scripts = new HashSet<IScript>(((IRepositoryService) inputElement).getScripts());

			for (IScript script : scripts)
				registerElement(script.getPath().removeLastSegments(1), script);
		}
	}
}
