package com.example.bhoomi.lms.Student.Activty;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.claudiodegio.msv.BaseMaterialSearchView;
import com.claudiodegio.msv.MaterialSearchView;
import com.claudiodegio.msv.OnSearchViewListener;
import com.example.bhoomi.lms.APIModel.SearchBy.CourseInfo;
import com.example.bhoomi.lms.APIModel.SearchBy.SearchBy;
import com.example.bhoomi.lms.APIModel.SubCategories.PopularCourse;
import com.example.bhoomi.lms.APIModel.SubCategories.SubCategories;
import com.example.bhoomi.lms.APIModel.SubCategories.SubCategoryInfo;
import com.example.bhoomi.lms.APIModel.SubcategoryList.SubcatList;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Retrofit.APIService;
import com.example.bhoomi.lms.Retrofit.APIUtils;
import com.example.bhoomi.lms.Student.Adapter.CategoriesAdapter;
import com.example.bhoomi.lms.Student.Adapter.CourseAdapter;
import com.example.bhoomi.lms.Student.Adapter.SearchAdapter;
import com.example.bhoomi.lms.Student.Constants.MyMediumText;
import com.example.bhoomi.lms.Student.Model.CategoryModel;
import com.example.bhoomi.lms.Student.Model.CourseModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeeAllCourse_Activity extends AppCompatActivity implements OnSearchViewListener {

    private RecyclerView recyclerView_dev, recyclerView_categories;
    private CourseAdapter mAdapter;
    private CategoriesAdapter mCategoryAdapter;
    private ArrayList<CourseModel> courseModelArrayList;
    private ArrayList<CategoryModel> categoryModelArrayList;
    private CourseModel courseModel;
    private CategoryModel categoryModel;
    private Toolbar toolbar_devcourse;
    private TextView textView_popularCourse, textView_browseCat,noDataFound;
    private APIService apiService;
    private ProgressDialog progressDialog;
    private String category_id;
    private MyMediumText toolbar_title;
    private BaseMaterialSearchView searchView;
    private String searchitem;
    private RecyclerView recyclerView_courses;
    private SearchAdapter mSearchAdapter;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_development_course_);

        apiService = APIUtils.getAPIService();
        progressDialog = new ProgressDialog(SeeAllCourse_Activity.this);
        Intent intent = getIntent();
        category_id = intent.getStringExtra("cat_id");

        toolbar_devcourse = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_dev_course);
        toolbar_devcourse.setTitle("");
        toolbar_devcourse.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar_devcourse);

        searchView = (MaterialSearchView) findViewById(R.id.sv);
        Configuration config = getApplicationContext().getResources().getConfiguration();
        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            searchView.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            searchView.setTextDirection(View.TEXT_DIRECTION_RTL);
//            searchView.setTextDirection(View.TEXT_DIRECTION_RTL);
        }
        searchView.setOnSearchViewListener(this); // this class implements OnSearchViewListener
        toolbar_devcourse.setNavigationIcon(R.drawable.ic_back);
        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL)
            toolbar_devcourse.getNavigationIcon().setAutoMirrored(true);
        toolbar_devcourse.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(intent.getStringExtra("cat_name"));


        recyclerView_dev = findViewById(R.id.recyclerView_dev);
        recyclerView_categories = findViewById(R.id.recyclerView_category);
        recyclerView_courses = findViewById(R.id.recyclerView_search);

        textView_browseCat = findViewById(R.id.textview_browseCategory);
        textView_popularCourse = findViewById(R.id.textview_popularCourse);
        noDataFound = findViewById(R.id.no_data_found);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView_dev.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView_categories.setLayoutManager(linearLayoutManager1); // set LayoutManager to RecyclerView
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView_courses.setLayoutManager(linearLayoutManager2); // set LayoutManager to RecyclerView

        courseModel = new CourseModel();
        categoryModel = new CategoryModel();

        courseModelArrayList = new ArrayList<>();
        categoryModelArrayList = new ArrayList<>();

        for (int i = 0; i < MyData.drawableArray.length; i++) {
            courseModelArrayList.add(new CourseModel(MyData.drawableArray[i]));
        }

        mAdapter = new CourseAdapter(this, new ArrayList<PopularCourse>(0));
        recyclerView_dev.setAdapter(mAdapter);

        mCategoryAdapter = new CategoriesAdapter(this, new ArrayList<SubCategoryInfo>(0));
        recyclerView_categories.setAdapter(mCategoryAdapter);

        mSearchAdapter = new SearchAdapter(this, new ArrayList<CourseInfo>(0));
        recyclerView_courses.setAdapter(mSearchAdapter);

        linearLayout = findViewById(R.id.ll_main);


        Typeface typeface_bold = Typeface.createFromAsset(getAssets(), "fonts/ubuntu_b.ttf");
        textView_popularCourse.setTypeface(typeface_bold);
        textView_browseCat.setTypeface(typeface_bold);

//        String dev = "Popular";
//        String browse = "Browse";
//
//        String dev_next = "<font color='#02b3e4'><b>Courses</b></font>";
//        String browse_next = "<font color='#de93e5'><b>Subcategories</b></font>";
//
//
//        textView_popularCourse.setText(Html.fromHtml(dev + " " + dev_next));
//        textView_browseCat.setText(Html.fromHtml(browse + " " + browse_next));
        textView_browseCat.setVisibility(View.GONE);
        textView_popularCourse.setVisibility(View.GONE);

        doGetSubCategories();
    }

    private void doGetSubCategories() {
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();

        apiService.getSubCategories(category_id).enqueue(new Callback<SubCategories>() {
            @Override
            public void onResponse(Call<SubCategories> call, Response<SubCategories> response) {

                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getPopularCourses() != null && response.body().getPopularCourses().size() > 0) {
                        noDataFound.setVisibility(View.GONE);
                        textView_popularCourse.setVisibility(View.VISIBLE);
                        mAdapter.updateSubCategories(response.body().getPopularCourses());
                    }
                    if (response.body().getSubCategoryInfo() != null && response.body().getSubCategoryInfo().size() > 0) {
                        noDataFound.setVisibility(View.GONE);
                        textView_browseCat.setVisibility(View.VISIBLE);
                        mCategoryAdapter.updateSubCategories(response.body().getSubCategoryInfo());
                    }else{
                        noDataFound.setVisibility(View.VISIBLE);
                        Toast.makeText(SeeAllCourse_Activity.this, response.message(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    noDataFound.setVisibility(View.VISIBLE);
                    Toast.makeText(SeeAllCourse_Activity.this, response.message(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<SubCategories> call, Throwable t) {
                progressDialog.dismiss();
                noDataFound.setVisibility(View.VISIBLE);
                System.out.println("throwableDaat " + t.getMessage());
                Toast.makeText(SeeAllCourse_Activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onSearchViewShown() {
        linearLayout.setVisibility(View.GONE);
        recyclerView_courses.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSearchViewClosed() {
        linearLayout.setVisibility(View.VISIBLE);
        recyclerView_courses.setVisibility(View.GONE);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public void onQueryTextChange(String s) {
        searchitem = s;
        doGetSubCatList(s);
    }

    private void doGetSubCatList(String s) {

        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();

        apiService.searchCourse(s).enqueue(new Callback<SearchBy>() {
            @Override
            public void onResponse(Call<SearchBy> call, Response<SearchBy> response) {

                progressDialog.dismiss();
                if (response.isSuccessful()) {

                    if (response.body().getStatus().equalsIgnoreCase("Success")) {

                        if (response.body().getStatus().equalsIgnoreCase("Success")) {
                            mSearchAdapter.updateSearch(response.body().getCourseInfo());
                        } else {

                        }


                    } else {
                        Toast.makeText(SeeAllCourse_Activity.this, R.string.no_data_found, Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(SeeAllCourse_Activity.this, response.message(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<SearchBy> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println("throwableDaat " + t.getMessage());
                Toast.makeText(SeeAllCourse_Activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem item = menu.findItem(R.id.action_filter_search);
        searchView.setMenuItem(item);

        return true;
    }


}
