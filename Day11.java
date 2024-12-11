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

        int blinks = 25;
        System.out.println("Part one answer: "+ doBlinks(stoneMap, blinks));
        blinks = 50;
        System.out.println("Part two answer: "+ doBlinks(stoneMap, blinks));

    }

    public static long doBlinks(HashMap<Long, Long> stoneMap, int blinks) {
        for (int i = 0; i < blinks; i++) {
            doSingleBlink(stoneMap);
        }
        long answer = 0;
        for (long value : stoneMap.values()) {
            answer += value;
        }
        return answer;

    }

    public static void doSingleBlink(HashMap<Long, Long> stoneMap) {
        ArrayList<Long> keys = new ArrayList<Long>();
        keys.addAll(stoneMap.keySet());
        HashMap<Long, Long> stoneMapCopy = new HashMap<Long, Long>();
        for (long key : keys) {
            stoneMapCopy.put(key, getStoneAmount(stoneMap, key));
        }
        for (long key : keys) {
            long stoneNumber = getStoneAmount(stoneMapCopy, key);
            String keyString = key + "";

            if (key == 0) {
                stoneMap.put(key+1, getStoneAmount(stoneMap,1) + stoneNumber);
                stoneMap.put(key, getStoneAmount(stoneMap,0) - stoneNumber);
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
