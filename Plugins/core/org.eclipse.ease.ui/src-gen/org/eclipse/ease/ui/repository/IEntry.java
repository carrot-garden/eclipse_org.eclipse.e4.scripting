/**
 */
package org.eclipse.ease.ui.repository;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Entry pointing to a script location.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.ease.ui.repository.IEntry#getTimestamp <em>Timestamp</em>}</li>
 *   <li>{@link org.eclipse.ease.ui.repository.IEntry#isRecursive <em>Recursive</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.ease.ui.repository.IRepositoryPackage#getEntry()
 * @model
 * @generated
 */
public interface IEntry extends ILocation {
	/**
	 * Returns the value of the '<em><b>Timestamp</b></em>' attribute.
	 * The default value is <code>"-1"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Timestamp of last update of this entry.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Timestamp</em>' attribute.
	 * @see #setTimestamp(long)
	 * @see org.eclipse.ease.ui.repository.IRepositoryPackage#getEntry_Timestamp()
	 * @model default="-1" required="true"
	 * @generated
	 */
	long getTimestamp();

	/**
	 * Sets the value of the '{@link org.eclipse.ease.ui.repository.IEntry#getTimestamp <em>Timestamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Timestamp</em>' attribute.
	 * @see #getTimestamp()
	 * @generated
	 */
	void setTimestamp(long value);

	/**
	 * Returns the value of the '<em><b>Recursive</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Flag indicating to parse this entry recursively.
	 * <!-- end-model-doc -->
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

} // IEntry
