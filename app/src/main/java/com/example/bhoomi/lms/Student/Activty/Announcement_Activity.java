package com.example.bhoomi.lms.Student.Activty;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Student.Adapter.AnnouncementAdapter;
import com.example.bhoomi.lms.Student.Adapter.QnAAdapter;
import com.example.bhoomi.lms.Student.Model.AnnouncementModel;
import com.example.bhoomi.lms.Student.Model.QnAModel;

import java.util.ArrayList;

public class Announcement_Activity extends AppCompatActivity {


    private Toolbar toolbar_announcement;
    private RecyclerView recyclerView_announcement;
    private AnnouncementAdapter mAdapter;
    private ArrayList<AnnouncementModel> announcementModelArrayList;
    private AnnouncementModel announcementModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_);

        toolbar_announcement = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_announcement);
        toolbar_announcement.setTitle("");
        toolbar_announcement.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar_announcement);

        toolbar_announcement.setNavigationIcon(R.mipmap.ic_chevron_left_white_24dp);

        toolbar_announcement.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        announcementModel = new AnnouncementModel();
        announcementModelArrayList = new ArrayList<>();

        recyclerView_announcement = findViewById(R.id.recyclerView_announcement);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView_announcement.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView


        for (int i = 0; i < 6; i ++)
        {
            announcementModel.setUsername("Franscaa");
            announcementModel.setQue("Help is neeeded to access documents");
            announcementModel.setAns("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
            announcementModel.setTime("April 5, 3:20 am");
            announcementModelArrayList.add(announcementModel);
        }

        mAdapter = new AnnouncementAdapter(this,announcementModelArrayList);
        recyclerView_announcement.setAdapter(mAdapter);
    }


}
