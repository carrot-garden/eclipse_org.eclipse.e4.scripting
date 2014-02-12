/**
 */
package org.eclipse.ease.ui.repository;

import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Script</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.ease.ui.repository.IScript#getParameter <em>Parameter</em>}</li>
 *   <li>{@link org.eclipse.ease.ui.repository.IScript#getTimestamp <em>Timestamp</em>}</li>
 *   <li>{@link org.eclipse.ease.ui.repository.IScript#getEntry <em>Entry</em>}</li>
 *   <li>{@link org.eclipse.ease.ui.repository.IScript#getFullName <em>Full Name</em>}</li>
 *   <li>{@link org.eclipse.ease.ui.repository.IScript#isUpdatePending <em>Update Pending</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.ease.ui.repository.IRepositoryPackage#getScript()
 * @model
 * @generated
 */
public interface IScript extends ILocation {
	/**
	 * Returns the value of the '<em><b>Parameter</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.ease.ui.repository.IParameter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameter</em>' containment reference list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameter</em>' containment reference list.
	 * @see org.eclipse.ease.ui.repository.IRepositoryPackage#getScript_Parameter()
	 * @model containment="true"
	 * @generated
	 */
	EList<IParameter> getParameter();

	/**
	 * Returns the value of the '<em><b>Timestamp</b></em>' attribute.
	 * The default value is <code>"-1"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Timestamp</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Timestamp</em>' attribute.
	 * @see #setTimestamp(long)
	 * @see org.eclipse.ease.ui.repository.IRepositoryPackage#getScript_Timestamp()
	 * @model default="-1" required="true"
	 * @generated
	 */
	long getTimestamp();

	/**
	 * Sets the value of the '{@link org.eclipse.ease.ui.repository.IScript#getTimestamp <em>Timestamp</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Timestamp</em>' attribute.
	 * @see #getTimestamp()
	 * @generated
	 */
	void setTimestamp(long value);

	/**
	 * Returns the value of the '<em><b>Entry</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entry</em>' reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entry</em>' reference.
	 * @see #setEntry(IEntry)
	 * @see org.eclipse.ease.ui.repository.IRepositoryPackage#getScript_Entry()
	 * @model required="true"
	 * @generated
	 */
	IEntry getEntry();

	/**
	 * Sets the value of the '{@link org.eclipse.ease.ui.repository.IScript#getEntry <em>Entry</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entry</em>' reference.
	 * @see #getEntry()
	 * @generated
	 */
	void setEntry(IEntry value);

	/**
	 * Returns the value of the '<em><b>Full Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Full Name</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Full Name</em>' attribute.
	 * @see #setFullName(String)
	 * @see org.eclipse.ease.ui.repository.IRepositoryPackage#getScript_FullName()
	 * @model required="true"
	 * @generated
	 */
	String getFullName();

	/**
	 * Sets the value of the '{@link org.eclipse.ease.ui.repository.IScript#getFullName <em>Full Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Full Name</em>' attribute.
	 * @see #getFullName()
	 * @generated
	 */
	void setFullName(String value);

	/**
	 * Returns the value of the '<em><b>Update Pending</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Update Pending</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Update Pending</em>' attribute.
	 * @see #setUpdatePending(boolean)
	 * @see org.eclipse.ease.ui.repository.IRepositoryPackage#getScript_UpdatePending()
	 * @model default="false" required="true" transient="true"
	 * @generated
	 */
	boolean isUpdatePending();

	/**
	 * Sets the value of the '{@link org.eclipse.ease.ui.repository.IScript#isUpdatePending <em>Update Pending</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Update Pending</em>' attribute.
	 * @see #isUpdatePending()
	 * @generated
	 */
	void setUpdatePending(boolean value);

	/**
	 * @generated NOT
	 */
	IParameter getParameter(String name);

	/**
	 * @generated NOT
	 */
	String getName();

	/**
	 * @generated NOT
	 */
	IPath getPath();

	/**
	 * @generated NOT
	 */
	void run();
} // IScript
