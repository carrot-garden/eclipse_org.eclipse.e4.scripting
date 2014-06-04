package org.eclipse.ease.engine.javascript.rhino.completion;

import java.util.Collection;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.ease.ui.completion.ModuleCompletionProvider;
import org.eclipse.jface.fieldassist.ContentProposal;

public class RhinoCompletionProvider extends ModuleCompletionProvider {

	private static final Pattern VARIABLES_PATTERN = Pattern.compile(".*?(\\p{Alnum}+)\\s*=\\s*[^=]+");
	private static final Pattern FUNCTION_PATTERN = Pattern.compile("function\\s+(\\p{Alpha}\\p{Alnum}*)\\(");

	private final Collection<String> fVariables = new HashSet<String>();
	private final Collection<String> fFunctions = new HashSet<String>();

	@Override
	public void addCode(final String code) {
		// extract variables
		Matcher matcher = VARIABLES_PATTERN.matcher(code);
		while (matcher.find())
			fVariables.add(matcher.group(1));

		// extract funcitons
		matcher = FUNCTION_PATTERN.matcher(code);
		while (matcher.find())
			fFunctions.add(matcher.group(1));

		super.addCode(code);
	}

	@Override
	protected void modifyProposals(final Collection<ContentProposal> proposals) {

		// add variables
		for (String variable : fVariables)
			proposals.add(new ContentProposal(variable));

		// add functions
		for (String function : fFunctions)
			proposals.add(new ContentProposal(function + "()"));
	}
}
