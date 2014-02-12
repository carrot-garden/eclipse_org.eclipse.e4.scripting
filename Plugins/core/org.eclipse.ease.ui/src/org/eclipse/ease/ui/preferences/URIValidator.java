package org.eclipse.ease.ui.preferences;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.IInputValidator;

public class URIValidator implements IInputValidator {

	@Override
	public String isValid(String text) {
		try {
			if (URI.createURI(text).isRelative())
				return "relative URI detected";
		} catch (Exception e) {
			return "Invalid URI detected";
		}

		return null;
	}
}
