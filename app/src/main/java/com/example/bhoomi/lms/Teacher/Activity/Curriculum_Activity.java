package com.example.bhoomi.lms.Teacher.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.Toast;

import com.example.bhoomi.lms.APIModel.AddSections.AddSection;
import com.example.bhoomi.lms.APIModel.CreateCourse.CreateCourse;
import com.example.bhoomi.lms.APIModel.SectionList.CourseSectionsInfo;
import com.example.bhoomi.lms.APIModel.SectionList.SectionList;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Retrofit.APIService;
import com.example.bhoomi.lms.Retrofit.APIUtils;
import com.example.bhoomi.lms.Student.Constants.MyMediumText;
import com.example.bhoomi.lms.Student.Model.CategoryModel;
import com.example.bhoomi.lms.Teacher.Adapter.Curriculum_Adapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Curriculum_Activity extends AppCompatActivity {

    private RecyclerView recyclerView_curriculum;
    private ArrayList<CourseSectionsInfo> categoryModelArrayList;
    private CategoryModel categoryModel;
    private Curriculum_Adapter curriculum_adapter;
    private Toolbar toolbar_curriculum;
    private String course_id;
    private APIService apiService;
    private ProgressDialog progressDialog;
    private MyMediumText noSetions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curriculum_);
        apiService = APIUtils.getAPIService();
        progressDialog = new ProgressDialog(Curriculum_Activity.this);
        Intent intent = getIntent();
        course_id = intent.getStringExtra("course_id");
        toolbar_curriculum = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_curriculum);
        toolbar_curriculum.setTitle("");
        toolbar_curriculum.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar_curriculum);
        toolbar_curriculum.setNavigationIcon(R.drawable.ic_back);
        Configuration config = getApplicationContext().getResources().getConfiguration();
        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL)
            toolbar_curriculum.getNavigationIcon().setAutoMirrored(true);
        noSetions = findViewById(R.id.noSetions);
        toolbar_curriculum.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        recyclerView_curriculum = findViewById(R.id.recyclerView_curriculum);
        categoryModelArrayList = new ArrayList<>();
        categoryModel = new CategoryModel();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView_curriculum.setLayoutManager(linearLayoutManager);
//        for (int i = 0 ; i < 5; i++)
//        {
//            categoryModel.setCat_name("Layout Design");
//            categoryModelArrayList.add(categoryModel);
//        }
        curriculum_adapter = new Curriculum_Adapter(this, new ArrayList<CourseSectionsInfo>(0));
        recyclerView_curriculum.setAdapter(curriculum_adapter);
        getSectionData(course_id);
    }

    private void getSectionData(String course_id) {
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
        System.out.println("course_id " + course_id);
        apiService.sectionList(course_id).enqueue(new Callback<SectionList>() {
            @Override
            public void onResponse(Call<SectionList> call, Response<SectionList> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("Success")) {
                        curriculum_adapter.updateCurriculum(response.body().getCourseSectionsInfo());
                        noSetions.setVisibility(View.GONE);
                    } else {
                        noSetions.setVisibility(View.VISIBLE);
                        Toast.makeText(Curriculum_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SectionList> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Curriculum_Activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
                View dialogView = inflater.inflate(R.layout.layout_add_curriculum, null);
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                dialogBuilder.setView(dialogView);
                dialogBuilder.setCancelable(true);
                final AlertDialog alertbox = dialogBuilder.create();
                Button button = dialogView.findViewById(R.id.buttn_save);
                final TextInputEditText textInputEditText_name = dialogView.findViewById(R.id.qname_textInputEditText);
                TextInputLayout textInputLayout_name = dialogView.findViewById(R.id.textInput_qname);
                final TextInputEditText textInputEditText = dialogView.findViewById(R.id.qtitle_textInputEditText);
                TextInputLayout textInputLayout = dialogView.findViewById(R.id.textInput_qtitle);
                ImageView imageCloseDialog = dialogView.findViewById(R.id.close_dialog);
                Configuration config = getApplicationContext().getResources().getConfiguration();
                if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
                    textInputEditText_name.setTextDirection(View.TEXT_DIRECTION_RTL);
                    textInputLayout_name.setTextDirection(View.TEXT_DIRECTION_RTL);
                    textInputEditText.setTextDirection(View.TEXT_DIRECTION_RTL);
                    textInputLayout.setTextDirection(View.TEXT_DIRECTION_RTL);
                }
                Typeface typeface_medium = Typeface.createFromAsset(getAssets(), "fonts/ubuntu_m.ttf");
                textInputEditText.setTypeface(typeface_medium);
                textInputLayout.setTypeface(typeface_medium);
                textInputEditText_name.setTypeface(typeface_medium);
                textInputLayout_name.setTypeface(typeface_medium);
                button.setTypeface(typeface_medium);
                imageCloseDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertbox.dismiss();
                    }
                });
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createCurriculum(textInputEditText_name.getText().toString(), textInputEditText.getText().toString(), alertbox);
                    }
                });
                alertbox.show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createCurriculum(String toString, String s, final AlertDialog alertbox) {
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
        apiService.addSections(toString, s, course_id).enqueue(new Callback<AddSection>() {
            @Override
            public void onResponse(Call<AddSection> call, Response<AddSection> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("Success")) {
                        alertbox.dismiss();
                        getSectionData(course_id);
                    } else {
                        Toast.makeText(Curriculum_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddSection> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Curriculum_Activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
