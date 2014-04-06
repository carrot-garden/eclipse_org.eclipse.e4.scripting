/**
 */
package org.eclipse.ease.ui.repository;

import org.eclipse.emf.common.util.EList;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.ease.ui.repository.IEntry#isRecursive <em>Recursive</em>}</li>
 *   <li>{@link org.eclipse.ease.ui.repository.IEntry#isHidden <em>Hidden</em>}</li>
 *   <li>{@link org.eclipse.ease.ui.repository.IEntry#getScripts <em>Scripts</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.ease.ui.repository.IRepositoryPackage#getEntry()
 * @model
 * @generated
 */
public interface IEntry extends ILocation {
	/**
	 * Returns the value of the '<em><b>Recursive</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Recursive</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Recursive</em>' attribute.
	 * @see #setRecursive(boolean)
	 * @see org.eclipse.ease.ui.repository.IRepositoryPackage#getEntry_Recursive()
	 * @model default="false" required="true"
	 * @generated
	 */
	boolean isRecursive();

	/**
	 * Sets the value of the '{@link org.eclipse.ease.ui.repository.IEntry#isRecursive <em>Recursive</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Recursive</em>' attribute.
	 * @see #isRecursive()
	 * @generated
	 */
	void setRecursive(boolean value);

	/**
	 * Returns the value of the '<em><b>Hidden</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hidden</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hidden</em>' attribute.
	 * @see #setHidden(boolean)
	 * @see org.eclipse.ease.ui.repository.IRepositoryPackage#getEntry_Hidden()
	 * @model default="false" required="true"
	 * @generated
	 */
	boolean isHidden();

	/**
	 * Sets the value of the '{@link org.eclipse.ease.ui.repository.IEntry#isHidden <em>Hidden</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hidden</em>' attribute.
	 * @see #isHidden()
	 * @generated
	 */
	void setHidden(boolean value);

	/**
	 * Returns the value of the '<em><b>Scripts</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.ease.ui.repository.IScript}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.ease.ui.repository.IScript#getEntry <em>Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Scripts</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Scripts</em>' containment reference list.
	 * @see org.eclipse.ease.ui.repository.IRepositoryPackage#getEntry_Scripts()
	 * @see org.eclipse.ease.ui.repository.IScript#getEntry
	 * @model opposite="entry" containment="true"
	 * @generated
	 */
	EList<IScript> getScripts();

} // IEntry
