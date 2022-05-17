package com.example.nationinfo;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    public static final String COUNTRY_DETAIL_KEY = "country";
    public static final String COUNTRY_DETAIL_POS = "pos";
    private static CountryAdapter countryAdapter;
    private CountryClient client;
    private static final String GEONAME_API_KEY = "tdnghia";
    private ArrayList<Country> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.lv);
        arrayList = new ArrayList<Country>();
        countryAdapter = new CountryAdapter(this, arrayList);
        lv.setAdapter(countryAdapter);

        setupCountrySelectedListener();
        fetchCountry(GEONAME_API_KEY);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        searchView = (SearchView) searchItem.getActionView();
        SearchView finalSearchView = searchView;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                ArrayList<Country> fillerCountries = new ArrayList<Country>();

                for (Country country : arrayList) {
                    if (country.getCountryName().toLowerCase().contains(s.toLowerCase())) {
                        fillerCountries.add(country);
                    }
                }

                countryAdapter = new CountryAdapter(getApplicationContext(), fillerCountries);
                lv.setAdapter(countryAdapter);

                finalSearchView.clearFocus();
                searchItem.collapseActionView();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                Log.e("Nomad", "onQueryTextSubmit");
                ArrayList<Country> fillerCountries = new ArrayList<Country>();

                for (Country country : arrayList) {
                    if (country.getCountryName().toLowerCase().contains(s.toLowerCase())) {
                        fillerCountries.add(country);
                    }
                }
                countryAdapter = new CountryAdapter(getApplicationContext(), fillerCountries);
                lv.setAdapter(countryAdapter);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setupCountrySelectedListener(){
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(COUNTRY_DETAIL_KEY, countryAdapter.getItem(position).getCountryCode());
                intent.putExtra(COUNTRY_DETAIL_POS,position);
                startActivity(intent);
            }
        });
    }

    private void fetchCountry(String username){
        Toast.makeText(getApplicationContext(),"Loading",Toast.LENGTH_LONG).show();
        client = new CountryClient();
        client.getCountryClient(username, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Toast.makeText(getApplicationContext(), "Load Success", Toast.LENGTH_LONG).show();
                    JSONArray docs = null;
                    if (response != null) {
                        docs = response.getJSONArray("geonames");
                        final ArrayList<Country> countries = Country.fromJson(docs);
                        countryAdapter.clear();
                        for (Country book : countries) {
                            countryAdapter.add(book);
                        }
                        countryAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "Load Failure", Toast.LENGTH_LONG).show();
            }
        });
    }
}
