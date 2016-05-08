package com.example.cy1023.tournamentcentral;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;


public class TeamRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://tourneycentral.comli.com/QueryTeams.php";

    public TeamRequest(Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
    }
}
