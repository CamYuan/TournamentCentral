package com.example.cy1023.tournamentcentral;

import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

public class MyHostedTourneysFrag extends ListFragment {
    //private static final String LOGIN_REQUEST_URL = "http://tourneycentral.comli.com/MyHostedTourneys.php";
    private static final String LOGIN_REQUEST_URL = "http://tourneycentral.comli.com/QueryTourneys.php";
    //get SQL tournaments and show them
    List<String> tourneys = new ArrayList<String>();


    public MyHostedTourneysFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) getActivity()).setActionBarTitleFromFragment("My Host");
        // Response received from the server
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("Debug", response);
                    //load the Json array that you get from the PHP file
                    // "[" Brackets indicate JSON Array
                    // "{" Curly Braces indicate JSON Objects
                    //PHP file returns "arrayName" [ {Object}, {Object}, ...]
                    JSONArray resultsArray = new JSONArray(response);
                    for (int i=0; i<resultsArray.length(); i++){
                        JSONObject tourney_object = resultsArray.getJSONObject(i);
                        String tourney_name = tourney_object.getString("tournament_name");
                        //Log.i("Debug", tourney_name);
                        //TODO: Query not working  correctly, comparing for my tournaments in local storage...
                        if(Objects.equals(tourney_object.getString("host"), ((MainActivity) getActivity()).local_userID)){
                            tourneys.add(tourney_name);}
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
        //should used MyRequests()
        CustomRequests myTourneys = new CustomRequests(LOGIN_REQUEST_URL, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(myTourneys);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
