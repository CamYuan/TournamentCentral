package com.example.cy1023.tournamentcentral;


import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
//import android.widget.TextView;
import com.example.cy1023.tournamentcentral.R;

import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeamsFragments extends ListFragment {

    //get SQL teams here and show them
    public String[] teamList = {"PotatoHeads","Lightning Bolts", "Daredevils"};


    public TeamsFragments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Arrays.sort(teamList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(),
                android.R.layout.simple_list_item_1,
                teamList);
                //getResources().getStringArray(R.array.pasta));
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
        //TextView textView = new TextView(getActivity());
        //textView.setText(R.string.hello_blank_fragment);
        //return textView;
    }

}
