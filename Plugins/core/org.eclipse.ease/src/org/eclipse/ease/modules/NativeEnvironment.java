package org.eclipse.ease.modules;

public class NativeEnvironment extends AbstractEnvironment implements IEnvironment {

	@Override
	public void wrap(final Object module) {
		// notify listeners
		fireModuleEvent(module, IModuleListener.LOADED);
	}
}
