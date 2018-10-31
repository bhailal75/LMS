package com.example.bhoomi.lms.Student.Activty;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.bhoomi.lms.APIModel.CourseDetails.CourseDetail;
import com.example.bhoomi.lms.APIModel.CourseDetails.RateInfo;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Retrofit.APIService;
import com.example.bhoomi.lms.Retrofit.APIUtils;
import com.example.bhoomi.lms.Student.Adapter.ReviewAdapter;
import com.example.bhoomi.lms.Student.Adapter.SectionExpandableAdapter;
import com.example.bhoomi.lms.Student.Adapter.SeeAllReviewAdapter;
import com.example.bhoomi.lms.Student.Constants.ConstantData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Review_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private Toolbar toolbar;

    private ProgressDialog progressDialog;
    private APIService apiService;
    private SeeAllReviewAdapter mReviewAdapter;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_);

        Intent intent = getIntent();
        apiService = APIUtils.getAPIService();
        progressDialog = new ProgressDialog(Review_Activity.this);
        sharedPreferences = getSharedPreferences(ConstantData.PREF_NAME, MODE_PRIVATE);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_review);
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
        recyclerView = findViewById(R.id.recyclerView_reviews);
        mReviewAdapter = new SeeAllReviewAdapter(this,new ArrayList<RateInfo>(0));
        recyclerView.setAdapter(mReviewAdapter);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager1); // set Lay
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
                        if (response.body().getRateInfo().size()==0)
                        {

                        }
                        else
                        {
                            mReviewAdapter.updateReview(response.body().getRateInfo());
                        }

                    }

                    else
                    {
                        Toast.makeText(Review_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {

                }
            }

            @Override
            public void onFailure(Call<CourseDetail> call, Throwable t) {

                progressDialog.dismiss();
                Toast.makeText(Review_Activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
