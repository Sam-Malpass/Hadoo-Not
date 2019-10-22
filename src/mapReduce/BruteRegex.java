/**
 * BruteRegex
 * @author Sam Malpass
 * @version 0.0.7
 * @since 0.0.7
 */
package mapReduce;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class BruteRegex {

    /**
     * Constructor with no arguments
     * <p>
     *     Sets up the object
     * </p>
     */
    public BruteRegex() {}

    /**
     * Function sample()
     * <p>
     *     Takes the input data and samples 10% of the data entries up to 500 data entries
     * </p>
     * @param input is the data being read in
     * @return the sample of the data
     */
    private ArrayList<String> sample(ArrayList<String> input) {
        int sampleSize = ( input.size() / 100 ) * 10;
        if(sampleSize > 500) {
            sampleSize = 500;
        }
        ArrayList<String> sample = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < sampleSize; i++) {
            sample.add(input.get(random.nextInt(input.size())));
        }
        return sample;
    }


    public ArrayList<String> guessRegex(ArrayList<String> input, String delimiter) {
        ArrayList<String> regexList = new ArrayList<>();
        ArrayList<String> sample = sample(input);
        for(String entry : sample) {
            String[] line = entry.split(delimiter);
            ArrayList<String> row = new ArrayList<>(Arrays.asList(line));
        }
    }
}
