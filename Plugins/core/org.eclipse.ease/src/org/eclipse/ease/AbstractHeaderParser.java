package org.eclipse.ease;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractHeaderParser implements IHeaderParser {

	private static final Pattern PARAMETER_PATTERN = Pattern.compile("\\s*([\\p{Alnum}-_]*)\\s*:(.*)");

	@Override
	public Map<String, String> parser(InputStream stream) {
		Map<String, String> parameters = new HashMap<String, String>();

		String comment = getComment(stream);

		String key = null;
		for (String line : comment.split("\\r?\\n")) {
			Matcher matcher = PARAMETER_PATTERN.matcher(line);
			if (matcher.matches()) {
				// key value pair found
				key = matcher.group(1);
				parameters.put(key, matcher.group(2).trim());

			} else if ((key != null) && (!line.trim().isEmpty())) {
				// check that we do not have a delimiter line (all same chars)
				line = line.trim();
				if (!Pattern.matches("[" + line.charAt(0) + "]+", line))
					// line belongs to previous key value pair
					parameters.put(key, parameters.get(key) + " " + line.trim());
			}

			// any other line will be ignored
		}

		return parameters;
	}

	protected abstract String getComment(InputStream stream);
}
