package org.eclipse.ease.ui.preferences;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.ease.ui.Activator;
import org.eclipse.ease.ui.repository.IEntry;
import org.eclipse.ease.ui.repository.ILocation;
import org.eclipse.ease.ui.repository.IRepositoryFactory;
import org.eclipse.ease.ui.scripts.repository.IRepositoryService;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

public class LocationsPage extends PreferencePage implements IWorkbenchPreferencePage {

	private Text ftext;
	private TableViewer tableViewer;
	private Button btnUseCustomLocation;

	public LocationsPage() {
	}

	@Override
	public void init(final IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}

	@Override
	protected Control createContents(final Composite parent) {
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
				btnUseCustomLocation = new Button(grpScriptRecording, SWT.CHECK);
				btnUseCustomLocation.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(final SelectionEvent e) {
						// enable/disable components depending on checkbox
						for (Control control : btnUseCustomLocation.getParent().getChildren()) {
							if (!control.equals(btnUseCustomLocation))
								control.setEnabled(btnUseCustomLocation.getSelection());
						}
					}
				});
				btnUseCustomLocation.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
				btnUseCustomLocation.setText("Use custom storage location");
			}
			new Label(grpScriptRecording, SWT.NONE);
			{
				Label lblLocationUri = new Label(grpScriptRecording, SWT.NONE);
				lblLocationUri.setEnabled(false);
				lblLocationUri.setText("Location URI:");
			}
			{
				ftext = new Text(grpScriptRecording, SWT.BORDER);
				ftext.setEnabled(false);
				ftext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
			}
			new Label(grpScriptRecording, SWT.NONE);
			new Label(grpScriptRecording, SWT.NONE);
			{
				Button btnWorkspace = new Button(grpScriptRecording, SWT.NONE);
				btnWorkspace.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(final SelectionEvent e) {
						ContainerSelectionDialog dialog = new ContainerSelectionDialog(getShell(), ResourcesPlugin.getWorkspace().getRoot(), true,
								"Select script storage folder");
						if (dialog.open() == Window.OK) {
							Object[] result = dialog.getResult();
							if ((result.length > 0) && (result[0] instanceof IPath))
								ftext.setText("workspace:/" + result[0].toString());
						}
					}
				});
				btnWorkspace.setEnabled(false);
				btnWorkspace.setText("Workspace...");
			}
			{
				Button btnFileSystem = new Button(grpScriptRecording, SWT.NONE);
				btnFileSystem.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(final SelectionEvent e) {
						DirectoryDialog dialog = new DirectoryDialog(getShell());
						String path = dialog.open();
						if (path != null)
							ftext.setText(new File(path).toURI().toString());
					}
				});
				btnFileSystem.setEnabled(false);
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
				tableViewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);
				Table table = tableViewer.getTable();
				table.setHeaderVisible(true);
				table.setLinesVisible(true);
				{
					TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
					TableColumn tblclmnLocation = tableViewerColumn.getColumn();
					tcl_composite.setColumnData(tblclmnLocation, new ColumnWeightData(5, ColumnWeightData.MINIMUM_WIDTH, true));
					tblclmnLocation.setText("Location");
					tableViewerColumn.setLabelProvider(new ColumnLabelProvider() {
						@Override
						public String getText(final Object element) {
							if (element instanceof IEntry)
								return ((IEntry) element).getLocation();

							return super.getText(element);
						}
					});
				}
				{
					TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
					TableColumn tblclmnRecursive = tableViewerColumn.getColumn();
					tcl_composite.setColumnData(tblclmnRecursive, new ColumnWeightData(1, ColumnWeightData.MINIMUM_WIDTH, true));
					tblclmnRecursive.setText("Recursive");
					tableViewerColumn.setLabelProvider(new ColumnLabelProvider() {
						@Override
						public String getText(final Object element) {
							if (element instanceof IEntry)
								return ((IEntry) element).isRecursive() ? "true" : "false";

							return super.getText(element);
						}
					});
				}

				tableViewer.setContentProvider(ArrayContentProvider.getInstance());
				tableViewer.setComparator(new ViewerComparator() {
					@Override
					public int compare(final Viewer viewer, final Object e1, final Object e2) {
						if ((e1 instanceof ILocation) && (e2 instanceof ILocation))
							return (((ILocation) e1).getLocation()).compareTo(((ILocation) e2).getLocation());

						return super.compare(viewer, e1, e2);
					}
				});

				tableViewer.setFilters(new ViewerFilter[] { new ViewerFilter() {

					@Override
					public boolean select(final Viewer viewer, final Object parentElement, final Object element) {
						if (element instanceof IEntry)
							return !((IEntry) element).isHidden();

						return true;
					}
				} });

				final IRepositoryService repositoryService = (IRepositoryService) PlatformUI.getWorkbench().getService(IRepositoryService.class);
				tableViewer.setInput(new HashSet<IEntry>(repositoryService.getLocations()));
			}
		}
		{
			Button btnAddWorkspace = new Button(container, SWT.NONE);
			btnAddWorkspace.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(final SelectionEvent e) {
					ContainerSelectionDialog dialog = new ContainerSelectionDialog(getShell(), ResourcesPlugin.getWorkspace().getRoot(), true,
							"Select script folder");
					if (dialog.open() == Window.OK) {
						Object[] result = dialog.getResult();
						if ((result.length > 0) && (result[0] instanceof IPath))
							addEntry("workspace:/" + result[0].toString(), false);
					}
				}
			});
			btnAddWorkspace.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
			btnAddWorkspace.setText("Add Workspace...");
		}
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		{
			Button btnAddFileSystem = new Button(container, SWT.NONE);
			btnAddFileSystem.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(final SelectionEvent e) {
					DirectoryDialog dialog = new DirectoryDialog(getShell());
					String path = dialog.open();
					if (path != null)
						addEntry(new File(path).toURI().toString(), false);
				}
			});
			btnAddFileSystem.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
			btnAddFileSystem.setText("Add File System...");
		}
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		{
			Button btnAddUri = new Button(container, SWT.NONE);
			btnAddUri.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(final SelectionEvent e) {
					InputDialog dialog = new InputDialog(getShell(), "Enter location URI", "Enter the URI of a location to add", "", new URIValidator());
					if (dialog.open() == Window.OK)
						addEntry(dialog.getValue(), false);
				}
			});
			btnAddUri.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
			btnAddUri.setText("Add URI...");
		}
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		{
			Button btnEdit = new Button(container, SWT.NONE);
			btnEdit.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(final SelectionEvent e) {
					// TODO implement edit functionality
				}
			});
			btnEdit.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, false, true, 1, 1));
			btnEdit.setText("Edit");
		}
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		{
			Button btnDelete = new Button(container, SWT.NONE);
			btnDelete.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(final SelectionEvent e) {
					IStructuredSelection selection = (IStructuredSelection) tableViewer.getSelection();
					if (!selection.isEmpty()) {
						Collection<IEntry> entries = (Collection<IEntry>) tableViewer.getInput();
						for (Object location : selection.toList())
							entries.remove(location);

						tableViewer.refresh();
					}
				}
			});
			btnDelete.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, false, false, 1, 1));
			btnDelete.setText("Delete");
		}
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);

		performDefaults();

		return container;
	}

	private void addEntry(final String location, final boolean hidden) {
		IEntry entry = IRepositoryFactory.eINSTANCE.createEntry();
		entry.setLocation(location);
		entry.setRecursive(true);
		entry.setHidden(hidden);

		Collection<IEntry> entries = (Collection<IEntry>) tableViewer.getInput();
		entries.add(entry);

		tableViewer.refresh();
	}

	@Override
	protected void performDefaults() {
		IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(Activator.PLUGIN_ID);
		String location = prefs.get(IPreferenceConstants.SCRIPT_STORAGE_LOCATION, IPreferenceConstants.DEFAULT_SCRIPT_STORAGE_LOCATION);
		if (IPreferenceConstants.DEFAULT_SCRIPT_STORAGE_LOCATION.equals(location)) {
			btnUseCustomLocation.setSelection(false);

		} else {
			btnUseCustomLocation.setSelection(true);
			ftext.setText(location);
		}

		for (Control control : btnUseCustomLocation.getParent().getChildren()) {
			if (!control.equals(btnUseCustomLocation))
				control.setEnabled(btnUseCustomLocation.getSelection());
		}

		super.performDefaults();
	}

	@Override
	public boolean performOk() {

		// store enties
		final IRepositoryService repositoryService = (IRepositoryService) PlatformUI.getWorkbench().getService(IRepositoryService.class);

		Collection<IEntry> existing = repositoryService.getLocations();
		for (IEntry entry : new HashSet<IEntry>(existing))
			repositoryService.removeLocation(entry);

		addEntry(getScriptStorageLocation(), true);
		Collection<IEntry> entries = (Collection<IEntry>) tableViewer.getInput();

		for (IEntry entry : entries)
			repositoryService.addLocation(entry);

		// store script storage location
		IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(Activator.PLUGIN_ID);
		prefs.put(IPreferenceConstants.SCRIPT_STORAGE_LOCATION, getScriptStorageLocation());

		return super.performOk();
	}

	private String getScriptStorageLocation() {
		if (btnUseCustomLocation.getSelection())
			// custom location
			return ftext.getText();

		else
			// default location
			return IPreferenceConstants.DEFAULT_SCRIPT_STORAGE_LOCATION;
	}
}
