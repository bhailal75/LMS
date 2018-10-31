package com.example.bhoomi.lms.Student.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bhoomi.lms.APIModel.SubCategories.PopularCourse;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Student.Activty.CourseView_Activity;
import com.example.bhoomi.lms.Student.Activty.Dashboard_Activity;
import com.example.bhoomi.lms.Student.Model.CourseModel;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private ArrayList<PopularCourse> dataSet;
    private Context context;

    public CourseAdapter(Activity dashboard_activity, ArrayList<PopularCourse> courseModelArrayList) {
        this.context = dashboard_activity;
        this.dataSet = courseModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_layout_item, parent, false);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        PopularCourse courseModel = dataSet.get(position);

        Glide.with(context)
                .load(courseModel.getCourseImage())
                .placeholder(R.drawable.profile_icon)
                .into(holder.imageView_thumb);

        holder.rating_course.setRating((courseModel.getCourseRating()));
        holder.textView_courseName.setText(courseModel.getCourseTitle());
        holder.textView_courseSubtitle.setText(courseModel.getCourseIntro());
        holder.textView_courseRating.setText(String.valueOf(courseModel.getCourseRating()));
        holder.textView_courseRatingppl.setText("(" + courseModel.getTotalUserRate() + ")");
        holder.textView_oldPrice.setText("$" + String.format("%.02f", Float.parseFloat(courseModel.getCoursePrice())));
        if (courseModel.getCourseNewPrice() != null && courseModel.getCourseNewPrice().equals("0"))
            holder.textView_newPrice.setText(R.string.free);
        else
            holder.textView_newPrice.setText("$" + String.format("%.02f",Float.parseFloat(courseModel.getCourseNewPrice())));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void updateSubCategories(List<PopularCourse> popularCourses) {
        dataSet = (ArrayList<PopularCourse>) popularCourses;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView_thumb;
        private RatingBar rating_course;
        private TextView textView_courseName, textView_courseSubtitle,
                textView_courseRating, textView_courseRatingppl, textView_oldPrice, textView_newPrice;


        public ViewHolder(View itemView) {
            super(itemView);

            imageView_thumb = itemView.findViewById(R.id.imageView_thumb);
            rating_course = itemView.findViewById(R.id.ratingbar_course);
            textView_courseName = itemView.findViewById(R.id.textView_coursename);
            textView_courseSubtitle = itemView.findViewById(R.id.textView_coursesubtiitle);
            textView_courseRating = itemView.findViewById(R.id.textView_rating);
            textView_courseRatingppl = itemView.findViewById(R.id.textView_ratingPpl);
            textView_newPrice = itemView.findViewById(R.id.textView_newPrice);
            textView_oldPrice = itemView.findViewById(R.id.textView_oldPrice);

            textView_oldPrice.setPaintFlags(textView_oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


            Typeface typeface_bold = Typeface.createFromAsset(context.getAssets(), "fonts/ubuntu_b.ttf");
            textView_newPrice.setTypeface(typeface_bold);
            textView_courseName.setTypeface(typeface_bold);

            Typeface typeface_reglr = Typeface.createFromAsset(context.getAssets(), "fonts/ubuntu_r.ttf");
            textView_courseSubtitle.setTypeface(typeface_reglr);
            textView_oldPrice.setTypeface(typeface_reglr);
            textView_courseRating.setTypeface(typeface_reglr);
            textView_courseRatingppl.setTypeface(typeface_reglr);


//            Typeface typeface_medium= Typeface.createFromAsset(context.getAssets(), "fonts/ubuntu_m.ttf");
//            textInputLayout_user.setTypeface(typeface_medium);
//            textInputLayout_pwd.setTypeface(typeface_medium);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int i = getAdapterPosition();

                    Intent intent = new Intent(context, CourseView_Activity.class);
                    intent.putExtra("course_id", dataSet.get(i).getCourseId());
                    context.startActivity(intent);
                }
            });

        }
    }
}
