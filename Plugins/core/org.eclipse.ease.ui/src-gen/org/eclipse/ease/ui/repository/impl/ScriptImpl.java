/**
 */
package org.eclipse.ease.ui.repository.impl;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.ease.IScriptEngine;
import org.eclipse.ease.Logger;
import org.eclipse.ease.service.IScriptService;
import org.eclipse.ease.ui.repository.IEntry;
import org.eclipse.ease.ui.repository.IParameter;
import org.eclipse.ease.ui.repository.IRepositoryPackage;
import org.eclipse.ease.ui.repository.IScript;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.ui.PlatformUI;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Script</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.ease.ui.repository.impl.ScriptImpl#getParameter <em>Parameter</em>}</li>
 *   <li>{@link org.eclipse.ease.ui.repository.impl.ScriptImpl#getTimestamp <em>Timestamp</em>}</li>
 *   <li>{@link org.eclipse.ease.ui.repository.impl.ScriptImpl#getEntry <em>Entry</em>}</li>
 *   <li>{@link org.eclipse.ease.ui.repository.impl.ScriptImpl#getFullName <em>Full Name</em>}</li>
 *   <li>{@link org.eclipse.ease.ui.repository.impl.ScriptImpl#isUpdatePending <em>Update Pending</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ScriptImpl extends LocationImpl implements IScript {
	/**
	 * The cached value of the '{@link #getParameter() <em>Parameter</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getParameter()
	 * @generated
	 * @ordered
	 */
	protected EList<IParameter> parameter;

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
	 * The cached value of the '{@link #getEntry() <em>Entry</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getEntry()
	 * @generated
	 * @ordered
	 */
	protected IEntry entry;

	/**
	 * The default value of the '{@link #getFullName() <em>Full Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getFullName()
	 * @generated
	 * @ordered
	 */
	protected static final String FULL_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFullName() <em>Full Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getFullName()
	 * @generated
	 * @ordered
	 */
	protected String fullName = FULL_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isUpdatePending() <em>Update Pending</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUpdatePending()
	 * @generated
	 * @ordered
	 */
	protected static final boolean UPDATE_PENDING_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isUpdatePending() <em>Update Pending</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUpdatePending()
	 * @generated
	 * @ordered
	 */
	protected boolean updatePending = UPDATE_PENDING_EDEFAULT;

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
	public EList<IParameter> getParameter() {
		if (parameter == null) {
			parameter = new EObjectContainmentEList<IParameter>(IParameter.class, this, IRepositoryPackage.SCRIPT__PARAMETER);
		}
		return parameter;
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
		if (entry != null && entry.eIsProxy()) {
			InternalEObject oldEntry = (InternalEObject)entry;
			entry = (IEntry)eResolveProxy(oldEntry);
			if (entry != oldEntry) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, IRepositoryPackage.SCRIPT__ENTRY, oldEntry, entry));
			}
		}
		return entry;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public IEntry basicGetEntry() {
		return entry;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEntry(IEntry newEntry) {
		IEntry oldEntry = entry;
		entry = newEntry;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IRepositoryPackage.SCRIPT__ENTRY, oldEntry, entry));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getFullName() {
		return fullName;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFullName(String newFullName) {
		String oldFullName = fullName;
		fullName = newFullName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IRepositoryPackage.SCRIPT__FULL_NAME, oldFullName, fullName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isUpdatePending() {
		return updatePending;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUpdatePending(boolean newUpdatePending) {
		boolean oldUpdatePending = updatePending;
		updatePending = newUpdatePending;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IRepositoryPackage.SCRIPT__UPDATE_PENDING, oldUpdatePending, updatePending));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IRepositoryPackage.SCRIPT__PARAMETER:
				return ((InternalEList<?>)getParameter()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IRepositoryPackage.SCRIPT__PARAMETER:
				return getParameter();
			case IRepositoryPackage.SCRIPT__TIMESTAMP:
				return getTimestamp();
			case IRepositoryPackage.SCRIPT__ENTRY:
				if (resolve) return getEntry();
				return basicGetEntry();
			case IRepositoryPackage.SCRIPT__FULL_NAME:
				return getFullName();
			case IRepositoryPackage.SCRIPT__UPDATE_PENDING:
				return isUpdatePending();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case IRepositoryPackage.SCRIPT__PARAMETER:
				getParameter().clear();
				getParameter().addAll((Collection<? extends IParameter>)newValue);
				return;
			case IRepositoryPackage.SCRIPT__TIMESTAMP:
				setTimestamp((Long)newValue);
				return;
			case IRepositoryPackage.SCRIPT__ENTRY:
				setEntry((IEntry)newValue);
				return;
			case IRepositoryPackage.SCRIPT__FULL_NAME:
				setFullName((String)newValue);
				return;
			case IRepositoryPackage.SCRIPT__UPDATE_PENDING:
				setUpdatePending((Boolean)newValue);
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
			case IRepositoryPackage.SCRIPT__PARAMETER:
				getParameter().clear();
				return;
			case IRepositoryPackage.SCRIPT__TIMESTAMP:
				setTimestamp(TIMESTAMP_EDEFAULT);
				return;
			case IRepositoryPackage.SCRIPT__ENTRY:
				setEntry((IEntry)null);
				return;
			case IRepositoryPackage.SCRIPT__FULL_NAME:
				setFullName(FULL_NAME_EDEFAULT);
				return;
			case IRepositoryPackage.SCRIPT__UPDATE_PENDING:
				setUpdatePending(UPDATE_PENDING_EDEFAULT);
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
			case IRepositoryPackage.SCRIPT__PARAMETER:
				return parameter != null && !parameter.isEmpty();
			case IRepositoryPackage.SCRIPT__TIMESTAMP:
				return timestamp != TIMESTAMP_EDEFAULT;
			case IRepositoryPackage.SCRIPT__ENTRY:
				return entry != null;
			case IRepositoryPackage.SCRIPT__FULL_NAME:
				return FULL_NAME_EDEFAULT == null ? fullName != null : !FULL_NAME_EDEFAULT.equals(fullName);
			case IRepositoryPackage.SCRIPT__UPDATE_PENDING:
				return updatePending != UPDATE_PENDING_EDEFAULT;
		}
		return super.eIsSet(featureID);
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
		result.append(", fullName: ");
		result.append(fullName);
		result.append(", updatePending: ");
		result.append(updatePending);
		result.append(')');
		return result.toString();
	}

	/**
	 * @generated NOT
	 */
	@Override
	public IParameter getParameter(String name) {
		for (IParameter parameter : getParameter()) {
			if (parameter.getKey().equals(name))
				return parameter;
		}

		return null;
	}

	/**
	 * @generated NOT
	 */
	@Override
	public String getName() {
		return new Path(getFullName()).lastSegment();
	}

	/**
	 * @generated NOT
	 */
	@Override
	public IPath getPath() {
		return new Path(getFullName()).removeLastSegments(1);
	}

	@Override
	public void run() {
		final IScriptService scriptService = (IScriptService) PlatformUI.getWorkbench().getService(IScriptService.class);

		IScriptEngine engine = null;

		// content type as provided in metadata
		IParameter parameter = getParameter("Content-type");
		if (parameter != null)
			engine = scriptService.createEngine(parameter.getValue());

		// content type from file
		if (engine == null) {
			IResource resource = getIResource();
			if (resource instanceof IFile) {
				try {
					IContentType contentType = ((IFile) resource).getContentDescription().getContentType();
					engine = scriptService.createEngine(scriptService.getScriptType(contentType).getName());
				} catch (CoreException e) {
					// could not read content description
				}
			}
		}

		// try to execute
		if (engine != null) {
			IResource resource = getIResource();
			if (resource instanceof IFile)
				engine.executeAsync(resource);
			else
				engine.executeAsync(getInputStream());

			engine.schedule();

		} else
			Logger.logError("Could not detect script engine for " + this);
	}

} // ScriptImpl
