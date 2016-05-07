package com.example.cy1023.tournamentcentral;

import android.app.AlertDialog;
import android.os.Bundle;
import android.app.Fragment;
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



public class LoginActivity extends Fragment {

    public LoginActivity() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_login, container, false);

        //get the information from the user
        final EditText etUsername = (EditText) view.findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) view.findViewById(R.id.etPassword);
        final Button tvRegisterLink = (Button) view.findViewById(R.id.tvRegisterLink);
        final Button bLogin = (Button) view.findViewById(R.id.bSignIn);

        //if they need to register...
        tvRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start register activity
                RegisterFragment fragment = new RegisterFragment();
                getActivity().getFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, fragment, null)
                        .addToBackStack(null)
                        .commit();
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                // Response received from the server
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                //go back to the home page
                                MyProfileFragment fragment = new MyProfileFragment();
                                getActivity().getFragmentManager().beginTransaction()
                                        .replace(R.id.content_frame, fragment, null)
                                        .addToBackStack(null)
                                        .commit();
                                ((MainActivity) getActivity()).signedIn = true;
                                ((MainActivity) getActivity()).local_userID = username;
                                ((MainActivity) getActivity()).local_password = password;
                                Toast toast = Toast.makeText(getActivity(), "Signed in!",
                                        Toast.LENGTH_LONG);
                                toast.show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                queue.add(loginRequest);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}
