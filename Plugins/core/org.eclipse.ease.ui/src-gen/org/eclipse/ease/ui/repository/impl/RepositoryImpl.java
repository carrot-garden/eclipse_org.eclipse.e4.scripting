/**
 */
package org.eclipse.ease.ui.repository.impl;

import java.util.Collection;
import org.eclipse.ease.ui.repository.IEntry;
import org.eclipse.ease.ui.repository.IRepository;
import org.eclipse.ease.ui.repository.IRepositoryPackage;
import org.eclipse.ease.ui.repository.IScript;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Repository</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.ease.ui.repository.impl.RepositoryImpl#getEntries <em>Entries</em>}</li>
 *   <li>{@link org.eclipse.ease.ui.repository.impl.RepositoryImpl#getScripts <em>Scripts</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RepositoryImpl extends MinimalEObjectImpl.Container implements IRepository {
	/**
	 * The cached value of the '{@link #getEntries() <em>Entries</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntries()
	 * @generated
	 * @ordered
	 */
	protected EList<IEntry> entries;

	/**
	 * The cached value of the '{@link #getScripts() <em>Scripts</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScripts()
	 * @generated
	 * @ordered
	 */
	protected EList<IScript> scripts;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RepositoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IRepositoryPackage.Literals.REPOSITORY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IEntry> getEntries() {
		if (entries == null) {
			entries = new EObjectContainmentEList<IEntry>(IEntry.class, this, IRepositoryPackage.REPOSITORY__ENTRIES);
		}
		return entries;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IScript> getScripts() {
		if (scripts == null) {
			scripts = new EObjectContainmentEList<IScript>(IScript.class, this, IRepositoryPackage.REPOSITORY__SCRIPTS);
		}
		return scripts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IRepositoryPackage.REPOSITORY__ENTRIES:
				return ((InternalEList<?>)getEntries()).basicRemove(otherEnd, msgs);
			case IRepositoryPackage.REPOSITORY__SCRIPTS:
				return ((InternalEList<?>)getScripts()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IRepositoryPackage.REPOSITORY__ENTRIES:
				return getEntries();
			case IRepositoryPackage.REPOSITORY__SCRIPTS:
				return getScripts();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case IRepositoryPackage.REPOSITORY__ENTRIES:
				getEntries().clear();
				getEntries().addAll((Collection<? extends IEntry>)newValue);
				return;
			case IRepositoryPackage.REPOSITORY__SCRIPTS:
				getScripts().clear();
				getScripts().addAll((Collection<? extends IScript>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case IRepositoryPackage.REPOSITORY__ENTRIES:
				getEntries().clear();
				return;
			case IRepositoryPackage.REPOSITORY__SCRIPTS:
				getScripts().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case IRepositoryPackage.REPOSITORY__ENTRIES:
				return entries != null && !entries.isEmpty();
			case IRepositoryPackage.REPOSITORY__SCRIPTS:
				return scripts != null && !scripts.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //RepositoryImpl
