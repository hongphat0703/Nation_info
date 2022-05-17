package com.example.nationinfo;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class CountryClient {
    private static final String API_BASE_URL = "http://api.geonames.org/countryInfoJSON";
    private AsyncHttpClient client;

    public CountryClient(){
        this.client = new AsyncHttpClient();
    }

    private String getApiBaseUrl(String url){
        return API_BASE_URL + url;
    }

    public void getCountryClient(final String username, JsonHttpResponseHandler handler){
        String url = getApiBaseUrl("?username=");
        client.get(url + username, handler);
        Log.e("Kiem tra Client", url + username);
    }

    public void getExtraCountryDetails(final String username, String countryCode, JsonHttpResponseHandler handler){
        String url = getApiBaseUrl("?username=");
        client.get(url + username + "&country=" + countryCode, handler);
    }
}
