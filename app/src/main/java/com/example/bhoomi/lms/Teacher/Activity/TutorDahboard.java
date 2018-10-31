package com.example.bhoomi.lms.Teacher.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bhoomi.lms.APIModel.Dashbourd.DashbourdResp;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Retrofit.APIService;
import com.example.bhoomi.lms.Retrofit.APIUtils;
import com.example.bhoomi.lms.Student.Activty.Categories_Activity;
import com.example.bhoomi.lms.Student.Activty.Featured_Activity;
import com.example.bhoomi.lms.Student.Activty.Profile_Activity;
import com.example.bhoomi.lms.Student.Activty.Setting_Activity;
//import com.example.bhoomi.lms.Student.Activty.Splash_Activity;
import com.example.bhoomi.lms.Student.Activty.WishList_Activity;
import com.example.bhoomi.lms.Student.Constants.ConstantData;
import com.example.bhoomi.lms.Student.Constants.CustomTypefaceSpan;
import com.example.bhoomi.lms.Student.Constants.MyMediumText;
import com.example.bhoomi.lms.Student.Constants.SharedPref;
import com.example.bhoomi.lms.Teacher.Adapter.DrawerExpnadableAdapter;
import com.example.bhoomi.lms.Teacher.Model.NavigationModel;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TutorDahboard extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    DisplayMetrics metrics;
    int width;
    private LinearLayout linearLayout_total_quiz, linearLayout_participants, linearLayout_totalcourse, linearLayout_createCoures,
            linearLayout_createcategories, linearLayout_createquiz, linearLayout_liveclass;
    private ArrayList<String> listDataHeader;
    private ArrayList<String> listDataHeaderIcons;
    private HashMap<String, List<String>> listDataChild;
    private ExpandableListView expListView;
    private DrawerExpnadableAdapter listAdapter;
    private Toolbar toolbar_dashboard;
    private DrawerLayout mDrawer;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private TextView textViewUname_nav;
    private MyMediumText txtCourse, txtQuize, txtparticipants;
    private ImageView circleImageView_nav;
    private ArrayList<NavigationModel> navigationModelArrayList;
    private NavigationModel navigationModel;
    private SharedPref session;
    private GoogleApiClient mGoogleApiClient;
    private String profile_img, u_name, u_role_id;
//    private String[] dataArray = {"Course Control", "Quiz Control", "Categories", "Live Class", "Contact Admin", "Sign out"};
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_tutor_dashboard);

        sharedPreferences = getSharedPreferences(ConstantData.PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        session = new SharedPref(TutorDahboard.this);
//        session.checkLogin();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        apiService = APIUtils.getAPIService();
        linearLayout_total_quiz = findViewById(R.id.ll_quiz);
        linearLayout_participants = findViewById(R.id.ll_participants);
        linearLayout_totalcourse = findViewById(R.id.ll_course);
        linearLayout_createCoures = findViewById(R.id.ll_createcourse);
        linearLayout_createcategories = findViewById(R.id.ll_categories);
        linearLayout_createquiz = findViewById(R.id.ll_createquiz);
        linearLayout_liveclass = findViewById(R.id.ll_liveclass);
        txtQuize = findViewById(R.id.text_total_quize);
        txtparticipants = findViewById(R.id.text_total_participants);
        txtCourse = findViewById(R.id.text_total_course);
        linearLayout_total_quiz.setOnClickListener(this);
        linearLayout_participants.setOnClickListener(this);
        linearLayout_totalcourse.setOnClickListener(this);
        linearLayout_createCoures.setOnClickListener(this);
        linearLayout_createcategories.setOnClickListener(this);
        linearLayout_createquiz.setOnClickListener(this);
        linearLayout_liveclass.setOnClickListener(this);
        toolbar_dashboard = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_dashboard);
        toolbar_dashboard.setTitle("");
        setSupportActionBar(toolbar_dashboard);
        toolbar_dashboard.setNavigationIcon(R.drawable.menu_icon);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // Setup drawer view
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        View view = nvDrawer.getHeaderView(0);
        textViewUname_nav = (TextView) view.findViewById(R.id.txtUsername);
        circleImageView_nav = (ImageView) view.findViewById(R.id.profile_image);
        profile_img = sharedPreferences.getString(ConstantData.USER_IMAGE, "");
        u_name = sharedPreferences.getString(ConstantData.USER_NAME, "");
        System.out.println("user_img " + profile_img);

        if (profile_img.length() != 0) {
            Glide.with(this)
                    .load(profile_img)
                    .placeholder(R.drawable.profile_icon)
                    .into(circleImageView_nav);
        }

        if (u_name.length() != 0) {
            textViewUname_nav.setText(u_name);
        }

        drawerToggle = setupDrawerToggle();
        circleImageView_nav.setOnClickListener(this);
        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);
        nvDrawer.setItemIconTintList(null);
        dashbourdAPI();
        enableExpandableList();
    }

    private void dashbourdAPI() {
        apiService.getDashbourd(sharedPreferences.getString(ConstantData.USER_ID,"")).enqueue(new Callback<DashbourdResp>() {
            @Override
            public void onResponse(Call<DashbourdResp> call, Response<DashbourdResp> response) {
                if (response.isSuccessful()) {
                    if (response.body().getDashbourdData() != null) {
                        txtQuize.setText(""+response.body().getDashbourdData().getQuizCount());
                        if (response.body().getDashbourdData().getParticipantsCount() != null)
                            txtparticipants.setText(""+response.body().getDashbourdData().getParticipantsCount());
                        txtCourse.setText(""+response.body().getDashbourdData().getCourseCount());
                    }
                }
            }

            @Override
            public void onFailure(Call<DashbourdResp> call, Throwable t) {
                Log.i("TAG", "onFailure: "+t.getMessage());
            }
        });
    }

    private void enableExpandableList() {
        listDataHeader = new ArrayList<String>();
        navigationModelArrayList = new ArrayList<>();
        navigationModel = new NavigationModel();
        listDataChild = new HashMap<String, List<String>>();
        expListView = (ExpandableListView) findViewById(R.id.left_drawer);
        prepareListData(navigationModelArrayList, listDataHeader, listDataChild);
        listAdapter = new DrawerExpnadableAdapter(this, listDataHeader, listDataChild);
        // setting list adapter
        expListView.setAdapter(listAdapter);

        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;

        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

                if (groupPosition == 3) {
                    startActivity(new Intent(getApplicationContext(), LiveClass_Activity.class));
                }
                if (groupPosition == 4) {
                    startActivity(new Intent(getApplicationContext(), ContactAdmin_Activity.class));
                }
                if (groupPosition == 5) {
                    signOut();
                    LoginManager.getInstance().logOut();
                    session.logoutUser();
                    finish();
                }
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                // Temporary code:

                if (groupPosition == 0) {
                    if (childPosition == 0) {
                        startActivity(new Intent(getApplicationContext(), ViewCourse_Activity.class));
                    } else {
                        startActivity(new Intent(getApplicationContext(), CreateCourse_Activity.class));
                    }
                } else if (groupPosition == 1) {
                    if (childPosition == 0) {
                        startActivity(new Intent(TutorDahboard.this, ViewQuiz_Activity.class));
                    } else if (childPosition == 1) {
                        startActivity(new Intent(TutorDahboard.this, CreateQuiz_Activity.class));
                    } else {
                        Toast.makeText(TutorDahboard.this, "View Results", Toast.LENGTH_SHORT).show();

                    }
                } else if (groupPosition == 2) {
                    if (childPosition == 0) {
                        startActivity(new Intent(getApplicationContext(), CreateCategories_Activity.class));
                    } else if (childPosition == 1) {
                        startActivity(new Intent(getApplicationContext(), CreateSubCategories_Activity.class));
                    } else if (childPosition == 2) {
                        startActivity(new Intent(getApplicationContext(), AddSubCategory.class));
                    }
                }

                return false;
            }
        });
    }

    private void prepareListData(ArrayList<NavigationModel> navigationModelArrayList, ArrayList<String> listDataHeader, HashMap<String, List<String>> listDataChild) {

        listDataHeader.add(getString(R.string.course_control));
        listDataHeader.add(getString(R.string.quiz_control));
        listDataHeader.add(getString(R.string.categories));
        listDataHeader.add(getString(R.string.live_class));
        listDataHeader.add(getString(R.string.contact_admin));
        listDataHeader.add(getString(R.string.sign_out));

        // Adding child data
        List<String> top = new ArrayList<String>();
        top.add(getString(R.string.view_course));
        top.add(getString(R.string.create_course));

        List<String> mid = new ArrayList<String>();
        mid.add(getString(R.string.view_quiz));
        mid.add(getString(R.string.create_quiz));
        mid.add(getString(R.string.view_result));

        List<String> bottom = new ArrayList<String>();
        bottom.add(getString(R.string.view_categories));
        bottom.add(getString(R.string.view_sub_categories));
        bottom.add(getString(R.string.create_sub_categories));

        List<String> third = new ArrayList<String>();


        listDataChild.put(listDataHeader.get(0), top); // Header, Child data
        listDataChild.put(listDataHeader.get(1), mid);
        listDataChild.put(listDataHeader.get(2), bottom);
        listDataChild.put(listDataHeader.get(3), third);
        listDataChild.put(listDataHeader.get(4), third);
        listDataChild.put(listDataHeader.get(5), third);
    }

    @Override
    public void onClick(View v) {

        if (v == linearLayout_createCoures) {
            startActivity(new Intent(getApplicationContext(), ViewCourse_Activity.class));
        } else if (v == linearLayout_createcategories) {
            startActivity(new Intent(getApplicationContext(), CreateCategories_Activity.class));
        } else if (v == linearLayout_liveclass) {
            startActivity(new Intent(getApplicationContext(), LiveClass_Activity.class));
        } else if (v == linearLayout_createquiz) {
            startActivity(new Intent(getApplicationContext(), ViewQuiz_Activity.class));
        } else if (v == circleImageView_nav) {
            startActivity(new Intent(getApplicationContext(), Profile_Activity.class));
        }
    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/ubuntu_m.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked

        switch (menuItem.getItemId()) {

            case R.id.featured:
                startActivity(new Intent(getApplicationContext(), Featured_Activity.class));
                finish();
                break;

            case R.id.my_courses:
                startActivity(new Intent(getApplicationContext(), Categories_Activity.class));
                finish();
                break;

            case R.id.wishlist:
                startActivity(new Intent(getApplicationContext(), WishList_Activity.class));
                finish();
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

    private ActionBarDrawerToggle setupDrawerToggle() {

        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar_dashboard, R.string.drawer_open, R.string.drawer_close);

    }

    public int GetDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
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
}
