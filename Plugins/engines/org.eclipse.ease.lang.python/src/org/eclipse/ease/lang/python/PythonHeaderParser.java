package org.eclipse.ease.lang.python;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.ease.AbstractHeaderParser;
import org.eclipse.ease.Logger;

public class PythonHeaderParser extends AbstractHeaderParser {

	private static final String LINE_COMMENT = "#";

	@Override
	protected String getComment(InputStream stream) {
		StringBuilder comment = new StringBuilder();

		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

		boolean isComment = true;
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

				// not a comment line, not empty
				isComment = false;

			} while (isComment);
		} catch (IOException e) {
			Logger.logError("Could not parse input stream header", e);
			return "";
		}

		return comment.toString();
	}

	@Override
	protected String getLineCommentToken() {
		return LINE_COMMENT;
	}
}