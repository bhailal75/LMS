package com.example.bhoomi.lms.Teacher.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.bhoomi.lms.APIModel.ViewQuiz.ViewQuizData;
import com.example.bhoomi.lms.APIModel.ViewQuiz.ViewQuizResp;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Retrofit.APIService;
import com.example.bhoomi.lms.Retrofit.APIUtils;
import com.example.bhoomi.lms.Student.Constants.ConstantData;
import com.example.bhoomi.lms.Student.Constants.MyMediumText;
import com.example.bhoomi.lms.Teacher.Adapter.View_Quiz_Adapter;
import com.example.bhoomi.lms.Teacher.Model.DeleteSectionResp;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewQuiz_Activity extends AppCompatActivity implements View_Quiz_Adapter.ViewQuizClickListner {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private List<ViewQuizData> listViewQuizLists;
    private APIService apiService;
    private int created_by = 1;
    private RecyclerView recyclerVew;
    private Toolbar toolbar_view_quiz;
    private MyMediumText textNoDataFound;
    private View_Quiz_Adapter mAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_quiz);

        toolbar_view_quiz = findViewById(R.id.toolbar_view_quiz);
        toolbar_view_quiz.setTitle("");
        toolbar_view_quiz.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar_view_quiz);
        toolbar_view_quiz.setNavigationIcon(R.drawable.ic_back);
        Configuration config = getApplicationContext().getResources().getConfiguration();
        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL)
            toolbar_view_quiz.getNavigationIcon().setAutoMirrored(true);
        toolbar_view_quiz.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        apiService = APIUtils.getAPIService();
        sharedPreferences = getSharedPreferences(ConstantData.PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        progressDialog = new ProgressDialog(ViewQuiz_Activity.this);
        listViewQuizLists = new ArrayList<>();
        recyclerVew = findViewById(R.id.recyclerView_quiz);
        textNoDataFound = findViewById(R.id.txt_no_data_found);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerVew.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
        mAdapter = new View_Quiz_Adapter(this, listViewQuizLists, this);
        recyclerVew.setAdapter(mAdapter);
        getQuizData();
    }
    private void getQuizData() {
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();
//        apiService.viewQuiz("62").enqueue(new Callback<ViewQuizResp>() {
        apiService.viewQuiz(sharedPreferences.getString(ConstantData.USER_ID,"")).enqueue(new Callback<ViewQuizResp>() {
            @Override
            public void onResponse(Call<ViewQuizResp> call, Response<ViewQuizResp> response) {
                if (progressDialog != null)
                    progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("Success")) {
                        if (response.body().getViewQuizData() != null && response.body().getViewQuizData().size() > 0) {
                            recyclerVew.setVisibility(View.VISIBLE);
                            listViewQuizLists.addAll(response.body().getViewQuizData());
                        } else {

                        }
                        mAdapter.notifyDataSetChanged();
                    }else{
                        recyclerVew.setVisibility(View.GONE);
                        textNoDataFound.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ViewQuizResp> call, Throwable t) {
                Log.i("TAG", "onFailure: "+t.getMessage());
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                recyclerVew.setVisibility(View.GONE);
                textNoDataFound.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onQuizClick(int pos) {
        Toast.makeText(this, "onclick", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onQuizDelete(int pos) {
        Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show();
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();
//        apiService.viewQuiz("62").enqueue(new Callback<ViewQuizResp>() {
        apiService.deleteQuiz(listViewQuizLists.get(pos).getTitleId()).enqueue(new Callback<DeleteSectionResp>() {
            @Override
            public void onResponse(Call<DeleteSectionResp> call, Response<DeleteSectionResp> response) {
                if (progressDialog != null)
                    progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("Success")) {
                        ViewQuiz_Activity.this.recreate();
                    } else {
                        Toast.makeText( ViewQuiz_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {

                }            }

            @Override
            public void onFailure(Call<DeleteSectionResp> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ViewQuiz_Activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
