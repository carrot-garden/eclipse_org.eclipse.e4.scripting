package org.eclipse.ease.modules;

import org.eclipse.core.runtime.IConfigurationElement;

public class ModuleCategoryDefinition {

	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String PARENT = "parent";

	private final IConfigurationElement fConfig;

	public ModuleCategoryDefinition(IConfigurationElement config) {
		fConfig = config;
	}

	public String getId() {
		return (fConfig.getAttribute(ID) != null) ? fConfig.getAttribute(ID) : "";
	}

	public String getParentId() {
		return fConfig.getAttribute(PARENT);
	}

	public String getName() {
		return fConfig.getAttribute(NAME);
	}
}
