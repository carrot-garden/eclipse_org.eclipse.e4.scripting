package org.eclipse.ease.ui.scripts.repository;

import org.eclipse.ease.ui.repository.IScript;

public interface IScriptListener {
	void notify(IScript[] scripts);
}
