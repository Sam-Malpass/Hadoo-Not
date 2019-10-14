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
    public static void fileReading() {
        FileHandler testHandler = new FileHandler();
        ArrayList<String> list = testHandler.read("./src/AComp_Passenger_data.csv");
        System.out.println("##### TESTING FILE READING #####");
        for(String s : list) {
            System.out.println(s);
        }
        System.out.println("##### FILE READING TEST COMPLETE #####");
    }
}
