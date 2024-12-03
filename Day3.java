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
        String regexPartTwo = "mul\\([1-9][0-9]{0,2},[1-9][0-9]{0,2}\\)|do\\(\\)|don't\\(\\)";

        Matcher partOne = Pattern.compile(regex).matcher(operations);
        Matcher partTwo = Pattern.compile(regexPartTwo).matcher(operations);

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

        boolean process = true;
        for (String op : partTwoMatches) {

            if (op.equals("don't()")) {
                process = false;
            }
            if (op.equals("do()")) {
                process = true;
            }
            if (op.contains("mul") && process)
                partTwoTotal += doMulOperation(op);
        }

        System.out.println("Part one answer: " + partOneTotal);
        System.out.println("Part one answer: " + partTwoTotal);
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
