/*******************************************************************************
 * Copyright (c) 2008 AIRBUS FRANCE.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Pierre-Charles David (Obeo) - initial API and implementation
 *    Vincent Hemery (Atos Origin) - removing modeler dependencies
 *    Arthur Daussy (Atos) - Update to be use in EASE
 *******************************************************************************/
package org.eclipse.ease.integration.modeling;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ease.common.RunnableWithResult;
import org.eclipse.ease.injection.CodeInjectorUtils;
import org.eclipse.ease.integration.modeling.selector.GMFSemanticSeletor;
import org.eclipse.ease.integration.modeling.ui.UriSelectionDialog;
import org.eclipse.ease.log.Logger;
import org.eclipse.ease.module.interaction.InputModule;
import org.eclipse.ease.module.interaction.OutputModule;
import org.eclipse.ease.module.platform.modules.DialogModule;
import org.eclipse.ease.module.platform.modules.SelectionModule;
import org.eclipse.ease.modules.AbstractScriptModule;
import org.eclipse.ease.modules.BootStrapper;
import org.eclipse.ease.modules.IModuleWrapper;
import org.eclipse.ease.modules.ScriptParameter;
import org.eclipse.ease.modules.WrapToScript;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

/**
 * A base class which provides access to meta-model independent objects and services for Modeling scripts. This class is
 * not exposed directly as a DOM in this plug-in. It is made to be extended and customized by meta-model specific DOMs
 * in other plug-ins.
 * 
 * @author <a href="mailto:pierre-charles.david@obeo.fr">Pierre-Charles David</a>
 * @author <a href="mailto:laurent.redor@obeo.fr">Laurent Redor</a>
 */
public class EcoreModule extends AbstractScriptModule {

	protected SelectionModule selectionModule = new SelectionModule();

	protected OutputModule output = new OutputModule();

	protected InputModule input = new InputModule();

	private String uri;

	/**
	 * Returns the currently selected model element, either in the editor or the outline view. If several elements are
	 * selected, only the first is returned.
	 * 
	 * @return the currently selected model element.
	 */
	public EObject getSelection() {
		Object selection = selectionModule.getCustomSelectionFromSelector(GMFSemanticSeletor.SELECTOR_ID);
		if(selection instanceof EObject) {
			return (EObject)selection;
		} else {
			String message = "Unable to retreive a EObject from the selection";
			DialogModule.error(message);
			return null;
		}

	}

	/**
	 * Return if the current instance is a instance of an EClass define by its name.
	 * 
	 * @param eObject
	 * @param typeName
	 * @return
	 */
	@WrapToScript
	public boolean eInstanceOf(@ScriptParameter(name = "eObject") EObject eObject, @ScriptParameter(name = "type") String type) {
		EClassifier classifier = getEPackage().getEClassifier(type);
		if(classifier == null) {
			DialogModule.error("Unable to find EClass named :" + type);
		}
		return classifier.isInstance(eObject);
	}

	protected String getUri() {
		return uri;
	}

	/**
	 * Returns the currently selected model element in the current UML editor if it is an instance of the named
	 * meta-class (or a sub-class).
	 * 
	 * @param umlTypeName
	 *        the name of a UML meta-class (e.g. "Property" or "Package")
	 * @return the first element selected in the current UML editor if there is one and it is an instance of the named
	 *         meta-class or a sub-class of it.
	 */
	@WrapToScript
	public EObject getSelection(@ScriptParameter(optional = true, name = "type") String type) {
		EObject selection = getSelection();
		if(type != null) {
			if(eInstanceOf(selection, type)) {
				return selection;
			} else {
				return null;
			}
		}
		return selection;
	}

	protected String getFactoryVariableName() {
		return "__" + getEPackage().getName().toUpperCase() + "__FACTORY";
	}

	/**
	 * Filter used to match all create method from the factory
	 */
	protected static Predicate<Method> createMethodFilter = new Predicate<Method>() {

		@Override
		public boolean apply(Method arg0) {
			if(arg0 != null) {
				return arg0.getName().startsWith("create");
			}
			return false;
		}
	};

	@WrapToScript
	public void initEPackage(@ScriptParameter(name = "uri") String uri) {
		if(uri == null) {
			initEPackageFromDialog();
		} else {
			this.uri = uri;
		}
		EFactory factory = getFactory();
		if(factory != null) {
			String factoryName = getFactoryVariableName();
			CodeInjectorUtils.injectJavaVariable(factoryName, factory, getScriptEngine());
			CodeInjectorUtils.injectClass(factory.getClass(), createMethodFilter, CodeInjectorUtils.NO_FIELD_PREDICATE, null, null, factoryName, getScriptEngine(), "[UML Module] Injecting class " + factory.getClass().getName());
		}
	}

	/**
	 * Create a new resource
	 * 
	 * @param modelName
	 *        Name of the resource or null is set dynamically
	 * @param uri
	 *        URI locating the container
	 * @return
	 */
	@WrapToScript
	public Resource createResource(@ScriptParameter(name = "name", optional = true) String modelName, @ScriptParameter(name = "uri", optional = true) String uri) {
		ResourceSet resourceSet = getResourceSet();
		if(resourceSet == null) {
			Logger.logWarning("Unable to get the current resourceSet. Creating a new one...");
			resourceSet = new ResourceSetImpl();
		}
		URI resourceURI = createURI(uri, modelName);
		Resource resource = null;
		try {
			resource = resourceSet.getResource(resourceURI, true);
		} catch (Exception e) {
			resource = resourceSet.createResource(resourceURI);
		}
		return resource;
	}

	/**
	 * Create a new URI. This URI is use to create of located a resource.
	 * 
	 * @param containerURI
	 *        path of the container of the new resource. Optional
	 * @param fileName
	 *        name of the new resource. Optional
	 * @return
	 */
	@WrapToScript
	public URI createURI(@ScriptParameter(name = "containerURI", optional = true) String containerURI, @ScriptParameter(name = "fileName", optional = true) String fileName) {
		URI container = null;
		if(containerURI == null) {
			//Launch dialog to get an URI
			ContainerSelectionDialog dialog = new ContainerSelectionDialog(getShell(), ResourcesPlugin.getWorkspace().getRoot(), false, "Select where you want to add your resource");
			if(dialog.open() != Window.OK) {
				return null;
			}
			Object[] result = dialog.getResult();
			if(result == null || result.length == 0) {
				output.error("Unable to retreive a container for the new resource from your selestion");
				return null;
			}
			IPath containerPath = (IPath)result[0];
			container = URI.createPlatformResourceURI(containerPath.toString(), true);
		} else {
			container = URI.createFileURI(containerURI);
		}
		if(fileName == null) {
			//Launch input dialog
			fileName = input.ask("Give the resource name (With it's extension)");
		}

		container = container.appendSegment(fileName);
		return container;
	}

	/**
	 * Get the factory of selected meta model.
	 * 
	 * @return
	 */
	@WrapToScript
	public EFactory getFactory() {
		if(this.uri == null) {
			initEPackageFromDialog();
		}
		return EPackage.Registry.INSTANCE.getEFactory(uri);
	}

	@WrapToScript
	public EPackage getEPackage() {
		if(this.uri == null) {
			initEPackageFromDialog();
		}
		return EPackage.Registry.INSTANCE.getEPackage(uri);
	}

	private void initEPackageFromDialog() {
		UriSelectionDialog dialog = new UriSelectionDialog(getShell());
		int returnCode = DialogModule.openDialog(dialog);
		if(returnCode == Window.OK) {
			Object[] result = dialog.getResult();
			if(result != null && result.length == 1) {
				uri = (String)result[0];
			}
		}

	}

	/**
	 * Add an error marker on a EObject
	 * 
	 * @param eObject
	 * @param message
	 * @throws CoreException
	 */
	@WrapToScript
	public void addErrorMarker(@ScriptParameter(name = "eObject") EObject eObject, @ScriptParameter(name = "message") String message) throws CoreException {
		EMFMarkerUtil.addMarkerFor(eObject, message, IMarker.SEVERITY_ERROR);

	}

	/**
	 * Add an Information marker on a EObject
	 * 
	 * @param eObject
	 * @param message
	 * @throws CoreException
	 */
	@WrapToScript
	public void addInfoMarker(@ScriptParameter(name = "eObject") EObject eObject, @ScriptParameter(name = "message") String message) throws CoreException {
		EMFMarkerUtil.addMarkerFor(eObject, message, IMarker.SEVERITY_INFO);
	}

	/**
	 * Add a Warning marker on a EObject
	 * 
	 * @param eObject
	 * @param message
	 * @throws CoreException
	 */
	@WrapToScript
	public void addWarningMarker(@ScriptParameter(name = "eObject") EObject eObject, @ScriptParameter(name = "message") String message) throws CoreException {
		EMFMarkerUtil.addMarkerFor(eObject, message, IMarker.SEVERITY_WARNING);
	}

	/**
	 * The current editor part is return or null if there is any active editor. In the case of there is any active
	 * editor a message is display to inform the user.
	 * 
	 * @return IEditorPart The current editor part or null
	 */
	protected IEditorPart getCurrentEditorPart() {
		/**
		 * ActiveEditorRef
		 */
		class ActiveEditorRef {

			public IEditorPart activeEditorPart = null;
		}

		final IWorkbench workbench = PlatformUI.getWorkbench();
		final ActiveEditorRef activeEditorRef = new ActiveEditorRef();
		Display display = workbench.getDisplay();

		display.syncExec(new Runnable() {

			@Override
			public void run() {
				IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();

				// this can be null if you close all perspectives
				if(window != null && window.getActivePage() != null && window.getActivePage().getActiveEditor() != null) {
					activeEditorRef.activeEditorPart = window.getActivePage().getActiveEditor();
				} else {
					Shell shell;
					if(window != null && window.getShell() != null) {
						shell = window.getShell();
					} else {
						shell = new Shell();
					}
				}
			}
		});
		return activeEditorRef.activeEditorPart;
	}

	/**
	 * Save:
	 * The current editor if no eObject is passed in argument
	 * The resource containing the eObject passed in argument
	 * 
	 * @param eObject
	 */
	@WrapToScript
	public void save(@ScriptParameter(optional = true, name = "target") Object target) {
		Resource toSave = null;
		if(target instanceof EObject) {
			EObject eObject = (EObject)target;
			toSave = eObject.eResource();
		} else if(target instanceof Resource) {
			toSave = (Resource)target;
		}
		if(toSave != null) {
			try {
				toSave.save(null);
			} catch (IOException e) {
				e.printStackTrace();
				output.error(e.getMessage());
				return;
			}
		} else {
			save();
		}
	}

	public void save() {
		getCurrentEditorPart().doSave(new NullProgressMonitor());
	}

	@WrapToScript
	protected Shell getShell() {
		RunnableWithResult<Shell> getShellRunnable = new RunnableWithResult<Shell>() {

			private Shell activeShell;

			@Override
			public void run() {
				activeShell = Display.getDefault().getActiveShell();

			}

			@Override
			public Shell getResult() {
				return activeShell;
			}
		};
		Display.getDefault().syncExec(getShellRunnable);
		return getShellRunnable.getResult();
	}

	protected EditingDomain getEditingDomain() {
		IEditorPart currentEditorPart = getCurrentEditorPart();
		if(currentEditorPart != null) {
			Object domain = currentEditorPart.getAdapter(EditingDomain.class);
			if(domain instanceof EditingDomain) {
				return (EditingDomain)domain;
			}
		} else {
			Logger.logWarning("Unable to retreive editing domain. There is not opened editor");
		}
		return null;
	}

	/**
	 * Return all object referencing this EObject.
	 * The return value is a collection of Array of size 2.
	 * Result[0] = EStructual feature linking the two object
	 * Result[1] = The referencing object
	 * 
	 * @param eObject
	 * @return
	 */
	@WrapToScript
	public static Collection<Object[]> getUsages(@ScriptParameter(name = "eObject") EObject eObject) {
		if(eObject == null) {
			return Collections.emptyList();
		}

		ECrossReferenceAdapter crossReferencer = ECrossReferenceAdapter.getCrossReferenceAdapter(eObject);
		if(crossReferencer == null) {
			// try to register a cross referencer at the highest level
			crossReferencer = new ECrossReferenceAdapter();
			if(eObject.eResource() != null) {
				if(eObject.eResource().getResourceSet() != null) {
					crossReferencer.setTarget(eObject.eResource().getResourceSet());
				} else {
					crossReferencer.setTarget(eObject.eResource());
				}
			} else {
				crossReferencer.setTarget(eObject);
			}
		}

		Collection<Setting> result = crossReferencer.getInverseReferences(eObject, true);
		return Collections2.transform(result, new Function<Setting, Object[]>() {

			@Override
			public Object[] apply(Setting arg0) {
				Object[] setting = new Object[2];
				setting[1] = arg0.getEStructuralFeature();
				setting[0] = arg0.getEObject();

				return setting;
			}
		});
	}

	protected ResourceSet getResourceSet() {
		EditingDomain editingDomain = getEditingDomain();
		if(editingDomain != null) {
			return editingDomain.getResourceSet();
		}
		return null;
	}

	public void runOperation(@ScriptParameter(name = "operation") final Runnable operation) {
		runOperation(operation, "Script Operation");
	}

	/**
	 * Run an operation in the current editor's command stack
	 * 
	 * @param operation
	 *        the operation to run
	 * @param operationName
	 *        the name to give to the operation execution
	 */
	@WrapToScript
	public void runOperation(@ScriptParameter(name = "operation") final Runnable operation, @ScriptParameter(name = "name", defaultValue = "Script Operation") String operationName) {
		EditingDomain domain = getEditingDomain();

		if(domain instanceof TransactionalEditingDomain) {
			((TransactionalEditingDomain)domain).getCommandStack().execute(new GMFtoEMFCommandWrapper(new RunnableTransactionalCommandWrapper((TransactionalEditingDomain)domain, operationName, null, operation)));
			//			((EditingDomain)domain).getCommandStack().undo();
		} else if(domain != null) {
			// execute the operation in a command
			domain.getCommandStack().execute(new RunnableCommandWrapper(operation));
		} else {
			// try simply running the operation
			operation.run();
		}
	}

	protected static class RunnableCommandWrapper extends AbstractCommand {

		private final Runnable operation;

		public RunnableCommandWrapper(Runnable operation) {
			super();
			this.operation = operation;
		}

		/**
		 * Execute the operation
		 */
		@Override
		public void execute() {
			operation.run();
		}

		/**
		 * Execute the operation
		 */
		@Override
		public void redo() {
			execute();
		}

		/**
		 * Return true
		 */
		@Override
		protected boolean prepare() {
			return true;
		}
	}

	@WrapToScript
	public Object[] selectFromList(List<? extends Object> inputs) {
		return DialogModule.selectFromList(inputs.toArray(), new LabelProvider() {

			@Override
			public String getText(Object element) {
				if(element instanceof EObject) {
					return getLabelProvider((EObject)element).getText(element);
				}
				return element.toString();
			}
		});

	}

	private static ComposedAdapterFactory adapter = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);

	@WrapToScript
	public String ePrint(EObject target) {
		IItemLabelProvider labelProvider = getLabelProvider(target);
		if(labelProvider != null) {
			return labelProvider.getText(target);
		}
		return "[ERRO] Unable to print this EObject";
	}

	private IItemLabelProvider getLabelProvider(EObject target) {
		IItemLabelProvider labelProvider = (IItemLabelProvider)adapter.adapt(target, IItemLabelProvider.class);
		return labelProvider;
	}

	protected static class RunnableTransactionalCommandWrapper extends AbstractTransactionalCommand {

		public RunnableTransactionalCommandWrapper(TransactionalEditingDomain domain, String label, List affectedFiles, Runnable operation) {
			super(domain, label, affectedFiles);
			this.operation = operation;
		}

		private final Runnable operation;

		@Override
		protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
			operation.run();
			return CommandResult.newOKCommandResult();
		}

	}

	@Override
	public IModuleWrapper getWrapper() {
		return BootStrapper.getWrapper(getScriptEngine().getID());
	}

}
