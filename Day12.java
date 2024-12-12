import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Day12 {
    public static void main(String[] args) {
        ArrayList<String> fileData = getFileData("src/InputFile");
        String border = "";
        for (int i = 0; i < fileData.get(0).length(); i++) {
            border += ".";
        }
        fileData.add(0, border);
        fileData.add(border);
        for (int i = 0; i < fileData.size(); i++) {
            String row = fileData.get(i);
            row = "." + row + ".";
            fileData.set(i, row);
        }

        int rows = fileData.size();
        int columns = fileData.get(0).length();
        String[][] garden = new String[rows][columns];

        for (int r = 0; r < garden.length; r++) {
            for (int c = 0; c < garden[0].length; c++) {
                garden[r][c] = fileData.get(r).substring(c, c + 1);
            }
        }
        HashSet<String> uniquePlants = getUniquePlants(garden);

        for (String plant : uniquePlants) {
            int label = 1;
            while (checkIfPlantUnlabeled(garden, plant)) {
                labelSingle(garden, label, plant);
                while (checkUnlabeledWithNeighbor(garden, label, plant)) {
                    labelNeighbors(garden, label, plant);
                }
                label++;
            }
        }
        HashSet<String> labeledUniquePlants = getUniquePlants(garden);

        int partOneAnswer = 0;
        int partTwoAnswer = 0;
        for (String plant : labeledUniquePlants) {
            HashSet<String> lines = getLineSegments(garden, plant);
            int sides = countSides(lines);
            int area = getArea(garden, plant);
            int perimeter = getPerimeter(garden, plant);
            partOneAnswer += (area*perimeter);
            partTwoAnswer += (area*sides);
        }
        System.out.println("Part one answer: " + partOneAnswer);
        System.out.println("Part two answer: " + partTwoAnswer);
    }

    public static int countSides(HashSet<String> lines) {
        int count = 0;

        while (!lines.isEmpty()) {
            HashSet<String> onSameLine = new HashSet<String>();
            for (String line : lines) {
                String direction = line.split(" ")[0];
                int row = Integer.parseInt(line.split(" ")[1]);
                int col = Integer.parseInt(line.split(" ")[2]);
                if (direction.equals("above") || direction.equals("below")) {
                    onSameLine.add(line);
                    HashSet<String> toRight = findToRight(lines, direction, row, col);
                    HashSet<String> toLeft = findToLeft(lines, direction, row, col);
                    onSameLine.addAll(toRight);
                    onSameLine.addAll(toLeft);
                    count++;
                    break;
                }
                if (direction.equals("left") || direction.equals("right")) {
                    onSameLine.add(line);
                    HashSet<String> above = findAbove(lines, direction, row, col);
                    HashSet<String> below = findBelow(lines, direction, row, col);
                    onSameLine.addAll(above);
                    onSameLine.addAll(below);
                    count++;
                    break;
                }
            }
            lines.removeAll(onSameLine);
        }

        return count;
    }

    public static HashSet<String> findToRight(HashSet<String> lines, String direction, int r, int c) {
        int checkRow = r;
        int checkCol = c + 1;
        HashSet<String> toRight = new HashSet<String>();
        String checkString = direction + " " + checkRow + " " + checkCol;
        while (lines.contains(checkString)) {
            toRight.add(checkString);
            checkCol++;
            checkString = direction + " " + checkRow + " " + checkCol;
        }
        return toRight;
    }

    public static HashSet<String> findToLeft(HashSet<String> lines, String direction, int r, int c) {
        int checkRow = r;
        int checkCol = c - 1;
        HashSet<String> toLeft = new HashSet<String>();
        String checkString = direction + " " + checkRow + " " + checkCol;
        while (lines.contains(checkString)) {
            toLeft.add(checkString);
            checkCol--;
            checkString = direction + " " + checkRow + " " + checkCol;
        }
        return toLeft;
    }

    public static HashSet<String> findAbove(HashSet<String> lines, String direction, int r, int c) {
        int checkRow = r - 1;
        int checkCol = c;
        HashSet<String> above = new HashSet<String>();
        String checkString = direction + " " + checkRow + " " + checkCol;
        while (lines.contains(checkString)) {
            above.add(checkString);
            checkRow--;
            checkString = direction + " " + checkRow + " " + checkCol;
        }
        return above;
    }

    public static HashSet<String> findBelow(HashSet<String> lines, String direction, int r, int c) {
        int checkRow = r + 1;
        int checkCol = c;
        HashSet<String> below = new HashSet<String>();
        String checkString = direction + " " + checkRow + " " + checkCol;
        while (lines.contains(checkString)) {
            below.add(checkString);
            checkRow++;
            checkString = direction + " " + checkRow + " " + checkCol;
        }
        return below;
    }

    public static int getArea(String[][] garden, String plant) {
        int count = 0;
        for (String[] row : garden) {
            for (String p : row) {
                if (p.equals(plant))
                    count++;
            }
        }
        return count;
    }

    public static HashSet<String> getLineSegments(String[][] garden, String plant) {
        HashSet<String> lines = new HashSet<String>();
        for (int r = 0; r < garden.length; r++) {
            for (int c = 0; c < garden[0].length; c++) {
                if (garden[r][c].equals(plant)) {
                    if (!garden[r+1][c].equals(plant)) {
                        lines.add("below " + r + " " + c);
                    }
                    if (!garden[r][c+1].equals(plant)) {
                        lines.add("right " + r + " " + c);
                    }
                    if (!garden[r-1][c].equals(plant)) {
                        lines.add("above " + r + " " + c);
                    }
                    if (!garden[r][c-1].equals(plant)) {
                        lines.add("left " + r + " " + c);
                    }
                }
            }
        }
        return lines;
    }

    public static int getPerimeter(String[][] garden, String plant) {
        int count = 0;
        for (int r = 0; r < garden.length; r++) {
            for (int c = 0; c < garden.length; c++) {
                if (garden[r][c].equals(plant)) {
                    // how many around this are NOT equal to it
                    if (!garden[r+1][c].equals(plant)) count++;
                    if (!garden[r][c+1].equals(plant)) count++;
                    if (!garden[r-1][c].equals(plant)) count++;
                    if (!garden[r][c-1].equals(plant)) count++;
                }
            }
        }
        return count;
    }

    public static boolean checkUnlabeledWithNeighbor(String[][] garden, int label, String p) {
        for (int r = 0; r < garden.length; r++) {
            for (int c = 0; c < garden[0].length; c++) {
                if (garden[r][c].equals(p)) {
                    if (checkNeighbors(garden, r, c, label))
                        return true;
                }
            }
        }
        return false;
    }

    public static void labelNeighbors(String[][] garden, int label, String p) {
        for (int r = 0; r < garden.length; r++) {
            for (int c = 0; c < garden[0].length; c++) {
                if (garden[r][c].equals(p)) {
                    if (checkNeighbors(garden, r, c, label)) {
                        garden[r][c] = p + label;
                    }
                }
            }
        }
    }

    public static boolean checkNeighbors(String[][] garden, int r, int c, int label) {
        String plant = garden[r][c];
        String labelPlant = plant + label;
        if (garden[r+1][c].equals(labelPlant)) return true;
        if (garden[r][c+1].equals(labelPlant)) return true;
        if (garden[r-1][c].equals(labelPlant)) return true;
        if (garden[r][c-1].equals(labelPlant)) return true;

        return false;
    }

    public static boolean labelSingle(String[][] garden, int label, String p) {
        for (int r = 0; r < garden.length; r++) {
            for (int c = 0; c < garden[0].length; c++) {
                if (garden[r][c].equals(p)) {
                    garden[r][c] = p + label;
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkIfPlantUnlabeled(String[][] garden, String p) {
        for (String[] row : garden) {
            for (String plant : row) {
                if (plant.equals(p)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static HashSet<String> getUniquePlants(String[][] garden) {
        HashSet<String> uniquePlants = new HashSet<String>();
        for (String[] row : garden) {
            for (String plant : row) {
                if (!plant.equals(".")) {
                    uniquePlants.add(plant);
                }
            }
        }
        return uniquePlants;
    }

    public static void printGarden(String[][] garden) {
        for (int r = 0; r < garden.length; r++) {
            for (int c = 0; c < garden[0].length; c++) {
                System.out.print(garden[r][c] + " ");
            }
            System.out.println();
        }
    }

    public static ArrayList<String> getFileData (String fileName){
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
        } catch (FileNotFoundException e) {
            return fileData;
        }
    }
}
