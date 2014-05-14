package org.eclipse.ease.ui.modules.ui;

import java.lang.reflect.Method;

import org.eclipse.core.runtime.IPath;
import org.eclipse.ease.modules.ModuleDefinition;
import org.eclipse.jface.viewers.ILabelProvider;

public class ModulesDecoratedLabelProvider extends DecoratedLabelProvider {

	public ModulesDecoratedLabelProvider(ILabelProvider commonLabelProvider) {
		super(commonLabelProvider);

	}

	@Override
	public String getToolTipText(Object element) {

		if (element instanceof IPath)
			return null;

		if (element instanceof ModuleDefinition)
			return ((ModuleDefinition) element).getBundleID();

		if (element instanceof Method)
			return ModulesTools.getSignature((Method) element, true);

		return null;
	}

}
