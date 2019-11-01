/**
 * Chain
 * @author Sam Malpass
 * @version 0.0.8
 * @since 0.0.8
 */
package application;

import java.util.ArrayList;

public class Chain {

    /**
     * jobChain holds a list of all Jobs to be performed in the process
     */
    ArrayList<Process> jobChain;

    /**
     * Constructor with arguments
     * <p>
     *
     * </p>
     * @param jarPath is the JAR to load jobs from
     * @param jobNames the list of jobs to be performed in order
     */
    public Chain(String jarPath, ArrayList<String> jobNames) {
        for(String s : jobNames) {
            Process p = new Process(jarPath, s);
            jobChain.add(p);
        }
    }
}
