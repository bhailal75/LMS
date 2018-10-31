package com.example.bhoomi.lms.Teacher.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.bhoomi.lms.APIModel.LiveClass.LiveClassData;
import com.example.bhoomi.lms.APIModel.LiveClass.LiveClassResp;
import com.example.bhoomi.lms.APIModel.ViewQuiz.ViewQuizData;
import com.example.bhoomi.lms.APIModel.ViewQuiz.ViewQuizResp;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Retrofit.APIService;
import com.example.bhoomi.lms.Retrofit.APIUtils;
import com.example.bhoomi.lms.Student.Activty.WebDevelopment_Activity;
import com.example.bhoomi.lms.Student.Constants.ConstantData;
import com.example.bhoomi.lms.Student.Constants.MyMediumText;
import com.example.bhoomi.lms.Teacher.Adapter.Live_Class_Adapter;
import com.example.bhoomi.lms.Teacher.Adapter.View_Quiz_Adapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveClass_Activity extends AppCompatActivity implements Live_Class_Adapter.ViewLiveClassClickListner {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private List<LiveClassData> listLiveClassLists;
    private APIService apiService;
    private int created_by = 1;
    private RecyclerView recyclerVew;
    private Toolbar toolbar_live_class;
    private MyMediumText textNoDataFound;
    private Live_Class_Adapter mAdapter;
    private ProgressDialog progressDialog;
    private LinearLayoutManager linearLayoutManager;
    private boolean isLoading;
    private boolean isMore = true;
    private int totalCount;
    private int offset = 0, limit = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_class);

        toolbar_live_class = findViewById(R.id.toolbar_live_class);
        toolbar_live_class.setTitle("");
        toolbar_live_class.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar_live_class);
        toolbar_live_class.setNavigationIcon(R.drawable.ic_back);
        Configuration config = getApplicationContext().getResources().getConfiguration();
        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL)
            toolbar_live_class.getNavigationIcon().setAutoMirrored(true);
        toolbar_live_class.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        apiService = APIUtils.getAPIService();
        sharedPreferences = getSharedPreferences(ConstantData.PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        progressDialog = new ProgressDialog(LiveClass_Activity.this);
        listLiveClassLists = new ArrayList<>();
        recyclerVew = findViewById(R.id.recyclerView_liveclass);
        textNoDataFound = findViewById(R.id.txt_no_data_found);

        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerVew.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
        mAdapter = new Live_Class_Adapter(this, listLiveClassLists, this);
        recyclerVew.setAdapter(mAdapter);
        getLiveClassData();
        pagination();
    }

    private void pagination() {
        recyclerVew.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

                if (!isLoading) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= (listLiveClassLists.size()) && isMore && listLiveClassLists.size() <= totalCount) {
                        offset += limit;
                        isLoading = true;
                        getLiveClassData();
                    }
                }
            }
        });
    }

    private void getLiveClassData() {
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();
        apiService.liveClass(sharedPreferences.getString(ConstantData.USER_ID,""),offset).enqueue(new Callback<LiveClassResp>() {
            @Override
            public void onResponse(Call<LiveClassResp> call, Response<LiveClassResp> response) {

                if (progressDialog != null)
                    progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("Success")) {
                        if (response.body().getLiveClassData() != null && response.body().getLiveClassData().size() > 0) {
                            recyclerVew.setVisibility(View.VISIBLE);
                            totalCount = response.body().getLiveClassData().size();
                            listLiveClassLists.addAll(response.body().getLiveClassData());
                        } else {
                            recyclerVew.setVisibility(View.GONE);
                            textNoDataFound.setVisibility(View.VISIBLE);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<LiveClassResp> call, Throwable t) {
                Log.i("TAG", "onFailure: "+t.getMessage());
                isMore = false;
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                recyclerVew.setVisibility(View.GONE);
                textNoDataFound.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        isMore = false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_addqa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_plus:
                startActivity(new Intent(this,Schedule_LivaClass_Activity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLiveClassClick(int pos) {
        Intent intent = new Intent(this, WebViewClassActivity.class);
        intent.putExtra("class_id", ""+ listLiveClassLists.get(pos).getClassId());
        intent.putExtra("course_name",listLiveClassLists.get(pos).getKeywords());
        intent.putExtra("lession",listLiveClassLists.get(pos).getClassTitle());
//        intent.putExtra("cat_id", dataSet.get(i).getId());
//        Configuration config = getResources().getConfiguration();
//        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL)
//            intent.putExtra("cat_name", dataSet.get(i).getSubCatNameAr());
//        else
//            intent.putExtra("cat_name", dataSet.get(i).getSubCatName());
        startActivity(intent);
    }
}
