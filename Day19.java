import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class Day19 {

    public static HashMap<String, TowelResult> cache = new HashMap<String, TowelResult>();

    public static void main(String[] args) {
        ArrayList<String> fileData = getFileData("src/InputFile");
        ArrayList<String> availableTowels = new ArrayList<String>();
        ArrayList<String> targets = new ArrayList<String>();
        for (String line : fileData) {
            if (line.contains(",")) {
                Collections.addAll(availableTowels, line.split(", "));
            } else {
                targets.add(line);
            }
        }
        int partOneAnswer = 0;
        long partTwoAnswer = 0;
        for (String towel : targets) {
            TowelResult t = canMakeTowel(towel, availableTowels);
            boolean check = t.isPossible();
            long combinations = t.getCount();
            if (check) {
                partOneAnswer++;
            }
            partTwoAnswer += combinations;
        }
        System.out.println("Part one answer: " + partOneAnswer);
        System.out.println("Part two answer: " + partTwoAnswer);
    }

    public static TowelResult canMakeTowel(String targetTowel, ArrayList<String> availableTowels) {

        if (cache.keySet().contains(targetTowel)) {
            return cache.get(targetTowel);
        }

        if (targetTowel.length() == 0) {
            TowelResult t = new TowelResult(true, 1);
            cache.put(targetTowel, t);
            return t;
        }

        boolean possible = false;
        long count = 0;
        for (String start : availableTowels) {
            if (targetTowel.indexOf(start) == 0) {
                TowelResult t = canMakeTowel(targetTowel.substring(start.length()), availableTowels);
                boolean check = t.isPossible();
                long combinations = t.getCount();
                if (check) {
                    possible = true;
                    count += combinations;
                }
            }
        }
        TowelResult t = new TowelResult(possible, count);
        cache.put(targetTowel, t);
        return t;
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

class TowelResult {
    private boolean possible;
    private long count;

    public TowelResult(boolean possible, long count) {
        this.possible = possible;
        this.count = count;
    }

    public boolean isPossible() {
        return possible;
    }

    public long getCount() {
        return count;
    }
}
