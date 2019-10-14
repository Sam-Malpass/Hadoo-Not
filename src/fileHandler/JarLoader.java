/**
 * JarLoader
 * @author Sam Malpass
 * @version 0.0.3
 * @since 0.0.3
 */
package fileHandler;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class JarLoader {

    /**
     * Constructor with no arguments
     * <p>
     *     Sets up the object
     * </p>
     */
    public JarLoader() {}

    /**
     * Function getClassNames()
     * <p>
     *     Load and scan a JAR file for all the classes inside
     * </p>
     * @param jarName is the JAR file to scan
     * @return the list of classes
     */
    public ArrayList<String> getClassNames(String jarName) {
        ArrayList<String> classes = new ArrayList<>();
        try {
            JarInputStream jarFile = new JarInputStream(new FileInputStream(jarName));
            JarEntry jarEntry;
            while(true) {
                jarEntry = jarFile.getNextJarEntry();
                if(jarEntry == null) {
                    break;
                }
                if(jarEntry.getName().endsWith(".class")) {
                    classes.add(jarEntry.getName().replaceAll("/", "\\."));
                }
            }
        }
        catch (Exception e) {
            System.err.println("[ERROR] Failed to load JAR");
        }
        return classes;
    }

    public
}
