/*
* generated by Xtext
*/
package org.eclipse.ease.ui.expression.xtext.parser.antlr;

import java.io.InputStream;
import org.eclipse.xtext.parser.antlr.IAntlrTokenFileProvider;

public class CoreExpressionAntlrTokenFileProvider implements IAntlrTokenFileProvider {
	
	public InputStream getAntlrTokenFile() {
		ClassLoader classLoader = getClass().getClassLoader();
    	return classLoader.getResourceAsStream("org/eclipse/ease/ui/expression/xtext/parser/antlr/internal/InternalCoreExpression.tokens");
	}
}
