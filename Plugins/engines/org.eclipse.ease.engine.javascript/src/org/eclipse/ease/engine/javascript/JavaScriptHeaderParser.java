package org.eclipse.ease.engine.javascript;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.ease.AbstractHeaderParser;
import org.eclipse.ease.Logger;

public class JavaScriptHeaderParser extends AbstractHeaderParser {

	private static final String LINE_COMMENT = "//";
	private static final String BLOCK_COMMENT_START = "/*";
	private static final String BLOCK_COMMENT_END = "*/";

	@Override
	protected String getComment(InputStream stream) {
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
