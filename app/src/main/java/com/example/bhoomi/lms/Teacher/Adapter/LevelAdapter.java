package com.example.bhoomi.lms.Teacher.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Student.Constants.MyMediumText;
import com.example.bhoomi.lms.Teacher.Model.CategoryModel;

import java.util.ArrayList;


public class LevelAdapter extends ArrayAdapter<String> {

    String[] data = null;
    CountryHolder countryHolder;
    private Context context;

    public LevelAdapter(Context listener, int simple_spinner_item, String[] countryList) {
        super(listener, simple_spinner_item, countryList);
        this.context = listener;
        this.data = countryList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getDropDownView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
//        final String item = data.get(position);

        if (row == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.simple_spinner_item, parent, false);

            countryHolder = new CountryHolder();
        }

        countryHolder.textViewCountry = (MyMediumText) row.findViewById(R.id.countryName);
        countryHolder.textViewCountry.setText(data[position]);

        return row;

    }


    private class CountryHolder {

        MyMediumText textViewCountry;
    }
}
