/**
 * RegGuess
 * @author Sam Malpass
 * @version 0.0.7
 * @since 0.0.7
 */
package mapReduce;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class RegGuess {

    /**
     * Constructor with no arguments
     * <p>
     *     Sets up the object
     * </p>
     */
    public RegGuess() {}

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

    private ArrayList<ArrayList<String>> convertTable(ArrayList<ArrayList<String>> table) {
        ArrayList<ArrayList<String>> convert = new ArrayList<>();
        int numCols = table.get(0).size();
        for(int i = 0; i < numCols; i++) {
            ArrayList<String> column = new ArrayList<>();
            for(ArrayList<String> row : table) {
                column.add(row.get(i));
            }
            convert.add(column);
        }
        return convert;
    }
    public ArrayList<String> guessRegex(ArrayList<String> input, String delimiter) {
        ArrayList<String> sample = sample(input);
        ArrayList<ArrayList<String>> table = new ArrayList<>();
        for(String entry : sample) {
            String[] line = entry.split(delimiter);
            ArrayList<String> row = new ArrayList<>(Arrays.asList(line));
            if(!row.contains("")) {
                table.add(row);
            }
        }
        ArrayList<ArrayList<String>> tableConvert = convertTable(table);
        ArrayList<String> regexList = new ArrayList<>();
        for(ArrayList<String> column : tableConvert) {
            ArrayList<String> regexCol = new ArrayList<>();
            for(String attribute : column) {
                String regex = "";
                char[] characters = attribute.toCharArray();
                for(char c : characters) {
                    if(Character.isUpperCase(c)) {
                        regex = regex + "[A-Z]";
                    }
                    else if(Character.isLowerCase(c)) {
                        regex = regex + "[a-z]";
                    }
                    else if(Character.isDigit(c)) {
                        regex = regex + "[0-9]";
                    }
                    else {
                        regex = regex + "[" + c + "]";
                    }
                }
                regexCol.add(regex);
            }
            String currentRegex = "";
            int currentBest = 0;
            for(String regex : regexCol) {
                int frequency = 0;
                for(String test : regexCol) {
                    if(test.equals(regex)) {
                        frequency++;
                    }
                }
                if(frequency > currentBest) {
                    currentBest = frequency;
                    currentRegex = regex;
                }
            }
            regexList.add(currentRegex);
        }
        return regexList;
    }
}
