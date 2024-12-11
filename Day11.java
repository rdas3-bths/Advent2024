import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Day11 {
    public static void main(String[] args) {
        ArrayList<String> fileData = getFileData("src/InputFile");
        ArrayList<Long> numbers = new ArrayList<Long>();
        String line = fileData.get(0);
        String[] stringNumbers = line.split(" ");
        for (String n : stringNumbers) {
            long stoneNumber = Long.parseLong(n);
            numbers.add(Long.parseLong(n));
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
}
