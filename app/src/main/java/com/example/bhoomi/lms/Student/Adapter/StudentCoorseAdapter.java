package com.example.bhoomi.lms.Student.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bhoomi.lms.APIModel.StudentCourseList.MyCourseData;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Student.Activty.WebDevelopment_Activity;

import java.util.ArrayList;

public class StudentCoorseAdapter extends RecyclerView.Adapter<StudentCoorseAdapter.ViewHolder> {

    private ArrayList<MyCourseData> dataSet;
    private Context context;
    private ViewCourseClickListner viewCourseClickListner;

    public StudentCoorseAdapter(Activity context, ArrayList<MyCourseData> categoryModelArrayList, ViewCourseClickListner viewCourseClickListner) {
        this.context = context;
        this.dataSet = categoryModelArrayList;
        this.viewCourseClickListner = viewCourseClickListner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_student_course, parent, false);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentCoorseAdapter.ViewHolder holder, int position) {
        MyCourseData categoryModel = dataSet.get(position);
        Glide.with(context)
                .load(categoryModel.getCourseImage())
                .placeholder(R.drawable.profile_icon)
                .into(holder.imageView_thumb);
        holder.textView_courseName.setText(categoryModel.getCourseTitle());
        holder.textView_courseCreated.setText(categoryModel.getCreatedByName());
        if (categoryModel.getCourseComplete().equals("1"))
            holder.textView_complete.setText(R.string.complete);
        else
            holder.textView_complete.setText(R.string.incomplete);
        holder.ll_student_course.setTag(position);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView_courseName, textView_courseCreated, textView_complete;
        private ImageView imageView_thumb;
        private RelativeLayout ll_student_course;

        public ViewHolder(View itemView) {
            super(itemView);

            ll_student_course = itemView.findViewById(R.id.llStudentCourse);
            imageView_thumb = itemView.findViewById(R.id.imgview_student_cource);
            textView_courseName = itemView.findViewById(R.id.txtCourseTitle);
            textView_courseCreated = itemView.findViewById(R.id.txtCourseCreatedBy);
            textView_complete = itemView.findViewById(R.id.txtCourseComplete);
            Typeface typeface_medium = Typeface.createFromAsset(context.getAssets(), "fonts/ubuntu_m.ttf");
            textView_courseName.setTypeface(typeface_medium);
            textView_courseCreated.setTypeface(typeface_medium);
            textView_complete.setTypeface(typeface_medium);
            ll_student_course.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == ll_student_course) {
                if (viewCourseClickListner != null) {
                    int pos = (int) v.getTag();
                    viewCourseClickListner.onCourseClick(pos);
                }
            }
        }
    }
    public interface ViewCourseClickListner {
        void onCourseClick(int pos);
//            void onQuizDelete(int pos);
    }

}

