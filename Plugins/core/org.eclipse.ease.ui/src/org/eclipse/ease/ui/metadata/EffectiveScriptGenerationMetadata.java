package org.eclipse.ease.ui.metadata;

import java.util.regex.Pattern;

import org.eclipse.ease.storedscript.metada.AbstractRegexMetadataParser;


public class EffectiveScriptGenerationMetadata extends AbstractRegexMetadataParser {

	public EffectiveScriptGenerationMetadata() {
	}

	@Override
	protected Pattern createPattern() {
		return Pattern.compile("GenerateInjectedCodeFile:\\s*(.*)");
	}


}
