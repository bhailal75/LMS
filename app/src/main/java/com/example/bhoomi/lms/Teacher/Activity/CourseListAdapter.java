package com.example.bhoomi.lms.Teacher.Activity;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Student.Constants.MyMediumText;
import com.example.bhoomi.lms.Teacher.Adapter.CategoryAdapter;
import com.example.bhoomi.lms.Teacher.Adapter.SubCategoryAdapter;
import com.example.bhoomi.lms.Teacher.Model.CategoryModel;
import com.example.bhoomi.lms.Teacher.Model.CourseListModel;
import com.example.bhoomi.lms.Teacher.Model.SubCategoryModel;

import java.util.ArrayList;

class CourseListAdapter extends ArrayAdapter<CourseListModel> {
    private Context context;
    ArrayList<CourseListModel> data = null;
    CountryHolder countryHolder;



    public CourseListAdapter(Context listener, int simple_spinner_item, ArrayList<CourseListModel> countryList) {
        super(listener, simple_spinner_item, countryList);
        this.context = listener;
        this.data = countryList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        final CourseListModel model = data.get(position);

        if (row == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.simple_spinner_item, parent, false);

            countryHolder = new CountryHolder();

        }

        countryHolder.textViewCountry = (MyMediumText) row.findViewById(R.id.countryName);
        countryHolder.textViewCountry.setText(model.getCourse_name());

        return row;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        final CourseListModel item = data.get(position);

        if (row == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.simple_spinner_item, parent, false);

            countryHolder = new CountryHolder();

        }

        countryHolder.textViewCountry = (MyMediumText) row.findViewById(R.id.countryName);
        countryHolder.textViewCountry.setText("sd");

        return row;
    }

    private class CountryHolder {

        MyMediumText textViewCountry;
    }
}
