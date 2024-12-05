import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day5 {
    public static void main(String[] args) {
        ArrayList<String> fileData = getFileData("src/InputFile");
        ArrayList<int[]> allRules = new ArrayList<int[]>();
        ArrayList<int[]> allNumbers = new ArrayList<int[]>();
        for (String line : fileData) {
            if (line.contains("|")) {
                String[] rules = line.split("\\|");
                int[] convertedRules = new int[rules.length];
                for (int i = 0; i < rules.length; i++) {
                    convertedRules[i] = Integer.parseInt(rules[i]);
                }
                allRules.add(convertedRules);
            }
            if (line.contains(",")) {
                String[] numbers = line.split(",");
                int[] convertedNumbers = new int[numbers.length];
                for (int i = 0; i < numbers.length; i++) {
                    convertedNumbers[i] = Integer.parseInt(numbers[i]);
                }
                allNumbers.add(convertedNumbers);
            }
        }

        int partOneAnswer = 0;
        int partTwoAnswer = 0;
        for (int[] numbers : allNumbers) {
            boolean passedAllRules = true;
            for (int[] rules : allRules) {
                boolean ruleStatus = checkRule(numbers, rules);
                if (!ruleStatus)
                    passedAllRules = false;
            }
            if (passedAllRules) {
                int middle = numbers[numbers.length/2];
                partOneAnswer += middle;
            }
            else {
                ArrayList<int[]> validRules = new ArrayList<int[]>();
                for (int[] rules : allRules) {
                    if (isValidRule(numbers, rules))
                        validRules.add(rules);
                }
                int middle = numbers.length/2;
                for (int n : numbers) {
                    int count = 0;
                    for (int[] validRule : validRules) {
                        if (validRule[1] == n)
                            count++;
                    }
                    if (count == middle)
                        partTwoAnswer += n;
                }
            }
        }

        System.out.println("Part one answer: " + partOneAnswer);
        System.out.println("Part two answer: " + partTwoAnswer);

    }

    public static boolean isValidRule(int[] numbers, int[] rule) {
        int firstNumber = rule[0];
        int secondNumber = rule[1];

        // check if a rule number is in the list at all
        boolean firstNumberFound = false;
        boolean secondNumberFound = false;
        boolean processRule = false;
        for (int n : numbers) {
            if (firstNumber == n)
                firstNumberFound = true;
            if (secondNumber == n)
                secondNumberFound = true;
        }

        if (firstNumberFound && secondNumberFound) {
            processRule = true;
        }


        return processRule;
    }

    public static boolean checkRule(int[] numbers, int[] rule) {

        int firstNumber = rule[0];
        int secondNumber = rule[1];

        if (!isValidRule(numbers, rule))
            return true;

        int firstNumberIndex = -1;
        int secondNumberIndex = -1;

        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == firstNumber) {
                firstNumberIndex = i;
            }
            if (numbers[i] == secondNumber) {
                secondNumberIndex = i;
            }
        }

        if (firstNumberIndex < secondNumberIndex) {
            return true;
        }
        else {
            return false;
        }
    }

    public static ArrayList<String> getFileData(String fileName) {
        ArrayList<String> fileData = new ArrayList<String>();
        try {
            File f = new File(fileName);
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                String line = s.nextLine();
                if (!line.equals(""))
                    fileData.add(line);
            }
            return fileData;
        }
        catch (FileNotFoundException e) {
            return fileData;
        }
    }
}
