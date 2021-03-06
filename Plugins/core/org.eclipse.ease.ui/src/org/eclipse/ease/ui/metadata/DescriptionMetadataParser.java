/**
 *   Copyright (c) 2013 Atos
 *   All rights reserved. This program and the accompanying materials
 *   are made available under the terms of the Eclipse Public License v1.0
 *   which accompanies this distribution, and is available at
 *   http://www.eclipse.org/legal/epl-v10.html
 *  
 *   Contributors:
 *       Arthur Daussy - initial implementation
 */
package org.eclipse.ease.ui.metadata;

import java.util.regex.Pattern;

import org.eclipse.ease.storedscript.metada.AbstractRegexMetadataParser;


/**
 * Use to parse description metadata
 * 
 * @author adaussy
 * 
 */
public class DescriptionMetadataParser extends AbstractRegexMetadataParser {


	@Override
	protected Pattern createPattern() {
		return Pattern.compile("Description:\\s*\\{(.*?)\\}", Pattern.DOTALL);
	}

}
