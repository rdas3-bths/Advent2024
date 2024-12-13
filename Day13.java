import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Day13 {
    public static void main(String[] args) {
        ArrayList<String> fileData = getFileData("src/InputFile");
        ArrayList<Game> games = parseGame(fileData);
        long partOneAnswer = 0;
        for (int i = 0; i < games.size(); i++) {
            int gameValue = playGame(games.get(i));
            System.out.println("Game " + (i+1) + ": " + gameValue);
            partOneAnswer += gameValue;
        }
        System.out.println("Part one answer: " + partOneAnswer);

        long partTwoAnswer = 0;
        for (int i = 0; i < games.size(); i++) {
            games.get(i).setxGoal(games.get(i).getxGoal()+10000000000000L);
            games.get(i).setyGoal(games.get(i).getyGoal()+10000000000000L);
            partTwoAnswer += playGamePartTwo(games.get(i));
        }
        System.out.println("Part two answer: " + partTwoAnswer);

    }

    public static long playGamePartTwo(Game g) {
        long ax = g.getA().getxChange();
        long ay = g.getA().getyChange();
        long bx = g.getB().getxChange();
        long by = g.getB().getyChange();
        long prizeX = g.getxGoal();
        long prizeY = g.getyGoal();
        // I used google to solve for a and b in these equations:
        // ax * a + bx * b = prizeX
        // ay * a + by * b = prizeY
        long b = (prizeX*ay-prizeY*ax)/(ay*bx-by*ax);
        long a = (prizeX*by-prizeY*bx)/(by*ax-bx*ay);
        if ((ax*a+bx*b == prizeX) && (ay*a+by*b) == prizeY) {
            return 3*a+b;
        }
        else {
            return 0;
        }
    }

    public static Integer playGame(Game g) {
        ArrayList<Integer> wins = new ArrayList<Integer>();
        long ax = g.getA().getxChange();
        long ay = g.getA().getyChange();
        long bx = g.getB().getxChange();
        long by = g.getB().getyChange();
        long prizeX = g.getxGoal();
        long prizeY = g.getyGoal();
        for (int a = 0; a < 100; a++) {
            for (int b = 0; b < 100; b++) {
                if ((ax*a+bx*b == prizeX) && (ay*a+by*b == prizeY)) {
                    wins.add(3*a+b);
                }
            }
        }
        if (wins.size() == 0) {
            return 0;
        }
        else {
            return Collections.min(wins);
        }
    }

    public static ArrayList<Game> parseGame(ArrayList<String> fileData) {
        ArrayList<Game> games = new ArrayList<Game>();
        for (int i = 0; i < fileData.size(); i += 3) {
            String buttonAInfo = fileData.get(i);
            String buttonBInfo = fileData.get(i+1);
            String prizeInfo = fileData.get(i+2);
            long buttonAxChange = Long.parseLong(buttonAInfo.split(":")[1].split(",")[0].trim().split("\\+")[1]);
            long buttonAyChange = Long.parseLong(buttonAInfo.split(":")[1].split(",")[1].trim().split("\\+")[1]);
            long buttonBxChange = Long.parseLong(buttonBInfo.split(":")[1].split(",")[0].trim().split("\\+")[1]);
            long buttonByChange = Long.parseLong(buttonBInfo.split(":")[1].split(",")[1].trim().split("\\+")[1]);
            long prizeX = Long.parseLong(prizeInfo.split(":")[1].split(",")[0].trim().split("=")[1]);
            long prizeY = Long.parseLong(prizeInfo.split(":")[1].split(",")[1].trim().split("=")[1]);
            Button a = new Button(buttonAxChange, buttonAyChange);
            Button b = new Button(buttonBxChange, buttonByChange);
            Game g = new Game(a, b, prizeX, prizeY);
            games.add(g);
        }
        return games;
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

class Game {
    private Button a;
    private Button b;
    private long xGoal;
    private long yGoal;

    public Game(Button a, Button b, long xGoal, long yGoal) {
        this.a = a;
        this.b = b;
        this.xGoal = xGoal;
        this.yGoal = yGoal;
    }

    public Button getA() {
        return a;
    }

    public Button getB() {
        return b;
    }

    public long getxGoal() {
        return xGoal;
    }

    public long getyGoal() {
        return yGoal;
    }

    public void setxGoal(long xGoal) {
        this.xGoal = xGoal;
    }

    public void setyGoal(long yGoal) {
        this.yGoal = yGoal;
    }

    public String toString() {
        return a + "\n" + b + "\n" + "Prize: " + "X=" + xGoal + ", Y=" + yGoal;
    }
}

class Button {
    private long xChange;
    private long yChange;

    public Button(long xChange, long yChange) {
        this.xChange = xChange;
        this.yChange = yChange;
    }

    public long getxChange() {
        return xChange;
    }

    public long getyChange() {
        return yChange;
    }

    public String toString() {
        return "Button: " + "X+" + xChange + ", Y+" + yChange;
    }
}
