/*******************************************************************************
 * Copyright (c) 2014 Christian Pontesegger and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Christian Pontesegger - initial API and implementation
 *******************************************************************************/package org.eclipse.ease.lang.python;

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