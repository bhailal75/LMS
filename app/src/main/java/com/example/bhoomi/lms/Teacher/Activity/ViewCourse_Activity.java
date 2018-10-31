package com.example.bhoomi.lms.Teacher.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bhoomi.lms.APIModel.CourseDetails.StudentAlsoView;
import com.example.bhoomi.lms.APIModel.ViewCourse.ViewCoursesInfo;
//import com.example.bhoomi.lms.APIModel.ViewCourse.ViewCourse;
import com.example.bhoomi.lms.APIModel.ViewCourse.ViewCourseList;
import com.example.bhoomi.lms.R;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bhoomi.lms.Retrofit.APIUtils;
import com.example.bhoomi.lms.Student.Adapter.StudentViewAdapter;
import com.example.bhoomi.lms.Student.Constants.ConstantData;
import com.example.bhoomi.lms.Student.Constants.MyMediumText;
import com.example.bhoomi.lms.Teacher.Adapter.View_Course_Adapter;
import com.philjay.valuebar.ValueBar;
import com.philjay.valuebar.colors.RedToGreenFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.android.volley.RequestQueue;
//import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.bhoomi.lms.Retrofit.APIService;
import com.example.bhoomi.lms.Retrofit.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


public class ViewCourse_Activity extends AppCompatActivity implements View_Course_Adapter.ViewCourseClickListner {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private List<ViewCoursesInfo> listViewCourseLists;
    private APIService apiService;
    private int created_by = 1;
    private RecyclerView recyclerVew;
    private Toolbar toolbar_view_course;
    private MyMediumText textNoDataFound;
    private View_Course_Adapter mAdapter;
    private ProgressDialog progressDialog;
    //  private APIService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course);

        toolbar_view_course = findViewById(R.id.toolbar_viewcourse);
        toolbar_view_course.setTitle("");
        toolbar_view_course.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar_view_course);
        toolbar_view_course.setNavigationIcon(R.drawable.ic_back);
        Configuration config = getApplicationContext().getResources().getConfiguration();
        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL)
            toolbar_view_course.getNavigationIcon().setAutoMirrored(true);

        toolbar_view_course.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        apiService = APIUtils.getAPIService();
        sharedPreferences = getSharedPreferences(ConstantData.PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        progressDialog = new ProgressDialog(ViewCourse_Activity.this);
        listViewCourseLists = new ArrayList<>();
        recyclerVew = findViewById(R.id.recyclerView_categories);
        textNoDataFound = findViewById(R.id.txt_no_data_found);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerVew.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
        mAdapter = new View_Course_Adapter(this, listViewCourseLists, this);
        recyclerVew.setAdapter(mAdapter);
        //Initializing our superheroes list
        populatedatatolist(created_by);
        }


    private void populatedatatolist(int created_by) {

        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();
        ////////////////////////////////////////
        apiService.viewCourse( sharedPreferences.getString(ConstantData.USER_ID,"")).enqueue(new Callback<ViewCourseList>() {
//        apiService.viewCourse("1").enqueue(new Callback<ViewCourseList>() {
            @Override
            public void onResponse(Call<ViewCourseList> call, Response<ViewCourseList> response) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("Success")) {
                        if (response.body().getViewCoursesInfo() != null && response.body().getViewCoursesInfo().size() > 0) {
                            recyclerVew.setVisibility(View.VISIBLE);
                            listViewCourseLists.addAll(response.body().getViewCoursesInfo());
                        } else {
                            recyclerVew.setVisibility(View.GONE);
                            textNoDataFound.setVisibility(View.VISIBLE);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<ViewCourseList> call, Throwable t) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                recyclerVew.setVisibility(View.GONE);
                textNoDataFound.setVisibility(View.VISIBLE);
            }
        });
    }
    @Override
    public void onCourseClick(int pos) {
        Toast.makeText(this, "You View Course :: "+listViewCourseLists.get(pos).getCourseTitle(), Toast.LENGTH_SHORT).show();
        System.out.println("courseId "+listViewCourseLists.get(pos).getCourseId());
        Intent intent = new Intent(getApplicationContext(),Curriculum_Activity.class);
        intent.putExtra("course_id",listViewCourseLists.get(pos).getCourseId());
        startActivity(intent);
        finish();
    }
}


