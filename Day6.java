import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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

        ArrayList<String> originalPath = traverseMap(grid);

        System.out.println("Part one answer: " + countVisited(grid));

        ArrayList<String> loopPositions = new ArrayList<String>();

        for (int i = 0; i < originalPath.size()-1; i++) {
            String[] data = originalPath.get(i).split(",");
            String position = data[0] + "," + data[1];
            if (!loopPositions.contains(position)) {
                loopPositions.add(position);
            }
        }

        int loopsCreated = 0;
        for (int i = 0; i < loopPositions.size(); i++) {
            rows = originalGrid.length;
            columns = originalGrid[0].length;
            grid = new String[rows][columns];
            for (int r = 0; r < grid.length; r++) {
                for (int c = 0; c < grid[0].length; c++) {
                    grid[r][c] = originalGrid[r][c];
                }
            }
            String[] obstacle = loopPositions.get(i).split(",");
            int[] obstacleInt = new int[2];
            obstacleInt[0] = Integer.parseInt(obstacle[0]);
            obstacleInt[1] = Integer.parseInt(obstacle[1]);
            grid[obstacleInt[0]][obstacleInt[1]] = "#";
            ArrayList<String> path = traverseMap(grid);
            if (path.get(path.size()-1).equals("yes"))
                loopsCreated++;
        }

        System.out.println("Part two answer: " + loopsCreated);
    }

    public static ArrayList<String> traverseMap(String[][] grid) {
        int guardRow = -1;
        int guardColumn = -1;
        String direction = "N";
        ArrayList<String> positionsVisited = new ArrayList<String>();
        String foundLoop = "no";

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
                if (grid[nextRow][nextColumn].equals("#")) {
                    direction = turn(direction);
                }
                else {
                    grid[nextRow][nextColumn] = "X";
                    guardRow = nextRow;
                    guardColumn = nextColumn;
                    if (foundLoop(guardRow, guardColumn, direction, positionsVisited)) {
                        foundLoop = "yes";
                        canMove = false;
                    }
                    positionsVisited.add(guardRow + "," + guardColumn + "," + direction);
                }
            }
            catch (Exception e) {
                canMove = false;
            }
        }

        positionsVisited.add(foundLoop);
        return positionsVisited;
    }

    public static boolean foundLoop(int r, int c, String direction, ArrayList<String> positionsVisited) {
        String newPosition = r + "," + c + "," + direction;
        for (String v : positionsVisited) {
            if (v.equals(newPosition)) {
                return true;
            }
        }
        return false;
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
