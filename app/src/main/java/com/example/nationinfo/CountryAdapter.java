package com.example.nationinfo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CountryAdapter extends ArrayAdapter<Country> {

    private LayoutInflater layoutInflater;
    private ArrayList<Country> mCountries;

    public CountryAdapter(@NonNull Context context, ArrayList<Country> mCountries) {
        super(context, android.R.layout.simple_list_item_1, mCountries);
        this.layoutInflater = LayoutInflater.from(context);
        this.mCountries = mCountries;
    }

    // View lookup cache
    private static class ViewHolder{
        public ImageView ivFlag;
        public TextView tvNameCountry;
        public TextView tvCapital;
    }

    // Translates a particular `Book` given a position
    // into a relevant row within an AdapterView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.listitem, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvNameCountry = convertView.findViewById(R.id.countryName);
            viewHolder.tvCapital = convertView.findViewById(R.id.countryCapital);
            viewHolder.ivFlag = convertView.findViewById(R.id.ivFlagCover);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Get the data item for this position
        final Country country = getItem(position);

        // Populate the data into the template view using the data object
        viewHolder.tvNameCountry.setText(country.getCountryName());
        viewHolder.tvCapital.setText(country.getCapital());

        String flagUrl = "http://img.geonames.org/flags/x/"+ country.getCountryCode().toLowerCase() + ".gif";
        //String flagUrl = "http://img.geonames.org/img/country/250/"+ country.getCountryCode().toUpperCase() + ".png";

        Log.e("Kiem tra", flagUrl);
        //Toast.makeText(getContext(),flagUrl,Toast.LENGTH_LONG).show();
        Picasso.get()
                .load(flagUrl)
                .error(R.drawable.stub)
                .placeholder(R.drawable.loading_icon)
                .into(viewHolder.ivFlag);

        // Return the completed view to render on screen
        return convertView;
    }
}
