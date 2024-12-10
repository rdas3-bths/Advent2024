import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day04 {
    public static void main(String[] args) {
        ArrayList<String> fileData = getFileData("src/InputFile");

        int rows = fileData.size();
        int columns = fileData.get(0).length();
        String[][] wordSearch = new String[rows][columns];

        for (int r = 0; r < wordSearch.length; r++) {
            for (int c = 0; c < wordSearch[0].length; c++) {
                wordSearch[r][c] = fileData.get(r).substring(c, c+1);
            }
        }

        int partOne = 0;
        for (int r = 0; r < wordSearch.length; r++) {
            for (int c = 0; c < wordSearch[0].length; c++) {
                if (checkXMASUp(wordSearch, r, c)) partOne++;
                if (checkXMASDown(wordSearch, r, c)) partOne++;
                if (checkXMASLeft(wordSearch, r, c)) partOne++;
                if (checkXMASRight(wordSearch, r, c)) partOne++;
                if (checkXMASUpLeft(wordSearch, r, c)) partOne++;
                if (checkXMASUpRight(wordSearch, r, c)) partOne++;
                if (checkXMASDownLeft(wordSearch, r, c)) partOne++;
                if (checkXMASDownRight(wordSearch, r, c)) partOne++;
            }
        }
        System.out.println("Part one answer: " + partOne);

        int partTwo = 0;
        for (int r = 0; r < wordSearch.length; r++) {
            for (int c = 0; c < wordSearch[0].length; c++) {
                if (wordSearch[r][c].equals("A")) {
                    if (checkXMas(wordSearch, r, c)) partTwo++;
                }
            }
        }

        System.out.println("Part two answer: " + partTwo);
    }

    public static boolean checkXMas(String[][] wordSearch, int r, int c) {

        try {
            // get up left and down right
            String upLeft = wordSearch[r-1][c-1];
            String downRight = wordSearch[r+1][c+1];
            String firstDiag = upLeft + downRight;

            // get up right and down left
            String upRight = wordSearch[r-1][c+1];
            String downLeft = wordSearch[r+1][c-1];
            String secondDiag = upRight + downLeft;

            if (firstDiag.equals("MS") || firstDiag.equals("SM")) {
                if (secondDiag.equals("MS") || secondDiag.equals("SM")) {
                    return true;
                }
            }
            return false;
        }
        catch (Exception e) {
            return false;
        }

    }

    public static boolean checkXMASUp(String[][] wordSearch, int r, int c) {
        try {
            String first = wordSearch[r][c];
            String second = wordSearch[r-1][c];
            String third = wordSearch[r-2][c];
            String fourth = wordSearch[r-3][c];
            String word = first + second + third + fourth;
            if (word.equals("XMAS"))
                return true;
            else
                return false;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static boolean checkXMASDown(String[][] wordSearch, int r, int c) {
        try {
            String first = wordSearch[r][c];
            String second = wordSearch[r+1][c];
            String third = wordSearch[r+2][c];
            String fourth = wordSearch[r+3][c];
            String word = first + second + third + fourth;
            if (word.equals("XMAS"))
                return true;
            else
                return false;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static boolean checkXMASLeft(String[][] wordSearch, int r, int c) {
        try {
            String first = wordSearch[r][c];
            String second = wordSearch[r][c-1];
            String third = wordSearch[r][c-2];
            String fourth = wordSearch[r][c-3];
            String word = first + second + third + fourth;
            if (word.equals("XMAS"))
                return true;
            else
                return false;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static boolean checkXMASRight(String[][] wordSearch, int r, int c) {
        try {
            String first = wordSearch[r][c];
            String second = wordSearch[r][c+1];
            String third = wordSearch[r][c+2];
            String fourth = wordSearch[r][c+3];
            String word = first + second + third + fourth;
            if (word.equals("XMAS"))
                return true;
            else
                return false;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static boolean checkXMASUpLeft(String[][] wordSearch, int r, int c) {
        try {
            String first = wordSearch[r][c];
            String second = wordSearch[r-1][c-1];
            String third = wordSearch[r-2][c-2];
            String fourth = wordSearch[r-3][c-3];
            String word = first + second + third + fourth;
            if (word.equals("XMAS"))
                return true;
            else
                return false;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static boolean checkXMASUpRight(String[][] wordSearch, int r, int c) {
        try {
            String first = wordSearch[r][c];
            String second = wordSearch[r-1][c+1];
            String third = wordSearch[r-2][c+2];
            String fourth = wordSearch[r-3][c+3];
            String word = first + second + third + fourth;
            if (word.equals("XMAS"))
                return true;
            else
                return false;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static boolean checkXMASDownLeft(String[][] wordSearch, int r, int c) {
        try {
            String first = wordSearch[r][c];
            String second = wordSearch[r+1][c-1];
            String third = wordSearch[r+2][c-2];
            String fourth = wordSearch[r+3][c-3];
            String word = first + second + third + fourth;
            if (word.equals("XMAS"))
                return true;
            else
                return false;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static boolean checkXMASDownRight(String[][] wordSearch, int r, int c) {
        try {
            String first = wordSearch[r][c];
            String second = wordSearch[r+1][c+1];
            String third = wordSearch[r+2][c+2];
            String fourth = wordSearch[r+3][c+3];
            String word = first + second + third + fourth;
            if (word.equals("XMAS"))
                return true;
            else
                return false;
        }
        catch (Exception e) {
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
