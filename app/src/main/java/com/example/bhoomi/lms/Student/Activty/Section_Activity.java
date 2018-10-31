package com.example.bhoomi.lms.Student.Activty;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bhoomi.lms.APIModel.CourseDetails.CourseDetail;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Retrofit.APIService;
import com.example.bhoomi.lms.Retrofit.APIUtils;
import com.example.bhoomi.lms.Student.Adapter.ExpandableListAdapter;
import com.example.bhoomi.lms.Student.Adapter.SectionExpandableAdapter;
import com.example.bhoomi.lms.Student.Constants.ConstantData;
import com.example.bhoomi.lms.Student.Constants.MyRegularText;
import com.example.bhoomi.lms.Student.Constants.NonScrollExpandableListView;
import com.example.bhoomi.lms.Student.Constants.ResizableCustomView;
import com.philjay.valuebar.ValueBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Section_Activity extends AppCompatActivity {

    private Toolbar toolbar;
    private ProgressDialog progressDialog;
    private APIService apiService;
    private ExpandableListView expandableListView;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    private SectionExpandableAdapter listAdapter;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_);

        Intent intent = getIntent();

        apiService = APIUtils.getAPIService();
        progressDialog = new ProgressDialog(Section_Activity.this);
        sharedPreferences = getSharedPreferences(ConstantData.PREF_NAME, MODE_PRIVATE);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_container);
        toolbar.setTitle("");
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
        expandableListView = (ExpandableListView) findViewById(R.id.section_expandable);
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        doGetCourseDetail(intent.getStringExtra("course_id"));
    }

    private void doGetCourseDetail(String course_id) {


        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();

        apiService.getCourseDetail(sharedPreferences.getString(ConstantData.USER_ID, ""),course_id).enqueue(new Callback<CourseDetail>() {


            @Override
            public void onResponse(Call<CourseDetail> call, Response<CourseDetail> response) {

                progressDialog.dismiss();

                if (response.isSuccessful())
                {
                    if (response.body().getStatus().equalsIgnoreCase("Success"))
                    {
                        for (int i = 0; i < response.body().getCoursesInfo().get(0).getSectionInfo().size(); i++)
                        {
//                           listDataChild.put(listDataHeader.get(i), null); // Header, Child data
                            System.out.println("listdataname "+ response.body().getCoursesInfo().get(0).getSectionInfo().get(i).getSectionName());
                            listDataHeader.add(response.body().getCoursesInfo().get(0).getSectionInfo().get(i).getSectionName());
                            List<String> restData = new ArrayList<String>();
                            restData.add(response.body().getCoursesInfo().get(0).getSectionInfo().get(i).getSectionTitle());

                            listDataChild.put(listDataHeader.get(i), restData);
//                           Toast.makeText(CourseView_Activity.this, "1", Toast.LENGTH_SHORT).show();
                        }
                        listAdapter = new SectionExpandableAdapter(Section_Activity.this, listDataHeader, listDataChild);
                        expandableListView.setAdapter(listAdapter);
                    }
                    else {
                        Toast.makeText(Section_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {

                }
            }

            @Override
            public void onFailure(Call<CourseDetail> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Section_Activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
