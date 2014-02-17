package org.eclipse.ease.ui.scripts.ui;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public abstract class AbstractVirtualTreeProvider implements ITreeContentProvider {

	private static final IPath ROOT = new Path("");
	private final Map<IPath, Collection<Object>> fElements = new HashMap<IPath, Collection<Object>>();

	@Override
	public void dispose() {
		// nothing to do
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// nothing to do
	}

	@Override
	public Object[] getElements(Object inputElement) {
		fElements.clear();
		registerPath(ROOT);

		populateElements(inputElement);

		return fElements.get(ROOT).toArray();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		Collection<Object> children = fElements.get(parentElement);
		if (children != null)
			return children.toArray();

		return new Object[0];
	}

	@Override
	public Object getParent(Object element) {
		for (IPath path : fElements.keySet()) {
			if (fElements.get(path).contains(element))
				return path;
		}

		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return (fElements.containsKey(element)) && (!fElements.get(element).isEmpty());
	}

	/**
	 * Register an element contained within the tree. To register an element 'myFoo' under the entry '/my/element/is/myFoo' use '/my/element/is' as path. The
	 * LabelProvider needs to take care of the rendering of the element itself.
	 * 
	 * @param path
	 *            full path to be used to display this element (excluding element entry)
	 * @param element
	 *            element to be stored within path
	 */
	public void registerElement(IPath path, Object element) {
		registerPath(path);

		fElements.get(path).add(element);
	}

	/**
	 * Register an element path to be visible on the tree.
	 * 
	 * @param path
	 *            path to be visible
	 */
	protected void registerPath(IPath path) {
		if (!fElements.containsKey(path)) {
			fElements.put(path, new HashSet<Object>());

			if (!path.isEmpty()) {
				IPath parent = path.removeLastSegments(1);
				registerPath(parent);
				fElements.get(parent).add(path);
			}
		}
	}

	protected abstract void populateElements(Object inputElement);
}
