import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class Day10 {

    public static int rating = 0;

    public static void main(String[] args) {
        ArrayList<String> fileData = getFileData("src/InputFile");
        int rows = fileData.size();
        int columns = fileData.get(0).length();
        String[][] originalGrid = new String[rows][columns];
        for (int r = 0; r < originalGrid.length; r++) {
            for (int c = 0; c < originalGrid[0].length; c++) {
                originalGrid[r][c] = fileData.get(r).substring(c, c + 1);
            }
        }

        int[][] lavaMap = new int[rows][columns];
        for (int r = 0; r < originalGrid.length; r++) {
            for (int c = 0; c < originalGrid[0].length; c++) {
                if (originalGrid[r][c].equals(".")) {
                    lavaMap[r][c] = -100;
                }
                else {
                    lavaMap[r][c] = Integer.parseInt(originalGrid[r][c]);
                }
            }
        }

        ArrayList<int[]> startingPositions = new ArrayList<int[]>();
        for (int r = 0; r < lavaMap.length; r++) {
            for (int c = 0; c < lavaMap[0].length; c++) {
                if (lavaMap[r][c] == 0) {
                    int[] position = new int[2];
                    position[0] = r;
                    position[1] = c;
                    startingPositions.add(position);
                }
            }
        }

        int partOneAnswer = 0;
        int partTwoAnswer = 0;
        for (int[] position : startingPositions) {
            rating = 0;
            TrailHeadInfo trailHead = new TrailHeadInfo(position[0], position[1]);
            traverseMap(lavaMap, position[0], position[1], trailHead);
            partOneAnswer += trailHead.getEndPoints().size();
            partTwoAnswer += trailHead.getRating();
        }
        System.out.println("Part one answer: " + partOneAnswer);
        System.out.println("Part two answer: " + partTwoAnswer);
    }

    public static TrailHeadInfo traverseMap(int[][] lavaMap, int startRow, int startCol, TrailHeadInfo trailHead) {


        // base case, found a 9
        if (lavaMap[startRow][startCol] == 9) {

            trailHead.addEndPoint(startRow + "," + startCol);
            if (trailHead.getEndPoints().contains(startRow + "," + startCol)) {
                trailHead.increaseRating();
            }
            return trailHead;
        }

        // find directions
        String directions = getDirections(lavaMap, startRow, startCol);

        for (int i = 0; i < directions.length(); i++) {
            int newRow = startRow;
            int newCol = startCol;
            char d = directions.charAt(i);

            if (d == 'u') newRow--;
            if (d == 'd') newRow++;
            if (d == 'l') newCol--;
            if (d == 'r') newCol++;

            traverseMap(lavaMap, newRow, newCol, trailHead);
        }

        return trailHead;

    }

    public static String getDirections(int[][] lavaMap, int row, int column) {
        String directions = "";
        try {
            // right
            if (lavaMap[row][column] + 1 == lavaMap[row][column+1]) {
                directions += "r";
            }
        }
        catch (ArrayIndexOutOfBoundsException a) { }

        try {
            // left
            if (lavaMap[row][column] + 1 == lavaMap[row][column-1]) {
                directions += "l";
            }
        }
        catch (ArrayIndexOutOfBoundsException a) { }

        try {
            // up
            if (lavaMap[row][column] + 1 == lavaMap[row-1][column]) {
                directions += "u";
            }
        }
        catch (ArrayIndexOutOfBoundsException a) { }

        try {
            // down
            if (lavaMap[row][column] + 1 == lavaMap[row+1][column]) {
                directions += "d";
            }
        }
        catch (ArrayIndexOutOfBoundsException a) { }

        return directions;

    }

    public static void printGrid(int[][] grid) {
        for (int[] r : grid) {
            for (int c : r) {
                System.out.print(c);
            }
            System.out.println();
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

    public void increaseRating() {
        rating++;
    }

    public void addEndPoint(String endPoint) {
        endPoints.add(endPoint);
    }

    public HashSet<String> getEndPoints() {
        return endPoints;
    }

    public int getRating() {
        return rating;
    }
}
