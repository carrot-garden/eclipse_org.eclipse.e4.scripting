package org.eclipse.ease.preferences;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class EmptyNodesFilter extends ViewerFilter {

	private final StringTreeContentProvider mContentProvider;

	public EmptyNodesFilter(StringTreeContentProvider contentProvider) {
		mContentProvider = contentProvider;
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof StringBuilder) {
			for (Object child : mContentProvider.getChildCollection(element)) {
				if (select(viewer, parentElement, child))
					return true;
			}

			return false;
		}

		return true;
	}
}
