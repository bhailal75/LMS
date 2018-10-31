package com.example.bhoomi.lms.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Student.Activty.Dashboard_Activity;
import com.example.bhoomi.lms.Student.Adapter.IntroAdapter;
import com.example.bhoomi.lms.Student.Adapter.SliderAdapter;
import com.example.bhoomi.lms.Student.Constants.ConstantData;
import com.example.bhoomi.lms.Student.Constants.IntroPageTransformer;
import com.example.bhoomi.lms.Student.Constants.MyMediumText;
import com.example.bhoomi.lms.Teacher.Activity.TutorDahboard;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class AppIntroducing_Activity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private MyMediumText textView_buynow, textView_signIN;

    private static int currentPage = 0;
    private static final Integer[] DASHBOARD= {R.drawable.slide_1,R.drawable.slide_3, R.drawable.slide_2};
    private ArrayList<Integer> COURSE_ARRAY = new ArrayList<Integer>();
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String u_role_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_introducing_);

        sharedPreferences = getSharedPreferences(ConstantData.PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();



        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        textView_buynow = findViewById(R.id.textView_buynow);
        textView_signIN = findViewById(R.id.textView_signin);

        textView_buynow.setOnClickListener(this);
        textView_signIN.setOnClickListener(this);

        // Set an Adapter on the ViewPager
        mViewPager.setAdapter(new IntroAdapter(getSupportFragmentManager()));

        // Set a PageTransformer
        IntroPageTransformer introPageTransformer = new IntroPageTransformer(this);
        mViewPager.setPageTransformer(false, new IntroPageTransformer());

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
//                addDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        init();
    }

    @Override
    public void onClick(View v) {
        if( v == textView_buynow)
        {
//            u_role_id = sharedPreferences.getString(ConstantData.U_ROLEID,"");
//            if(u_role_id.equals("4")) {
//                startActivity(new Intent(getApplicationContext(), TutorDahboard.class));
//                editor.putString("firstTime", "true").apply();
//                finish();
//            }

        }
        else{
//            if (u_role_id.equals("5")){
            u_role_id = sharedPreferences.getString(ConstantData.U_ROLEID,"0");

            if(u_role_id.equals("4")) {
                startActivity(new Intent(getApplicationContext(), TutorDahboard.class));
                editor.putString("firstTime", "true").apply();
                finish();
            }else if (u_role_id.equals("5")) {
                startActivity(new Intent(getApplicationContext(), Dashboard_Activity.class));
                editor.putString("firstTime", "true").apply();
                finish();
            }else{
                startActivity(new Intent(getApplicationContext(),Welcome_Activity.class));
                finish();
            }

        }

    }


    private void init() {
        for(int i=0;i<DASHBOARD.length;i++)
            COURSE_ARRAY.add(DASHBOARD[i]);

        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mViewPager);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == DASHBOARD.length) {
                    currentPage = 0;
                }
                mViewPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask()
        {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2500, 2500);
    }


}
