package com.example.cy1023.tournamentcentral;

/**
 * Created by cy1023 on 5/4/2016.
 */
public class GenerateBracket {

    private String[] Teams;

    //people, single/double/round, random/custom
    public GenerateBracket(String title, int participants, String type, String format){
        switch (type){
            case "Single Elimination":
                //create single elimination bracket
                Teams = new String[participants];

                break;
            case "Double Elimination":
                //create single elimination bracket
                break;
            case "Round Robin":
                //create single elimination bracket
                break;

        }

    }



    private void single_elimination(int participants){

    }
    private void double_elimination(int participants){

    }
    private void round_robin(int participants){

    }
}
