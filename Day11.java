import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Day11 {
    public static void main(String[] args) {
        ArrayList<String> fileData = getFileData("src/InputFile");
        ArrayList<Long> numbers = new ArrayList<Long>();
        HashMap<Long, Long> stoneMap = new HashMap<Long, Long>();
        String line = fileData.get(0);
        String[] stringNumbers = line.split(" ");
        for (String n : stringNumbers) {
            long stoneNumber = Long.parseLong(n);
            numbers.add(Long.parseLong(n));
            long stoneCount = getStoneAmount(stoneMap, stoneNumber);
            stoneMap.put(stoneNumber, stoneCount+1);
        }


        int partOneAnswer = 0;
        for (int j = 0; j < numbers.size(); j++) {
            ArrayList<Long> process = new ArrayList<Long>();
            process.add(numbers.get(j));
            int blinks = 25;
            for (int i = 0; i < blinks; i++) {
                doBlinkPartOne(process);
            }
            partOneAnswer += process.size();
        }


        System.out.println("Part one answer: " + partOneAnswer);
        int blinks = 75;
        for (int i = 0; i < blinks; i++) {
            doBlinkPartTwo(stoneMap);
        }

        long partTwoAnswer = 0;
        for (long value : stoneMap.values()) {
            partTwoAnswer += value;
        }
        System.out.println("Part two answer: " + partTwoAnswer);


    }

    public static void doBlinkPartTwo(HashMap<Long, Long> stoneMap) {
        ArrayList<Long> keys = new ArrayList<Long>();
        keys.addAll(stoneMap.keySet());
        HashMap<Long, Long> stoneMapCopy = new HashMap<Long, Long>();
        for (long key : keys) {
            stoneMapCopy.put(key, getStoneAmount(stoneMap, key));
        }
        for (long key : keys) {
            long stoneNumber = getStoneAmount(stoneMapCopy, key);
            if (stoneNumber == 0)
                continue;
            String keyString = key + "";

            if (key == 0) {
                stoneMap.put((long)1, getStoneAmount(stoneMap,1) + stoneNumber);
                stoneMap.put((long)0, getStoneAmount(stoneMap,0) - stoneNumber);
            }

            else if (keyString.length() % 2 == 0) {
                String firstHalf = keyString.substring(0, keyString.length()/2);
                String secondHalf = keyString.substring(keyString.length()/2);
                long stoneLeft = Long.parseLong(firstHalf);
                long stoneRight = Long.parseLong(secondHalf);
                stoneMap.put(stoneLeft, getStoneAmount(stoneMap, stoneLeft) + stoneNumber);
                stoneMap.put(stoneRight, getStoneAmount(stoneMap, stoneRight) + stoneNumber);
                stoneMap.put(key, getStoneAmount(stoneMap, key) - stoneNumber);
            }

            else {
                stoneMap.put(key * 2024, getStoneAmount(stoneMap, key * 2024) + stoneNumber);
                stoneMap.put(key, getStoneAmount(stoneMap, key) - stoneNumber);
            }

        }
    }

    public static void doBlinkPartOne(ArrayList<Long> numbers) {
        for (int i = 0; i < numbers.size(); i++) {
            long currentNumber = numbers.get(i);
            String currentNumberString = currentNumber + "";

            if (currentNumber == 0) {
                numbers.set(i, currentNumber+1);
            }

            else if (currentNumberString.length() % 2 == 0) {
                String firstHalf = currentNumberString.substring(0, currentNumberString.length()/2);
                String secondHalf = currentNumberString.substring(currentNumberString.length()/2);
                long firstHalfNumber = Long.parseLong(firstHalf);
                long secondHalfNumber = Long.parseLong(secondHalf);
                numbers.set(i, firstHalfNumber);
                numbers.add(i+1, secondHalfNumber);
                i++;
            }

            else {
                numbers.set(i, currentNumber*2024);
            }
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

    public static long getStoneAmount(HashMap<Long, Long> stones, long key) {
        if (stones.get(key) == null) {
            return (long)0;
        }
        else {
            return stones.get(key);
        }
    }
}
