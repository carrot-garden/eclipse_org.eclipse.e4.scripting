package org.eclipse.ease;

import java.io.InputStream;
import java.util.Map;

public interface IHeaderParser {

	Map<String, String> parser(InputStream stream);
}
