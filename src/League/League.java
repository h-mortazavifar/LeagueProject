package League;

import javafx.scene.image.Image;

import java.io.File;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class League {
    private String name;
    private int howManyTeams;
    private static Team[] teams;
    private String currentDate;
    private Scanner in = new Scanner(System.in);
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public League(String name, int howManyTeams, String date) throws MalformedURLException, ParseException {
        setTeams();
        setCurrentDate(date);
        this.teams = getTeams();
        Boolean compareCurrentDate = compareCurrentDate();
        if (compareCurrentDate) {
            this.name = name;
            this.howManyTeams = howManyTeams;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws ParseException {
        if (compareCurrentDate())
            this.name = name;
    }

    public Boolean compareCurrentDate() throws ParseException {
//        currentDate = new Date();
        Date today = dateFormat.parse(dateFormat.format(new Date()));
        int parseToday = Integer.parseInt(String.valueOf(today));
        int parseInput = Integer.parseInt(getCurrentDate());
        return parseInput > parseToday;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String date) throws ParseException {
        date = String.valueOf(dateFormat.parse(date));
        this.currentDate = date;
    }

    public Team[] getTeams() {
        return teams;
    }

    public void setTeams() throws MalformedURLException {
        for (int i = 0; i < howManyTeams; i++) {
            String name = in.next();
            int playerNum = in.nextInt();
            Image image = new Image(new File("C:\\Users\\user\\Desktop\\x.jpg").toURI().toURL().toExternalForm());
            Team team = new Team(name, playerNum, image);
            teams[i] = team;
        }
    }

    public Team getTeam(int i) {
        return teams[i];
    }

    public int getHowManyTeams() {
        return howManyTeams;
    }
}
