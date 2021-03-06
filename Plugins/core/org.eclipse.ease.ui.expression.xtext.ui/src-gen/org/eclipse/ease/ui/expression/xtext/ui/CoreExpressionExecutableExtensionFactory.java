/*
 * generated by Xtext
 */
package org.eclipse.ease.ui.expression.xtext.ui;

import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;

import com.google.inject.Injector;

import org.eclipse.ease.ui.expression.xtext.ui.internal.CoreExpressionActivator;

/**
 * This class was generated. Customizations should only happen in a newly
 * introduced subclass. 
 */
public class CoreExpressionExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {

	@Override
	protected Bundle getBundle() {
		return CoreExpressionActivator.getInstance().getBundle();
	}
	
	@Override
	protected Injector getInjector() {
		return CoreExpressionActivator.getInstance().getInjector(CoreExpressionActivator.ORG_ECLIPSE_EASE_UI_EXPRESSION_XTEXT_COREEXPRESSION);
	}
	
}
