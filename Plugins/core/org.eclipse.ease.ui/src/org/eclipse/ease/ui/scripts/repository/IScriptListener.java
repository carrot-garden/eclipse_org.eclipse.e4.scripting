package org.eclipse.ease.ui.scripts.repository;

import org.eclipse.ease.ui.scripts.repository.impl.ScriptRepositoryEvent;

public interface IScriptListener {
	void notify(ScriptRepositoryEvent event);
}
