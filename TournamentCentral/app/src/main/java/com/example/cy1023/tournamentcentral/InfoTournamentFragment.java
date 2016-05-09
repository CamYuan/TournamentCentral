package com.example.cy1023.tournamentcentral;

import android.app.AlertDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class InfoTournamentFragment extends Fragment {

    private static final String LOGIN_REQUEST_URL = "http://tourneycentral.comli.com/TournamentsINFO.php";

    //get SQL tournaments and show them
    List<String> participating_teams = new ArrayList<String>();
    String info_on_tournament;
    String check_host;
    int bracket_teams = 0;

    public InfoTournamentFragment() {

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        final View info_view = inflater.inflate(R.layout.info_tournament, container, false);
        Bundle bundle = getArguments();
        info_on_tournament = bundle.getString("tournament_name");

        final TextView info_name = (TextView) info_view.findViewById(R.id.info_name);
        final TextView info_type = (TextView) info_view.findViewById(R.id.info_type);
        final TextView info_format = (TextView) info_view.findViewById(R.id.info_format);
        final TextView info_host = (TextView) info_view.findViewById(R.id.info_host);
        final TextView info_dates = (TextView) info_view.findViewById(R.id.info_dates);
        final TextView info_num_teams = (TextView) info_view.findViewById(R.id.info_num_teams);
        final ListView list = (ListView) info_view.findViewById(R.id.info_teams);
        final Button generate = (Button) info_view.findViewById(R.id.generate_bracket);
        // Response received from the server
        final Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("Debug", response);
                    //load the Json array that you get from the PHP file
                    // "[" Brackets indicate JSON Array
                    // "{" Curly Braces indicate JSON Objects
                    //PHP file returns "arrayName" [ {Object}, {Object}, ...]
                    JSONArray resultsArray = new JSONArray(response);
                    for (int i = 0; i < resultsArray.length(); i++) {
                        JSONObject tourney_object = resultsArray.getJSONObject(i);
                        String tourney_name = tourney_object.getString("tournament_name");
                        if (Objects.equals(info_on_tournament, tourney_name)){
                            String team_name = tourney_object.getString("team_name");
                            //String tourney_id = tourney_object.getString("tourneyID");
                            String host = tourney_object.getString("host");
                            check_host = host;
                            String start_date = tourney_object.getString("start_date");
                            String end_date = tourney_object.getString("end_date");
                            String type = tourney_object.getString("type");
                            String format = tourney_object.getString("format");
                            int max_teams =  tourney_object.getInt("num_teams");
                            bracket_teams = max_teams;
                            participating_teams.add(team_name);
                            info_name.setText(tourney_name);
                            info_type.setText(type);
                            info_format.setText(format);
                            info_host.setText("Tournament Director: " + host+"");
                            info_dates.setText(""+start_date+" to "+end_date+"");
                            info_num_teams.setText("Max # of Teams: " +max_teams+"");
                        }
                        //Log.i("Debug", tourney_name);
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                            inflater.getContext(),
                            android.R.layout.simple_list_item_1,
                            participating_teams );
                    list.setAdapter(arrayAdapter);

                    if(Objects.equals(((MainActivity) getActivity()).local_userID, check_host)) {
                        generate.setVisibility(View.VISIBLE);
                        generate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                GenerateBracket(bracket_teams,
                                        info_type.getText().toString(),
                                        info_format.getText().toString(),
                                        participating_teams);
                            }
                        });
                    } else{
                        generate.setVisibility(View.GONE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        CustomRequests teamRequest = new CustomRequests(LOGIN_REQUEST_URL, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(teamRequest);
        return info_view;
    }


    //people, single/double/round, random/custom
    public void GenerateBracket(int participants, String type, String format, List<String> teams){
        String[][][] Teams;
        Toast toast;
        switch (type){
            case "Single Elimination":
                //create single elimination bracket
                //Teams = new String[participants];
                Teams = single_elimination(participants, teams, format);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Bracket", Teams);
                BracketViewFragment fragment = new BracketViewFragment();
                fragment.setArguments(bundle);
                getActivity().getFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, fragment, null)
                        .addToBackStack(null)
                        .commit();

                break;
            case "Double Elimination":
                //create single elimination bracket
                toast = Toast.makeText(getActivity(), type,
                        Toast.LENGTH_LONG);
                toast.show();
                break;
            case "Round Robin":
                toast = Toast.makeText(getActivity(), type,
                        Toast.LENGTH_LONG);
                toast.show();
                //create single elimination bracket
                break;

        }

    }

    public String[][][] single_elimination(int participants, List<String> bracket, String format) {
        int rounds = 0;
        int games = (int) Math.ceil(bracket.size() / 2);
        Toast toast = Toast.makeText(getActivity(), ""+bracket.size()+"",
                Toast.LENGTH_LONG);
        toast.show();
        while (participants != 1) {
            rounds++;
            participants /= 2;
        }
        String[][][] Rounds;
        switch (format) {
            case "Standard Seeding":
                Rounds = new String[rounds][games][2];
                for (int i = 1; i < rounds; i++) {
                    for (int j = 0; j < games; j ++) {
                        for (int k = 0; k < 2; k ++) {
                        Rounds[i][j][k] = "BYE";}}}
                for (int j = 0; j < games; j++) {
                    if (Objects.equals(bracket.get(j), bracket.get(bracket.size()-1-j))) {
                        Rounds[0][j][0] = bracket.get(j);
                        Rounds[0][j][1] = "BYE";
                    } else {
                        Rounds[0][j][0] = bracket.get(j);
                        Rounds[0][j][1] = bracket.get(bracket.size()-1-j);
                    }
                }
                int extra_round_games = 0;
                int jj, jjj;
                for (int i = 1; i < rounds; i++) {
                    for (int j = 0; j < games; j = j +2) {
                        jj=j+1;
                        if (Objects.equals(bracket.get(j), bracket.get(bracket.size()-1-j))) {
                            Rounds[i][extra_round_games][0] = "W of Round " + i + " Game " + jj;
                            Rounds[i][extra_round_games][1] = "BYE";
                        } else {
                            jjj = j+2;
                            Rounds[i][extra_round_games][0] = "W of Round " + i + " Game " + jj;
                            Rounds[i][extra_round_games][1] = "W of Round " + i + " Game " + jjj;
                        }
                        if (extra_round_games%2 == 0){
                            extra_round_games++;
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
