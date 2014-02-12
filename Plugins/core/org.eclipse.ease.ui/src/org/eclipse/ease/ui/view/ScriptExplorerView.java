package org.eclipse.ease.ui.view;

import org.eclipse.core.runtime.IPath;
import org.eclipse.ease.ui.Activator;
import org.eclipse.ease.ui.repository.IRepositoryService;
import org.eclipse.ease.ui.repository.IScript;
import org.eclipse.ease.ui.repository.IScriptListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

public class ScriptExplorerView extends ViewPart implements IScriptListener {

	public static final String ID = "org.eclipse.ease.ui.view.ScriptExplorerView"; //$NON-NLS-1$
	private TreeViewer treeViewer;

	public ScriptExplorerView() {
	}

	/**
	 * Create contents of the view part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		GridLayout gl_parent = new GridLayout(1, false);
		gl_parent.marginWidth = 0;
		gl_parent.horizontalSpacing = 0;
		gl_parent.marginHeight = 0;
		parent.setLayout(gl_parent);

		treeViewer = new TreeViewer(parent, SWT.NONE);
		Tree tree = treeViewer.getTree();
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		treeViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof IPath)
					return ((IPath) element).lastSegment();

				if (element instanceof IScript)
					return ((IScript) element).getName();

				return super.getText(element);
			}

			@Override
			public Image getImage(Object element) {
				if (element instanceof IPath)
					return Activator.getImage("org.eclipse.ui", "/icons/full/obj16/fldr_obj.gif", true);

				if (element instanceof IScript)
					return Activator.getImage("org.eclipse.ease.ui", "/images/shell_view.gif", true);

				return super.getImage(element);
			}
		});
		treeViewer.setContentProvider(new ScriptContentProvider());

		treeViewer.setComparator(new ViewerComparator() {
			@Override
			public int category(Object element) {
				return (element instanceof IPath) ? 1 : 2;
			}

			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				return e1.toString().compareTo(e2.toString());
			}
		});

		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				Object element = ((IStructuredSelection) treeViewer.getSelection()).getFirstElement();
				if (element instanceof IScript)
					((IScript) element).run();
			}
		});

		getSite().setSelectionProvider(treeViewer);

		final IRepositoryService repositoryService = (IRepositoryService) PlatformUI.getWorkbench().getService(IRepositoryService.class);
		treeViewer.setInput(repositoryService);

		repositoryService.addScriptListener(this);
	}

	@Override
	public void setFocus() {
		treeViewer.getTree().setFocus();
	}

	@Override
	public void dispose() {
		final IRepositoryService repositoryService = (IRepositoryService) PlatformUI.getWorkbench().getService(IRepositoryService.class);
		repositoryService.removeScriptListener(this);

		super.dispose();
	}

	@Override
	public void notify(IScript[] scripts) {
		// FIXME needs some performance improvements on multiple script updates
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				treeViewer.refresh();
			}
		});
	}
}
