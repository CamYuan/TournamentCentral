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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;


public class TournamentsFragment extends ListFragment {

    //get SQL tournaments and show them
    public String[] Tournaments;
    List<String> tourneys = new ArrayList<String>();
    public ArrayList<Participants> list_of_tournaments = new ArrayList<>(32);


    public TournamentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final LinearLayout linearLayout = new LinearLayout(getActivity());
        getActivity().setContentView(linearLayout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        // Response received from the server
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    TextView textView = new TextView(getActivity());
                    textView.setText(response);
                    linearLayout.addView(textView);
                    Toast toast = Toast.makeText(getActivity(), response,
                            Toast.LENGTH_LONG);
                    toast.show();
                        //go back to the home page
                    String team_names = jsonResponse.getString("team_name");
                    tourneys.add(team_names);
                    //tourneys.add(team_names);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        TeamRequest teamRequest = new TeamRequest("cam", responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(teamRequest);

        //Arrays.sort(Tournaments);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(),
                android.R.layout.simple_list_item_1,
                tourneys);
                //getResources().getStringArray(R.array.Tournaments));
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
        //TextView textView = new TextView(getActivity());
        //textView.setText(R.string.hello_blank_fragment);
        //return textView;
    }
}
