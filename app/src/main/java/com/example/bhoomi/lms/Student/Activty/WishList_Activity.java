package com.example.bhoomi.lms.Student.Activty;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bhoomi.lms.APIModel.StudentCourseList.MyCourseResp;
import com.example.bhoomi.lms.APIModel.WishList.WishListData;
import com.example.bhoomi.lms.APIModel.WishList.WishListResp;
import com.example.bhoomi.lms.Activity.Welcome_Activity;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Retrofit.APIService;
import com.example.bhoomi.lms.Retrofit.APIUtils;
import com.example.bhoomi.lms.Student.Adapter.ParticularCourseListAdapter;
import com.example.bhoomi.lms.Student.Adapter.WishListAdapter;
import com.example.bhoomi.lms.Student.Constants.ConstantData;
import com.example.bhoomi.lms.Student.Constants.SharedPref;
import com.example.bhoomi.lms.Student.Model.CourseModel;
import com.example.bhoomi.lms.Teacher.Model.CourseListModel;
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

public class WishList_Activity extends AppCompatActivity implements WishListAdapter.ViewWhisListClick, View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private APIService apiService;
    private RecyclerView recyclerView_wishlist;
    private WishListAdapter mAdapter;
    private ArrayList<WishListData> wishlistArraylist;
    private Toolbar toolbar_wishlist;
    private DrawerLayout mDrawer;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private TextView textViewProfile_nav, textViewUname_nav, noDataFound;
    private ImageView circleImageView_nav;
    private String profile_img, u_name, u_role_id;
    private ProgressDialog progressDialog;
    private GoogleApiClient mGoogleApiClient;
    private SharedPref session;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayout wishlistProgress;
    private boolean isLoading;
    private boolean isMore = true;
    private int totalCount;
    private int offset = 0, limit = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list_);
        apiService = APIUtils.getAPIService();
        sharedPreferences = getSharedPreferences(ConstantData.PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        session = new SharedPref(WishList_Activity.this);
        progressDialog = new ProgressDialog(WishList_Activity.this);
        toolbar_wishlist = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_wishlist);
        toolbar_wishlist.setTitle("");
        toolbar_wishlist.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar_wishlist);
        toolbar_wishlist.setNavigationIcon(R.drawable.menu_icon);
        recyclerView_wishlist = findViewById(R.id.recyclerView_wishlist);
        noDataFound = findViewById(R.id.no_data_found);
        wishlistProgress = findViewById(R.id.wishlist_progress);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView_wishlist.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
        wishlistArraylist = new ArrayList<>();
        mAdapter = new WishListAdapter(this, wishlistArraylist, this);
        recyclerView_wishlist.setAdapter(mAdapter);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // Setup drawer view
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
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
        mDrawer.addDrawerListener(drawerToggle);
        nvDrawer.setItemIconTintList(null);
        getWishListData();
        pagination();
//        getWishListData();
    }

    private void pagination() {
        recyclerView_wishlist.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                            && totalItemCount >= (wishlistArraylist.size()) && isMore && wishlistArraylist.size() <= totalCount) {
                        offset += limit;
                        isLoading = true;
                        getWishListDataProgress();
                    }
                }
            }
        });
    }

    private void getWishListData() {
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();
//        wishlistProgress.setVisibility(View.VISIBLE);
        apiService.getMyWishlist(sharedPreferences.getString(ConstantData.USER_ID, ""), offset).enqueue(new Callback<WishListResp>() {
            @Override
            public void onResponse(Call<WishListResp> call, Response<WishListResp> response) {
                if (progressDialog != null)
                    progressDialog.dismiss();
//                wishlistProgress.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("Success")) {
                        isLoading = false;
                        if (response.body().getWishInfo() != null && response.body().getWishInfo().size() > 0) {
                            recyclerView_wishlist.setVisibility(View.VISIBLE);
                            totalCount = response.body().getWishInfo().size();
                            wishlistArraylist.addAll(response.body().getWishInfo());
                        } else {

                        }
                        mAdapter.notifyItemInserted(mAdapter.getItemCount());
                    } else {
//                        wishlistProgress.setVisibility(View.GONE);
//                        recyclerView_wishlist.setVisibility(View.GONE);
//                        noDataFound.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<WishListResp> call, Throwable t) {
                Log.i("TAG", "onFailure: " + t.getMessage());
                isMore = false;
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
//                wishlistProgress.setVisibility(View.GONE);
                recyclerView_wishlist.setVisibility(View.GONE);
                noDataFound.setVisibility(View.VISIBLE);
            }
        });
    }
    private void getWishListDataProgress() {
//        progressDialog.setMessage(getString(R.string.please_wait));
//        progressDialog.show();
        wishlistProgress.setVisibility(View.VISIBLE);
        apiService.getMyWishlist(sharedPreferences.getString(ConstantData.USER_ID, ""), offset).enqueue(new Callback<WishListResp>() {
            @Override
            public void onResponse(Call<WishListResp> call, Response<WishListResp> response) {
//                if (progressDialog != null)
//                    progressDialog.dismiss();

                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("Success")) {
                        wishlistProgress.setVisibility(View.GONE);
                        isLoading = false;
                        if (response.body().getWishInfo() != null && response.body().getWishInfo().size() > 0) {
                            recyclerView_wishlist.setVisibility(View.VISIBLE);
                            totalCount = response.body().getWishInfo().size();
                            wishlistArraylist.addAll(response.body().getWishInfo());
                        } else {

                        }
                        mAdapter.notifyItemInserted(mAdapter.getItemCount());
                    } else {
                        wishlistProgress.setVisibility(View.GONE);
//                        recyclerView_wishlist.setVisibility(View.GONE);
//                        noDataFound.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<WishListResp> call, Throwable t) {
                Log.i("TAG", "onFailure: " + t.getMessage());
                isMore = false;
//                if (progressDialog != null) {
//                    progressDialog.dismiss();
//                }
                wishlistProgress.setVisibility(View.GONE);
                recyclerView_wishlist.setVisibility(View.GONE);
                noDataFound.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        isMore = false;
    }

    private ActionBarDrawerToggle setupDrawerToggle() {

        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar_wishlist, R.string.drawer_open, R.string.drawer_close);

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
                sharedPreferences.edit().clear();
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
    public void onWishListClick(int pos) {
        Intent intent = new Intent(this, CourseView_Activity.class);
        intent.putExtra("course_id", wishlistArraylist.get(pos).getCourseId());
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (v == circleImageView_nav) {
            startActivity(new Intent(getApplicationContext(), Profile_Activity.class));
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
