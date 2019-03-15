package League.allOther;

import java.util.Date;

public class League {
    private String name;
    private int howManyTeams;
    private Team[] teams;
    private Date startDate;

    public League(String name, int howManyTeams, Date startDate) {
        this.name = name;
        this.howManyTeams = howManyTeams;
        this.startDate = startDate;
        teams = new Team[howManyTeams];
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

    public Team[] getTeams() {
        return teams;
    }

    public void setTeams( String icon, String teamName, int playerNum ,int number) {
        teams[number] = new Team(icon, teamName, playerNum);
    }

    public Team getTeam(int i) {
        return teams[i];
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
