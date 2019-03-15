
package League.allOther;

import static League.allOther.Controller.leagues;

public class Match {
    private Team teamNoOne;
    private Team teamNoTwo;
    private Team loser;
    private Team winner;
    private Player[]playersTeamOne;
    private Player []playersTeamTwo;
    private League league;

    public Match(int league, Team teamNoOne, Team teamNoTwo) {
        this.league = leagues.get(league);
        setTeams(teamNoOne, teamNoTwo);
        setPlayers();
    }

    public League getLeague() {
        return league;
    }

    public void setTeams(Team teamNoOne, Team teamNoTwo) {
        this.teamNoOne = teamNoOne;
        this.teamNoTwo = teamNoTwo;
    }

    public Team getTeamOne() {
        return teamNoOne;
    }

    public Team getTeamNoTwo() {
        return teamNoOne;
    }

    public void match(Team teamNoOne, int score1, Team teamNoTwo, int score2) {
        teamNoOne.setGoalsScored(score1);
        teamNoOne.setGoalsAgainst(score2);
        teamNoTwo.setGoalsScored(score2);
        teamNoTwo.setGoalsAgainst(score1);
        if (score1 > score2) {
            setWinnerAndLoser(teamNoOne, teamNoTwo);
        } else if (score2 > score1) {
            setWinnerAndLoser(teamNoTwo, teamNoOne);
        } else {
            setDraw(teamNoTwo, teamNoOne);
        }
    }

    public void setWinnerAndLoser(Team winner, Team loser) {
        winner.setWon();
        this.winner = winner;
        loser.setLoss();
        this.loser = loser;
    }

    public Team getWinner() {
        return winner;
    }

    public Team getLoser() {
        return loser;
    }

    public void setDraw(Team teamNoOne, Team teamNoTwo) {
        teamNoOne.setDraw();
        teamNoTwo.setDraw();
    }

    public void setScores(Team team, int score) {
        team.setScore(score);
    }

    public void setPlayers() {
        this.playersTeamOne = (Player[]) this.teamNoOne.getPlayers().toArray();
        this.playersTeamTwo = (Player[]) this.teamNoTwo.getPlayers().toArray();
    }

    public Player[] getPlayersTeamOne() {
        return playersTeamOne;
    }

    public Player[] getPlayersTeamTwo() {
        return playersTeamTwo;
    }

    public void goalForPlayer(Player player, int goals) {
        player.setGoals(goals);
    }
}


