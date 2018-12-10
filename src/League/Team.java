package League;

import javafx.scene.image.Image;

import java.util.Arrays;
import java.util.Scanner;

public class Team {
    private String name;
    private Player[] players;
    private int howManyPlayers = 14;
    private Image icon;
    private int numOfMatches;
    private double score;
    private int goalsScored;
    private int goalsAgainst;
    private int won;
    private int loss;
    private int draw;
    Scanner in = new Scanner(System.in);

    private Team(String name, Image icon, int howManyPlayers, int numOfMatches, double score,
                 int goalsScored, int goalsAgainst, int won, int loss, int draw) {
        setHowManyPlayers(howManyPlayers);
        setPlayers();
        this.name = name;
        this.howManyPlayers = getHowManyPlayers();
        this.icon = icon;
        this.numOfMatches = numOfMatches;
        this.score = score;
        this.goalsScored = goalsScored;
        this.goalsAgainst = goalsAgainst;
        this.won = won;
        this.loss = loss;
        this.draw = draw;
    }

    public Team(String name, int howManyPlayers, Image icon) {
        this(name, icon, howManyPlayers, 0, 0, 0, 0, 0, 0, 0);
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers() {
        for (int i = 0; i < howManyPlayers; i++) {
            String name = in.next();
            String lastName = in.next();
            String post = in.next();
            players[i] = new Player(name, lastName, post);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHowManyPlayers() {
        return howManyPlayers;
    }

    public void setHowManyPlayers(int howManyPlayers) {
        if (howManyPlayers > 14 && howManyPlayers <= 18)
            this.howManyPlayers = howManyPlayers;
    }

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

    public int getNumOfMatches() {
        return numOfMatches;
    }

    public void setNumOfMatches(int numOfMatches) {
        this.numOfMatches = numOfMatches;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getGoalsScored() {
        return goalsScored;
    }

    public void setGoalsScored(int goalsScored) {
        this.goalsScored += goalsScored;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public void setGoalsAgainst(int goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

    public int getWon() {
        return won;
    }

    public void setWon() {
        this.won += 1;
    }

    public int getLoss() {
        return loss;
    }

    public void setLoss() {
        this.loss += 1;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw() {
        this.draw += 1;
    }

    @Override
    public String toString() {
        return Arrays.toString(players);
    }
}



