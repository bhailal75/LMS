package com.example.bhoomi.lms.Student.Fragment;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bhoomi.lms.APIModel.Celebrity.CelebrityData;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Student.Constants.CircularImageView;

@SuppressLint("ValidFragment")
public class CelebrityDetailFragment extends Fragment {
    private CelebrityData celebrityData;
    private CircularImageView imageCelebrity;
    private TextView txtTotalStudent, txtTotalCourse, txtTotalreview, txtCelebrityDescriptin;
    private Toolbar toolbar;

    public CelebrityDetailFragment() {
    }

    public CelebrityDetailFragment(CelebrityData celebrityData) {
        this.celebrityData = celebrityData;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_celebrity, container, false);
        toolbar = view.findViewById(R.id.toolbar_celebrity_fragment);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        Configuration config = getActivity().getResources().getConfiguration();
        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL)
            toolbar.getNavigationIcon().setAutoMirrored(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        imageCelebrity = view.findViewById(R.id.image_celebrity_fragment);
        txtTotalStudent = view.findViewById(R.id.txt_total_student);
        txtTotalCourse = view.findViewById(R.id.txt_courses);
        txtTotalreview = view.findViewById(R.id.txt_review);
        txtCelebrityDescriptin = view.findViewById(R.id.txt_celebrity_description);

        if (celebrityData != null) {
            Glide.with(getActivity())
                    .load(celebrityData.getProfileImage())
                    .placeholder(R.drawable.profile_icon)
                    .into(imageCelebrity);
            txtTotalStudent.setText(celebrityData.getTotalstudents());
            txtTotalCourse.setText(celebrityData.getTotalcourse());
            txtTotalreview.setText(celebrityData.getReviews());
            txtCelebrityDescriptin.setText(celebrityData.getAboutTheInstructor());
        }
        return view;
    }
}



