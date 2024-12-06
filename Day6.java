import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Day6 {
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

        String[][] grid = new String[rows][columns];
        for (int r = 0; r < originalGrid.length; r++) {
            for (int c = 0; c < originalGrid[0].length; c++) {
                grid[r][c] = originalGrid[r][c];
            }
        }

        HashSet<String> originalPath = traverseMap(grid);

        System.out.println("Part one answer: " + countVisited(grid));

        HashSet<String> loopPositions = new HashSet<String>();

        // save all the UNIQUE original positions that we traversed the first time through
        // the map
        for (String s : originalPath) {
            if (s.contains(",")) {
                String[] data = s.split(",");
                String position = data[0] + "," + data[1];
                if (!loopPositions.contains(position)) {
                    loopPositions.add(position);
                }
            }
        }

        // put an obstacle at every original position on the map and check
        // if it loops
        int loopsCreated = 0;
        for (String loopPosition : loopPositions) {

            // reset the map
            rows = originalGrid.length;
            columns = originalGrid[0].length;
            grid = new String[rows][columns];
            for (int r = 0; r < grid.length; r++) {
                for (int c = 0; c < grid[0].length; c++) {
                    grid[r][c] = originalGrid[r][c];
                }
            }
            String[] obstacle = loopPosition.split(",");
            int[] obstacleInt = new int[2];
            obstacleInt[0] = Integer.parseInt(obstacle[0]);
            obstacleInt[1] = Integer.parseInt(obstacle[1]);
            // put an obstacle in place
            grid[obstacleInt[0]][obstacleInt[1]] = "#";

            // traverse map and check if it looped
            HashSet<String> path = traverseMap(grid);
            if (path.contains("yes"))
                loopsCreated++;
        }

        System.out.println("Part two answer: " + loopsCreated);
    }

    public static HashSet<String> traverseMap(String[][] grid) {

        // set up starting positions
        int guardRow = -1;
        int guardColumn = -1;
        String direction = "N";
        HashSet<String> positionsVisited = new HashSet<String>();
        String foundLoop = "no";

        // set up where the guard starts
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c].equals("^")) {
                    guardRow = r;
                    guardColumn = c;
                    grid[r][c] = "X";
                }
            }
        }

        boolean canMove = true;
        while (canMove) {

            // get next position
            int nextRow = guardRow;
            int nextColumn = guardColumn;

            if (direction.equals("N")) nextRow = guardRow - 1;
            if (direction.equals("E")) nextColumn = guardColumn + 1;
            if (direction.equals("S")) nextRow = guardRow + 1;
            if (direction.equals("W")) nextColumn = guardColumn - 1;

            try {
                // found an obstacle
                if (grid[nextRow][nextColumn].equals("#")) {
                    direction = turn(direction);
                }
                else {
                    // no obstacle, move here
                    grid[nextRow][nextColumn] = "X";
                    guardRow = nextRow;
                    guardColumn = nextColumn;
                    // have we been here before?
                    if (foundLoop(guardRow, guardColumn, direction, positionsVisited)) {
                        foundLoop = "yes";
                        canMove = false;
                    }
                    // save that we have been here
                    positionsVisited.add(guardRow + "," + guardColumn + "," + direction);
                }
            }
            catch (Exception e) {
                canMove = false;
            }
        }

        // the last item in the list is whether we looped or not
        positionsVisited.add(foundLoop);
        return positionsVisited;
    }

    public static boolean foundLoop(int r, int c, String direction, HashSet<String> positionsVisited) {
        String newPosition = r + "," + c + "," + direction;
        return positionsVisited.contains(newPosition);
    }

    public static int countVisited(String[][] grid) {
        int count = 0;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid.length; c++) {
                if (grid[r][c].equals("X")) count++;
            }
        }
        return count;
    }

    public static String turn(String currentDirection) {
        if (currentDirection.equals("N")) return "E";
        if (currentDirection.equals("E")) return "S";
        if (currentDirection.equals("S")) return "W";
        if (currentDirection.equals("W")) return "N";

        return "";
    }

    public static void printGrid(String[][] grid) {
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid.length; c++) {
                System.out.print(grid[r][c]);
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
