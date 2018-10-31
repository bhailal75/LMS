package com.example.bhoomi.lms.Student.Activty;

import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.bhoomi.lms.R;

public class AboutCourse_Activity extends AppCompatActivity {

    private Toolbar toolbar_abtcourse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_course_);

        toolbar_abtcourse = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_abtcourse);
        toolbar_abtcourse.setTitle("");
        toolbar_abtcourse.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar_abtcourse);

        toolbar_abtcourse.setNavigationIcon(R.drawable.ic_back);
        Configuration config = getApplicationContext().getResources().getConfiguration();
        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL)
            toolbar_abtcourse.getNavigationIcon().setAutoMirrored(true);
        toolbar_abtcourse.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
