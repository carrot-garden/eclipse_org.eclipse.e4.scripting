/*******************************************************************************
 * Copyright (c) 2013 Christian Pontesegger and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Christian Pontesegger - initial API and implementation
 *     Arthur Daussy - Allow optional parameter
 *******************************************************************************/
package org.eclipse.ease.engine.javascript.rhino;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.ease.Activator;
import org.eclipse.ease.Logger;
import org.eclipse.ease.modules.AbstractModuleWrapper;
import org.eclipse.ease.modules.ScriptParameter;

public class RhinoModuleWrapper extends AbstractModuleWrapper {

	private static final String UNDIFINED_KEYWORD = "undifined";

	@Override
	public String getSaveVariableName(final String variableName) {
		return RhinoScriptEngine.getSaveName(variableName);
	}

	@Override
	public String createFunctionWrapper(final String moduleVariable, final Method method, final Set<String> functionNames, final String resultName,
			final String preExecutionCode, final String postExecutionCode) {

		StringBuilder javaScriptCode = new StringBuilder();

		// parse parameters
		List<Parameter> parameters = parseParameters(method);

		// build parameter string
		StringBuilder parameterList = new StringBuilder();
		for (Parameter parameter : parameters)
			parameterList.append(", ").append(parameter.getName());

		if (parameterList.length() > 2)
			parameterList.delete(0, 2);

		StringBuilder body = new StringBuilder();
		// insert parameter checks
		body.append(verifyParameters(parameters));

		// insert hooked pre execution code
		if (preExecutionCode != null)
			body.append(preExecutionCode);

		// insert method call
		body.append("\tvar ").append(resultName).append(" = ").append(moduleVariable).append('.').append(method.getName()).append('(');
		body.append(parameterList);
		body.append(");\n");

		// insert hooked post execution code
		if (postExecutionCode != null)
			body.append(postExecutionCode);

		// insert return statement
		body.append("\treturn ").append(resultName).append(";\n");

		// build function declarations
		for (String name : functionNames) {
			if (!isCorrectMethodName(name)) {
				Logger.logError("The method name \"" + name + "\" from the module \"" + moduleVariable + "\" can not be wrapped because it's name is reserved",
						Activator.PLUGIN_ID);

			} else if (!name.isEmpty()) {
				javaScriptCode.append("function ").append(name).append("(").append(parameterList).append(") {\n");
				javaScriptCode.append(body);
				javaScriptCode.append("}\n");
			}
		}

		return javaScriptCode.toString();
	}

	private StringBuilder verifyParameters(final List<Parameter> parameters) {
		StringBuilder data = new StringBuilder();

		if (!parameters.isEmpty()) {
			Parameter parameter = parameters.get(parameters.size() - 1);
			data.append("\tif (typeof " + parameter.getName() + " === \"undefined\") {\n");
			if (parameter.isOptional()) {
				data.append("\t\t" + parameter.getName() + " = " + getDefaultValue(parameter) + ";\n");
			} else {
				data.append("\t\tthrow new java.lang.RuntimeException('Parameter " + parameter.getName() + " is not optional');\n");

			}
			data.append(verifyParameters(parameters.subList(0, parameters.size() - 1)));
			data.append("\t}\n");
		}

		return data;
	}

	/**
	 * Generate code for optional parameter (and handle default value)
	 * 
	 * @param type
	 * @param a
	 * @return
	 */
	protected CharSequence setOptionalParameterValue(final Class<?> type, final ScriptParameter a) {
		Object defaultValue = ScriptParameter.OptionalParameterHelper.getDefaultValue(a, type);
		StringBuilder parametersSignature = new StringBuilder();
		parametersSignature.append("=");
		if (defaultValue != null) {
			if (defaultValue instanceof String) {
				parametersSignature.append("\"" + defaultValue + "\"");
			} else {
				parametersSignature.append(defaultValue.toString());
			}
		}
		return null;
	}

	protected String getNullVariableName() {
		return UNDIFINED_KEYWORD;
	}

	public static List<String> RESERVED_KEYWORDS = new ArrayList<String>();

	public boolean isCorrectMethodName(final String methodName) {
		return RhinoScriptEngine.isSaveName(methodName) && !RESERVED_KEYWORDS.contains(methodName);
	}

	static {
		RESERVED_KEYWORDS.add("for");
		RESERVED_KEYWORDS.add("while");
		RESERVED_KEYWORDS.add("delete");
		// TODO Complete this list
	}

	@Override
	public String getConstantDefinition(final String name, final Field field) {
		return "const " + RhinoScriptEngine.getSaveName(name) + " = Packages." + field.getDeclaringClass().getName() + "." + field.getName() + ";\n";
	}

	@Override
	public String getVariableDefinition(final String name, final String content) {
		return "var " + RhinoScriptEngine.getSaveName(name) + " = " + content + ";";
	}

	@Override
	public String classInstantiation(final Class<?> clazz, final String[] parameters) {
		StringBuilder code = new StringBuilder();
		code.append("new Packages.");
		code.append(clazz.getName());
		code.append("(");

		if (parameters != null) {
			for (String parameter : parameters) {
				code.append('"');
				code.append(parameter);
				code.append('"');
				code.append(", ");
			}
			if (parameters.length > 0)
				code.replace(code.length() - 2, code.length(), "");
		}

		code.append(")");

		return code.toString();
	}

	@Override
	protected String getNullString() {
		return "null";
	}
}
