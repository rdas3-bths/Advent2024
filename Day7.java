import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day7 {
    public static void main(String[] args) {
        long partOneAnswer = 0;
        long partTwoAnswer = 0;
        ArrayList<String> fileData = getFileData("src/InputFile");
        ArrayList<EquationChecker> all_equations = new ArrayList<EquationChecker>();
        for (String line : fileData) {
            long r = Long.parseLong(line.split(":")[0]);
            ArrayList<Integer> convertedOperands = new ArrayList<Integer>();
            String[] operands = line.split(": ")[1].split(" ");
            for (String o : operands) {
                convertedOperands.add(Integer.parseInt(o));
            }
            EquationChecker e = new EquationChecker(r, convertedOperands, false);
            if (e.isValidEquation()) partOneAnswer += e.getResult();
            EquationChecker e2 = new EquationChecker(r, convertedOperands, true);
            if (e2.isValidEquation()) partTwoAnswer += e2.getResult();
            all_equations.add(e);
        }

        System.out.println("Part one answer: " + partOneAnswer);
        System.out.println("Part two answer: " + partTwoAnswer);
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

class EquationChecker {
    private long result;
    private ArrayList<Integer> operands;
    private ArrayList<String> operatorCombinations;
    private ArrayList<ArrayList<Integer>> allEquations;
    private boolean validEquation;

    public EquationChecker(long result, ArrayList<Integer> operands, boolean partTwo) {
        this.result = result;
        this.operands = operands;
        String operatorsString = "01";
        if (partTwo) operatorsString = "012";
        operatorCombinations = generateCombinations(operatorsString, operands.size()-1);
        allEquations = new ArrayList<ArrayList<Integer>>();
        for (String operators : operatorCombinations) {
            int index = 1;
            ArrayList<Integer> equation = new ArrayList<Integer>();
            for (int n : operands) {
                equation.add(n);
            }
            for (int i = 0; i < operators.length(); i++) {
                int operator = Integer.parseInt(operators.substring(i, i+1));
                equation.add(index, operator);
                index += 2;
            }
            allEquations.add(equation);
        }

        for (ArrayList<Integer> equation : allEquations) {
            evaluateEquation(equation);
        }
    }

    private long evaluateEquation(ArrayList<Integer> equation) {
        long result = 0;
        for (int i = 1; i < equation.size(); i += 2) {
            int operator = equation.get(i);
            if (i == 1) {
                if (operator == 0) result += equation.get(0) + equation.get(i+1);
                if (operator == 1) result += (long) equation.get(0) * equation.get(i+1);
                if (operator == 2) result += Long.parseLong(("" + equation.get(0) + equation.get(i+1)));
            }
            else {
                if (operator == 0) result += equation.get(i+1);
                if (operator == 1) result *= equation.get(i+1);
                if (operator == 2) result = Long.parseLong("" + result + equation.get(i+1));
            }
        }
        if (result == this.result) validEquation = true;
        return result;
    }

    public String toString() {
        return "\nDesired result: " + result + "\nOperands: " + operands + "\n" + "Valid: " + validEquation;
    }

    private ArrayList<String> generateCombinations(String input, int length) {
        ArrayList<String> result = new ArrayList<>();
        generateCombinationsHelper(input, "", length, result);
        return result;
    }

    private void generateCombinationsHelper(String input, String current, int length, ArrayList<String> result) {
        if (current.length() == length) {
            result.add(current);
            return;
        }
        for (int i = 0; i < input.length(); i++) {
            generateCombinationsHelper(input, current + input.charAt(i), length, result);
        }
    }

    public boolean isValidEquation() {
        return validEquation;
    }

    public long getResult() {
        return result;
    }
}
