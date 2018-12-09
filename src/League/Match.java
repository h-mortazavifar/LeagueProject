
package League;

import java.util.Scanner;

public class Match {
    private int goal;
    private int teamNumber;
    private Team[] teams = new Team[2];
    private Team winner;
    private Player players;
    private League league;
    private Scanner in = new Scanner(System.in);

    public Match(int goal, int teamNumber, Team[] teams, Player players,
                 League league) {
        this.goal = goal;
        this.teamNumber = teamNumber;
        this.players = players;
        this.league = league;
    }

    public void setTeams(int teamNoOne, int teamNoTwo) {
        do {
            if (teamNoOne >= 0 && teamNoOne <= league.getHowManyTeams()) {
                teams[0] = league.getTeam(teamNoOne - 1);
                break;
            }
        } while (true);
        do {
            if (teamNoTwo >= 0 && teamNoTwo <= league.getHowManyTeams()) {
                teams[1] = league.getTeam(teamNoTwo - 1);
                break;
            }
        } while (true);
    }

    public Team[] getTeams() {
        return teams;
    }

    public void setWinner() {
        if (teams[0].getScore() > teams[1].getScore()) {
            teams[0].setWon();
            teams[1].setLoss();
        } else if (teams[0].getScore() < teams[1].getScore()) {
            teams[1].setWon();
            teams[0].setLoss();
        } else {
            teams[0].setDraw();
            teams[1].setDraw();
        }
    }

    public Team getWinner() {
        return winner;
    }

    public void setScores(Team team, int score) {
        team.setScore(score);
    }

//    public int getScore(Team team) {
//        return ;
//    }
}


