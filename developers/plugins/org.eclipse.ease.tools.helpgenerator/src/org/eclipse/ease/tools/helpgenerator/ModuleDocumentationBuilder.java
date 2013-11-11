package org.eclipse.ease.tools.helpgenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.osgi.framework.FrameworkUtil;

import com.sun.tools.javadoc.Main;

public class ModuleDocumentationBuilder extends IncrementalProjectBuilder {

    public static final String BUILDER_ID = "com.infineon.modulesDocumentationBuilder";

    @Override
    protected IProject[] build(final int kind, final Map<String, String> args, final IProgressMonitor monitor) throws CoreException {
        try {

            final IProject project = getProject();

            final ArrayList<String> parameters = new ArrayList<String>();
            parameters.add("-d");
            parameters.add(project.getFullPath().toPortableString());
            parameters.add("-sourcepath");
            parameters.add(project.getLocation().append("src").toOSString());
            parameters.add("-subpackages");
            parameters.add("com");
            parameters.add("-doclet");
            parameters.add("com.infineon.javascript.helpgenerator.JavascriptDoclet");
            parameters.add("-docletpath");
            parameters.add(FileLocator.toFileURL(FrameworkUtil.getBundle(this.getClass()).getEntry("/")).getPath());
            parameters.add("-classpath");
            parameters.add(FileLocator.toFileURL(FrameworkUtil.getBundle(this.getClass()).getEntry("/lib/Annotations.jar")).getPath());
            for (final String param : parameters)
                System.out.println("\tP: " + param);

            try {
                Main.execute("Module Doc Generator", parameters.toArray(new String[parameters.size()]));
            } catch (final NoClassDefFoundError e) {
                // need to run the garbage collector to be ready for next build
                System.gc();

                // try to re-run
                Main.execute("Module Doc Generator", parameters.toArray(new String[parameters.size()]));
            }

        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public static final boolean hasBuilder(final IProject project) {
        try {
            for (final ICommand buildSpec : project.getDescription().getBuildSpec()) {
                if (BUILDER_ID.equals(buildSpec.getBuilderName()))
                    return true;
            }
        } catch (final CoreException e) {
        }

        return false;
    }

}
