package League;

import javafx.scene.image.Image;

import java.io.File;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class League {
    private String name;
    private int howManyTeams;
    private static ArrayList<Team> teams;
    private Date startDate;
//    private Scanner in = new Scanner(System.in);
//    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public League(String name, int howManyTeams, Date startDate) {
//        setTeams();
        setStartDate(startDate);
        teams = getTeams();
        setName(name);
        setHowManyTeams(howManyTeams);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (compareCurrentDate())
            this.name = name;
    }

    public Boolean compareCurrentDate() {
//        Date today = dateFormat.parse(dateFormat.format(new Date()));
        Date today = new Date();
//        int parseToday = Integer.parseInt(String.valueOf(today));
//        int parseInput = Integer.parseInt(getStartDate());
        return startDate.after(today);
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date date) {
//        date = String.valueOf(dateFormat.parse(date));
        this.startDate = date;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public void setTeams(String teamName, int playerNum, String icon) {
        for (int i = 0; i < howManyTeams; i++) {
            Team team = new Team(teamName, playerNum, icon);
            teams.add(team);
        }
    }

    public Team getTeam(int i) {
        return teams.get(i);
    }

    public void setHowManyTeams(int howManyTeams) {
        if (compareCurrentDate()) {
            this.howManyTeams = howManyTeams;
        }
    }

    public int getHowManyTeams() {
        return howManyTeams;
    }
}
