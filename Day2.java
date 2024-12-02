import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Arrays;

public class Day2 {
    public static void main(String[] args) {

        ArrayList<String> fileData = getFileData("src/InputFile");
        ArrayList<ArrayList<Integer>> lists = new ArrayList<ArrayList<Integer>>();
        for (String line : fileData) {
            String[] numbers = line.split(" ");
            ArrayList<Integer> convertedNumbers = new ArrayList<Integer>();
            for (String number : numbers) {
                convertedNumbers.add(Integer.parseInt(number));
            }
            lists.add(convertedNumbers);
        }

        System.out.println("Part one answer: " + doPartOne(lists));
        System.out.println("Part two answer: " + doPartTwo(lists));

    }

    private static int doPartOne(ArrayList<ArrayList<Integer>> lists) {
        int safe = 0;

        for (ArrayList<Integer> list : lists) {
            if (checkSafe(list))
                safe++;
        }

        return safe;
    }

    private static int doPartTwo(ArrayList<ArrayList<Integer>> lists) {
        int safe = 0;

        for (ArrayList<Integer> list : lists) {
            if (checkSafe(list))
                safe++;
            else {
                int removeIndex = 0;
                boolean safeFound = false;
                while (removeIndex != list.size() && !safeFound) {
                    ArrayList<Integer> copyList = new ArrayList<Integer>();
                    for (int j = 0; j < list.size(); j++) {
                        if (j != removeIndex) {
                            copyList.add(list.get(j));
                        }
                    }
                    if (checkSafe(copyList)) {
                        safe++;
                        safeFound = true;
                    }
                    removeIndex++;
                }

            }
        }

        return safe;
    }

    private static boolean checkSafe(ArrayList<Integer> numbers) {
        boolean safe = true;

        ArrayList<Integer> differences = new ArrayList<Integer>();

        for (int i = 0; i < numbers.size()-1; i++) {
            int diff = numbers.get(i) - numbers.get(i+1);
            differences.add(diff);
        }

        if (!checkAllPositive(differences) && !checkAllNegative(differences)) {
            return false;
        }

        return checkDifferenceValues(differences);
    }

    private static boolean checkDifferenceValues(ArrayList<Integer> numbers) {
        for (int diff : numbers) {
            int absoluteDiff = Math.abs(diff);
            if (absoluteDiff < 1)
                return false;
            if (absoluteDiff > 3)
                return false;
        }
        return true;
    }

    private static boolean checkAllPositive(ArrayList<Integer> numbers) {

        for (int number : numbers) {
            if (number <= 0) {
                return false;
            }
        }

        return true;
    }

    private static boolean checkAllNegative(ArrayList<Integer> numbers) {

        for (int number : numbers) {
            if (number >= 0) {
                return false;
            }
        }

        return true;
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