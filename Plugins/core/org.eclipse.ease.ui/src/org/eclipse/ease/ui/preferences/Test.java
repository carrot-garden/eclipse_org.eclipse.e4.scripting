package org.eclipse.ease.ui.preferences;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class Test extends ViewPart {

	public static final String ID = "org.eclipse.ease.ui.preferences.Test"; //$NON-NLS-1$
	private Text ftext;
	private Table ftable;

	public Test() {
	}

	/**
	 * Create contents of the view part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(4, false));
		{
			Group grpScriptRecording = new Group(container, SWT.NONE);
			grpScriptRecording.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
			grpScriptRecording.setText("Script Recording");
			GridLayout gl_grpScriptRecording = new GridLayout(4, false);
			grpScriptRecording.setLayout(gl_grpScriptRecording);
			new Label(grpScriptRecording, SWT.NONE);
			{
				Button btnUseCustomLocation = new Button(grpScriptRecording, SWT.CHECK);
				btnUseCustomLocation.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
				btnUseCustomLocation.setText("Use custom storage location");
			}
			new Label(grpScriptRecording, SWT.NONE);
			{
				Label lblLocationUri = new Label(grpScriptRecording, SWT.NONE);
				lblLocationUri.setText("Location URI:");
			}
			{
				ftext = new Text(grpScriptRecording, SWT.BORDER);
				ftext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
			}
			new Label(grpScriptRecording, SWT.NONE);
			new Label(grpScriptRecording, SWT.NONE);
			{
				Button btnWorkspace = new Button(grpScriptRecording, SWT.NONE);
				btnWorkspace.setText("Workspace...");
			}
			{
				Button btnFileSystem = new Button(grpScriptRecording, SWT.NONE);
				btnFileSystem.setText("File System...");
			}
		}
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		{
			Label lblProvideLocationsTo = new Label(container, SWT.NONE);
			GridData gd_lblProvideLocationsTo = new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1);
			gd_lblProvideLocationsTo.verticalIndent = 20;
			lblProvideLocationsTo.setLayoutData(gd_lblProvideLocationsTo);
			lblProvideLocationsTo.setText("Provide locations to look for scripts.");
		}
		{
			Composite composite = new Composite(container, SWT.NONE);
			composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 5));
			TableColumnLayout tcl_composite = new TableColumnLayout();
			composite.setLayout(tcl_composite);
			{
				TableViewer tableViewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);
				ftable = tableViewer.getTable();
				ftable.setHeaderVisible(true);
				ftable.setLinesVisible(true);
				{
					TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
					TableColumn tblclmnLocation = tableViewerColumn.getColumn();
					tcl_composite.setColumnData(tblclmnLocation, new ColumnWeightData(5, ColumnWeightData.MINIMUM_WIDTH, true));
					tblclmnLocation.setText("Location");
				}
				{
					TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
					TableColumn tblclmnRecursive = tableViewerColumn.getColumn();
					tcl_composite.setColumnData(tblclmnRecursive, new ColumnWeightData(1, ColumnWeightData.MINIMUM_WIDTH, true));
					tblclmnRecursive.setText("Recursive");
				}
			}
		}
		{
			Button btnAddWorkspace = new Button(container, SWT.NONE);
			btnAddWorkspace.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
			btnAddWorkspace.setText("Add Workspace...");
		}
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		{
			Button btnAddFileSystem = new Button(container, SWT.NONE);
			btnAddFileSystem.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
			btnAddFileSystem.setText("Add File System...");
		}
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		{
			Button btnAddUri = new Button(container, SWT.NONE);
			btnAddUri.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
			btnAddUri.setText("Add URI...");
		}
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		{
			Button btnEdit = new Button(container, SWT.NONE);
			btnEdit.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, false, true, 1, 1));
			btnEdit.setText("Edit");
		}
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		{
			Button btnDelete = new Button(container, SWT.NONE);
			btnDelete.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, false, false, 1, 1));
			btnDelete.setText("Delete");
		}
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);

		createActions();
		initializeToolBar();
		initializeMenu();
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Initialize the toolbar.
	 */
	private void initializeToolBar() {
		IToolBarManager toolbarManager = getViewSite().getActionBars().getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		IMenuManager menuManager = getViewSite().getActionBars().getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}

}
