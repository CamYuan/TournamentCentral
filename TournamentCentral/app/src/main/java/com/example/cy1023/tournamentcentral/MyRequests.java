package com.example.cy1023.tournamentcentral;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MyRequests extends StringRequest {
    private Map<String, String> params;


    public MyRequests(String username, String LOGIN_REQUEST_URL, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
    }
}
