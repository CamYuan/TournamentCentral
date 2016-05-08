package com.example.cy1023.tournamentcentral;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;


public class CustomRequests extends StringRequest {


    public CustomRequests(String LOGIN_REQUEST_URL, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
    }
}
