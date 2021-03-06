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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.ease.storedscript.metada.AbstractRegexMetadataParser;

import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;


public class AvailableFromMetadataParser extends AbstractRegexMetadataParser {

	public AvailableFromMetadataParser() {
	}

	private static final String SEPARATOR = ",";

	public static List<String> getTargets(String toParse) {
		String[] separators = toParse.split(SEPARATOR);
		UnmodifiableIterator<String> ite = Iterators.forArray(separators);
		ArrayList<String> menu = new ArrayList<String>();
		while(ite.hasNext()) {
			String m = (String)ite.next();
			menu.add(m.trim());
		}
		return menu;
	}

	@Override
	protected Pattern createPattern() {
		return Pattern.compile("AvailableFrom:\\s*(.*)");
	}

}
