package org.eclipse.ease.ui.repository.impl;

import org.eclipse.ui.IStartup;

public class Startup implements IStartup {

	@Override
	public void earlyStartup() {
		// make sure the service gets started automatically
		// TODO this might be achieved by auto starting the bundle and activating the service from the bundle activator
		RepositoryService.getInstance();
	}
}
