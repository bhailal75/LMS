package com.example.bhoomi.lms.Student.Activty;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bhoomi.lms.APIModel.Celebrity.CelebrityData;
import com.example.bhoomi.lms.APIModel.Celebrity.CelebrityResp;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Retrofit.APIService;
import com.example.bhoomi.lms.Retrofit.APIUtils;
import com.example.bhoomi.lms.Student.Adapter.CelebrityAdapter;
import com.example.bhoomi.lms.Student.Fragment.CelebrityDetailFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Celebrity_activity extends AppCompatActivity implements CelebrityAdapter.ViewCelebrityClick {
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView_celebrity;
    private ArrayList<CelebrityData> celebrityArayList;
    private TextView txtNodataFound;
//    private LinearLayout ll_celebrity_frame;
    private Toolbar toolbar;
    private ProgressDialog progressDialog;
    private CelebrityAdapter mAdapter;
    private APIService apiService;
    private boolean isLoading;
    private boolean isMore = true;
    private int totalCount;
    private int offset = 0, limit = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_celebrity);
        apiService = APIUtils.getAPIService();
        progressDialog = new ProgressDialog(Celebrity_activity.this);
        toolbar = findViewById(R.id.toolbar_celebrity);
        txtNodataFound = findViewById(R.id.no_data_found_celebrity);
        recyclerView_celebrity = findViewById(R.id.recycler_celebrity);
//        ll_celebrity_frame = findViewById(R.id.ll_celebrity_frame);
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


        celebrityArayList = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView_celebrity.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
        celebrityArayList = new ArrayList<>();
        mAdapter = new CelebrityAdapter(this, celebrityArayList, this);
        recyclerView_celebrity.setAdapter(mAdapter);

        getCelebrityData();
        pagination();
    }

    private void pagination() {
        recyclerView_celebrity.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                            && totalItemCount >= (celebrityArayList.size()) && isMore && celebrityArayList.size() <= totalCount) {
                        offset += limit;
                        isLoading = true;
                        getCelebrityData();
                    }
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        isMore = false;
    }

    private void getCelebrityData() {
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();

        apiService.getCelebrity(offset).enqueue(new Callback<CelebrityResp>() {
            @Override
            public void onResponse(Call<CelebrityResp> call, Response<CelebrityResp> response) {
                if (progressDialog != null)
                    progressDialog.dismiss();

                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("Success")) {
                        isLoading = false;
                        if (response.body().getCelebrityInfo() != null && response.body().getCelebrityInfo().size() > 0) {
                            recyclerView_celebrity.setVisibility(View.VISIBLE);
                            totalCount = response.body().getCelebrityInfo().size();
                            celebrityArayList.addAll(response.body().getCelebrityInfo());
                        } else {

                        }
                        mAdapter.notifyItemInserted(mAdapter.getItemCount());
                    } else {
//                        recyclerView_celebrity.setVisibility(View.GONE);
//                        txtNodataFound.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<CelebrityResp> call, Throwable t) {
                Log.i("TAG", "onFailure: " + t.getMessage());
                isMore = false;
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                recyclerView_celebrity.setVisibility(View.GONE);
                txtNodataFound.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onCelebrityClick(int pos) {
        CelebrityDetailFragment celebrityDetailFragment = new CelebrityDetailFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.ll_celebrity_frame, new CelebrityDetailFragment(celebrityArayList.get(pos)),celebrityDetailFragment.getClass().getSimpleName());
        ft.addToBackStack(celebrityDetailFragment.getClass().getSimpleName());
        ft.commit();
        Toast.makeText(this, "onClick", Toast.LENGTH_SHORT).show();
    }
}
