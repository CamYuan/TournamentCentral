package com.example.cy1023.tournamentcentral;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by cy1023 on 5/4/2016.
 */
public class CreateTeamFragment extends Fragment {

    public CreateTeamFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View host_view = inflater.inflate(R.layout.create_team_fragment, container, false);


        final EditText etTeam = (EditText) host_view.findViewById(R.id.etTeamName);
        final Button create_button = (Button) host_view.findViewById(R.id.bCreate_team);
        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start register activity
                String team_name = etTeam.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //Log.i("Debug", response);
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                MyProfileFragment fragment = new MyProfileFragment();
                                getActivity().getFragmentManager().beginTransaction()
                                        .replace(R.id.content_frame, fragment, null)
                                        .addToBackStack(null)
                                        .commit();
                                Toast toast = Toast.makeText(getActivity(), "Team Created!",
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
                SaveTeamRequest registerRequest = new SaveTeamRequest(((MainActivity) getActivity()).local_userID,
                        team_name, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                queue.add(registerRequest);
            }
        });
        return host_view;
    }

}
