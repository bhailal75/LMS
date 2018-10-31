package com.example.bhoomi.lms.Teacher.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bhoomi.lms.APIModel.CategoryList.CaregoryList;
import com.example.bhoomi.lms.APIModel.CreateSubCategories.CreateSubcategories;
import com.example.bhoomi.lms.APIModel.SubCategories.SubCategories;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Retrofit.APIService;
import com.example.bhoomi.lms.Retrofit.APIUtils;
import com.example.bhoomi.lms.Student.Constants.ConstantData;
import com.example.bhoomi.lms.Student.Constants.MyMediumText;
import com.example.bhoomi.lms.Student.Constants.SharedPref;
import com.example.bhoomi.lms.Student.Model.QnAModel;
import com.example.bhoomi.lms.Teacher.Adapter.CategoryAdapter;
import com.example.bhoomi.lms.Teacher.Adapter.DrawerExpnadableAdapter;
import com.example.bhoomi.lms.Teacher.Adapter.SubCategoryAdapter;
import com.example.bhoomi.lms.Teacher.Model.CategoryModel;
import com.example.bhoomi.lms.Teacher.Model.NavigationModel;
import com.example.bhoomi.lms.Teacher.Model.SubCategoryModel;
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

public class CreateCourse_Activity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    ArrayList<CategoryModel> categoryList;
    ArrayList<SubCategoryModel> subCategoryList;
    DisplayMetrics metrics;
    int width;
    private Button button_creareCourse;
    private ProgressDialog progressDialog;
    private APIService apiService;
    private String spinner_selected_item, spinner_selected_sub_item;
    private Toolbar toolbar_create_course;
    private TextInputLayout textInputLayout_course_title, textInputLayout_course_info, textInputLayout_description, textInputLayout_requirement;
    private TextInputEditText textInputEditText_title, textInputEditText_course_info, textInputEditText_description, textInputEditText_requirement;
    private QnAModel qnAModel;
    private TextInputEditText spin_category, spin_subcategory;
    private CategoryModel categoryModel;
    private CategoryAdapter categoryAdapter;
    private SubCategoryAdapter subcategoryAdapter;
    private String category_id;
    private ArrayList<String> listDataHeader;
    private ArrayList<String> listDataHeaderIcons;
    private HashMap<String, List<String>> listDataChild;
    private ExpandableListView expListView;
    private DrawerExpnadableAdapter listAdapter;
//    private DrawerLayout mDrawer;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private TextView textViewUname_nav;
    private ImageView circleImageView_nav;
    private ArrayList<NavigationModel> navigationModelArrayList;
    private NavigationModel navigationModel;

    private GoogleApiClient mGoogleApiClient;
    private String profile_img, u_name, u_role_id;
    private SharedPref session;


//    private String[] dataArray = {"Course Control", "Quiz Control", "Categories", "Live Class", "Contact Admin", "Sign out"};

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static boolean isConnectd(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course_);

        sharedPreferences = getSharedPreferences(ConstantData.PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        session = new SharedPref(CreateCourse_Activity.this);

        session.checkLogin();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        button_creareCourse = findViewById(R.id.buttn_continue);

        toolbar_create_course = findViewById(R.id.toolbar_create_course);
        toolbar_create_course.setTitle("");
        toolbar_create_course.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar_create_course);

        toolbar_create_course.setNavigationIcon(R.drawable.ic_back);
        Configuration config = getApplicationContext().getResources().getConfiguration();
        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL)
            toolbar_create_course.getNavigationIcon().setAutoMirrored(true);
        toolbar_create_course.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        spinner_selected_sub_item = "";
        spinner_selected_item = "";

        button_creareCourse.setOnClickListener(this);

        apiService = APIUtils.getAPIService();

        progressDialog = new ProgressDialog(CreateCourse_Activity.this);

        categoryList = new ArrayList<>();
        subCategoryList = new ArrayList<>();

        doGetCategoryList();

        textInputLayout_course_title = findViewById(R.id.textInput_coursetitle);
        textInputLayout_course_info = findViewById(R.id.textInput_intro);
        textInputLayout_description = findViewById(R.id.textInput_desc);
        textInputLayout_requirement = findViewById(R.id.textInput_requirement);

        textInputEditText_title = findViewById(R.id.title_textInputEditText);
        textInputEditText_course_info = findViewById(R.id.intro_textInputEditText);
        textInputEditText_description = findViewById(R.id.desc_textInputEditText);
        textInputEditText_requirement = findViewById(R.id.requirement_textInputEditText);

        spin_category = (TextInputEditText) findViewById(R.id.spinner_category);
        spin_category.setOnClickListener(this);

        spin_subcategory = (TextInputEditText) findViewById(R.id.spinner_subcategory);
        spin_subcategory.setOnClickListener(this);


//        Configuration config = getApplicationContext().getResources().getConfiguration();
        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            spin_category.setTextDirection(View.TEXT_DIRECTION_RTL);
            spin_subcategory.setTextDirection(View.TEXT_DIRECTION_RTL);
            textInputLayout_course_title.setTextDirection(View.TEXT_DIRECTION_RTL);
            textInputLayout_course_info.setTextDirection(View.TEXT_DIRECTION_RTL);
            textInputLayout_description.setTextDirection(View.TEXT_DIRECTION_RTL);
            textInputLayout_requirement.setTextDirection(View.TEXT_DIRECTION_RTL);
        }

        Typeface typeface_medium = Typeface.createFromAsset(getAssets(), "fonts/ubuntu_m.ttf");
        textInputLayout_course_title.setTypeface(typeface_medium);
        textInputLayout_course_info.setTypeface(typeface_medium);
        textInputLayout_description.setTypeface(typeface_medium);
        textInputLayout_requirement.setTypeface(typeface_medium);
        textInputEditText_title.setTypeface(typeface_medium);
        textInputEditText_course_info.setTypeface(typeface_medium);
        textInputEditText_description.setTypeface(typeface_medium);
        textInputEditText_requirement.setTypeface(typeface_medium);
        spin_subcategory.setTypeface(typeface_medium);
        spin_category.setTypeface(typeface_medium);

        spin_subcategory.setEnabled(false);

//        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Setup drawer view
//        nvDrawer = (NavigationView) findViewById(R.id.nvView);


//        View view = nvDrawer.getHeaderView(0);

//        nvDrawer.getMenu().getItem(0).setChecked(false);


//        textViewUname_nav = (TextView) view.findViewById(R.id.txtUsername);
//        circleImageView_nav = (ImageView) view.findViewById(R.id.profile_image);

//        profile_img = sharedPreferences.getString(ConstantData.USER_IMAGE, "");
//        u_name = sharedPreferences.getString(ConstantData.USER_NAME, "");
//
//        if (profile_img.length() != 0) {
//
//
//            Glide.with(this)
//                    .load(profile_img)
//                    .placeholder(R.drawable.profile_icon)
//                    .into(circleImageView_nav);
//
//        }
//
//        if (u_name.length() != 0) {
//            textViewUname_nav.setText(u_name);
//        }
//
//        drawerToggle = setupDrawerToggle();
//
//        circleImageView_nav.setOnClickListener(this);

        // Tie DrawerLayout events to the ActionBarToggle
//        mDrawer.addDrawerListener(drawerToggle);

//        nvDrawer.setItemIconTintList(null);

//        enableExpandableList();

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

//        expListView.setIndicatorBounds( width-GetDipsFromPixel(35), width-GetDipsFromPixel(5));

        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

                if (groupPosition == 3) {
                    Toast.makeText(CreateCourse_Activity.this, R.string.live_class, Toast.LENGTH_SHORT).show();

                } else if (groupPosition == 4) {
                    startActivity(new Intent(getApplicationContext(), ContactAdmin_Activity.class));
                } else if (groupPosition == 5) {
                    signOut();
                    LoginManager.getInstance().logOut();
                    session.logoutUser();
                    finish();
                }
//                Toast.makeText(getApplicationContext(),
//                        listDataHeader.get(groupPosition) + " Expanded",
//                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        listDataHeader.get(groupPosition) + " Collapsed",
//                        Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(CreateCourse_Activity.this, R.string.view_course, Toast.LENGTH_SHORT).show();
                    } else {
                        startActivity(new Intent(getApplicationContext(), CreateCourse_Activity.class));
                    }
                } else if (groupPosition == 1) {
                    if (childPosition == 0) {
                        Toast.makeText(CreateCourse_Activity.this, R.string.view_quiz, Toast.LENGTH_SHORT).show();

                    } else if (childPosition == 1) {
                        Toast.makeText(CreateCourse_Activity.this, R.string.create_quiz, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CreateCourse_Activity.this, CreateQuiz_Activity.class));

                    } else {
                        Toast.makeText(CreateCourse_Activity.this, R.string.view_result, Toast.LENGTH_SHORT).show();

                    }
                } else if (groupPosition == 2) {
                    if (childPosition == 0) {
                        Toast.makeText(CreateCourse_Activity.this, R.string.view_caegory, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), CreateCategories_Activity.class));
                    } else if (childPosition == 1) {
                        Toast.makeText(CreateCourse_Activity.this, R.string.view_sub_category, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), CreateSubCategories_Activity.class));
                    } else if (childPosition == 2) {

                        Toast.makeText(CreateCourse_Activity.this, R.string.create_sub_category, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), AddSubCategory.class));

                    }
                }


                // till here
//                Toast.makeText(
//                        getApplicationContext(),
//                        listDataHeader.get(groupPosition)
//                                + " : "
//                                + listDataChild.get(
//                                listDataHeader.get(groupPosition)).get(
//                                childPosition), Toast.LENGTH_SHORT)
//                        .show();
                return false;
            }
        });
    }

    private void prepareListData(ArrayList<NavigationModel> navigationModelArrayList, ArrayList<String> listDataHeader, HashMap<String, List<String>> listDataChild) {

//        for (int i = 0; i < MyData.drawableArray.length; i ++)
//        {
//            navigationModelArrayList.add(new NavigationModel(MyData.drawableArray[i]));
//        }
//
//        for (int i = 0; i < dataArray.length; i++)
//        {
//            navigationModel.setTitle(dataArray[i]);
//        }
//
//        navigationModelArrayList.add(navigationModel);

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

//    private ActionBarDrawerToggle setupDrawerToggle() {
////
////        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
////        // and will not render the hamburger icon without it.
////        return new ActionBarDrawerToggle(this, mDrawer, toolbar_create_course, R.string.drawer_open, R.string.drawer_close);
////
////    }


    @Override
    public void onClick(View v) {

        if (v == button_creareCourse) {

            if (spinner_selected_item.length() == 0) {
                spin_category.setError(getString(R.string.select_category));
            } else if (spinner_selected_sub_item.length() == 0) {
                spin_subcategory.setError(getString(R.string.select_sub_category));
            } else if (textInputEditText_title.getText().toString().length() == 0) {
                textInputEditText_title.setError(getString(R.string.enter_course_title));
            } else if (textInputEditText_course_info.getText().toString().length() == 0) {
                textInputEditText_course_info.setError(getString(R.string.enter_course_interoduction));
            } else if (textInputEditText_description.getText().toString().length() == 0) {
                textInputEditText_description.setError(getString(R.string.enter_course_description));
            } else if (textInputEditText_requirement.getText().toString().length() == 0) {
                textInputEditText_requirement.setError(getString(R.string.enter_course_requirement));
            } else {
                Intent intent = new Intent(getApplicationContext(), ContinueCreateCourse_Activity.class);
                intent.putExtra("cat_id", spinner_selected_item);
                intent.putExtra("sub_cat_id", spinner_selected_sub_item);
                intent.putExtra("course_title", textInputEditText_title.getText().toString());
                intent.putExtra("course_intro", textInputEditText_course_info.getText().toString());
                intent.putExtra("course_desc", textInputEditText_description.getText().toString());
                intent.putExtra("course_requi", textInputEditText_requirement.getText().toString());
                startActivity(intent);

            }

        } else if (v == spin_category) {
            LayoutInflater inflater = (LayoutInflater) CreateCourse_Activity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View titleView = inflater.inflate(R.layout.custom_title, null);

            ((MyMediumText) titleView.findViewById(R.id.custom_title)).setText(R.string.select_category);
            AlertDialog alertDialog = new AlertDialog.Builder(CreateCourse_Activity.this)
                    .setTitle(R.string.select_category)
                    .setAdapter(categoryAdapter, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int position) {

                            spin_subcategory.setEnabled(true);
                            subCategoryList.clear();
                            spin_subcategory.setText("");
                            System.out.println("Categorylistdata " + categoryList.get(1).getCat_name());
                            spin_category.setText(categoryList.get(position).getCat_name());
                            spinner_selected_item = categoryList.get(position).getCat_id();

                            if (!isConnectd(CreateCourse_Activity.this)) {
                                Toast.makeText(CreateCourse_Activity.this, R.string.check_internet_connection, Toast.LENGTH_SHORT).show();
                            } else {
                                doGetSubCategoryList(spinner_selected_item);
                            }
                            // TODO: user specific action
                            dialog.dismiss();
                        }

                    }).create();
            alertDialog.show();
        } else if (v == spin_subcategory) {
            LayoutInflater inflater = (LayoutInflater) CreateCourse_Activity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View titleView = inflater.inflate(R.layout.custom_title, null);

            ((MyMediumText) titleView.findViewById(R.id.custom_title)).setText(R.string.select_sub_category);
            AlertDialog alertDialog = new AlertDialog.Builder(CreateCourse_Activity.this)
                    .setTitle(R.string.select_sub_category)
                    .setAdapter(subcategoryAdapter, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int position) {

                            spin_subcategory.setText(subCategoryList.get(position).getSub_cat_name());
                            spinner_selected_sub_item = subCategoryList.get(position).getSub_cat_id();

                            if (!isConnectd(CreateCourse_Activity.this)) {
//                                displayAlert();
                                Toast.makeText(CreateCourse_Activity.this, R.string.check_internet_connection, Toast.LENGTH_SHORT).show();
                            } else {
//                                spinner_city.setText("");
                                doGetSubCategoryList(spinner_selected_sub_item);
                            }
                            // TODO: user specific action
                            dialog.dismiss();
                        }
                    }).create();
            alertDialog.show();
        }
    }

    private void doGetCategoryList() {
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();

        apiService.getData().enqueue(new Callback<CaregoryList>() {

            @Override
            public void onResponse(Call<CaregoryList> call, Response<CaregoryList> response) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                if (response.isSuccessful()) {

                    if (response.body().getStatus().equalsIgnoreCase("Success")) {

                        for (int i = 0; i < response.body().getCategoryInfo().size(); i++) {
                            CategoryModel categoryModel = new CategoryModel();
                            categoryModel.setCat_name(response.body().getCategoryInfo().get(i).getCategoryName());
                            categoryModel.setCat_id(response.body().getCategoryInfo().get(i).getCategoryId());

                            categoryList.add(categoryModel);
                        }

                        categoryAdapter = new CategoryAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, categoryList);
                        categoryAdapter.notifyDataSetChanged();


                    } else {

                    }
                } else {
                }

            }

            @Override
            public void onFailure(Call<CaregoryList> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }

    private void doGetSubCategoryList(String category_id) {

        progressDialog.setMessage(getResources().getString(R.string.please_wait));
        progressDialog.show();


        apiService.getSubCategories(category_id).enqueue(new Callback<SubCategories>() {

            @Override
            public void onResponse(Call<SubCategories> call, Response<SubCategories> response) {

                progressDialog.dismiss();
                if (response.isSuccessful()) {

                    if (response.body().getStatus().equalsIgnoreCase("Success")) {


                        for (int i = 0; i < response.body().getSubCategoryInfo().size(); i++) {
                            SubCategoryModel subCategoryModel = new SubCategoryModel();
                            subCategoryModel.setSub_cat_name(response.body().getSubCategoryInfo().get(i).getSubCatName());
                            subCategoryModel.setSub_cat_id(response.body().getSubCategoryInfo().get(i).getCatId());

                            subCategoryList.add(subCategoryModel);

                        }

                        System.out.println("subcatlist " + subCategoryList.size());
                        subcategoryAdapter = new SubCategoryAdapter(CreateCourse_Activity.this, android.R.layout.simple_spinner_item, subCategoryList);
                        subcategoryAdapter.notifyDataSetChanged();

                    } else {

                    }
                }

            }

            @Override
            public void onFailure(Call<SubCategories> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println("throwableDaat " + t.getMessage());

            }
        });
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
            finish();
        }


    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
