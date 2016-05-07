package com.example.cy1023.tournamentcentral;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class TourneyRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://tourneycentral.comli.com/QueryTournaments.php";

    public TourneyRequest(Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
    }
}
