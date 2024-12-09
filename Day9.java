import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day9 {
    public static void main(String[] args) {
        ArrayList<Integer> memory = getOriginalMemory("src/InputFile");

        while (!checkSorted(memory)) {
            for (int i = 0; i < memory.size(); i++) {
                if (memory.get(i) == -1) {
                    int lastFileNumber = getLastFileNumber(memory);
                    int replace = memory.get(lastFileNumber);
                    memory.set(lastFileNumber, -1);
                    memory.set(i, replace);
                    break;
                }
            }
        }
        long partOneAnswer = 0;
        for (int i = 0; i < memory.size(); i++) {
            int number = memory.get(i);
            if (number != -1) {
                partOneAnswer += (number * i);
            }
        }
        System.out.println("Part one answer: " + partOneAnswer);

        memory = getOriginalMemory("src/InputFile");

        int index = memory.size()-1;
        while (index >= 0) {
            int number = memory.get(index);
            if (number != -1) {
                int lengthOfFile = findLengthOfFile(memory, number);
                int freeMemoryPosition = findFreeMemory(memory, lengthOfFile);
                if (freeMemoryPosition != -1 && freeMemoryPosition < index) {
                    for (int i = 0; i < lengthOfFile; i++) {
                        int replace = memory.get(index);
                        memory.set(index, -1);
                        memory.set(freeMemoryPosition, replace);
                        index--;
                        freeMemoryPosition++;
                    }
                }
                else {
                    index = index - lengthOfFile;
                }
            }
            else {
                index--;
            }
        }
        long partTwoAnswer = 0;
        for (int i = 0; i < memory.size(); i++) {
            int number = memory.get(i);
            if (number != -1) {
                partTwoAnswer += (number * i);
            }
        }
        System.out.println("Part one answer: " + partTwoAnswer);

    }

    public static int findFreeMemory(ArrayList<Integer> memory, int length) {
        for (int i = 0; i < memory.size(); i++) {
            int number = memory.get(i);
            if (number == -1) {
                int consecutive = 0;

                for (int j = i; j < memory.size(); j++) {
                    if (memory.get(j) == -1) {
                        consecutive++;
                    }
                    else {
                        break;
                    }
                }
                if (consecutive >= length) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static ArrayList<Integer> getOriginalMemory(String fileData) {
        ArrayList<String> fd = getFileData(fileData);
        String input = fd.get(0);
        ArrayList<Integer> memory = new ArrayList<Integer>();
        int fileID = 0;
        for (int i = 0; i < input.length(); i++) {
            int spaces = Integer.parseInt(input.substring(i,i+1));
            if (i % 2 == 0) {
                for (int j = 0; j < spaces; j++) {
                    memory.add(fileID);
                }
                fileID++;
            }
            else {
                for (int j = 0; j < spaces; j++) {
                    memory.add(-1);
                }
            }
        }
        return memory;
    }

    public static int findLengthOfFile(ArrayList<Integer> memory, int fileID) {
        int length = 0;
        for (int i = 0; i < memory.size(); i++) {
            if (memory.get(i) == fileID) {
                length++;
            }
        }
        return length;
    }

    public static int getLastFileNumber(ArrayList<Integer> memory) {
        for (int i = memory.size()-1; i >= 0; i--) {
            if (memory.get(i) != -1) {
                return i;
            }
        }
        return -1;
    }

    public static boolean checkSorted(ArrayList<Integer> memory) {
        boolean foundEmpty = false;
        for (int i = 0; i < memory.size(); i++) {
            int number = memory.get(i);
            if (number == -1) {
                foundEmpty = true;
            }
            if (foundEmpty) {
                if (number != -1) {
                    return false;
                }
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
