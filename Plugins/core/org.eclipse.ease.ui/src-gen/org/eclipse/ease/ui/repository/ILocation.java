/**
 */
package org.eclipse.ease.ui.repository;

import java.io.File;
import java.io.InputStream;
import java.net.URI;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Location</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.ease.ui.repository.ILocation#getLocation <em>Location</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.ease.ui.repository.IRepositoryPackage#getLocation()
 * @model
 * @generated
 */
public interface ILocation extends EObject {
	/**
	 * Returns the value of the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Location</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Location</em>' attribute.
	 * @see #setLocation(String)
	 * @see org.eclipse.ease.ui.repository.IRepositoryPackage#getLocation_Location()
	 * @model id="true" required="true"
	 * @generated
	 */
	String getLocation();

	/**
	 * Sets the value of the '{@link org.eclipse.ease.ui.repository.ILocation#getLocation <em>Location</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Location</em>' attribute.
	 * @see #getLocation()
	 * @generated
	 */
	void setLocation(String value);

	/**
	 * @generated NOT
	 */
	IResource getIResource();

	/**
	 * @generated NOT
	 */
	File getFile();

	/**
	 * @generated NOT
	 */
	InputStream getInputStream();

	/**
	 * @generated NOT
	 */
	URI getURI();
} // ILocation
