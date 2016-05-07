package com.example.cy1023.tournamentcentral;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by cy1023 on 5/4/2016.
 */
public class HostFragment extends Fragment {

    public HostFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View host_view = inflater.inflate(R.layout.host_tournament, container, false);


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



        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start register activity
                int num_teams = Integer.parseInt(teams.getSelectedItem().toString());
                String tourney_type = (String) type.getSelectedItem();
                String tourney_format = (String) format.getSelectedItem();
                //generate_tournament(tourney_name, num_teams, tourney_type);


                MyProfileFragment fragment = new MyProfileFragment();
                getActivity().getFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, fragment, null)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return host_view;
    }

    public void setDate(){
        PickerDialogs pickerDialogs = new PickerDialogs();
        pickerDialogs.show(getActivity().getFragmentManager(), "date_picker");

    }

}
