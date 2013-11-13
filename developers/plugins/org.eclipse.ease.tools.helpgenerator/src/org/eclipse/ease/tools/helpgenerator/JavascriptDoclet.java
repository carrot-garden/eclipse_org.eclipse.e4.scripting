package org.eclipse.ease.tools.helpgenerator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.XMLMemento;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.AnnotationDesc.ElementValuePair;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.DocErrorReporter;
import com.sun.javadoc.Doclet;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.ParamTag;
import com.sun.javadoc.Parameter;
import com.sun.javadoc.RootDoc;

public class JavascriptDoclet extends Doclet {

	private static final String WRAP_TO_SCRIPT = "org.eclipse.ease.modules.WrapToScript";
	private static final String LINE_DELIMITER = "\n";

	public static boolean start(final RootDoc root) {
		final JavascriptDoclet doclet = new JavascriptDoclet();
		return doclet.process(root);
	}

	public static int optionLength(final String option) {
		if (option.equals("-d")) {
			return 2;
		}
		return 0;
	}

	public static boolean validOptions(final String options[][], final DocErrorReporter reporter) {
		boolean foundDestinationOption = false;
		for (int i = 0; i < options.length; i++) {
			final String[] opt = options[i];
			if (opt[0].equals("-d")) {
				if (foundDestinationOption) {
					reporter.printError("Only one -d option allowed.");
					return false;
				} else
					foundDestinationOption = true;
			}
		}
		if (!foundDestinationOption)
			reporter.printError("Usage: javadoc -d destinationfolder ...");

		return foundDestinationOption;
	}

	private Map<String, String> mLookupTable;

	private boolean process(final RootDoc root) {
		System.out.println("----------------------------------------");
		System.out.println("-- running doclet");
		System.out.println("----------------------------------------");
		final ClassDoc[] classes = root.classes();

		// write to output file
		final String[][] options = root.options();

		for (final String[] option : options) {
			try {
				if ("-d".equals(option[0])) {
					// get project location
					final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(option[1]);

					// create lookup table with module data
					createModuleLookupTable(project);

					// create HTML help files
					System.out.println("----------------------------------------");
					System.out.println("-- building HTML files");
					if (createHTMLFiles(project, classes)) {
						// some files were created, update tocs, ...

						// create TOC file
						System.out.println("----------------------------------------");
						System.out.println("-- building TOC");
						createTOCFile(project);

						// update plugin.xml
						System.out.println("----------------------------------------");
						System.out.println("-- update plugin.xml");
						updatePluginXML(project);

						// update MANIFEST.MF
						System.out.println("----------------------------------------");
						System.out.println("-- update MANIFEST.MF");
						updateManifest(project);

						// update build.properties
						System.out.println("----------------------------------------");
						System.out.println("-- update build.properties");
						updateBuildProperties(project);
					}
				}
			} catch (final CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (final IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return true;
	}

	private void updateManifest(final IProject project) throws CoreException, IOException {
		final IFile file = project.getFile(new Path("META-INF/MANIFEST.MF"));

		Manifest manifest = new Manifest();
		manifest.read(file.getContents());

		Attributes mainAttributes = manifest.getMainAttributes();
		String require = mainAttributes.getValue("Require-Bundle");

		if ((require == null) || (require.isEmpty()))
			mainAttributes.putValue("Require-Bundle", "org.eclipse.help;bundle-version=\"[3.6.0,4.0.0)\"");

		else if (!require.contains("org.eclipse.help"))
			mainAttributes.putValue("Require-Bundle", "org.eclipse.help;bundle-version=\"[3.6.0,4.0.0)\"," + require);

		else
			// manifest contains reference to org.eclipse.help, bail out
			return;

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		manifest.write(out);

		ByteArrayInputStream contentStream = new ByteArrayInputStream(out.toByteArray());
		file.setContents(contentStream, 0, null);
	}

	private void updateBuildProperties(final IProject project) throws IOException, CoreException {
		final IFile file = project.getFile("build.properties");
		final Properties properties = new Properties();
		properties.load(file.getContents());
		final String property = properties.getProperty("bin.includes");
		if (!property.contains("help/")) {
			if (property.trim().isEmpty())
				properties.setProperty("bin.includes", "help/");
			else
				properties.setProperty("bin.includes", "help/," + property.trim());

			final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			properties.store(outputStream, "");
			writeFile(file, outputStream.toString());
		}
	}

	private void updatePluginXML(final IProject project) throws WorkbenchException, CoreException, IOException {
		final IFile pluginFile = project.getFile("plugin.xml");
		XMLMemento memento = XMLMemento.createReadRoot(new InputStreamReader(pluginFile.getContents(true)));
		for (IMemento extensionNode : memento.getChildren("extension")) {
			String extensionPoint = extensionNode.getString("point");
			if ("org.eclipse.help.toc".equals(extensionPoint)) {
				// a help topic is registered
				for (IMemento tocNode : extensionNode.getChildren("toc")) {
					if ("help/modules_toc.xml".equals(tocNode.getString("file")))
						// already registered
						return;
				}
			}
		}

		// modules TOC node not registered yet
		IMemento extensionNode = memento.createChild("extension");
		extensionNode.putString("point", "org.eclipse.help.toc");
		IMemento tocNode = extensionNode.createChild("toc");
		tocNode.putString("file", "help/modules_toc.xml");
		tocNode.putBoolean("primary", false);

		writeFile(project.getFile("plugin.xml"), memento.toString().replace("&#x0A;", "\n"));
	}

	private void createTOCFile(final IProject project) throws CoreException, IOException {
		XMLMemento memento = XMLMemento.createWriteRoot("toc");
		memento.putString("label", "Modules");
		memento.putString("link_to", "../org.eclipse.ease/help/scripting_book.xml#modules_anchor");
		for (String moduleName : mLookupTable.values()) {
			IMemento topicNode = memento.createChild("topic");
			topicNode.putString("href", "help/module_" + escape(moduleName) + ".html");
			topicNode.putString("label", moduleName);
		}

		writeFile(project.getFile(new Path("/help/modules_toc.xml")), memento.toString());
	}

	private boolean createHTMLFiles(final IProject project, final ClassDoc[] classes) throws CoreException, IOException {
		boolean createdFiles = false;

		for (final ClassDoc clazz : classes) {

			// only add classes which are registered in our modules lookup table
			if (mLookupTable.containsKey(clazz.qualifiedName())) {
				// class found to create help for
				StringBuffer buffer = new StringBuffer();
				buffer.append(readResourceFile("/templates/header.txt"));

				buffer.append("\t<h1>Module ");
				buffer.append(mLookupTable.get(clazz.qualifiedName()));
				buffer.append("</h1>");
				buffer.append(LINE_DELIMITER);

				buffer.append("\t<p>");
				final String classComment = clazz.commentText();
				if ((classComment != null) && (!classComment.isEmpty()))
					buffer.append(clazz.commentText());

				buffer.append("</p>");
				buffer.append(LINE_DELIMITER);

				// TODO add dependencies
				buffer.append("\t<p class=\"dependencies\">");
				buffer.append("");
				buffer.append("</p>");
				buffer.append(LINE_DELIMITER);

				buffer.append(LINE_DELIMITER);
				buffer.append("\t<h2>Function Overview</h2>");
				buffer.append(LINE_DELIMITER);
				buffer.append("\t<table class=\"functions\">");
				buffer.append(LINE_DELIMITER);
				for (final MethodDoc method : clazz.methods()) {
					if (isExported(method)) {
						buffer.append("\t\t<tr>");
						buffer.append(LINE_DELIMITER);
						buffer.append("\t\t\t<th><a href=\"#" + method.name() + "\">" + method.name() + "</a></th>");
						buffer.append(LINE_DELIMITER);
						buffer.append("\t\t\t<td>" + method.commentText() + "</td>");
						buffer.append(LINE_DELIMITER);
						buffer.append("\t\t</tr>");
						buffer.append(LINE_DELIMITER);
					}
				}
				buffer.append("\t</table>");
				buffer.append(LINE_DELIMITER);
				buffer.append(LINE_DELIMITER);

				buffer.append("\t<h2>Functions</h2>");
				buffer.append(LINE_DELIMITER);

				for (final MethodDoc method : clazz.methods()) {
					if (isExported(method)) {
						buffer.append(LINE_DELIMITER);
						buffer.append("\t<h3><a id=\"" + method.name() + "\">" + method.name() + "</a></h3>");
						buffer.append(LINE_DELIMITER);

						buffer.append("\t<p class=\"synopsis\">");
						buffer.append(method.returnType().qualifiedTypeName());
						buffer.append(" ");
						buffer.append(method.name());
						buffer.append("(");
						for (Parameter parameter : method.parameters()) {
							buffer.append(parameter.type().qualifiedTypeName());
							buffer.append(" ");
							buffer.append(parameter.name());
							buffer.append(", ");
						}
						if (method.parameters().length > 0)
							buffer.delete(buffer.length() - 2, buffer.length());

						buffer.append(")");
						buffer.append("</p>");
						buffer.append(LINE_DELIMITER);

						buffer.append("\t<p class=\"description\">" + method.commentText() + "</p>");
						buffer.append(LINE_DELIMITER);

						String synonyms = getSynonyms(method);
						if (!synonyms.isEmpty()) {
							buffer.append("\t<p class=\"synonyms\">" + synonyms.replace(";", ", ") + "</p>");
							buffer.append(LINE_DELIMITER);
						}

						if (method.parameters().length > 0) {
							buffer.append(readResourceFile("/templates/parameters_header.txt"));
							for (Parameter parameter : method.parameters()) {
								buffer.append("\t\t<tr>");
								buffer.append(LINE_DELIMITER);
								buffer.append("\t\t\t<td>" + parameter.name() + "</td>");
								buffer.append(LINE_DELIMITER);
								buffer.append("\t\t\t<td>" + createLink(parameter.type().qualifiedTypeName()) + "</td>");
								buffer.append(LINE_DELIMITER);
								buffer.append("\t\t\t<td>" + findComment(method, parameter.name()) + "</td>");
								buffer.append(LINE_DELIMITER);
								// TODO add default value
								buffer.append("\t\t</tr>");
								buffer.append(LINE_DELIMITER);
							}
							buffer.append("\t</table>");
							buffer.append(LINE_DELIMITER);
						}

						if (!"void".equals(method.returnType().qualifiedTypeName())) {
							buffer.append("\t<p class=\"return\">");
							buffer.append(createLink(method.returnType().qualifiedTypeName()));
							// TODO add return type description
							buffer.append(" ... </p>");
							buffer.append(LINE_DELIMITER);
						}
					}
				}

				buffer.append(readResourceFile("/templates/footer.txt"));

				// write document
				writeFile(project.getFile(new Path("/help/module_" + escape(mLookupTable.get(clazz.qualifiedName()) + ".html"))), buffer.toString());
				createdFiles = true;
			}
		}

		return createdFiles;
	}

	private String createLink(final String qualifiedTypeName) {
		if (qualifiedTypeName.startsWith("java.")) {
			String target = "http://docs.oracle.com/javase/7/docs/api/" + qualifiedTypeName.replace('.', '/') + ".html";

			return "<a href=\"" + target + "\">" + qualifiedTypeName + "</a>";
		}
		return qualifiedTypeName;
	}

	private String getSynonyms(final MethodDoc method) {
		for (AnnotationDesc annotation : method.annotations()) {
			if (WRAP_TO_SCRIPT.equals(annotation.annotationType().qualifiedName())) {
				for (ElementValuePair pair : annotation.elementValues()) {
					if ("alias".equals(pair.element().name()))
						return pair.value().toString();
				}
			}
		}

		return "";
	}

	private static void writeFile(final IFile file, final String data) throws CoreException, UnsupportedEncodingException {
		if (file.getParent() instanceof IFolder)
			Tools.createFolder((IFolder) file.getParent(), IResource.NONE, true);

		// save data to file
		if (!file.exists())
			file.create(new ByteArrayInputStream(data.getBytes("UTF-8")), false, null);
		else
			file.setContents(new ByteArrayInputStream(data.getBytes("UTF-8")), false, true, null);

		file.refreshLocal(IResource.DEPTH_ZERO, null);
	}

	private String escape(final String data) {
		return data.replace(' ', '_').toLowerCase();
	}

	private void createModuleLookupTable(final IProject project) {
		mLookupTable = new HashMap<String, String>();

		// read plugin.xml
		final IFile pluginXML = project.getFile("plugin.xml");

		try {
			final IMemento root = XMLMemento.createReadRoot(new InputStreamReader(pluginXML.getContents()));
			for (final IMemento extensionNode : root.getChildren("extension")) {
				if ("org.eclipse.ease.modules".equals(extensionNode.getString("point"))) {
					for (final IMemento instanceNode : extensionNode.getChildren("module"))
						mLookupTable.put(instanceNode.getString("class"), instanceNode.getString("name"));
				}
			}
		} catch (final Exception e) {
		}
	}

	/**
	 * COMMENT add method comment.
	 * 
	 * @param method
	 * @param name
	 * @return
	 */
	private String findComment(final MethodDoc method, final String name) {

		for (final ParamTag paramTags : method.paramTags()) {
			if (name.equals(paramTags.parameterName()))
				return paramTags.parameterComment();
		}

		return "";
	}

	/**
	 * COMMENT add method comment.
	 * 
	 * @param method
	 * @return
	 */
	private boolean isExported(final MethodDoc method) {
		for (final AnnotationDesc annotation : method.annotations()) {
			if (WRAP_TO_SCRIPT.equals(annotation.annotationType().qualifiedName()))
				return true;
		}

		return false;
	}

	private static StringBuffer readResourceFile(final String path) throws IOException {
		final StringBuffer buffer = new StringBuffer();
		final InputStream stream = Tools.getResourceStream("org.eclipse.ease.tools.helpgenerator", path);
		while (stream.available() > 0)
			buffer.append((char) stream.read());

		return buffer;
	}
}
