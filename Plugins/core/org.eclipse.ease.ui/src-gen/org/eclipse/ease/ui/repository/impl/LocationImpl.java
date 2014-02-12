/**
 */
package org.eclipse.ease.ui.repository.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.ease.ui.repository.ILocation;
import org.eclipse.ease.ui.repository.IRepositoryPackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Location</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.ease.ui.repository.impl.LocationImpl#getUri <em>Uri</em>}</li>
 * <li>{@link org.eclipse.ease.ui.repository.impl.LocationImpl#isHidden <em>Hidden</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class LocationImpl extends MinimalEObjectImpl.Container implements ILocation {
	/**
	 * The default value of the '{@link #getUri() <em>Uri</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getUri()
	 * @generated
	 * @ordered
	 */
	protected static final String URI_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUri() <em>Uri</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getUri()
	 * @generated
	 * @ordered
	 */
	protected String uri = URI_EDEFAULT;

	/**
	 * The default value of the '{@link #isHidden() <em>Hidden</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isHidden()
	 * @generated
	 * @ordered
	 */
	protected static final boolean HIDDEN_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isHidden() <em>Hidden</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isHidden()
	 * @generated
	 * @ordered
	 */
	protected boolean hidden = HIDDEN_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected LocationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IRepositoryPackage.Literals.LOCATION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getUri() {
		return uri;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setUri(String newUri) {
		String oldUri = uri;
		uri = newUri;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IRepositoryPackage.LOCATION__URI, oldUri, uri));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean isHidden() {
		return hidden;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setHidden(boolean newHidden) {
		boolean oldHidden = hidden;
		hidden = newHidden;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IRepositoryPackage.LOCATION__HIDDEN, oldHidden, hidden));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case IRepositoryPackage.LOCATION__URI:
			return getUri();
		case IRepositoryPackage.LOCATION__HIDDEN:
			return isHidden();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case IRepositoryPackage.LOCATION__URI:
			setUri((String) newValue);
			return;
		case IRepositoryPackage.LOCATION__HIDDEN:
			setHidden((Boolean) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case IRepositoryPackage.LOCATION__URI:
			setUri(URI_EDEFAULT);
			return;
		case IRepositoryPackage.LOCATION__HIDDEN:
			setHidden(HIDDEN_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case IRepositoryPackage.LOCATION__URI:
			return URI_EDEFAULT == null ? uri != null : !URI_EDEFAULT.equals(uri);
		case IRepositoryPackage.LOCATION__HIDDEN:
			return hidden != HIDDEN_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (uri: ");
		result.append(uri);
		result.append(", hidden: ");
		result.append(hidden);
		result.append(')');
		return result.toString();
	}

	/**
	 * @generated NOT
	 */
	@Override
	public IResource getIResource() {
		String uri = getUri();
		// FIXME use uri resolvers for that purpose
		if (uri.startsWith("workspace://")) {
			Path path = new Path(uri.substring(12));

			// try for a file
			IResource resource;
			try {
				resource = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
				if ((resource != null) && (resource.exists()))
					return resource;
			} catch (IllegalArgumentException e) {
			}

			// try for a folder
			try {
				resource = ResourcesPlugin.getWorkspace().getRoot().getFolder(path);
				if ((resource != null) && (resource.exists()))
					return resource;
			} catch (IllegalArgumentException e) {
			}

			// try for a project
			resource = ResourcesPlugin.getWorkspace().getRoot().getProject(path.toString());
			if ((resource != null) && (resource.exists()))
				return resource;
		}

		return null;
	}

	/**
	 * @generated NOT
	 */
	@Override
	public File getFile() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @generated NOT
	 */
	@Override
	public InputStream getInputStream() {
		// try for workspace file
		IResource resource = getIResource();
		if ((resource instanceof IFile) && (resource.exists())) {
			try {
				return ((IFile) resource).getContents();
			} catch (CoreException e) {
				throw new RuntimeException("Could not read from workspace file " + resource);
			}
		}

		// try for system file
		File file = getFile();
		if ((file != null) && (file.isFile()) && (file.exists())) {
			try {
				return new FileInputStream(file);
			} catch (FileNotFoundException e) {
				throw new RuntimeException("Could not read from file " + resource);
			}
		}

		// try for generic URI
		try {
			return new URL(getUri()).openStream();
		} catch (MalformedURLException e) {
			throw new RuntimeException("Invalid URI " + getUri());
		} catch (IOException e) {
			throw new RuntimeException("Could not read from URI " + getUri());
		}
	}
} // LocationImpl
