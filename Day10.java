import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class Day10 {

    public static void main(String[] args) {
        ArrayList<String> fileData = getFileData("src/InputFile");
        int rows = fileData.size();
        int columns = fileData.get(0).length();
        int[][] lavaMap = new int[rows][columns];
        for (int r = 0; r < lavaMap.length; r++) {
            for (int c = 0; c < lavaMap[0].length; c++) {
                String entry = fileData.get(r).substring(c, c + 1);
                if (entry.equals(".")) lavaMap[r][c] = -100;
                else
                    lavaMap[r][c] = Integer.parseInt(entry);

            }
        }

        int partOneAnswer = 0;
        int partTwoAnswer = 0;

        for (int r = 0; r < lavaMap.length; r++) {
            for (int c = 0; c < lavaMap[0].length; c++) {
                if (lavaMap[r][c] == 0) {
                    TrailHeadInfo trailHead = new TrailHeadInfo(r, c);
                    traverseMap(lavaMap, r, c, trailHead);
                    partOneAnswer += trailHead.getEndPoints().size();
                    partTwoAnswer += trailHead.getRating();
                }
            }
        }


        System.out.println("Part one answer: " + partOneAnswer);
        System.out.println("Part two answer: " + partTwoAnswer);
    }

    public static void traverseMap(int[][] lavaMap, int row, int col, TrailHeadInfo trailHead) {
        // base case, found a 9
        if (lavaMap[row][col] == 9) {
            trailHead.addEndPoint(row + "," + col);
        }
        else {
            // find directions
            String directions = getDirections(lavaMap, row, col);

            // for each direction
            for (int i = 0; i < directions.length(); i++) {
                int newRow = row;
                int newCol = col;
                char d = directions.charAt(i);

                if (d == 'u') newRow--;
                if (d == 'd') newRow++;
                if (d == 'l') newCol--;
                if (d == 'r') newCol++;

                // recursive call to try each direction
                traverseMap(lavaMap, newRow, newCol, trailHead);
            }
        }
    }

    public static String getDirections(int[][] lavaMap, int row, int column) {
        String directions = "";
        // right
        if (column < lavaMap[0].length - 1) {
            if (lavaMap[row][column] + 1 == lavaMap[row][column+1]) {
                directions += "r";
            }
        }
        if (column > 0) {
            // left
            if (lavaMap[row][column] + 1 == lavaMap[row][column-1]) {
                directions += "l";
            }
        }

        if (row > 0) {
            // up
            if (lavaMap[row][column] + 1 == lavaMap[row-1][column]) {
                directions += "u";
            }
        }

        if (row < lavaMap.length - 1) {
            // down
            if (lavaMap[row][column] + 1 == lavaMap[row+1][column]) {
                directions += "d";
            }
        }

        return directions;

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

class TrailHeadInfo {
    private HashSet<String> endPoints;
    private int rating;
    private int startingRow;
    private int startingCol;

    public TrailHeadInfo(int r, int c) {
        rating = 0;
        endPoints = new HashSet<String>();
        startingRow = r;
        startingCol = c;
    }

    public void addEndPoint(String endPoint) {

        endPoints.add(endPoint);

        // this is for part 2
        // if we are trying to add an end point that we already got to, the rating should go up
        if (endPoints.contains(endPoint)) rating++;
    }

    public HashSet<String> getEndPoints() {
        return endPoints;
    }

    public int getRating() {
        return rating;
    }
}
