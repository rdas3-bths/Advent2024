import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {
    public static void main(String[] args) {
        ArrayList<String> fileData = getFileData("src/InputFile");

        // modified input to be on one line for easier processing
        String operations = fileData.get(0);

        ArrayList<String> partOneMatches = new ArrayList<String>();
        ArrayList<String> partTwoMatches = new ArrayList<String>();

        String regex = "mul\\([1-9][0-9]{0,2},[1-9][0-9]{0,2}\\)";

        String filteredOperations = filterOperations(operations);

        Matcher partOne = Pattern.compile(regex).matcher(operations);
        Matcher partTwo = Pattern.compile(regex).matcher(filteredOperations);

        while (partOne.find()) {
            partOneMatches.add(partOne.group());
        }

        while (partTwo.find()) {
            partTwoMatches.add(partTwo.group());
        }


        int partOneTotal = 0;
        int partTwoTotal = 0;

        for (String op : partOneMatches) {
            partOneTotal += doMulOperation(op);
        }

        for (String op : partTwoMatches) {
            partTwoTotal += doMulOperation(op);
        }

        System.out.println("Part one answer: " + partOneTotal);
        System.out.println("Part one answer: " + partTwoTotal);
    }

    public static String filterOperations(String fullOp) {
        String newOp = "";
        boolean foundDont = false;
        for (int i = 0; i < fullOp.length(); i++) {
            String currentLetter = fullOp.substring(i, i+1);
            if (currentLetter.equals("d")) {

                String dontCheck = fullOp.substring(i, i+7);
                String doCheck = fullOp.substring(i, i+4);
                if (dontCheck.equals("don't()")) {
                    foundDont = true;
                }
                if (doCheck.equals("do()")) {
                    foundDont = false;
                }
            }
            if (!foundDont) {
                newOp += currentLetter;
            }

        }
        return newOp;
    }

    public static int doMulOperation(String op) {
        int openParenthesis = op.indexOf("(");
        int comma = op.indexOf(",");
        int closeParenthesis = op.indexOf(")");
        String first = op.substring(openParenthesis+1, comma);
        String second = op.substring(comma+1, closeParenthesis);
        return Integer.parseInt(first) * Integer.parseInt(second);
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
