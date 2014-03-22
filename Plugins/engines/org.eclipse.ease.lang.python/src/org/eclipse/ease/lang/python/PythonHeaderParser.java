package org.eclipse.ease.lang.python;

import org.eclipse.ease.AbstractHeaderParser;

public class PythonHeaderParser extends AbstractHeaderParser {

	private static final String LINE_COMMENT = "#";

	@Override
	protected String getLineCommentToken() {
		return LINE_COMMENT;
	}

	@Override
	protected boolean hasBlockComment() {
		return false;
	}

	@Override
	protected String getBlockCommentEndToken() {
		return null;
	}

	@Override
	protected String getBlockCommentStartToken() {
		return null;
	}
}