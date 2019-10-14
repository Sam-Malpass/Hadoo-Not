/**
 * Tester
 * @author Sam Malpass
 * @version 0.0.2
 * @since 0.0.2
 */
package testSuite;

import fileHandler.FileHandler;
import java.util.ArrayList;

public class Test {

    /**
     * Function fileReading()
     * <p>
     *     Tests the opening and reading of a file
     * </p>
     */
    public static void fileReading() {
        FileHandler testHandler = new FileHandler();
        ArrayList<String> list = testHandler.read("./src/AComp_Passenger_data.csv");
        System.out.println("##### TESTING FILE READING #####");
        for(String s : list) {
            System.out.println(s);
        }
        System.out.println("##### FILE READING TEST COMPLETE #####");
    }

    /**
     * Function fileWriting()
     * <p>
     *     Tests the writing of a file
     * </p>
     */
    public static void fileWriting() {
        FileHandler testHandler = new FileHandler();
        ArrayList<String> list = testHandler.read("./src/AComp_Passenger_data.csv");
        String builder = "";
        for(String s : list) {
            builder = builder + s + "\n";
        }
        System.out.println("##### TESTING FILE WRITING #####");
        testHandler.write("testOutput.txt", builder);
        System.out.println("##### FILE WRITING TEST COMPLETE #####");
    }
}
