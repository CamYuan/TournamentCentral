package com.example.cy1023.tournamentcentral;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SaveTournamentRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://tourneycentral.comli.com/SaveTournament.php";
    private Map<String, String> params;

    public SaveTournamentRequest(String tournament_name, String host, String start_date,
                                 String end_date, String type, String format,
                                 int num_teams, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("tournament_name", tournament_name);
        params.put("host", host);
        params.put("start_date", start_date);
        params.put("end_date", end_date);
        params.put("type", type);
        params.put("format", format);
        params.put("num_teams", num_teams+"");
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
