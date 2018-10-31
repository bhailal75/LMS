package com.example.bhoomi.lms.Student.Activty;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bhoomi.lms.APIModel.AddWishList.AddWishListResp;
import com.example.bhoomi.lms.APIModel.CourseDetails.CourseDetail;
import com.example.bhoomi.lms.APIModel.CourseDetails.CoursesInfo;
import com.example.bhoomi.lms.APIModel.CourseDetails.RateInfo;
import com.example.bhoomi.lms.APIModel.CourseDetails.StudentAlsoView;
import com.example.bhoomi.lms.APIModel.StudentCourseList.MyCourseResp;
import com.example.bhoomi.lms.APIModel.SubcategoryList.CourseInfo;
import com.example.bhoomi.lms.APIModel.SubcategoryList.SubcatList;
import com.example.bhoomi.lms.APIModel.UpdateProfile.UpdateProfile;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Retrofit.APIService;
import com.example.bhoomi.lms.Retrofit.APIUtils;
import com.example.bhoomi.lms.Student.Adapter.ExpandableListAdapter;
import com.example.bhoomi.lms.Student.Adapter.ParticularCourseListAdapter;
import com.example.bhoomi.lms.Student.Adapter.ReviewAdapter;
import com.example.bhoomi.lms.Student.Adapter.StudentViewAdapter;
import com.example.bhoomi.lms.Student.Constants.ConstantData;
import com.example.bhoomi.lms.Student.Constants.MyCustomValueTextFormatter;
import com.example.bhoomi.lms.Student.Constants.MyMediumText;
import com.example.bhoomi.lms.Student.Constants.MyRegularText;
import com.example.bhoomi.lms.Student.Constants.NonScrollExpandableListView;
import com.example.bhoomi.lms.Student.Constants.ResizableCustomView;
import com.example.bhoomi.lms.Student.Constants.TouchDetectableScrollView;
import com.example.bhoomi.lms.Student.Model.CourseModel;
import com.example.bhoomi.lms.Student.Model.ReviewModel;

import com.philjay.valuebar.ValueBar;
import com.philjay.valuebar.colors.RedToGreenFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tcking.github.com.giraffeplayer2.Option;
import tcking.github.com.giraffeplayer2.VideoInfo;
import tcking.github.com.giraffeplayer2.VideoView;

public class CourseView_Activity extends AppCompatActivity implements View.OnClickListener, ExpandableListView.OnGroupExpandListener, ExpandableListView.OnGroupCollapseListener {

    private static final int MAX_LINES = 3;
    ExpandableListAdapter listAdapter;
    NonScrollExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    Double per1, per2, per3, per4, per5;
    private ArrayList<CoursesInfo> courseArrayList;
    private Toolbar toolbar_courseview;
    private MyRegularText textView_description;
    private RecyclerView recyclerView_courses, recyclerView_reviews;
    private StudentViewAdapter mAdapter;
    private ReviewAdapter mReviewAdapter;
    private ArrayList<CourseModel> courseModelArrayList;
    private ArrayList<ReviewModel> reviewModelArraylist;
    private CourseModel courseModel;
    private ReviewModel reviewModel;
    private ValueBar[] valueBars = new ValueBar[5];
    private int total, oneStar, twoStar, threeStar, fourStar, fiveStar;
    private int REQUEST_INVITE = 1;
    private SharedPreferences sharedPreferences;
    private TouchDetectableScrollView scrollView;
    private FrameLayout frameLayout_price, frame_free;

    private MyRegularText textView_oldprice, text_courseCurriculamDetail;
    private MyMediumText textView_buynow, textView_free, textView_enroll, button_new_rs, mediumTxt_price;
    private LinearLayout ll_price;

    private APIService apiService;
    private ProgressDialog progressDialog;
    private MyRegularText ratingPercent5, ratingPercent4, ratingPercent3, ratingPercent2, ratingPercent1;
    private MyRegularText textView_students, textView_courses, textView_courserating, text_createdBy, text_author;
    private MyMediumText text_title;
    private MyRegularText text_subtitle, text_courseSection;

    private ImageView imageView2, imageView_thumb, toolbar_courseView, image_play;
    private String course_id;
    private String course_price;

    private CardView cardView, card_reviews;
    private LinearLayout layout_what_it_gets, linearLayout_img_thumbnail;
    private LinearLayout layout_what_will_learn, linearLayout_learn_thumbnail;

    private ArrayList<String> slotList;
    private MyRegularText text_wht_it_include, avg_Rating_text;

    private CardView cardView_reviews;
    private MyRegularText text_reviewSection;
    private String tempwish;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_view_);
        Intent intent = getIntent();
        course_id = intent.getStringExtra("course_id");
        apiService = APIUtils.getAPIService();
        progressDialog = new ProgressDialog(CourseView_Activity.this);
        sharedPreferences = getSharedPreferences(ConstantData.PREF_NAME, MODE_PRIVATE);
        toolbar_courseview = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_courseView);
        toolbar_courseview.setTitle("");
        setSupportActionBar(toolbar_courseview);

        slotList = new ArrayList<>();
        courseArrayList = new ArrayList<>();

        toolbar_courseview.setNavigationIcon(R.drawable.ic_back);
        Configuration config = getApplicationContext().getResources().getConfiguration();
        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL)
            toolbar_courseview.getNavigationIcon().setAutoMirrored(true);
        toolbar_courseview.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        textView_description = findViewById(R.id.text_coursedesc);
        text_courseCurriculamDetail = findViewById(R.id.text_courseCurriculamDetail);
        text_reviewSection = findViewById(R.id.text_reviewSection);
        avg_Rating_text = findViewById(R.id.avgRating);
        expListView = (NonScrollExpandableListView) findViewById(R.id.lvExp);
        scrollView = findViewById(R.id.scrollView_courseDetail);
        videoView = findViewById(R.id.video_view);
        videoView.setVisibility(View.GONE);
        image_play = findViewById(R.id.image_play);

        frameLayout_price = findViewById(R.id.frame_pricing);
        frame_free = findViewById(R.id.frame_free);
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        cardView = findViewById(R.id.card_viewsection);
        card_reviews = findViewById(R.id.card_reviews);
        recyclerView_courses = findViewById(R.id.recyclerView_courses);
        recyclerView_reviews = findViewById(R.id.recyclerView_comments);
        recyclerView_courses.setFocusable(false);
        recyclerView_reviews.setFocusable(false);
        expListView.setFocusable(false);

        textView_oldprice = findViewById(R.id.button_old_rs);
        textView_oldprice.setPaintFlags(textView_oldprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        button_new_rs = findViewById(R.id.button_new_rs);
        textView_buynow = findViewById(R.id.textView_buynow);
        textView_free = findViewById(R.id.text_free);
        textView_enroll = findViewById(R.id.text_enroll);
        cardView_reviews = findViewById(R.id.see_Reviews);
        cardView_reviews.setOnClickListener(this);
        ll_price = findViewById(R.id.ll_price);
        layout_what_it_gets = findViewById(R.id.this_course_include);
        layout_what_will_learn = findViewById(R.id.wht_will_learn);
        linearLayout_learn_thumbnail = findViewById(R.id.img_learn_thumbnail);
        valueBars[0] = (ValueBar) findViewById(R.id.valueBar1);
        valueBars[1] = (ValueBar) findViewById(R.id.valueBar2);
        valueBars[2] = (ValueBar) findViewById(R.id.valueBar3);
        valueBars[3] = (ValueBar) findViewById(R.id.valueBar4);
        valueBars[4] = (ValueBar) findViewById(R.id.valueBar5);
        linearLayout_img_thumbnail = findViewById(R.id.img_thumbnail);
        textView_students = findViewById(R.id.textView_students);
        textView_courses = findViewById(R.id.textView_courses);
        textView_courserating = findViewById(R.id.textView_courserating);
        text_courseSection = findViewById(R.id.text_courseSection);
        imageView2 = findViewById(R.id.imageView2);
        imageView_thumb = findViewById(R.id.imageView_thumb);
        toolbar_courseView = findViewById(R.id.img_back);


        ll_price.setOnClickListener(this);
        textView_buynow.setOnClickListener(this);
        textView_free.setOnClickListener(this);
        textView_enroll.setOnClickListener(this);
        cardView.setOnClickListener(this);
        image_play.setOnClickListener(this);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView_courses.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView_reviews.setLayoutManager(linearLayoutManager1); // set Lay

        courseModel = new CourseModel();
        reviewModel = new ReviewModel();
        courseModelArrayList = new ArrayList<>();
        reviewModelArraylist = new ArrayList<>();

        mAdapter = new StudentViewAdapter(this, new ArrayList<StudentAlsoView>(0));
        recyclerView_courses.setAdapter(mAdapter);
        mReviewAdapter = new ReviewAdapter(this, new ArrayList<RateInfo>(0));
        recyclerView_reviews.setAdapter(mReviewAdapter);

        doGetCourseDetail(course_id);

        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousItem = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousItem)
                    expListView.collapseGroup(previousItem);
                previousItem = groupPosition;
            }
        });


//        setupBarColor();
//        setUp();

        scrollView.setMyScrollChangeListener(new TouchDetectableScrollView.OnMyScrollChangeListener() {
            @Override
            public void onScrollUp() {
                frame_free.setVisibility(View.GONE);
                frameLayout_price.setVisibility(View.GONE);
            }

            @Override
            public void onScrollDown() {
                if (course_price != null && course_price.equalsIgnoreCase("0")) {
                    frame_free.setVisibility(View.VISIBLE);
                    frameLayout_price.setVisibility(View.GONE);
                } else {
                    frameLayout_price.setVisibility(View.VISIBLE);
                    frame_free.setVisibility(View.GONE);
                }
            }
        });

        ratingPercent5 = findViewById(R.id.ratingPercent5);
        ratingPercent4 = findViewById(R.id.ratingPercent4);
        ratingPercent3 = findViewById(R.id.ratingPercent3);
        ratingPercent2 = findViewById(R.id.ratingPercent2);
        ratingPercent1 = findViewById(R.id.ratingPercent1);
        text_title = findViewById(R.id.text_title);
        text_subtitle = findViewById(R.id.text_subtitle);
        text_author = findViewById(R.id.text_author);
        text_createdBy = findViewById(R.id.text_createdBy);
    }

    private void doGetCourseDetail(String s) {
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();
        apiService.getCourseDetail(sharedPreferences.getString(ConstantData.USER_ID, ""), s).enqueue(new Callback<CourseDetail>() {
            @Override
            public void onResponse(Call<CourseDetail> call, Response<CourseDetail> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("Success")) {
                        ////user_id add karvanu model ma
                        String course_id = response.body().getCoursesInfo().get(0).getCourseId();
                        String course_title = response.body().getCoursesInfo().get(0).getCourseTitle();
                        String course_intro = response.body().getCoursesInfo().get(0).getCourseIntro();
                        String course_description = response.body().getCoursesInfo().get(0).getCourseDescription();
                        course_price = response.body().getCoursesInfo().get(0).getCoursePrice();
                        String category_id = response.body().getCoursesInfo().get(0).getCategoryId();
                        String course_requirement = response.body().getCoursesInfo().get(0).getCourseRequirement();
                        String target_audience = response.body().getCoursesInfo().get(0).getTargetAudience();
                        String what_i_get = response.body().getCoursesInfo().get(0).getWhatIGet();
                        String course_count_review = response.body().getCoursesInfo().get(0).getCourseCountReviews();
                        String course_rating = response.body().getCoursesInfo().get(0).getCourseRating();
                        String course_view_count = response.body().getCoursesInfo().get(0).getCourseViewCount();
                        String created_by = response.body().getCoursesInfo().get(0).getCreatedBy();
                        String public_ = response.body().getCoursesInfo().get(0).getPublic();
                        String active = response.body().getCoursesInfo().get(0).getActive();
                        String parent_category_id = response.body().getCoursesInfo().get(0).getParentCategoryId();
                        String crated_date = response.body().getCoursesInfo().get(0).getCreatedDate();
                        String discount = response.body().getCoursesInfo().get(0).getDiscount();
                        String this_course_include = response.body().getCoursesInfo().get(0).getThisCourseIncludes();
                        String what_will_i_learn = response.body().getCoursesInfo().get(0).getWhatWillILearn();
                        String course_image = response.body().getCoursesInfo().get(0).getCourseImage();
                        String course_articles = response.body().getCoursesInfo().get(0).getTotalArticals();
                        String course_video_hr = response.body().getCoursesInfo().get(0).getTotalVideoHours();

                        text_courseCurriculamDetail.setText(getString(R.string.lecture) + "(" + course_articles + ")   " + getString(R.string.total) + " (" + course_video_hr + getString(R.string.hours) + ")");
                        textView_oldprice.setText("$" + course_price);
                        button_new_rs.setText("$" + course_price);
                        mAdapter.updateSubCatList(response.body().getStudentAlsoView());
                        textView_description.setText(course_description);
                        ResizableCustomView.doResizeTextView(textView_description, MAX_LINES, "View More", true);

                        if (response.body().getCoursesInfo().get(0).getCourseVideo().trim().length() > 0) {
                            image_play.setVisibility(View.VISIBLE);
                        }


                        Glide.with(CourseView_Activity.this)
                                .load(course_image)
                                .placeholder(R.drawable.profile_icon)
                                .into(imageView2);

                        Glide.with(CourseView_Activity.this)
                                .load(course_image)
                                .placeholder(R.drawable.profile_icon)
                                .into(toolbar_courseView);
                        String lines[] = what_i_get.split("\\r?\\n");
                        String learn_lines[] = what_will_i_learn.split("\\r?\\n");
                        for (int i = 0; i < lines.length; i++) {
                            try {
                                slotList.add(lines[i]);
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(CourseView_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        createWhatItIncludes(slotList.size());
                        for (int i = 0; i < learn_lines.length; i++) {
                            try {

                                ImageView imageView = new ImageView(CourseView_Activity.this);
//                                imageView.setImageResource(R.mipmap.user_dark);

                                MyRegularText textView = new MyRegularText(CourseView_Activity.this);
                                textView.setText(Html.fromHtml(Html.fromHtml(learn_lines[i]).toString()) + "\n");
                                textView.setId(i);
                                layout_what_will_learn.addView(textView);
                                linearLayout_learn_thumbnail.addView(imageView);
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(CourseView_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        String user_id = response.body().getCreatedbyInfo().getUserId();
                        String user_name = response.body().getCreatedbyInfo().getUserName();
                        String user_email = response.body().getCreatedbyInfo().getUserEmail();
                        String user_phone = response.body().getCreatedbyInfo().getUserPhone();
                        String user_img = response.body().getCreatedbyInfo().getProfileImage();
                        String no_of_students = response.body().getCreatedbyInfo().getNoOfStudent();
                        String courses = response.body().getCreatedbyInfo().getCountCourse();
                        String course_avg_rating = response.body().getCreatedbyInfo().getCourseRating();

                        Glide.with(CourseView_Activity.this)
                                .load(user_img)
                                .placeholder(R.drawable.profile_icon)
                                .into(imageView_thumb);

                        oneStar = Integer.parseInt(response.body().getAvarageRate().getOnestar());
                        twoStar = Integer.parseInt(response.body().getAvarageRate().getTwostar());
                        threeStar = Integer.parseInt(response.body().getAvarageRate().getThreestar());
                        fourStar = Integer.parseInt(response.body().getAvarageRate().getFourstar());
                        fiveStar = Integer.parseInt(response.body().getAvarageRate().getFiveStar());
                        Integer avg = response.body().getAvarageRate().getAverage();

                        valueBars[0].animate(0, Float.parseFloat(String.valueOf(fiveStar)), 1500);
                        valueBars[1].animate(0, Float.parseFloat(String.valueOf(fourStar)), 1500);
                        valueBars[2].animate(0, Float.parseFloat(String.valueOf(threeStar)), 1500);
                        valueBars[3].animate(0, Float.parseFloat(String.valueOf(twoStar)), 1500);
                        valueBars[4].animate(0, Float.parseFloat(String.valueOf(oneStar)), 1500);

                        valueBars[0].setColor(getResources().getColor(android.R.color.darker_gray));
                        valueBars[1].setColor(getResources().getColor(android.R.color.darker_gray));
                        valueBars[2].setColor(getResources().getColor(android.R.color.darker_gray));
                        valueBars[3].setColor(getResources().getColor(android.R.color.darker_gray));
                        valueBars[4].setColor(getResources().getColor(android.R.color.darker_gray));

                        for (ValueBar bar : valueBars) {
                            bar.setDrawValueText(false);
                            bar.setTouchEnabled(false);
                            bar.setMinMax(0, 100);
                            bar.setInterval(1f);
                            bar.setDrawBorder(false);
                            bar.setValueTextSize(0f);
                            bar.setMinMaxTextSize(0f);
                            bar.setBackgroundColor(getResources().getColor(R.color.colorgLightGray));
                            // bar.setColor(Color.BLUE);
                        }

                        System.out.println("getdatasize " + response.body().getRateInfo());
                        if (response.body().getRateInfo().size() == 0) {
                            cardView_reviews.setVisibility(View.GONE);
                            card_reviews.setVisibility(View.GONE);
                        } else {
                            text_reviewSection.setText(R.string.see_all_review);
                            cardView_reviews.setVisibility(View.VISIBLE);
                            card_reviews.setVisibility(View.VISIBLE);
                            mReviewAdapter.updateReview(response.body().getRateInfo());
                        }
                        total = oneStar + twoStar + threeStar + fourStar + fiveStar;
                        if (total != 0) {
                            per1 = Double.valueOf(((oneStar * 100) / total));
                            per2 = Double.valueOf((twoStar * 100) / total);
                            per3 = Double.valueOf((threeStar * 100) / total);
                            per4 = Double.valueOf((fourStar * 100) / total);
                            per5 = Double.valueOf((fiveStar * 100) / total);
                        } else {
                            per1 = per2 = per3 = per4 = per5 = Double.valueOf(0);
                        }


                        String onePrecent = String.format("%.2f", per1);
                        String twoPrecent = String.format("%.2f", per2);
                        String threePrecent = String.format("%.2f", per3);
                        String fourPrecent = String.format("%.2f", per4);
                        String fivePrecent = String.format("%.2f", per5);

                        ratingPercent1.setText(onePrecent + "%");
                        ratingPercent2.setText(twoPrecent + "%");
                        ratingPercent3.setText(threePrecent + "%");
                        ratingPercent4.setText(fourPrecent + "%");
                        ratingPercent5.setText(fivePrecent + "%");

                        textView_students.setText(no_of_students + " " + getString(R.string.student));
                        textView_courses.setText(courses + " " + getString(R.string.courses));
                        textView_courserating.setText(course_avg_rating + " " + getString(R.string.average_rating));
                        avg_Rating_text.setText(avg + " ");

                        text_title.setText(course_title);
                        text_subtitle.setText(course_intro);
                        text_author.setText(getString(R.string.createdby) + " " + created_by);

                        text_createdBy.setText(" " + user_name);
                        if (response.body().getCoursesInfo() != null) {
                            courseArrayList.addAll(response.body().getCoursesInfo());
                            invalidateOptionsMenu();
                        }

                        for (int i = 0; i < response.body().getCoursesInfo().get(0).getSectionInfo().size(); i++) {
//                           listDataChild.put(listDataHeader.get(i), null); // Header, Child data
                            System.out.println("listdataname " + response.body().getCoursesInfo().get(0).getSectionInfo().get(i).getSectionName());
                            listDataHeader.add(response.body().getCoursesInfo().get(0).getSectionInfo().get(i).getSectionName());
                            List<String> restData = new ArrayList<String>();
                            restData.add(response.body().getCoursesInfo().get(0).getSectionInfo().get(i).getSectionTitle());
                            listDataChild.put(listDataHeader.get(i), restData);
//                           Toast.makeText(CourseView_Activity.this, "1", Toast.LENGTH_SHORT).show();
                        }
                        if (listDataHeader.size() > 3) {
                            int sectionsize = listDataHeader.size() - 3;
                            text_courseSection.setText(sectionsize + " " + getString(R.string.more_section));
                        } else {
                            text_courseSection.setText(listDataHeader.size() + " " + getString(R.string.sections));
                        }
                        listAdapter = new ExpandableListAdapter(CourseView_Activity.this, listDataHeader, listDataChild);
                        expListView.setAdapter(listAdapter);
                    } else {
                        Toast.makeText(CourseView_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<CourseDetail> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CourseView_Activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void createWhatItIncludes(int size) {
        for (int i = 0; i < size; i++) {
            ImageView imageView = new ImageView(this);
//            imageView.setImageResource(R.mipmap.user_dark);
            MyRegularText textView = new MyRegularText(this);
            textView.setText(Html.fromHtml(Html.fromHtml(slotList.get(i)).toString()) + "\n");
            textView.setId(i);
            layout_what_it_gets.addView(textView);
            linearLayout_img_thumbnail.addView(imageView);
        }
    }

    private void setUp() {
        for (ValueBar bar : valueBars) {

            bar.setDrawValueText(false);
            bar.setTouchEnabled(false);
            bar.setMinMax(0, 100);
            bar.setInterval(1f);
            bar.setDrawBorder(false);
            bar.setValueTextSize(14f);
            bar.setMinMaxTextSize(16f);
            bar.setValueTextFormatter(new MyCustomValueTextFormatter());
            bar.setColorFormatter(new RedToGreenFormatter());
            bar.setOverlayColor(Color.BLACK);
            // bar.setColor(Color.BLUE);
        }
    }

    private void setupBarColor() {
        for (ValueBar bar : valueBars) {
            bar.setColor(getResources().getColor(R.color.colorgDarkGray));
            bar.setBorderColor(getResources().getColor(R.color.colorgDarkGray));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favouties, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorites:
                clickWishList();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (courseArrayList != null && courseArrayList.size() > 0) {
            if (courseArrayList.get(0).getIsWish().equals("1"))
                menu.findItem(R.id.action_favorites).setIcon(R.drawable.ic_like_red);
            else
                menu.findItem(R.id.action_favorites).setIcon(R.drawable.ic_like_white);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void clickWishList() {
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();
        if (courseArrayList.get(0).getIsWish().equals("1"))
            tempwish = "0";
        else
            tempwish = "1";
        apiService.getAddWishlist(sharedPreferences.getString(ConstantData.USER_ID, ""), course_id, tempwish).enqueue(new Callback<AddWishListResp>() {
            @Override
            public void onResponse(Call<AddWishListResp> call, Response<AddWishListResp> response) {
                if (progressDialog != null)
                    progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("Success")) {
                        courseArrayList.get(0).setIsWish(response.body().getIsWish());
                        invalidateOptionsMenu();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddWishListResp> call, Throwable t) {
                Log.i("TAG", "onFailure: " + t.getMessage());
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == ll_price) {
        } else if (v == textView_buynow) {
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.layout_continue_dialog, null);
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setView(dialogView);
            dialogBuilder.setCancelable(true);
            final AlertDialog alertbox = dialogBuilder.create();
            Button button = dialogView.findViewById(R.id.buttn_continue);
            mediumTxt_price = dialogView.findViewById(R.id.mediumTxt_price);
            mediumTxt_price.setText("$" + course_price);
            Typeface typeface_rglr = Typeface.createFromAsset(getAssets(), "fonts/ubuntu_m.ttf");
            button.setTypeface(typeface_rglr);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertbox.dismiss();
                    LayoutInflater inflater = getLayoutInflater();
                    final View dialogView = inflater.inflate(R.layout.layout_payment_method, null);
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CourseView_Activity.this);
                    dialogBuilder.setView(dialogView);
                    dialogBuilder.setCancelable(true);
                    final AlertDialog alertbox = dialogBuilder.create();
                    LinearLayout linearLayout_card = dialogView.findViewById(R.id.ll_creditcard);
                    LinearLayout linearLayout_netbank = dialogView.findViewById(R.id.ll_netbanking);
                    linearLayout_card.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            alertbox.dismiss();
                            LayoutInflater inflater = getLayoutInflater();
                            View dialogView = inflater.inflate(R.layout.layout_add_credit_debit_dialog, null);
                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CourseView_Activity.this);
                            dialogBuilder.setView(dialogView);
                            dialogBuilder.setCancelable(true);
                            final AlertDialog alertbox = dialogBuilder.create();
                            alertbox.show();
                            Button button1 = dialogView.findViewById(R.id.buttn_continuecard);
                            Typeface typeface_rglr = Typeface.createFromAsset(getAssets(), "fonts/ubuntu_m.ttf");
                            button1.setTypeface(typeface_rglr);
                            button1.setOnClickListener(new View.OnClickListener() {
                                                           @Override
                                                           public void onClick(View v) {
                                                               alertbox.dismiss();
                                                               LayoutInflater inflater = getLayoutInflater();
                                                               View dialogView = inflater.inflate(R.layout.layout_creditdetail_dialog, null);
                                                               AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CourseView_Activity.this);
                                                               dialogBuilder.setView(dialogView);
                                                               dialogBuilder.setCancelable(true);
                                                               final AlertDialog alertbox = dialogBuilder.create();
                                                               Button buttonsave = dialogView.findViewById(R.id.buttn_savecard);
                                                               buttonsave.setOnClickListener(new View.OnClickListener() {
                                                                   @Override
                                                                   public void onClick(View v) {
                                                                       alertbox.dismiss();
                                                                   }
                                                               });
                                                               alertbox.show();
                                                           }
                                                       }
                            );
                        }
                    });
                    linearLayout_netbank.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertbox.dismiss();
                            LayoutInflater inflater = getLayoutInflater();
                            View dialogView = inflater.inflate(R.layout.layout_add_credit_debit_dialog, null);
                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CourseView_Activity.this);
                            dialogBuilder.setView(dialogView);
                            dialogBuilder.setCancelable(true);
                            final AlertDialog alertbox = dialogBuilder.create();
                            Button button1 = dialogView.findViewById(R.id.buttn_continuecard);
                            button1.setOnClickListener(new View.OnClickListener() {
                                                           @Override
                                                           public void onClick(View v) {
                                                               alertbox.dismiss();
                                                               LayoutInflater inflater = getLayoutInflater();
                                                               View dialogView = inflater.inflate(R.layout.layout_creditdetail_dialog, null);
                                                               AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CourseView_Activity.this);
                                                               dialogBuilder.setView(dialogView);
                                                               dialogBuilder.setCancelable(true);
                                                               final AlertDialog alertbox = dialogBuilder.create();
                                                               Button buttonsave = dialogView.findViewById(R.id.buttn_savecard);
                                                               EditText editText_exDate = dialogView.findViewById(R.id.date_textInputEditText);
                                                               editText_exDate.setKeyListener(null);
                                                               TextInputLayout textInputLayout = dialogView.findViewById(R.id.textInput_exdate);
                                                               textInputLayout.setOnClickListener(new View.OnClickListener() {
                                                                   @Override
                                                                   public void onClick(View v) {
                                                                   }
                                                               });
                                                               buttonsave.setOnClickListener(new View.OnClickListener() {
                                                                   @Override
                                                                   public void onClick(View v) {
                                                                       alertbox.dismiss();
                                                                   }
                                                               });
                                                               alertbox.show();
                                                           }
                                                       }
                            );
                            alertbox.show();
                        }
                    });
                    alertbox.show();
                }
            });
            alertbox.show();
        } else if (v == textView_free) {

        } else if (v == textView_enroll) {
            Intent intent = new Intent(getApplicationContext(), EnrollVideo_Activity.class);
            intent.putExtra("course_id", course_id);
            startActivity(intent);
        } else if (v == cardView) {
            Intent intent = new Intent(getApplicationContext(), Section_Activity.class);
            intent.putExtra("course_id", course_id);
            startActivity(intent);
        } else if (v == cardView_reviews) {
            Intent intent = new Intent(getApplicationContext(), Review_Activity.class);
            intent.putExtra("course_id", course_id);
            startActivity(intent);
        } else if (v == image_play) {
            videoView.setVisibility(View.VISIBLE);
            videoView.getVideoInfo()
                    .setTitle(courseArrayList.get(0).getCourseTitle())
                    .setShowTopBar(true)
                    .setBgColor(Color.GRAY)
                    .setFullScreenAnimation(true)
                    .setPortraitWhenFullScreen(false);
            videoView.setVideoPath(courseArrayList.get(0).getCourseVideo()).getPlayer().start();
        }
    }

    @Override
    public void onGroupExpand(int groupPosition) {
        LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) expListView.getLayoutParams();
        param.height = (listDataHeader.size() * expListView.getHeight());
        expListView.setLayoutParams(param);
        expListView.refreshDrawableState();
        scrollView.refreshDrawableState();
    }

    @Override
    public void onGroupCollapse(int groupPosition) {
        LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) expListView.getLayoutParams();
        param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        expListView.setLayoutParams(param);
        expListView.refreshDrawableState();
        scrollView.refreshDrawableState();
    }
}
