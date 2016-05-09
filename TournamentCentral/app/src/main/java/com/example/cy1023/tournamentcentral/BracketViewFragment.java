package com.example.cy1023.tournamentcentral;


import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BracketViewFragment extends Fragment {
    //private static final String LOGIN_REQUEST_URL = "http://tourneycentral.comli.com/QueryTeams.php";

    public BracketViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View bracket_view = inflater.inflate(R.layout.bracket_view, container, false);
        Bundle bundle = getArguments();
        String[][][] the_bracket = (String[][][]) bundle.getSerializable("Bracket");
        TableLayout populate = (TableLayout) bracket_view.findViewById(R.id.populate);
        int ii, jj;
        for (int i = 0; i <the_bracket.length; i++) {
            TableRow round_rows = new TableRow(getActivity());
            TextView round_views = new TextView(getActivity());
            ii = i+1;
            round_views.setText("Round " + ii + "");
            round_views.setTypeface(Typeface.DEFAULT_BOLD);
            round_views.setTextAppearance(android.R.style.TextAppearance_Large);
            round_rows.addView(round_views);
            populate.addView(round_rows);
            for (int j = 0; j <the_bracket[i].length; j++){
                TableRow game_rows = new TableRow(getActivity());
                TextView game_views = new TextView(getActivity());
                jj=j+1;
                game_views.setText("Game " + jj + "");
                game_views.setTextAppearance(android.R.style.TextAppearance_Medium);
                game_rows.addView(game_views);
                populate.addView(game_rows);
                Log.i("info", "" + the_bracket[i][j][0]);
                Log.i("info", ""+the_bracket[i][j][1]);

                TableRow team_row1 = new TableRow(getActivity());
                TableRow team_row2 = new TableRow(getActivity());
                TextView team_1 = new TextView(getActivity());
                TextView team_2 = new TextView(getActivity());
                team_1.setText(the_bracket[i][j][0]);
                team_2.setText(the_bracket[i][j][1]);
                team_1.setTextAppearance(android.R.style.TextAppearance_Small);
                team_2.setTextAppearance(android.R.style.TextAppearance_Small);
                team_1.setBackgroundResource(R.drawable.game_boxes);
                team_2.setBackgroundResource(R.drawable.game_boxes);
                TextView score1 = new TextView(getActivity());
                TextView score2 = new TextView(getActivity());
                score1.setBackgroundResource(R.drawable.scores_boxes);
                score2.setBackgroundResource(R.drawable.scores_boxes);
                team_row1.setWeightSum(3);
                team_row2.setWeightSum(3);
                score1.setText("0");
                score2.setText("0");
                //team_1.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
                //team_2.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
                team_row1.addView(team_1);
                team_row1.addView(score1);
                team_row2.addView(team_2);
                team_row2.addView(score2);
                populate.addView(team_row1);
                populate.addView(team_row2);
            }
        }

        return bracket_view;
    }

   /* @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //Go to a tournament page
        String value = (String) getListAdapter().getItem(position);
        Bundle bundle = new Bundle();
        bundle.putString("team_name", value);

        Fragment fragment = new InfoTeamFragment();
        fragment.setArguments(bundle);
        getActivity().getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment, null)
                .addToBackStack(null)
                .commit();
        super.onListItemClick(l, v, position, id);

    }*/
}
