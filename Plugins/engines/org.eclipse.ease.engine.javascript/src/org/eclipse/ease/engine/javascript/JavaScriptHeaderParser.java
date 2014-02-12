package org.eclipse.ease.engine.javascript;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.ease.IHeaderParser;
import org.eclipse.ease.Logger;

public class JavaScriptHeaderParser implements IHeaderParser {

	private static final String LINE_COMMENT = "//";
	private static final String BLOCK_COMMENT_START = "/*";
	private static final String BLOCK_COMMENT_END = "*/";

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

	private String getComment(InputStream stream) {
		StringBuilder comment = new StringBuilder();

		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

		boolean isComment = true;
		boolean isBlock = false;
		try {
			do {
				String line = reader.readLine();
				if (line == null)
					break;

				line = line.trim();

				if (line.isEmpty())
					continue;

				if (line.startsWith(LINE_COMMENT)) {
					comment.append(line.substring(LINE_COMMENT.length()).trim());
					comment.append("\n");
					continue;
				}

				if (line.startsWith(BLOCK_COMMENT_START)) {
					isBlock = true;
					line = line.substring(BLOCK_COMMENT_START.length()).trim();
				}

				if (isBlock) {
					if (line.contains(BLOCK_COMMENT_END)) {
						isBlock = false;
						line = line.substring(0, line.indexOf(BLOCK_COMMENT_END));
					}

					// remove leading '*' characters
					line = line.trim();
					while (line.startsWith("*"))
						line = line.substring(1);

					comment.append(line.trim());
					comment.append("\n");
					continue;
				}

				// not a comment line, not empty
				isComment = false;

			} while (isComment);
		} catch (IOException e) {
			Logger.logError("Could not parse input stream header", e);
			return "";
		}

		return comment.toString();
	}
}
