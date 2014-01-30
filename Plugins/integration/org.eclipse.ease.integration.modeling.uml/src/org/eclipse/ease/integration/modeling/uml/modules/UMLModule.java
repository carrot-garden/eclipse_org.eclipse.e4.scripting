package org.eclipse.ease.integration.modeling.uml.modules;

import org.eclipse.ease.IScriptEngine;
import org.eclipse.ease.integration.modeling.EcoreModule;
import org.eclipse.ease.module.platform.modules.DialogModule;
import org.eclipse.ease.modules.IEnvironment;
import org.eclipse.ease.modules.WrapToScript;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLPackage;


/**
 * This module help to handle UML models
 * 
 * @author adaussy
 * 
 */
public class UMLModule extends EcoreModule {

    @Override
    public void initialize(final IScriptEngine engine, final IEnvironment environment) {
        super.initialize(engine, environment);
        initEPackage(UMLPackage.eNS_URI);
    }

	/**
	 * Get the UML model from the current active editor
	 * 
	 * @return
	 */
	@WrapToScript
	public Model getModel() {
		EditingDomain editingDomain = getEditingDomain();
		if(editingDomain == null) {
			DialogModule.error("Unable to retreive editing domain");
		}
		ResourceSet resourceSet = editingDomain.getResourceSet();
		if(resourceSet == null) {
			DialogModule.error("Unable to retreive the resource set");
		}
		for(Resource r : resourceSet.getResources()) {
			Model result = lookForModel(r);
			if(result != null) {
				return result;
			}
		}
		return null;
	}

	private Model lookForModel(Resource r) {
		URI resourceURI = r.getURI();
		if(resourceURI != null) {
			if(UMLPackage.eNS_PREFIX.equals(resourceURI.fileExtension())) {
				EList<EObject> content = r.getContents();
				if(!content.isEmpty()) {
					EObject root = content.get(0);
					if(root instanceof Model) {
						return (Model)root;
					}
				}
			}
		}
		return null;
	}
}
