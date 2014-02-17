/**
 */
package org.eclipse.ease.ui.repository.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;

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
 *   <li>{@link org.eclipse.ease.ui.repository.impl.LocationImpl#getLocation <em>Location</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LocationImpl extends MinimalEObjectImpl.Container implements ILocation {
	/**
	 * The default value of the '{@link #getLocation() <em>Location</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getLocation()
	 * @generated
	 * @ordered
	 */
	protected static final String LOCATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLocation() <em>Location</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getLocation()
	 * @generated
	 * @ordered
	 */
	protected String location = LOCATION_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected LocationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IRepositoryPackage.Literals.LOCATION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLocation() {
		return location;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLocation(String newLocation) {
		String oldLocation = location;
		location = newLocation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IRepositoryPackage.LOCATION__LOCATION, oldLocation, location));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IRepositoryPackage.LOCATION__LOCATION:
				return getLocation();
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
			case IRepositoryPackage.LOCATION__LOCATION:
				setLocation((String)newValue);
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
			case IRepositoryPackage.LOCATION__LOCATION:
				setLocation(LOCATION_EDEFAULT);
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
			case IRepositoryPackage.LOCATION__LOCATION:
				return LOCATION_EDEFAULT == null ? location != null : !LOCATION_EDEFAULT.equals(location);
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
		result.append(" (location: ");
		result.append(location);
		result.append(')');
		return result.toString();
	}

	/**
	 * @generated NOT
	 */
	@Override
	public IResource getIResource() {
		String location = getLocation();
		// FIXME use uri resolvers for that purpose
		if (location.startsWith("workspace://")) {
			Path path = new Path(location.substring(12));

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
			try {
				resource = ResourcesPlugin.getWorkspace().getRoot().getProject(path.toString());
				if ((resource != null) && (resource.exists()))
					return resource;
			} catch (IllegalArgumentException e) {
			}
		}

		return null;
	}

	/**
	 * @generated NOT
	 */
	@Override
	public File getFile() {
		File file;
		try {
			file = new File(getURI());
			return file.exists() ? file : null;
		} catch (IllegalArgumentException e) {
			// cannot resolve URI to a file
		}

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

			return getURI().toURL().openStream();
		} catch (MalformedURLException e) {
			throw new RuntimeException("Invalid URI " + getURI());
		} catch (IOException e) {
			throw new RuntimeException("Could not read from URI " + getURI());
		}
	}

	@Override
	public URI getURI() {
		return URI.create(getLocation());
	}
} // LocationImpl
