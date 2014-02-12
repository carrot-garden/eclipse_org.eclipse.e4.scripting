/**
 */
package org.eclipse.ease.ui.repository;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Parameter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.ease.ui.repository.IParameter#getKey <em>Key</em>}</li>
 *   <li>{@link org.eclipse.ease.ui.repository.IParameter#getValue <em>Value</em>}</li>
 *   <li>{@link org.eclipse.ease.ui.repository.IParameter#isScriptOrigin <em>Script Origin</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.ease.ui.repository.IRepositoryPackage#getParameter()
 * @model
 * @generated
 */
public interface IParameter extends EObject {
	/**
	 * Returns the value of the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Key</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Key</em>' attribute.
	 * @see #setKey(String)
	 * @see org.eclipse.ease.ui.repository.IRepositoryPackage#getParameter_Key()
	 * @model required="true"
	 * @generated
	 */
	String getKey();

	/**
	 * Sets the value of the '{@link org.eclipse.ease.ui.repository.IParameter#getKey <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Key</em>' attribute.
	 * @see #getKey()
	 * @generated
	 */
	void setKey(String value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see org.eclipse.ease.ui.repository.IRepositoryPackage#getParameter_Value()
	 * @model required="true"
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.ease.ui.repository.IParameter#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

	/**
	 * Returns the value of the '<em><b>Script Origin</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Script Origin</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Script Origin</em>' attribute.
	 * @see #setScriptOrigin(boolean)
	 * @see org.eclipse.ease.ui.repository.IRepositoryPackage#getParameter_ScriptOrigin()
	 * @model default="true" required="true"
	 * @generated
	 */
	boolean isScriptOrigin();

	/**
	 * Sets the value of the '{@link org.eclipse.ease.ui.repository.IParameter#isScriptOrigin <em>Script Origin</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Script Origin</em>' attribute.
	 * @see #isScriptOrigin()
	 * @generated
	 */
	void setScriptOrigin(boolean value);

} // IParameter
