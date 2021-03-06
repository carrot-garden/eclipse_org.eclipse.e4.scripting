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
package org.eclipse.ease.tools;

public abstract class RunnableWithResult<T extends Object> implements Runnable {

	private T fResult = null;

	protected void setResult(final T result) {
		fResult = result;
	}

	public T getResult() {
		return fResult;
	}
}
