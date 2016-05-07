package com.example.cy1023.tournamentcentral;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class TeamRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://tourneycentral.comli.com/QueryTeams.php";
    private Map<String, String> params;

    public TeamRequest(String coach, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("coach", coach);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
