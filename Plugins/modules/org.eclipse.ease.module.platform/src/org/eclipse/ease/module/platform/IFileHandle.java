package org.eclipse.ease.module.platform;

import java.io.IOException;

public interface IFileHandle {
	int READ = 1;
	int WRITE = 2;
	int APPEND = 4;
	int RANDOM_ACCESS = 8;

	String read(int characters) throws IOException;

	String readLine() throws IOException;

	boolean write(String data, int offset);

	boolean exists();

	boolean createFile(boolean createHierarchy) throws Exception;
}
