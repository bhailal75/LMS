package com.example.bhoomi.lms.Teacher.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.bhoomi.lms.APIModel.CategoryList.CaregoryList;
import com.example.bhoomi.lms.APIModel.CreateCategory.CreateCategory;
import com.example.bhoomi.lms.APIModel.CreateSubCategories.CreateSubcategories;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Retrofit.APIService;
import com.example.bhoomi.lms.Retrofit.APIUtils;
import com.example.bhoomi.lms.Student.Constants.ConstantData;
import com.example.bhoomi.lms.Student.Constants.MyMediumText;
import com.example.bhoomi.lms.Teacher.Adapter.CategoryAdapter;
import com.example.bhoomi.lms.Teacher.Model.CategoryModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSubCategory extends AppCompatActivity {

    TextInputEditText textInputEditText;
    TextInputLayout textInput_qtitle;
    Typeface externalFont;
    RadioGroup radioGroup;
    RadioButton radioBtn;
    Button buttonsave;
    EditText editText;
    RadioButton radio_active;
    RadioButton radio_deactive;
    RadioGroup radioStts;
    ArrayList<CategoryModel> categoryList;
    private Toolbar toolbar;
    private APIService apiService;
    private ProgressDialog progressDialog;
    private String status;
    private SharedPreferences sharedPreferences;
    private String user_id;
    private CategoryAdapter categoryAdapter;
    private com.example.bhoomi.lms.Student.Model.CategoryModel categoryModel;
    private String spinner_selected_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sub_category);
        toolbar = findViewById(R.id.toolbar_subcategories);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        Configuration config = getApplicationContext().getResources().getConfiguration();
        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL)
            toolbar.getNavigationIcon().setAutoMirrored(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        apiService = APIUtils.getAPIService();
        progressDialog = new ProgressDialog(AddSubCategory.this);
        sharedPreferences = getSharedPreferences(ConstantData.PREF_NAME, MODE_PRIVATE);
        user_id = sharedPreferences.getString(ConstantData.USER_ID, "");

        categoryModel = new com.example.bhoomi.lms.Student.Model.CategoryModel();
        categoryList = new ArrayList<>();

        editText = findViewById(R.id.spinner_category);
        textInputEditText = findViewById(R.id.qtitle_textInputEditText);
        textInput_qtitle = findViewById(R.id.textInput_qtitle);


        buttonsave = findViewById(R.id.buttn_save);


        radio_active = findViewById(R.id.radio_active);
        radio_deactive = findViewById(R.id.radio_deactive);
        radioStts = findViewById(R.id.radiogrp_stts);

        radioStts.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int checkedRadioButtonId = radioStts.getCheckedRadioButtonId();
                RadioButton radioBtn = (RadioButton) findViewById(checkedRadioButtonId);

                if (radioBtn.getText().equals(getString(R.string.active))) {
                    status = "1";
                } else {
                    status = "0";
                }
            }
        });

        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            editText.setTextDirection(View.TEXT_DIRECTION_RTL);
            textInputEditText.setTextDirection(View.TEXT_DIRECTION_RTL);
            textInputEditText.setTextDirection(View.TEXT_DIRECTION_RTL);
            textInput_qtitle.setTextDirection(View.TEXT_DIRECTION_RTL);
        }
        Typeface typeface_medium = Typeface.createFromAsset(getAssets(), "fonts/ubuntu_m.ttf");
        editText.setTypeface(typeface_medium);
        textInputEditText.setTypeface(typeface_medium);
        textInput_qtitle.setTypeface(typeface_medium);
        buttonsave.setTypeface(typeface_medium);
        radio_active.setTypeface(typeface_medium);
        radio_deactive.setTypeface(typeface_medium);

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) AddSubCategory.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View titleView = inflater.inflate(R.layout.custom_title, null);
                ((MyMediumText) titleView.findViewById(R.id.custom_title)).setText(R.string.select_category);
                AlertDialog alertDialog = new AlertDialog.Builder(AddSubCategory.this)
                        .setTitle(R.string.select_category)
                        .setAdapter(categoryAdapter, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int position) {


                                editText.setText(categoryList.get(position).getCat_name());
                                spinner_selected_item = categoryList.get(position).getCat_id();

                                dialog.dismiss();
                            }

                        }).create();
                alertDialog.show();
            }
        });
        buttonsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( spinner_selected_item != null && spinner_selected_item.length() == 0){
                    Toast.makeText(AddSubCategory.this, R.string.select_category, Toast.LENGTH_SHORT).show();
                }else if (textInputEditText.getText().length() == 0){
                    textInputEditText.setError(getString(R.string.subcat_name));
                }else if (status == null){
                    Toast.makeText(AddSubCategory.this, R.string.select_sub_category_status, Toast.LENGTH_SHORT).show();
                }else {
                    createSubCategories(spinner_selected_item, textInputEditText.getText().toString(), status, null);
                    editText.getText().clear();
                    textInputEditText.getText().clear();
                }

            }
        });
        doGetCategoryList();

    }

    private void createSubCategories(String spinner_selected_item, String s, String p2, final AlertDialog alertbox) {

        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();

        apiService.createSubCategories(spinner_selected_item, s, p2, user_id).enqueue(new Callback<CreateSubcategories>() {

            @Override
            public void onResponse(Call<CreateSubcategories> call, Response<CreateSubcategories> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("Success")) {
                        Toast.makeText(AddSubCategory.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                         progressDialog.dismiss();
                         finish();
                    } else {
                        Toast.makeText(AddSubCategory.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(AddSubCategory.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreateSubcategories> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private void doGetCategoryList() {

        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();

        apiService.getData().enqueue(new Callback<CaregoryList>() {

            @Override
            public void onResponse(Call<CaregoryList> call, Response<CaregoryList> response) {

                progressDialog.dismiss();

                if (response.isSuccessful()) {

                    if (response.body().getStatus().equalsIgnoreCase("Success")) {

                        for (int i = 0; i < response.body().getCategoryInfo().size(); i++) {
                            com.example.bhoomi.lms.Teacher.Model.CategoryModel categoryModel = new com.example.bhoomi.lms.Teacher.Model.CategoryModel();
                            categoryModel.setCat_name(response.body().getCategoryInfo().get(i).getCategoryName());
                            categoryModel.setCat_id(response.body().getCategoryInfo().get(i).getCategoryId());
                            categoryList.add(categoryModel);
                        }
                        categoryAdapter = new CategoryAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, categoryList);
                        categoryAdapter.notifyDataSetChanged();


                    } else {

                    }
                } else {
                }

            }

            @Override
            public void onFailure(Call<CaregoryList> call, Throwable t) {
                progressDialog.dismiss();
            }
        });


    }
}
