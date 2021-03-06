/*******************************************************************************
 * Copyright (c) 2013 Atos
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Arthur Daussy - initial implementation
 *******************************************************************************/
package org.eclipse.ease.storedscript.metada;

import org.eclipse.ease.storedscript.storedscript.IStoredScript;
import org.eclipse.ease.storedscript.storedscript.ScriptMetadata;
import org.eclipse.emf.common.util.EList;


public class AbstractMetadataUtils {

	protected static String getUniqueMeta(IStoredScript script, String key) {
		if(script != null) {
			EList<ScriptMetadata> metas = script.getMetadatas();
			for(ScriptMetadata s : metas) {
				if(key.equals(s.getKey())) {
					String v = s.getValue();
					return v;
				}
			}
		}
		return null;
	}

}
