/**
 * JarLoader
 * @author Sam Malpass
 * @version 0.0.9
 * @since 0.0.3
 */
package fileHandler;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.net.URLClassLoader;
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
                    if(!jarEntry.getName().contains("fileHandler/") && !jarEntry.getName().contains("mapReduce")) {
                        classes.add(jarEntry.getName().replaceAll("/", "\\."));
                    }
                }
            }
        }
        catch (Exception e) {
            System.err.println("[ERROR] Failed to load JAR");
        }
        return classes;
    }

    /**
     * Function createObject()
     * <p>
     *     Attempts to create an object of a class from an external class
     * </p>
     * @param jarName is the JAR file
     * @param className is the name of the class
     * @return the object
     */
    public Object createObject(String jarName, String className) {
        File jarFile = new File(jarName);
        Object instance = null;
        try {
            URL jarPath = jarFile.toURI().toURL();
            String jarURL = "jar:"+jarPath+"!/";
            URL[] urls = {new URL(jarURL)};
            URLClassLoader child = new URLClassLoader(urls);
            Class load = Class.forName(className, true, child);
            instance = load.newInstance();
        }
        catch (Exception e) {
            System.err.println("[ERROR] Issue loading class " + className + " from JAR file " + jarName);
        }
        return instance;
    }
}
