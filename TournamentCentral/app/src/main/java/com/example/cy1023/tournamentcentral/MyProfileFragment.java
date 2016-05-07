package com.example.cy1023.tournamentcentral;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;

/**
 * Created by cy1023 on 5/4/2016.
 */
public class MyProfileFragment extends ListFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(),
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.myStuff));
        setListAdapter(adapter);


        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Fragment fragment;
        String title = "";
        switch (position) {
            case 0:
                fragment = new MyTourneyFrag();
                title = "My Tournaments";
                break;
            case 1:
                fragment = new MyTeamsFragment();
                title = "My Teams";
                break;
            default:
                fragment = new TopFragment();
        }
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
        ((MainActivity) getActivity()).setActionBarTitleFromFragment(title);
        super.onListItemClick(l, v, position, id);
    }

}
