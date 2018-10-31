package com.example.bhoomi.lms.Teacher.Activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.bhoomi.lms.APIModel.CreateCategory.CreateCategory;
import com.example.bhoomi.lms.APIModel.TutorCategoryList.CategoryInfo;
import com.example.bhoomi.lms.APIModel.TutorCategoryList.TutorCategoryList;
import com.example.bhoomi.lms.APIModel.UpdateCategotyTutor.UpdateCategoryResp;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Retrofit.APIService;
import com.example.bhoomi.lms.Retrofit.APIUtils;
import com.example.bhoomi.lms.Student.Constants.ConstantData;
import com.example.bhoomi.lms.Student.Model.CategoryModel;
import com.example.bhoomi.lms.Teacher.Adapter.CategoryList_Adapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateCategories_Activity extends AppCompatActivity implements CategoryList_Adapter.ViewCategoryClickListner {
    private RecyclerView recyclerView_curriculum;
    private ArrayList<CategoryInfo> categoryArrayList;
    private CategoryModel categoryModel;
    private CategoryList_Adapter curriculum_adapter;
    private Toolbar toolbar_categories;
    private APIService apiService;
    private ProgressDialog progressDialog;
    private String status;
    private SharedPreferences sharedPreferences;
    private String user_id;
    private String category_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_categories_);

        apiService = APIUtils.getAPIService();
        progressDialog = new ProgressDialog(CreateCategories_Activity.this);
        toolbar_categories = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_categories);
        toolbar_categories.setTitle("");
        toolbar_categories.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar_categories);

        toolbar_categories.setNavigationIcon(R.drawable.ic_back);
        Configuration config = getApplicationContext().getResources().getConfiguration();
        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL)
            toolbar_categories.getNavigationIcon().setAutoMirrored(true);
        toolbar_categories.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        sharedPreferences = getSharedPreferences(ConstantData.PREF_NAME, MODE_PRIVATE);
        user_id = sharedPreferences.getString(ConstantData.USER_ID, "");

        recyclerView_curriculum = findViewById(R.id.recyclerView_categories);
        categoryArrayList = new ArrayList<>();
        categoryModel = new CategoryModel();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView_curriculum.setLayoutManager(linearLayoutManager);
        curriculum_adapter = new CategoryList_Adapter(this, new ArrayList<CategoryInfo>(0), user_id, this);
        recyclerView_curriculum.setAdapter(curriculum_adapter);
        getCategoryData();
    }

    private void getCategoryData() {
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();
        apiService.getCategoryData().enqueue(new Callback<TutorCategoryList>() {
            @Override
            public void onResponse(Call<TutorCategoryList> call, Response<TutorCategoryList> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("Success")) {
                        categoryArrayList.addAll(response.body().getCategoryInfo());
                        curriculum_adapter.updateData(response.body().getCategoryInfo());
                    } else {
                        Toast.makeText(CreateCategories_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CreateCategories_Activity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TutorCategoryList> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_addqa, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_plus:

                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.layout_add_category, null);
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                dialogBuilder.setView(dialogView);
                dialogBuilder.setCancelable(true);
                final AlertDialog alertbox = dialogBuilder.create();

                final TextInputEditText textInputEditText_name = dialogView.findViewById(R.id.qtitle_textInputEditText);
                TextInputLayout textInputLayout = dialogView.findViewById(R.id.textInput_qtitle);
                ImageView imageView = dialogView.findViewById(R.id.close_dialog);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertbox.dismiss();
                    }
                });
                Configuration config = getApplicationContext().getResources().getConfiguration();
                if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
                    textInputEditText_name.setTextDirection(View.TEXT_DIRECTION_RTL);
                }

                Typeface externalFont = Typeface.createFromAsset(getAssets(), "fonts/ubuntu_r.ttf");
                textInputEditText_name.setTypeface(externalFont);
                textInputLayout.setTypeface(externalFont);


                final RadioGroup radioGroup = dialogView.findViewById(R.id.radiogrp_stts);

                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {

                        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                        RadioButton radioBtn = (RadioButton) dialogView.findViewById(checkedRadioButtonId);

                        if (radioBtn.getText().equals(getString(R.string.active))) {
                            status = "1";
                        } else {
                            status = "0";
                        }

                    }
                });

                Button button = dialogView.findViewById(R.id.buttn_save);

                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        creaqteCategory(textInputEditText_name.getText().toString(), alertbox, status);
                    }
                });

                alertbox.show();

                break;


            default:
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void creaqteCategory(String s, final AlertDialog alertbox, String status) {

        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();

        apiService.createCategories(s, status, null, user_id).enqueue(new Callback<CreateCategory>() {

            @Override
            public void onResponse(Call<CreateCategory> call, Response<CreateCategory> response) {

                progressDialog.dismiss();

                if (response.isSuccessful()) {

                    if (response.body().getStatus().equalsIgnoreCase("Success")) {
                        alertbox.dismiss();
                        Toast.makeText(CreateCategories_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        recreate();
//                        homeGridAdapter.updateList(response.body().getCategoryInfo());
                    } else {
                        Toast.makeText(CreateCategories_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                }

            }

            @Override
            public void onFailure(Call<CreateCategory> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onCategoryClick(int pos) {
        if (user_id.equals(categoryArrayList.get(pos).getCreatedBy())) {
            LayoutInflater inflater = getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.layout_update_category, null);
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setView(dialogView);
            dialogBuilder.setCancelable(true);
            final AlertDialog alertbox = dialogBuilder.create();

            final TextInputEditText textInputEditText_name = dialogView.findViewById(R.id.qtitle_textInputEditText);
            TextInputLayout textInputLayout = dialogView.findViewById(R.id.textInput_qtitle);
            ImageView imageView = dialogView.findViewById(R.id.close_dialog);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertbox.dismiss();
                }
            });

            Configuration config = getApplicationContext().getResources().getConfiguration();
            if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
                textInputEditText_name.setTextDirection(View.TEXT_DIRECTION_RTL);
            }

            Typeface externalFont = Typeface.createFromAsset(getAssets(), "fonts/ubuntu_r.ttf");
            textInputEditText_name.setTypeface(externalFont);
            textInputLayout.setTypeface(externalFont);


            final RadioGroup radioGroup = dialogView.findViewById(R.id.radiogrp_stts);
            RadioButton radioActive = dialogView.findViewById(R.id.active);
            RadioButton radioDeactive = dialogView.findViewById(R.id.deactive);

            category_id = categoryArrayList.get(pos).getCategoryId();
            textInputEditText_name.setText(categoryArrayList.get(pos).getCategoryName());
            if (categoryArrayList.get(pos).getActive().equals("1")) {
                radioActive.setChecked(true);
                status = "1";
            } else {
                radioDeactive.setChecked(true);
                status = "0";
            }

            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                    RadioButton radioBtn = (RadioButton) dialogView.findViewById(checkedRadioButtonId);

                    if (radioBtn.getText().equals(getString(R.string.active))) {
                        status = "1";
                    } else {
                        status = "0";
                    }

                }
            });

            Button button = dialogView.findViewById(R.id.buttn_save);

            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    updateCategory(category_id, textInputEditText_name.getText().toString(), alertbox, status);
                }
            });

            alertbox.show();
        } else {
            Toast.makeText(this, R.string.upload_only_own_category, Toast.LENGTH_SHORT).show();
        }
    }

    private void updateCategory(String category_id, String s, final AlertDialog alertbox, String
            status) {

        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();


        apiService.updateCategory(category_id, s, status, null, user_id).enqueue(new Callback<UpdateCategoryResp>() {

            @Override
            public void onResponse(Call<UpdateCategoryResp> call, Response<UpdateCategoryResp> response) {

                progressDialog.dismiss();

                if (response.isSuccessful()) {

                    if (response.body().getStatus().equalsIgnoreCase("Success")) {
                        alertbox.dismiss();
                        Toast.makeText(CreateCategories_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        recreate();
//                        homeGridAdapter.updateList(response.body().getCategoryInfo());
                    } else {
                        Toast.makeText(CreateCategories_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                }

            }

            @Override
            public void onFailure(Call<UpdateCategoryResp> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

}
