package League;

import java.util.ArrayList;

public class Team {
    private String name;
    private ArrayList<Player> players;
    private int howManyPlayers = 14;
    private String icon;
    private int numOfMatches;
    private double score;
    private int goalsScored;
    private int goalsAgainst;
    private int won;
    private int loss;
    private int draw;

    private Team(String icon, String teamName, int howManyPlayers, int numOfMatches, double score,
                 int goalsScored, int goalsAgainst, int won, int loss, int draw) {
        setHowManyPlayers(howManyPlayers);
        this.icon = icon;
        this.name = teamName;
        this.numOfMatches = numOfMatches;
        this.score = score;
        this.goalsScored = goalsScored;
        this.goalsAgainst = goalsAgainst;
        this.won = won;
        this.loss = loss;
        this.draw = draw;
    }

    public Team(String icon, String teamName, int howManyPlayers) {
        this(icon, teamName, howManyPlayers, 0, 0, 0, 0, 0, 0, 0);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getPlayerOfNumber(int number) {
        return players.get(number);
    }
    public Player setPlayerOfNumber(int number) {
        return players.get(number);
    }

    public void setPlayers(String name, String lastName, String post) {
        players.add(new Player(name, lastName, post));
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
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
        this.score += score;
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
        this.goalsAgainst += goalsAgainst;
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

}



