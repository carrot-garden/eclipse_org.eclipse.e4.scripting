/*******************************************************************************
 * Copyright (c) 2014 Christian Pontesegger and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Christian Pontesegger - initial API and implementation
 *******************************************************************************//**
 */
package org.eclipse.ease.ui.repository.impl;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.ease.IScriptEngine;
import org.eclipse.ease.Logger;
import org.eclipse.ease.service.EngineDescription;
import org.eclipse.ease.service.IScriptService;
import org.eclipse.ease.service.ScriptType;
import org.eclipse.ease.tools.ResourceTools;
import org.eclipse.ease.ui.console.ScriptConsole;
import org.eclipse.ease.ui.repository.IEntry;
import org.eclipse.ease.ui.repository.IRepositoryPackage;
import org.eclipse.ease.ui.repository.IScript;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.ui.PlatformUI;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Script</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.ease.ui.repository.impl.ScriptImpl#getTimestamp <em>Timestamp</em>}</li>
 *   <li>{@link org.eclipse.ease.ui.repository.impl.ScriptImpl#getEntry <em>Entry</em>}</li>
 *   <li>{@link org.eclipse.ease.ui.repository.impl.ScriptImpl#getScriptParameters <em>Script Parameters</em>}</li>
 *   <li>{@link org.eclipse.ease.ui.repository.impl.ScriptImpl#getUserParameters <em>User Parameters</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ScriptImpl extends LocationImpl implements IScript {
	/**
	 * The default value of the '{@link #getTimestamp() <em>Timestamp</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getTimestamp()
	 * @generated
	 * @ordered
	 */
	protected static final long TIMESTAMP_EDEFAULT = -1L;

	/**
	 * The cached value of the '{@link #getTimestamp() <em>Timestamp</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getTimestamp()
	 * @generated
	 * @ordered
	 */
	protected long timestamp = TIMESTAMP_EDEFAULT;

	/**
	 * The cached value of the '{@link #getScriptParameters() <em>Script Parameters</em>}' map.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getScriptParameters()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> scriptParameters;

	/**
	 * The cached value of the '{@link #getUserParameters() <em>User Parameters</em>}' map.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getUserParameters()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> userParameters;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected ScriptImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IRepositoryPackage.Literals.SCRIPT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTimestamp(long newTimestamp) {
		long oldTimestamp = timestamp;
		timestamp = newTimestamp;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IRepositoryPackage.SCRIPT__TIMESTAMP, oldTimestamp, timestamp));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IEntry getEntry() {
		if (eContainerFeatureID() != IRepositoryPackage.SCRIPT__ENTRY) return null;
		return (IEntry)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEntry(IEntry newEntry, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newEntry, IRepositoryPackage.SCRIPT__ENTRY, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEntry(IEntry newEntry) {
		if (newEntry != eInternalContainer() || (eContainerFeatureID() != IRepositoryPackage.SCRIPT__ENTRY && newEntry != null)) {
			if (EcoreUtil.isAncestor(this, newEntry))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newEntry != null)
				msgs = ((InternalEObject)newEntry).eInverseAdd(this, IRepositoryPackage.ENTRY__SCRIPTS, IEntry.class, msgs);
			msgs = basicSetEntry(newEntry, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IRepositoryPackage.SCRIPT__ENTRY, newEntry, newEntry));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EMap<String, String> getScriptParameters() {
		if (scriptParameters == null) {
			scriptParameters = new EcoreEMap<String,String>(IRepositoryPackage.Literals.PARAMETER_MAP, ParameterMapImpl.class, this, IRepositoryPackage.SCRIPT__SCRIPT_PARAMETERS);
		}
		return scriptParameters;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EMap<String, String> getUserParameters() {
		if (userParameters == null) {
			userParameters = new EcoreEMap<String,String>(IRepositoryPackage.Literals.PARAMETER_MAP, ParameterMapImpl.class, this, IRepositoryPackage.SCRIPT__USER_PARAMETERS);
		}
		return userParameters;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public void run() {
		final IScriptService scriptService = (IScriptService) PlatformUI.getWorkbench().getService(IScriptService.class);

		EngineDescription engineDescription = scriptService.getEngine(getType().getName());

		// try to execute
		if (engineDescription != null) {
			IScriptEngine engine = engineDescription.createEngine();

			// create console
			final ScriptConsole console = ScriptConsole.create(engine.getName() + ": " + getPath(), engine);
			engine.setOutputStream(console.getOutputStream());
			engine.setErrorStream(console.getErrorStream());

			// set dummy input parameters. Scripts do not have any, but script source might expect them
			engine.setVariable("argv", new String[0]);
			engine.executeAsync(getContent());
			engine.schedule();

		} else
			Logger.logError("Could not detect script engine for " + this);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public String getName() {
		return getPath().lastSegment();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public IPath getPath() {
		String name = getParameters().get("name");
		if (name != null)
			return new Path(name).makeAbsolute();

		return new Path(getLocation().substring(getEntry().getLocation().length())).makeAbsolute();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IRepositoryPackage.SCRIPT__ENTRY:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetEntry((IEntry)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IRepositoryPackage.SCRIPT__ENTRY:
				return basicSetEntry(null, msgs);
			case IRepositoryPackage.SCRIPT__SCRIPT_PARAMETERS:
				return ((InternalEList<?>)getScriptParameters()).basicRemove(otherEnd, msgs);
			case IRepositoryPackage.SCRIPT__USER_PARAMETERS:
				return ((InternalEList<?>)getUserParameters()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case IRepositoryPackage.SCRIPT__ENTRY:
				return eInternalContainer().eInverseRemove(this, IRepositoryPackage.ENTRY__SCRIPTS, IEntry.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IRepositoryPackage.SCRIPT__TIMESTAMP:
				return getTimestamp();
			case IRepositoryPackage.SCRIPT__ENTRY:
				return getEntry();
			case IRepositoryPackage.SCRIPT__SCRIPT_PARAMETERS:
				if (coreType) return getScriptParameters();
				else return getScriptParameters().map();
			case IRepositoryPackage.SCRIPT__USER_PARAMETERS:
				if (coreType) return getUserParameters();
				else return getUserParameters().map();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case IRepositoryPackage.SCRIPT__TIMESTAMP:
				setTimestamp((Long)newValue);
				return;
			case IRepositoryPackage.SCRIPT__ENTRY:
				setEntry((IEntry)newValue);
				return;
			case IRepositoryPackage.SCRIPT__SCRIPT_PARAMETERS:
				((EStructuralFeature.Setting)getScriptParameters()).set(newValue);
				return;
			case IRepositoryPackage.SCRIPT__USER_PARAMETERS:
				((EStructuralFeature.Setting)getUserParameters()).set(newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case IRepositoryPackage.SCRIPT__TIMESTAMP:
				setTimestamp(TIMESTAMP_EDEFAULT);
				return;
			case IRepositoryPackage.SCRIPT__ENTRY:
				setEntry((IEntry)null);
				return;
			case IRepositoryPackage.SCRIPT__SCRIPT_PARAMETERS:
				getScriptParameters().clear();
				return;
			case IRepositoryPackage.SCRIPT__USER_PARAMETERS:
				getUserParameters().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case IRepositoryPackage.SCRIPT__TIMESTAMP:
				return timestamp != TIMESTAMP_EDEFAULT;
			case IRepositoryPackage.SCRIPT__ENTRY:
				return getEntry() != null;
			case IRepositoryPackage.SCRIPT__SCRIPT_PARAMETERS:
				return scriptParameters != null && !scriptParameters.isEmpty();
			case IRepositoryPackage.SCRIPT__USER_PARAMETERS:
				return userParameters != null && !userParameters.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case IRepositoryPackage.SCRIPT___RUN:
				run();
				return null;
			case IRepositoryPackage.SCRIPT___GET_NAME:
				return getName();
			case IRepositoryPackage.SCRIPT___GET_PATH:
				return getPath();
		}
		return super.eInvoke(operationID, arguments);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (timestamp: ");
		result.append(timestamp);
		result.append(')');
		return result.toString();
	}

	@Override
	public Map<String, String> getParameters() {
		// first merge script parameters
		Map<String, String> parameters = new HashMap<String, String>(getScriptParameters().map());

		// now apply user parameters, as they have higher priority
		parameters.putAll(getUserParameters().map());

		return parameters;
	}

	@Override
	public ScriptType getType() {
		final IScriptService scriptService = (IScriptService) PlatformUI.getWorkbench().getService(IScriptService.class);
		ScriptType type = null;

		// script type as provided in metadata
		String identifier = getParameters().get("script-type");
		if (identifier != null)
			type = scriptService.getAvailableScriptTypes().get(identifier);

		// script type from file
		if (type == null) {
			Object content = getContent();
			if ((content instanceof IFile) && (((IFile) content).exists()))
				type = ResourceTools.getScriptType((IFile) content);

			else if ((content instanceof File) && (((File) content).exists()))
				type = ResourceTools.getScriptType((File) content);

			// TODO get content type from raw file data (read file)
		}

		return type;
	}
} // ScriptImpl
