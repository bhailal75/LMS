package com.example.bhoomi.lms.Teacher.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Student.Constants.MyMediumText;
import com.example.bhoomi.lms.Teacher.Model.CategoryModel;
import com.example.bhoomi.lms.Teacher.Model.SubCategoryModel;

import java.util.ArrayList;

public class SubCategoryAdapter extends ArrayAdapter<SubCategoryModel> {

    private Activity context;
    ArrayList<SubCategoryModel> data = null;
    CountryHolder countryHolder;

    public SubCategoryAdapter(Activity listener, int simple_spinner_item, ArrayList<SubCategoryModel> countryList) {
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
    public View getDropDownView(int position, View convertView,  ViewGroup parent) {
        View row = convertView;
        final SubCategoryModel item = data.get(position);

        if (row == null) {

            LayoutInflater inflater = context.getLayoutInflater();
            row = inflater.inflate(R.layout.simple_spinner_item, parent, false);

            countryHolder = new CountryHolder();

        }

        countryHolder.textViewCountry = (MyMediumText) row.findViewById(R.id.countryName);
        countryHolder.textViewCountry.setText(item.getSub_cat_name());

        return row;

    }


    private class CountryHolder {

        MyMediumText textViewCountry;
    }
}
