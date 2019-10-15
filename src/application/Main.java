/**
 * Main
 * @author Sam Malpass
 * @version 0.0.3
 * @since 0.0.0
 */
package application;


public class Main {

    /**
     * Function main()
     * <p>
     *     Entry point for application
     * </p>
     * @param args passed arguments
     */
    public static void main(String[] args) {
        Process p = new Process(10, "mapReduce.premadeJobs.Task1");
        p.start("./src/AComp_Passenger_data.csv", "Output.txt");
    }
}
