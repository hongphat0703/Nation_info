package com.example.nationinfo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import pl.droidsonroids.gif.GifImageView;

public class DetailActivity extends AppCompatActivity {
    private static final String GEONAME_API_KEY = "tdnghia";
    private GifImageView ivNation;
    private TextView tvname, tvarea, tvpop, tvflag, tv_name, tv_area, tv_pop;
    private GifImageView imgFlag;
    private CountryClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        MainActivity main = new MainActivity();
        tvname = (TextView) findViewById(R.id.tvname);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tvarea = (TextView) findViewById(R.id.tvarea);
        tv_area = (TextView) findViewById(R.id.tv_area);
        tvpop = (TextView) findViewById(R.id.tvpop);
        tv_pop = (TextView) findViewById(R.id.tv_pop);
        tvflag = (TextView) findViewById(R.id.tvflag);
        imgFlag = (GifImageView) findViewById(R.id.imgFlag);
        ivNation = (GifImageView) findViewById(R.id.ivNation);

        String countryCode = getIntent().getStringExtra(MainActivity.COUNTRY_DETAIL_KEY);
        final int[] pos = {0};
        pos[0] = getIntent().getIntExtra(MainActivity.COUNTRY_DETAIL_POS,0);
        Log.e("Kiem tra Activity", countryCode);
        loadCountry(countryCode);
    }
    private void loadCountry(String countryCode){
        client = new CountryClient();
        client.getExtraCountryDetails(GEONAME_API_KEY, countryCode, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    JSONArray docs = null;
                    if(response.has("geonames")) {
                        Log.e("Kiem tra Country Activity", response.toString());
                        // Get the docs json array
                        docs = response.getJSONArray("geonames");
                        // Parse json array into array of model objects
                        final ArrayList<Country> countries = Country.fromJson(docs);

                        //change activity title
                        Log.e("Kiem tra Country Activity", countries.get(0).getCountryName());
                        DetailActivity.this.setTitle(countries.get(0).getCountryName());
                        // Populate data
                        String nationUrl = "http://img.geonames.org/img/country/250/"+ countries.get(0).getCountryCode().toUpperCase() + ".png";
                        Log.e("Kiem tra Country Activity", nationUrl);

                        Picasso.get()
                                .load(nationUrl)
                                .placeholder(R.drawable.loading_icon)
                                .error(R.drawable.stub)
                                .into(ivNation);
                        tv_name.setText(countries.get(0).getCountryName());
                        //Format area
                        Locale locale = new Locale("en","EN");
                        NumberFormat en = NumberFormat.getInstance(locale);
                        String str = en.format(Double.parseDouble(countries.get(0).getAreaInSqKm()));
                        tv_area.setText(str + " km²");
                        //Format population
                        Locale locale2 = new Locale("en","EN");
                        NumberFormat en2 = NumberFormat.getInstance(locale2);
                        String str2 = en2.format(Double.parseDouble(countries.get(0).getPopulation()));
                        tv_pop.setText(str2 + " people");

                        String flagUrl = "http://img.geonames.org/flags/x/"+ countries.get(0).getCountryCode().toLowerCase() + ".gif";
                        Log.e("Kiem tra Country Activity", flagUrl);
                        Picasso.get()
                                .load(flagUrl)
                                .placeholder(R.drawable.loading_icon)
                                .error(R.drawable.stub)
                                .into(imgFlag);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "Load Failure", Toast.LENGTH_LONG).show();
            }
        });
    }
}
