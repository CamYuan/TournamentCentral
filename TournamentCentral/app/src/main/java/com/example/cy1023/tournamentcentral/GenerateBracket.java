package com.example.cy1023.tournamentcentral;

import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

/**
Kept for reference
 */
public class GenerateBracket {

    private String[] Teams;

    //people, single/double/round, random/custom
    public GenerateBracket(int participants, String type, String format){
        switch (type){
            case "Single Elimination":
                //create single elimination bracket
                Teams = new String[participants];
                single_elimination(participants, Teams, format);

                break;
            case "Double Elimination":
                //create single elimination bracket
                break;
            case "Round Robin":
                //create single elimination bracket
                break;

        }

    }

    public String[][][] single_elimination(int participants, String[] bracket, String format) {
        int rounds = 0;
        int games = (int) Math.ceil(bracket.length / 2);
        while (participants != 1) {
            rounds++;
            participants /= 2;
        }
        String[][][] Rounds;
        switch (format) {
            case "Standard Seeding":
                Rounds = new String[rounds][games][2];
                for (int j = 0; j < games; j++) {
                    if (Objects.equals(bracket[j], bracket[bracket.length - j])) {
                        Rounds[0][j][1] = bracket[j];
                        Rounds[0][j][2] = "BYE";

                    } else {
                        Rounds[0][j][1] = bracket[j];
                        Rounds[0][j][2] = bracket[bracket.length - j];
                    }
                }
                for (int i = 1; i < rounds; i++) {
                    for (int j = 0; j < games; j += 2) {
                        if (Objects.equals(bracket[j], bracket[bracket.length - j])) {
                            Rounds[i][j][1] = "W of Round" + i + " Game" + j;
                            Rounds[i][j][2] = "BYE";
                        } else {
                            Rounds[i][j][1] = "W of Round" + i + " Game" + j;
                            Rounds[i][j][2] = "W of Round" + i + " Game" + j + 1;
                        }
                    }
                }



            break;
            case "Random Seeding":
                Collections.shuffle(Arrays.asList(bracket));
                Rounds = new String[0][][];

                break;
            case "Custom Seeding":
                Rounds = new String[0][][];

                /*Toast toast = Toast.makeText(this, "Sorry this isn't implemented yet!",
                        Toast.LENGTH_LONG);
                toast.show();*/

                break;
            default:
                Rounds = new String[0][][];
                break;
        }
        return Rounds;
    }
    public void double_elimination(int participants){

    }
    public void round_robin(int participants){
        int rounds = participants - 1;

    }
}
