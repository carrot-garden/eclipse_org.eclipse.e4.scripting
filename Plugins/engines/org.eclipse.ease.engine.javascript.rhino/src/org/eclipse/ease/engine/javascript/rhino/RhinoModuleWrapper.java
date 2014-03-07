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

import java.lang.annotation.Annotation;
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

	private class Parameter {

		private Class<?> fClazz;
		private String fName = "";
		private boolean fOptional = true;
		private String fDefaultValue = ScriptParameter.UNDEFINED;

		public void setClass(final Class<?> clazz) {
			fClazz = clazz;
		}

		public void setName(final String name) {
			fName = name;
		}

		public void setOptional(final boolean optional) {
			fOptional = optional;
		}

		public void setDefault(final String defaultValue) {
			fDefaultValue = defaultValue;
		}

		public String getName() {
			return fName;
		}

		public Class<?> getClazz() {
			return fClazz;
		}

		public String getDefaultValue() {
			return fDefaultValue;
		}

		public boolean isOptional() {
			return fOptional;
		}
	}

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
		ArrayList<Parameter> parameters = new ArrayList<Parameter>();
		for (int index = 0; index < method.getParameterTypes().length; index++) {
			Parameter parameter = new Parameter();
			parameter.setClass(method.getParameterTypes()[index]);

			ScriptParameter annotation = getParameterAnnotation(method.getParameterAnnotations()[index]);
			if (annotation != null) {
				parameter.setName(annotation.name());
				parameter.setOptional(annotation.optional());
				parameter.setDefault(annotation.defaultValue());
			}
			parameters.add(parameter);
		}

		// post process parameters: find unique names for unnamed parameters
		StringBuilder parameterList = new StringBuilder();
		for (Parameter parameter : parameters) {
			if (parameter.getName().isEmpty())
				parameter.setName(findName(parameters));

			parameterList.append(", ").append(parameter.getName());
		}
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
			if (parameter.isOptional()) {
				data.append("\tif (typeof " + parameter.getName() + " === \"undefined\") {\n");
				data.append("\t\t" + parameter.getName() + " = " + getDefaultValue(parameter) + ";\n");

				data.append(verifyParameters(parameters.subList(0, parameters.size() - 1)));

				data.append("\t}\n");
			}
		}

		return data;
	}

	private String getDefaultValue(final Parameter parameter) {
		String defaultStringValue = parameter.getDefaultValue().replaceAll("\\r", "\\\\r").replaceAll("\\n", "\\\\n");
		Class<?> clazz = parameter.getClazz();

		// null as default value
		if (ScriptParameter.NULL.equals(defaultStringValue))
			return "null";

		// base datatypes
		if ((Integer.class.equals(clazz)) || (int.class.equals(clazz))) {
			try {
				return Integer.toString(Integer.parseInt(defaultStringValue));
			} catch (NumberFormatException e1) {
			}
		}
		if ((Long.class.equals(clazz)) || (long.class.equals(clazz))) {
			try {
				return Long.toString(Long.parseLong(defaultStringValue));
			} catch (NumberFormatException e1) {
			}
		}
		if ((Float.class.equals(clazz)) || (float.class.equals(clazz))) {
			try {
				return Float.toString(Float.parseFloat(defaultStringValue));
			} catch (NumberFormatException e1) {
			}
		}
		if ((Double.class.equals(clazz)) || (double.class.equals(clazz))) {
			try {
				return Double.toString(Double.parseDouble(defaultStringValue));
			} catch (NumberFormatException e1) {
			}
		}
		if ((Boolean.class.equals(clazz)) || (boolean.class.equals(clazz))) {
			return Boolean.toString(Boolean.parseBoolean(defaultStringValue));
		}

		// undefined resolves to empty constructor
		if (ScriptParameter.UNDEFINED.equals(defaultStringValue)) {
			// look for empty constructor
			try {
				clazz.getConstructor();
				// empty constructor found, return class
				return classInstantiation(clazz, null);
			} catch (SecurityException e) {
			} catch (NoSuchMethodException e) {
			}
		}

		// look for string constructor
		try {
			clazz.getConstructor(String.class);
			// string constructor found, return class
			return classInstantiation(clazz, new String[] { defaultStringValue });
		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
		}

		// special handling for string defaults passed to an Object.class
		if (clazz.isAssignableFrom(String.class))
			return classInstantiation(String.class, new String[] { defaultStringValue });

		return "null";
	}

	/**
	 * Find a unique name that is not used yet.
	 * 
	 * @param parameters
	 *            list of available parameters
	 * @return unique unused parameter name
	 */
	private String findName(final ArrayList<Parameter> parameters) {
		String name;
		int index = 1;
		boolean found;
		do {
			found = true;
			name = "param" + index;

			for (Parameter parameter : parameters) {
				if (name.equals(parameter.getName())) {
					index++;
					found = false;
					break;
				}
			}

		} while (!found);

		return name;
	}

	private ScriptParameter getParameterAnnotation(final Annotation[] annotations) {
		for (Annotation annotation : annotations) {
			if (annotation instanceof ScriptParameter)
				return (ScriptParameter) annotation;
		}

		return null;
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
}
