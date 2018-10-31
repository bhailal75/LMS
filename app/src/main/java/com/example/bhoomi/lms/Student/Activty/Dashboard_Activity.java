package com.example.bhoomi.lms.Student.Activty;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.claudiodegio.msv.BaseMaterialSearchView;
import com.claudiodegio.msv.MaterialSearchView;
import com.claudiodegio.msv.OnSearchViewListener;
import com.example.bhoomi.lms.APIModel.Categories.Categories;
import com.example.bhoomi.lms.APIModel.Categories.HomeInfo;
import com.example.bhoomi.lms.APIModel.SearchBy.CourseInfo;
import com.example.bhoomi.lms.APIModel.SearchBy.SearchBy;
import com.example.bhoomi.lms.APIModel.Slider.SliderData;
import com.example.bhoomi.lms.APIModel.Slider.SliderResp;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Retrofit.APIService;
import com.example.bhoomi.lms.Retrofit.APIUtils;
import com.example.bhoomi.lms.Student.Adapter.HomeAdapter;
import com.example.bhoomi.lms.Student.Adapter.SearchAdapter;
import com.example.bhoomi.lms.Student.Adapter.SliderAdapter;
import com.example.bhoomi.lms.Student.Constants.ConstantData;
import com.example.bhoomi.lms.Student.Constants.CustomTypefaceSpan;
import com.example.bhoomi.lms.Student.Constants.SharedPref;
import com.example.bhoomi.lms.Student.Model.CourseModel;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Dashboard_Activity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener, OnSearchViewListener {

    private static final int PERMISSION_REQUEST_CODE = 200;
    private static final int REQUEST_PERMISSION_SETTING = 1;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private Toolbar toolbar_dashboard;
    private DrawerLayout mDrawer;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private GoogleApiClient mGoogleApiClient;
    private TextView textViewProfile_nav, textViewUname_nav;
    private ImageView circleImageView_nav;
    private RecyclerView recyclerView_dev, recyclerView_business, recyclerView_design, recyclerView_health;
    private TextView textView_dev, textView_business, textView_design, textView_health;
    private TextView textView_Viewdev, textView_Viewbusiness, textView_Viewdesign, textView_Viewhealth;
    private HomeAdapter mAdapter;
    private SliderAdapter sliderAdapter;
    private ArrayList<CourseModel> courseModelArrayList;
    private CourseModel courseModel;
    private ArrayList<SliderData> sliderArray;
    private SharedPref session;
    private APIService apiService;
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView_home;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String profile_img, u_name, u_role_id;
    private BaseMaterialSearchView searchView;
    private String searchitem;
    private RecyclerView recyclerView_courses;
    private SearchAdapter mSearchAdapter;
    private LinearLayout linearLayout;
    private AVLoadingIndicatorView progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_);

        sharedPreferences = getSharedPreferences(ConstantData.PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        apiService = APIUtils.getAPIService();
        progressDialog = new ProgressDialog(Dashboard_Activity.this);
        if (!checkPermission()) {
            requestPermission();
        }
        searchView = (MaterialSearchView) findViewById(R.id.sv);
        Configuration config = getApplicationContext().getResources().getConfiguration();
        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            searchView.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            searchView.setTextDirection(View.TEXT_DIRECTION_RTL);

//            searchView.setTextDirection(View.TEXT_DIRECTION_RTL);
        }
        searchView.setOnSearchViewListener(this); // this class implements OnSearchViewListener
        session = new SharedPref(Dashboard_Activity.this);
        session.checkLogin();

//        u_role_id = sharedPreferences.getString(ConstantData.U_ROLEID,"");

//        if (u_role_id.equalsIgnoreCase("4"))
//        {
//            startActivity(new Intent(getApplicationContext(), TutorDahboard.class));
//            finish();
//        }
//        else
//        {
//
//        }
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        init();

        toolbar_dashboard = (Toolbar) findViewById(R.id.toolbar_dashboard);
        toolbar_dashboard.setTitle("");
        setSupportActionBar(toolbar_dashboard);
        toolbar_dashboard.setNavigationIcon(R.drawable.menu_icon);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // Setup drawer view
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        Menu m = nvDrawer.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);
            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }
            //the method we have create in activity
            applyFontToMenuItem(mi);
        }
        View view = nvDrawer.getHeaderView(0);
        nvDrawer.getMenu().getItem(0).setChecked(false);
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
        mDrawer.addDrawerListener(drawerToggle);
        nvDrawer.setItemIconTintList(null);
        recyclerView_home = findViewById(R.id.recyclerView_home);
        progress = findViewById(R.id.progress_recycler);
        recyclerView_courses = findViewById(R.id.recyclerView_searchcourses);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView_home.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView_courses.setLayoutManager(linearLayoutManager1);
        courseModel = new CourseModel();
        courseModelArrayList = new ArrayList<>();
        mAdapter = new HomeAdapter(this, new ArrayList<HomeInfo>());
        recyclerView_home.setAdapter(mAdapter);
        mSearchAdapter = new SearchAdapter(this, new ArrayList<CourseInfo>(0));
        recyclerView_courses.setAdapter(mSearchAdapter);
        linearLayout = findViewById(R.id.ll_main);

        doGetCategory();
    }

    private void doGetCategory() {
//        progressDialog.setMessage(getString(R.string.please_wait));
//        progressDialog.show();
//        recyclerView_home.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
        apiService.getHomeData(sharedPreferences.getString(ConstantData.USER_ID,"")).enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("Success")) {
                        progress.setVisibility(View.GONE);
//                        recyclerView_home.setVisibility(View.VISIBLE);
                        mAdapter.updateSubCategories(response.body().getHomeInfo());
//                        progressDialog.dismiss();
                    } else {
                        progress.setVisibility(View.GONE);
//                        progressDialog.dismiss();
                    }
                } else {
                    progress.setVisibility(View.GONE);
//                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                progress.setVisibility(View.GONE);
//                recyclerView_home.setVisibility(View.GONE);
//                progressDialog.dismiss();
                System.out.println("throwableDaat " + t.getMessage());
                Toast.makeText(Dashboard_Activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar_dashboard, R.string.drawer_open, R.string.drawer_close);
    }

    public void selectDrawerItem(MenuItem menuItem) {
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
        setTitle(menuItem.getTitle());
        mDrawer.closeDrawers();
    }

    private void init() {
        sliderArray = new ArrayList<>();
        mPager = (ViewPager) findViewById(R.id.pager);
        sliderAdapter = new SliderAdapter(Dashboard_Activity.this, sliderArray);
        mPager.setAdapter(sliderAdapter);

        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();
        apiService.getSliderData().enqueue(new Callback<SliderResp>() {
            @Override
            public void onResponse(Call<SliderResp> call, Response<SliderResp> response) {
                if (response.isSuccessful()) {
                    if (progressDialog != null)
                        progressDialog.dismiss();
                    if (response.body().getStatus().equalsIgnoreCase("Success")) {
                        if (response.body().getSliderData() != null && response.body().getSliderData().size() > 0) {
                            sliderArray.addAll(response.body().getSliderData());
                        }
                        sliderAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<SliderResp> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mPager);
        sliderAdapter.registerDataSetObserver(indicator.getDataSetObserver());
        // Auto start of viewpager
        if (sliderArray != null) {
            final Handler handler = new Handler();
            final Runnable Update = new Runnable() {
                public void run() {
                    if (currentPage == sliderArray.size()) {
                        currentPage = 0;
                    }
                    mPager.setCurrentItem(currentPage++, true);
                }
            };
            Timer swipeTimer = new Timer();
            swipeTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, 2500, 2500);


        }
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
                linearLayout.setVisibility(View.GONE);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v == textView_Viewdev) {
            Intent intent = new Intent(getApplicationContext(), SeeAllCourse_Activity.class);
            intent.putExtra("Type", "Development");
            startActivity(intent);
        } else if (v == textView_Viewbusiness) {
            Intent intent = new Intent(getApplicationContext(), SeeAllCourse_Activity.class);
            intent.putExtra("Type", "Business");
            startActivity(intent);
        } else if (v == textView_Viewhealth) {
            Intent intent = new Intent(getApplicationContext(), SeeAllCourse_Activity.class);
            intent.putExtra("Type", "Health");
            startActivity(intent);
        } else if (v == textView_Viewdesign) {
            Intent intent = new Intent(getApplicationContext(), SeeAllCourse_Activity.class);
            intent.putExtra("Type", "Design");
            startActivity(intent);
        } else if (v == circleImageView_nav) {
            startActivity(new Intent(getApplicationContext(), Profile_Activity.class));
        }
    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE, READ_PHONE_STATE, CAMERA, RECORD_AUDIO}, PERMISSION_REQUEST_CODE);
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_PHONE_STATE);
        int result3 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int result4 = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_DENIED && result3 == PackageManager.PERMISSION_GRANTED && result4 == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean writeExternalAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean readExternalAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean readPhnStateAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    boolean recordAudioAccepted = grantResults[4] == PackageManager.PERMISSION_GRANTED;
                    if (writeExternalAccepted && readExternalAccepted && readPhnStateAccepted && cameraAccepted && recordAudioAccepted) {
                        Log.d("TAG", "PERMISSION GRANTED");
                    } else {
                        Toast.makeText(this, "Permission Denied.", Toast.LENGTH_SHORT).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE) || shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE) || shouldShowRequestPermissionRationale(READ_PHONE_STATE) && shouldShowRequestPermissionRationale(CAMERA) || shouldShowRequestPermissionRationale(RECORD_AUDIO)) {
                                showMessageOKCancel("To perform this application you must allow to access all the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE, READ_PHONE_STATE, CAMERA, RECORD_AUDIO},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String s, DialogInterface.OnClickListener onClickListener) {

        new AlertDialog.Builder(Dashboard_Activity.this)
                .setMessage(s)
                .setPositiveButton("OK", onClickListener)
                .setNegativeButton("Go to Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                    }
                })
                .create()
                .show();
    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/ubuntu_m.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
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
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onSearchViewShown() {
        if(linearLayout != null) {
            linearLayout.setVisibility(View.GONE);
            recyclerView_home.setVisibility(View.GONE);
            recyclerView_courses.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSearchViewClosed() {
        if (linearLayout != null) {
            recyclerView_courses.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
            recyclerView_home.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public void onQueryTextChange(String s) {
        searchitem = s;
        doGetList(s);
    }

    private void doGetList(String s) {
        apiService.searchCourse(s).enqueue(new Callback<SearchBy>() {
            @Override
            public void onResponse(Call<SearchBy> call, Response<SearchBy> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("Success")) {
                        mSearchAdapter.updateSearch(response.body().getCourseInfo());
                    } else {
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<SearchBy> call, Throwable t) {
//                progressDialog.dismiss();
                System.out.println("throwableDaat " + t.getMessage());
                Toast.makeText(Dashboard_Activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
