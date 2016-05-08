package com.example.cy1023.tournamentcentral;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SaveTeamRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://tourneycentral.comli.com/SaveTeam.php";
    private Map<String, String> params;

    public SaveTeamRequest(String coach, String team_name, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("coach", coach);
        params.put("team_name", team_name);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
