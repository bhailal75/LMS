package com.example.bhoomi.lms.Student.Activty;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.claudiodegio.msv.BaseMaterialSearchView;
import com.claudiodegio.msv.MaterialSearchView;
import com.claudiodegio.msv.OnSearchViewListener;
import com.example.bhoomi.lms.APIModel.SearchBy.CourseInfo;
import com.example.bhoomi.lms.APIModel.SearchBy.SearchBy;
import com.example.bhoomi.lms.APIModel.StudentCourseList.MyCourseData;
import com.example.bhoomi.lms.APIModel.StudentCourseList.MyCourseResp;
import com.example.bhoomi.lms.APIModel.ViewQuiz.ViewQuizResp;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Retrofit.APIService;
import com.example.bhoomi.lms.Retrofit.APIUtils;
import com.example.bhoomi.lms.Student.Adapter.SearchAdapter;
import com.example.bhoomi.lms.Student.Adapter.StudentCoorseAdapter;
import com.example.bhoomi.lms.Student.Constants.ConstantData;
import com.example.bhoomi.lms.Student.Constants.MyMediumText;
import com.example.bhoomi.lms.Student.Constants.SharedPref;
import com.example.bhoomi.lms.Teacher.Activity.ViewQuiz_Activity;
import com.example.bhoomi.lms.Teacher.Adapter.View_Quiz_Adapter;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCourse_Activty extends AppCompatActivity implements View.OnClickListener, StudentCoorseAdapter.ViewCourseClickListner, OnSearchViewListener, GoogleApiClient.OnConnectionFailedListener {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private APIService apiService;
    private RecyclerView recyclerVew;
    private RecyclerView recyclerView_courses;
    private Toolbar toolbar_view_course;
    private ProgressDialog progressDialog;
    private MyMediumText textNoDataFound;
    private ArrayList<MyCourseData> listMyCourse;
    private StudentCoorseAdapter mAdapter;
    private BaseMaterialSearchView searchView;
    private String[] category = null;
    private String searchitem;
    private DrawerLayout mDrawer;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private TextView textViewProfile_nav, textViewUname_nav;
    private ImageView circleImageView_nav;
    private String profile_img, u_name, u_role_id;
    private GoogleApiClient mGoogleApiClient;
    private SharedPref session;

    private SearchAdapter mSearchAdapter;
    private RelativeLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_course_activty);

        toolbar_view_course = findViewById(R.id.toolbar_myCoures);
        session = new SharedPref(MyCourse_Activty.this);


        apiService = APIUtils.getAPIService();
        sharedPreferences = getSharedPreferences(ConstantData.PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        progressDialog = new ProgressDialog(MyCourse_Activty.this);
        listMyCourse = new ArrayList<>();

        searchView = (MaterialSearchView) findViewById(R.id.sv);
        searchView.setOnSearchViewListener(this);
        recyclerView_courses = findViewById(R.id.recyclerView_search);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        recyclerVew = findViewById(R.id.recyclerView_course);
        textNoDataFound = findViewById(R.id.txt_no_data_found);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerVew.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
        mAdapter = new StudentCoorseAdapter(this, listMyCourse, this);
        recyclerVew.setAdapter(mAdapter);

        category = getResources().getStringArray(R.array.category);
        toolbar_view_course = (Toolbar) findViewById(R.id.toolbar_myCoures);
        toolbar_view_course.setTitle("");

        setSupportActionBar(toolbar_view_course);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_view_course.setNavigationIcon(R.drawable.menu_icon);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView_courses.setLayoutManager(linearLayoutManager2); // set LayoutManager to RecyclerView

        mSearchAdapter = new SearchAdapter(this, new ArrayList<CourseInfo>(0));
        recyclerView_courses.setAdapter(mSearchAdapter);

        linearLayout = findViewById(R.id.llStudentCourse);
        recyclerView_courses.setVisibility(View.GONE);


//        Spinner navigationSpinner = new Spinner(getSupportActionBar().getThemedContext());
//
//        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, category) {
//
//            public View getView(int position, View convertView, ViewGroup parent) {
//                View v = super.getView(position, convertView, parent);
//
//                Typeface externalFont = Typeface.createFromAsset(getAssets(), "fonts/ubuntu_r.ttf");
//                ((TextView) v).setTypeface(externalFont);
//                ((TextView) v).setTextColor(getResources().getColor(android.R.color.white));
//
//                return v;
//            }
//
//
//            public View getDropDownView(int position, View convertView, ViewGroup parent) {
//                View v = super.getDropDownView(position, convertView, parent);
//
//                Typeface externalFont = Typeface.createFromAsset(getAssets(), "fonts/ubuntu_r.ttf");
//                ((TextView) v).setTypeface(externalFont);
//
//                return v;
//            }
//        };
//
//        navigationSpinner.setAdapter(aa);
//        toolbar_view_course.addView(navigationSpinner, 0);
//
//        navigationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MyCourse_Activty.this,
//                        "you selected: " + category[position],
//                        Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Setup drawer view
        nvDrawer = (NavigationView) findViewById(R.id.nvView);

        View view = nvDrawer.getHeaderView(0);
        nvDrawer.getMenu().getItem(0).setChecked(false);
        textViewUname_nav = (TextView) view.findViewById(R.id.txtUsername);
        circleImageView_nav = (ImageView) view.findViewById(R.id.profile_image);
        textViewUname_nav = (TextView) view.findViewById(R.id.txtUsername);
        circleImageView_nav = (ImageView) view.findViewById(R.id.profile_image);
        profile_img = sharedPreferences.getString(ConstantData.USER_IMAGE, "");
        u_name = sharedPreferences.getString(ConstantData.USER_NAME, "");

        if (profile_img.length() != 0) {
            Glide.with(this)
                    .load(profile_img)
                    .placeholder(R.drawable.profile_icon)
                    .into(circleImageView_nav);

        } else {
            circleImageView_nav.setImageResource(R.drawable.profile_icon);
        }

        if (u_name.length() != 0) {
            textViewUname_nav.setText(u_name);
        }

        nvDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                selectDrawerItem(menuItem);
                return true;
            }
        });
        drawerToggle = setupDrawerToggle();
        circleImageView_nav.setOnClickListener(this);
        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);
        nvDrawer.setItemIconTintList(null);
        getCoursedata();
    }

    private void getCoursedata() {
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();

        apiService.getMyCourse(sharedPreferences.getString(ConstantData.USER_ID,"")).enqueue(new Callback<MyCourseResp>() {
            @Override
            public void onResponse(Call<MyCourseResp> call, Response<MyCourseResp> response) {
                if (progressDialog != null)
                    progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("Success")) {
                        if (response.body().getMyCourse() != null && response.body().getMyCourse().size() > 0) {
                            recyclerVew.setVisibility(View.VISIBLE);
                            listMyCourse.addAll(response.body().getMyCourse());
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
            public void onFailure(Call<MyCourseResp> call, Throwable t) {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.action_filter_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter_search:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar_view_course, R.string.drawer_open, R.string.drawer_close);
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        switch (menuItem.getItemId()) {
            case R.id.featured:
                startActivity(new Intent(getApplicationContext(), Dashboard_Activity.class));
                finish();
                break;
            case R.id.my_courses:
                startActivity(new Intent(getApplicationContext(), MyCourse_Activty.class));
                finish();
                break;
            case R.id.categories:
                startActivity(new Intent(getApplicationContext(), Categories_Activity.class));
                finish();
                break;
            case R.id.wishlist:
                startActivity(new Intent(getApplicationContext(), WishList_Activity.class));
                finish();
                break;
            case R.id.celebrity:
                startActivity(new Intent(getApplicationContext(), Celebrity_activity.class));
//                finish();
                break;
            case R.id.settings:
                startActivity(new Intent(getApplicationContext(), Setting_Activity.class));
                finish();
                break;
            case R.id.signout:
                signOut();
                LoginManager.getInstance().logOut();
                session.logoutUser();
                finish();
                break;
            default:
        }
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }
    private void signOut() {

        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        updateUI(false);
                    }
                });
    }

    private void updateUI(boolean b) {
        if (b) {
        } else {
            session.logoutUser();
            sharedPreferences.edit().clear();
            finish();
        }
    }
    @Override
    public void onClick(View v) {
        if (v == circleImageView_nav) {
            startActivity(new Intent(getApplicationContext(), Profile_Activity.class));
        }
    }

    @Override
    public void onSearchViewShown() {
        if (linearLayout != null) {
            linearLayout.setVisibility(View.GONE);
            recyclerView_courses.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSearchViewClosed() {
        if (linearLayout != null) {
            linearLayout.setVisibility(View.VISIBLE);
            recyclerView_courses.setVisibility(View.GONE);
        }
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
//        progressDialog.setMessage(getString(R.string.please_wait));
//        progressDialog.show();
        apiService.searchCourse(s).enqueue(new Callback<SearchBy>() {
            @Override
            public void onResponse(Call<SearchBy> call, Response<SearchBy> response) {
//                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("Success")) {
                        if (response.body().getStatus().equalsIgnoreCase("Success")) {
                            mSearchAdapter.updateSearch(response.body().getCourseInfo());
                        } else {
                        }
                    } else {
                        Toast.makeText(MyCourse_Activty.this, R.string.no_data_found, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MyCourse_Activty.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SearchBy> call, Throwable t) {
//                progressDialog.dismiss();
                System.out.println("throwableDaat " + t.getMessage());
                Toast.makeText(MyCourse_Activty.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCourseClick(int pos) {
        Toast.makeText(this, "Open"+pos, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
