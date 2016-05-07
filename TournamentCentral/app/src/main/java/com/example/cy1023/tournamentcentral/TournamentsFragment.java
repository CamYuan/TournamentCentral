package com.example.cy1023.tournamentcentral;


import android.app.AlertDialog;
import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.cy1023.tournamentcentral.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Vector;


public class TournamentsFragment extends ListFragment {

    //get SQL tournaments and show them
    List<String> tourneys = new ArrayList<String>();


    public TournamentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Response received from the server
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //Log.d("Debug", response);
                    //load the Json array that you get from the PHP file
                    // "[" Brackets indicate JSON Array
                    // "{" Curly Braces indicate JSON Objects
                    //PHP file returns "arrayName" [ {Object}, {Object}, ...]
                    JSONArray resultsArray = new JSONArray(response);
                    for (int i=0; i<resultsArray.length(); i++){
                        JSONObject team_object = resultsArray.getJSONObject(i);
                        String team_name = team_object.getString("team_name");
                        //Log.d("Debug", team_name);
                        tourneys.add(team_name);
                    }
                    Collections.sort(tourneys);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            inflater.getContext(),
                            android.R.layout.simple_list_item_1,
                            tourneys);
                    setListAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        TeamRequest teamRequest = new TeamRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(teamRequest);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
