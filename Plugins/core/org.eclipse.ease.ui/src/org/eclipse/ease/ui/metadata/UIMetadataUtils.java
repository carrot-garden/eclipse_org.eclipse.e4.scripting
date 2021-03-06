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

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.ease.storedscript.metada.AbstractMetadataUtils;
import org.eclipse.ease.storedscript.metada.IBasicMetadata;
import org.eclipse.ease.storedscript.storedscript.IStoredScript;
import org.eclipse.ease.storedscript.storedscript.ScriptMetadata;


public class UIMetadataUtils extends AbstractMetadataUtils {

	public static String getDescription(IStoredScript script) {
		return getUniqueMeta(script, IUIMetadata.DESCRIPTION_METADATA);
	}

	public static String getFileName(IStoredScript script) {
		URI uri = script.createURI();
		return uri.lastSegment().replace(uri.fileExtension(), "");

	}

	public static boolean generateCodeInjectionFile(IStoredScript script) {
		String effective = getUniqueMeta(script, IUIMetadata.GENERATE_CODE_INJECTION_FILE_METADA);
		if("true".equals(effective)) {
			return true;
		}
		return false;
	}

	public static List<String> getMenu(IStoredScript script) {
		String menuList = getUniqueMeta(script, IUIMetadata.MENU_METADATA);
		if(menuList == null) {
			return Collections.singletonList(getFileName(script));
		} else {
			return MenuMetadataParser.getMenus(menuList);
		}
	}

	public static boolean hasToBeLaunchInUI(IStoredScript script) {
		ScriptMetadata meta = script.getMetadata(IBasicMetadata.THREAD_METADATA);
		if(meta != null) {
			String value = meta.getValue();
			if(value != null) {
				String v = value.trim();
				if(IBasicMetadata.UI_THREAD_METADATA_VALUE.equals(v)) {
					return true;
				}
			}
		}
		return false;

	}



}
