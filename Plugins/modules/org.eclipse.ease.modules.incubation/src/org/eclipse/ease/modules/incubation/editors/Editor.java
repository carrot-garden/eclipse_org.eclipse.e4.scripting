/**
 * Copyright (c) 2005-2006 Aptana, Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html. If redistributing this code,
 * this entire header must remain intact.
 */
package org.eclipse.ease.modules.incubation.editors;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentRewriteSession;
import org.eclipse.jface.text.DocumentRewriteSessionType;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension4;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * @author Kevin Lindsey, Paul Colton (Aptana, Inc.)
 */
public class Editor {

	/*
	 * Fields
	 */
	private static final long serialVersionUID = 2489151579665730844L;

	private final IEditorPart _editor;

	private DocumentRewriteSession _key;

	/*
	 * Properties
	 */

	public String getClassName() {
		return "Editor";
	}

	/**
	 * getDocument
	 * 
	 * @return IDocument
	 */
	private IDocument getDocument() {
		ITextEditor editor = getTextEditor();
		IDocumentProvider dp = editor.getDocumentProvider();
		IDocument doc = dp.getDocument(editor.getEditorInput());

		return doc;
	}

	/**
	 * Get the editor
	 * 
	 * @return editor
	 */
	public ITextEditor getTextEditor() {
		IEditorPart part = _editor;
		ITextEditor result = null;

		if (part instanceof ITextEditor) {
			result = (ITextEditor) part;
		}

		return result;
	}

	/*
	 * Constructors
	 */

	/**
	 * Create a new instance of Editor
	 * 
	 * @param scope
	 * @param editor
	 */
	public Editor(final IEditorPart editor) {
		_editor = editor;

		String[] functions = new String[] { "applyEdit", "beginCompoundChange", "close", "endCompoundChange", "getLineAtOffset", "getOffsetAtLine", "save",
				"selectAndReveal", "toString" };

		// this.defineFunctionProperties(functions, Editor.class, READONLY | PERMANENT);
		//
		// // read-only properties
		// this.defineProperty("id", Editor.class, READONLY | PERMANENT);
		// this.defineProperty("lineDelimiter", Editor.class, READONLY | PERMANENT);
		// this.defineProperty("source", Editor.class, READONLY | PERMANENT);
		// this.defineProperty("sourceLength", Editor.class, READONLY | PERMANENT);
		// this.defineProperty("selectionRange", Editor.class, READONLY | PERMANENT);
		// this.defineProperty("title", Editor.class, READONLY | PERMANENT);
		// this.defineProperty("textEditor", Editor.class, READONLY | PERMANENT);
		//
		// // read/write properties
		// this.defineProperty("currentOffset", Editor.class, PERMANENT);
	}

	/*
	 * Methods
	 */

	/**
	 * save
	 */
	public void save() {
		if (_editor != null) {
			_editor.doSave(null);
		}
	}

	/**
	 * @param offset
	 * @param length
	 */
	public void selectAndReveal(final int offset, final int length) {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		Display display = workbench.getDisplay();

		display.asyncExec(new Runnable() {

			@Override
			public void run() {
				ITextEditor editor = getTextEditor();
				editor.selectAndReveal(offset, length);
			}
		});
	}

	/**
	 * getCurrentOffset
	 * 
	 * @return int
	 */
	public int getCurrentOffset() {
		/**
		 * ResultRef
		 */
		class ResultRef {

			public int result = -1;
		}

		final IWorkbench workbench = PlatformUI.getWorkbench();
		Display display = workbench.getDisplay();
		final ResultRef result = new ResultRef();

		display.syncExec(new Runnable() {

			@Override
			public void run() {
				ITextSelection ts = (ITextSelection) getTextEditor().getSelectionProvider().getSelection();

				result.result = ts.getOffset();
			}
		});

		return result.result;
	}

	/**
	 * setCurrentOffset
	 * 
	 * @param offset
	 */
	public void setCurrentOffset(final int offset) {
		getTextEditor().getSelectionProvider().setSelection(new TextSelection(offset, 0));
	}

	/**
	 * getLineDelimiter
	 * 
	 * @return String
	 */
	public String getLineDelimiter() {
		IDocument document = getDocument();
		String result = "\n";

		if (document != null) {
			String[] delims = document.getLegalLineDelimiters();

			if (delims.length > 0) {
				result = delims[0];
			}
		}

		return result;
	}

	/**
	 * getId
	 * 
	 * @return String
	 */
	public String getId() {
		String result = "";

		if (_editor != null) {
			result = _editor.getSite().getId();
		}

		return result;
	}

	/**
	 * getSelectionRange
	 * 
	 * @return Object
	 */
	public SelectionRange getSelectionRange() {
		ITextSelection ts = (ITextSelection) getTextEditor().getSelectionProvider().getSelection();

		return new SelectionRange(ts);
	}

	/**
	 * getSource
	 * 
	 * @return Scriptable
	 */
	public String getSource() {
		return getDocument().get();
	}

	/**
	 * getSourceLength
	 * 
	 * @return Scriptable
	 */
	public int getSourceLength() {
		return getDocument().getLength();
	}

	/**
	 * getTitle
	 * 
	 * @return String
	 */
	public String getTitle() {
		ITextEditor uniEditor = getTextEditor();

		if (uniEditor != null) {
			return ((IEditorPart) uniEditor).getTitle();
		}

		return null;
	}

	/**
	 * applyEdit
	 * 
	 * @param offset
	 * @param deleteLength
	 * @param insertText
	 */
	public void applyEdit(final int offset, final int deleteLength, final String insertText) {
		IEditorPart part = _editor;

		if ((part != null) && (part instanceof ITextEditor)) {
			IDocument doc = getDocument();

			try {
				doc.replace(offset, deleteLength, insertText);
			} catch (BadLocationException e) {
				System.err.println("Error: " + e);
			}
		}
	}

	/**
	 * beginCompoundChange
	 */
	public void beginCompoundChange() {
		if (_key == null) {
			IDocument document = getDocument();

			if (document != null) {
				IDocumentExtension4 docExt = (IDocumentExtension4) document;

				_key = docExt.startRewriteSession(DocumentRewriteSessionType.SEQUENTIAL);
			}
		} else {
			throw new IllegalStateException("A previous begin change was not closed");
		}
	}

	/**
	 * close
	 * 
	 * @param save
	 */
	public void close(final boolean save) {
		ITextEditor editor = getTextEditor();

		if (editor != null) {
			editor.close(save);
		}
	}

	/**
	 * endCompoundChange
	 */
	public void endCompoundChange() {
		if (_key != null) {
			IDocument document = getDocument();

			if (document != null) {
				IDocumentExtension4 docExt = (IDocumentExtension4) document;

				docExt.stopRewriteSession(_key);
			}

			_key = null;
		}
	}

	/**
	 * getLineAtOffset
	 * 
	 * @param offset
	 * @return int
	 */
	public int getLineAtOffset(final int offset) {
		try {
			return getDocument().getLineOfOffset(offset);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}

		return -1;
	}

	/**
	 * getOffsetAtLine
	 * 
	 * @param line
	 * @return int
	 */
	public int getOffsetAtLine(final int line) {
		/**
		 * Result
		 */
		class Result {

			public int result;
		}

		final Result r = new Result();
		r.result = -1;

		final IWorkbench workbench = PlatformUI.getWorkbench();
		Display display = workbench.getDisplay();

		display.syncExec(new Runnable() {

			@Override
			public void run() {
				try {
					r.result = getDocument().getLineOffset(line);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
			}
		});

		return r.result;
	}

	/**
	 * toString
	 * 
	 * @return String
	 */
	@Override
	public String toString() {
		return "[object Editor]";
	}
}
