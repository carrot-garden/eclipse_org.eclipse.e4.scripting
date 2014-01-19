package org.eclipse.ease.modules;

import java.lang.reflect.Method;

/**
 * Adds functionality to trace script execution. When loaded, all methods automatically create trace messages within the Tracer view.
 */
public class TracerModule implements IScriptFunctionModifier {

	@Override
	public String getPreExecutionCode(final Object module, final Method method) {
		StringBuilder body = new StringBuilder();

		// create call to tracer
		body.append("var __tags = new Array();\n");
		body.append("__tags[0] = \"" + module.toString() + "\";\n");
		body.append("__tags[1] = \"Syntax\";\n");

		body.append("var __message = '" + method.getName() + "(' + ");
		for (int i = 0; i < method.getParameterTypes().length; i++) {
			if (String.class.equals(method.getParameterTypes()[i]))
				body.append("'\"' + ");

			body.append((char) ('a' + i));
			body.append(" + ");

			if (String.class.equals(method.getParameterTypes()[i]))
				body.append("'\"' + ");

			if (i < (method.getParameterTypes().length - 1))
				body.append("\", \" + ");
		}
		body.append("')';\n");
		body.append("__message = __message.replace(/\"?undefined\"?/g, \"\").replace(/ ,/g, \"\").replace(/, \\)/g, ')');\n");
		body.append("Packages.com.infineon.tracer.Tracer.log(__tags, __message);\n");

		return body.toString();
	}

	@Override
	public String getPostExecutionCode(final Object module, final Method method) {
		return "";
	}
}
