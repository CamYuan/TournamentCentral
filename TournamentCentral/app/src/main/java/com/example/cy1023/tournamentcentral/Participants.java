package com.example.cy1023.tournamentcentral;

/**
 * Created by cy1023 on 5/4/2016.
 */
public class Participants {
    private String team_name;
    private String[] members;

    public Participants(String team, String[] names){
        team_name = team;
        members = names;
    }

    public String getTeam_name(){
        return team_name;
    }

    public String[] getMembers(){
        return members;
    }
}
