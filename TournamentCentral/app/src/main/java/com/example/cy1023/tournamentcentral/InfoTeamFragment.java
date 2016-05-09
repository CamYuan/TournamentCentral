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
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class InfoTeamFragment extends Fragment {

    private static final String LOGIN_REQUEST_URL = "http://tourneycentral.comli.com/TournamentsINFO.php";

    //get SQL tournaments and show them
    List<String> registered_for_tournaments = new ArrayList<String>();
    String info_on_tournament;

    public InfoTeamFragment() {

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        final View info_view = inflater.inflate(R.layout.info_team, container, false);
        Bundle bundle = getArguments();
        info_on_tournament = bundle.getString("team_name");

        final TextView info_team_name = (TextView) info_view.findViewById(R.id.info_team_name);
        final TextView info_coach = (TextView) info_view.findViewById(R.id.info_coach);
        //final TextView info_gender = (TextView) info_view.findViewById(R.id.info_gender);
        //final TextView info_sport = (TextView) info_view.findViewById(R.id.info_sport);
        final ListView list = (ListView) info_view.findViewById(R.id.info_players);
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
                        JSONObject team_object = resultsArray.getJSONObject(i);
                        String team_name = team_object.getString("team_name");
                        if (Objects.equals(info_on_tournament, team_name)){
                            String tourney_name = team_object.getString("tournament_name");
                            String coach = team_object.getString("coach");
                            //String gender = team_object.getString("gender");
                            //String sport = team_object.getString("sport");

                            registered_for_tournaments.add(tourney_name);
                            info_team_name.setText(team_name);
                            //info_sport.setText(sport);
                            //info_gender.setText(gender);
                            info_coach.setText("Coach: " + coach+"");
                        }
                        //Log.i("Debug", tourney_name);
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                            inflater.getContext(),
                            android.R.layout.simple_list_item_1,
                            registered_for_tournaments );
                    list.setAdapter(arrayAdapter);


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
}
