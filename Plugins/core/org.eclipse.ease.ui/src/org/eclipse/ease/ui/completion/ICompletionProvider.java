package org.eclipse.ease.ui.completion;

import org.eclipse.jface.fieldassist.IContentProposalProvider;

public interface ICompletionProvider extends IContentProposalProvider {

	char[] getActivationChars();

	/**
	 * Adds code to the auto completion stack. Code can be parsed for variables, modules, etc which might be used for the next content assist request.
	 * 
	 * @param code
	 *            executed code from script engine
	 */
	void addCode(String code);

}
