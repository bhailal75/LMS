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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.bhoomi.lms.APIModel.CategoryList.CaregoryList;
import com.example.bhoomi.lms.APIModel.CreateSubCategories.CreateSubcategories;
import com.example.bhoomi.lms.APIModel.TutorCategoryList.TutorCategoryList;
import com.example.bhoomi.lms.APIModel.TutorSubCategoryList.SubCategoryInfo;
import com.example.bhoomi.lms.APIModel.TutorSubCategoryList.TutorSubCategoryList;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Retrofit.APIService;
import com.example.bhoomi.lms.Retrofit.APIUtils;
import com.example.bhoomi.lms.Student.Constants.ConstantData;
import com.example.bhoomi.lms.Student.Constants.MyMediumText;
import com.example.bhoomi.lms.Student.Model.CategoryModel;
import com.example.bhoomi.lms.Teacher.Adapter.CategoryAdapter;
import com.example.bhoomi.lms.Teacher.Adapter.CategoryList_Adapter;
import com.example.bhoomi.lms.Teacher.Adapter.Subcategories_Adapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bhoomi.lms.Teacher.Activity.CreateCourse_Activity.isConnectd;

public class CreateSubCategories_Activity extends AppCompatActivity {

    ArrayList<com.example.bhoomi.lms.Teacher.Model.CategoryModel> categoryList;
    private RecyclerView recyclerView_curriculum;
    private ArrayList<CategoryModel> categoryModelArrayList;
    private CategoryModel categoryModel;
    private Subcategories_Adapter curriculum_adapter;
    private Toolbar toolbar_categories;
    private ProgressDialog progressDialog;
    private APIService apiService;
    private CategoryAdapter categoryAdapter;
    private String spinner_selected_item;
    private String status;

    private SharedPreferences sharedPreferences;
    private String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_sub_categories_);

        apiService = APIUtils.getAPIService();
        progressDialog = new ProgressDialog(CreateSubCategories_Activity.this);

        toolbar_categories = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_subcategories);
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

        recyclerView_curriculum = findViewById(R.id.recyclerView_subcategories);

        categoryModelArrayList = new ArrayList<>();

        categoryModel = new CategoryModel();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView_curriculum.setLayoutManager(linearLayoutManager);

        for (int i = 0; i < 5; i++) {
            categoryModel.setCat_name("Programming");
            categoryModelArrayList.add(categoryModel);
        }

        curriculum_adapter = new Subcategories_Adapter(this, new ArrayList<SubCategoryInfo>(0),user_id);
        recyclerView_curriculum.setAdapter(curriculum_adapter);

        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
//            edittext_price.setTextDirection(View.TEXT_DIRECTION_RTL);
        }

        categoryList = new ArrayList<>();

        doGetCategoryList();
        doGetSubCategoryList();

    }

    private void doGetSubCategoryList() {
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();

        apiService.getSubCategoryData().enqueue(new Callback<TutorSubCategoryList>() {

            @Override
            public void onResponse(Call<TutorSubCategoryList> call, Response<TutorSubCategoryList> response) {
                progressDialog.dismiss();
                curriculum_adapter.updateData(response.body().getSubCategoryInfo());
            }

            @Override
            public void onFailure(Call<TutorSubCategoryList> call, Throwable t) {
                    progressDialog.dismiss();
            }
        });
    }

    private void doGetCategoryList() {

        apiService.getData().enqueue(new Callback<CaregoryList>() {

            @Override
            public void onResponse(Call<CaregoryList> call, Response<CaregoryList> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("Success")) {
                        progressDialog.dismiss();
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_addqa, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.action_plus:
//                LayoutInflater inflater = getLayoutInflater();
//                final View dialogView = inflater.inflate(R.layout.layout_add_subcategory, null);
//                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
//                dialogBuilder.setView(dialogView);
//                dialogBuilder.setCancelable(true);
//                final AlertDialog alertbox = dialogBuilder.create();
//                final EditText editText = dialogView.findViewById(R.id.spinner_category);
//                final TextInputEditText textInputEditText = dialogView.findViewById(R.id.qtitle_textInputEditText);
//                final TextInputLayout textInput_qtitle = dialogView.findViewById(R.id.textInput_qtitle);
//                final Button buttonsave = dialogView.findViewById(R.id.buttn_save);
//                final RadioButton radio_active = dialogView.findViewById(R.id.radio_active);
//                final RadioButton radio_deactive = dialogView.findViewById(R.id.radio_deactive);
//                final RadioGroup radioStts = dialogView.findViewById(R.id.radiogrp_stts);
//                ImageView imageView = dialogView.findViewById(R.id.close_dialog);
//                imageView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        alertbox.dismiss();
//                    }
//                });
//                radioStts.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//
//                    @Override
//                    public void onCheckedChanged(RadioGroup group, int checkedId) {
//                        int checkedRadioButtonId = radioStts.getCheckedRadioButtonId();
//                        RadioButton radioBtn = (RadioButton) dialogView.findViewById(checkedRadioButtonId);
//                        if (radioBtn.getText().equals("Active")) {
//                            status = "1";
//                        } else {
//                            status = "0";
//                        }
//                    }
//                });
//
//                Typeface typeface_medium = Typeface.createFromAsset(getAssets(), "fonts/ubuntu_m.ttf");
//                editText.setTypeface(typeface_medium);
//                textInputEditText.setTypeface(typeface_medium);
//                textInput_qtitle.setTypeface(typeface_medium);
//                buttonsave.setTypeface(typeface_medium);
//                radio_active.setTypeface(typeface_medium);
//                radio_deactive.setTypeface(typeface_medium);
//
//                editText.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//
//                        LayoutInflater inflater = (LayoutInflater) CreateSubCategories_Activity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                        View titleView = inflater.inflate(R.layout.custom_title, null);
//
//                        ((MyMediumText) titleView.findViewById(R.id.custom_title)).setText("Select Category");
//                        AlertDialog alertDialog = new AlertDialog.Builder(CreateSubCategories_Activity.this)
//                                .setTitle("Select Category")
//                                .setAdapter(categoryAdapter, new DialogInterface.OnClickListener() {
//
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int position) {
//                                        editText.setText(categoryList.get(position).getCat_name());
//                                        spinner_selected_item = categoryList.get(position).getCat_id();
//                                        dialog.dismiss();
//                                    }
//                                }).create();
//                        alertDialog.show();
//                    }
//                });
//
//                Button button = dialogView.findViewById(R.id.buttn_save);
//
//                button.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        createSubCategories(spinner_selected_item, textInputEditText.getText().toString(), status, alertbox);
//                    }
//                });
//                alertbox.show();
//                break;
//            default:
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private void createSubCategories(String spinner_selected_item, String s, String p2, final AlertDialog alertbox) {
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();
        apiService.createSubCategories(spinner_selected_item, s, p2, user_id).enqueue(new Callback<CreateSubcategories>() {
            @Override
            public void onResponse(Call<CreateSubcategories> call, Response<CreateSubcategories> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("Success")) {
                        Toast.makeText(CreateSubCategories_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        alertbox.dismiss();
                        recreate();
                    } else {
                        Toast.makeText(CreateSubCategories_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CreateSubCategories_Activity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreateSubcategories> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
}
