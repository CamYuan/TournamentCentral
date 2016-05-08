package com.example.cy1023.tournamentcentral;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by cy1023 on 5/4/2016.
 */
public class HostFragment extends Fragment {

    public HostFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View host_view = inflater.inflate(R.layout.host_tournament, container, false);


        final EditText tourney_name = (EditText) host_view.findViewById(R.id.tourney_name);
        final Button create_button = (Button) host_view.findViewById(R.id.create_tournament);
        //Type dropdown
        final Spinner type = (Spinner) host_view.findViewById(R.id.tourney_type);
        String[] types = new String[]{"Single Elimination", "Double Elimination", "Round Robin"};
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, types);
        type.setAdapter(adapt);
        //num teams dropdown
        final Spinner teams = (Spinner) host_view.findViewById(R.id.num_teams_dropdown);
        String[] participants = new String[29];
        for(int i=0; i<29; i++){
            participants[i] = Integer.toString(i + 4);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, participants);
        teams.setAdapter(adapter);
        //format random/custom
        final Spinner format = (Spinner) host_view.findViewById(R.id.tourney_format);
        String[] formats = new String[]{"Standard Seeding", "Random Seeding", "Custom Seeding"};
        ArrayAdapter<String> formatter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, formats);
        format.setAdapter(formatter);

        //upload image here?
        final Spinner start_months = (Spinner) host_view.findViewById(R.id.start_month);
        ArrayAdapter<CharSequence> monthsAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.months, android.R.layout.simple_spinner_item);
        monthsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        start_months.setAdapter(monthsAdapter);
        final Spinner end_months = (Spinner) host_view.findViewById(R.id.end_month);
        end_months.setAdapter(monthsAdapter);

        final Spinner start_days = (Spinner) host_view.findViewById(R.id.start_day);
        String[] days = new String[31];
        for(int i=1; i<32; i++){
            days[i-1] = Integer.toString(i);
        }
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, days);
        start_days.setAdapter(dayAdapter);
        final Spinner end_days = (Spinner) host_view.findViewById(R.id.end_day);
        end_days.setAdapter(dayAdapter);

        final Spinner start_year = (Spinner) host_view.findViewById(R.id.start_year);
        ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.years, android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        start_year.setAdapter(yearAdapter);
        final Spinner end_year = (Spinner) host_view.findViewById(R.id.end_year);
        end_year.setAdapter(yearAdapter);

        final EditText tourney_id = (EditText) host_view.findViewById(R.id.tourney_name);
        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start register activity
                int num_teams = Integer.parseInt(teams.getSelectedItem().toString());
                String tourney_type = (String) type.getSelectedItem();
                String tourney_format = (String) format.getSelectedItem();
                String date_month = start_months.getSelectedItem().toString();
                int date_day = Integer.parseInt(start_days.getSelectedItem().toString());
                int date_year = Integer.parseInt(start_year.getSelectedItem().toString());
                String start_date = date_month +"-" + date_day+ "-"+date_year;
                date_month = end_months.getSelectedItem().toString();
                date_day = Integer.parseInt(end_days.getSelectedItem().toString());
                date_year = Integer.parseInt(end_year.getSelectedItem().toString());
                String end_date = date_month +"-" + date_day+ "-"+date_year;
                String tourney_name = tourney_id.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("Debug", response);
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                MyProfileFragment fragment = new MyProfileFragment();
                                getActivity().getFragmentManager().beginTransaction()
                                        .replace(R.id.content_frame, fragment, null)
                                        .addToBackStack(null)
                                        .commit();
                                Toast toast = Toast.makeText(getActivity(), "Tournament Created!",
                                        Toast.LENGTH_LONG);
                                toast.show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setMessage("An error has occurred.")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                SaveTournamentRequest registerRequest = new SaveTournamentRequest(tourney_name,
                        ((MainActivity) getActivity()).local_userID,
                        start_date,
                        end_date,
                        tourney_type,
                        tourney_format,
                        num_teams, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                queue.add(registerRequest);
            }
        });
        return host_view;
    }

}
