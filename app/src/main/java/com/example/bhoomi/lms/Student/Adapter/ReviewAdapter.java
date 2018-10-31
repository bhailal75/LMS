package com.example.bhoomi.lms.Student.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.bhoomi.lms.APIModel.CourseDetails.RateInfo;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Student.Activty.Review_Activity;
import com.example.bhoomi.lms.Student.Activty.WebDevelopment_Activity;
import com.example.bhoomi.lms.Student.Model.CategoryModel;
import com.example.bhoomi.lms.Student.Model.ReviewModel;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private ArrayList<RateInfo> dataSet;
    private Context context;

    public ReviewAdapter(Activity dashboard_activity, List<RateInfo> categoryModelArrayList) {
        this.context = dashboard_activity;
        this.dataSet = (ArrayList<RateInfo>) categoryModelArrayList;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_review_item, parent, false);

       ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RateInfo reviewModel = dataSet.get(position);


        holder.textView_comments.setText(reviewModel.getComment());
        holder.textView_author.setText(reviewModel.getUserName());
        holder.textView_time.setText(reviewModel.getCreatedDateTime());

        holder.ratingBar.setRating(Float.parseFloat(reviewModel.getNoOfRate()));
    }



    @Override
    public int getItemCount() {
        if (dataSet.size()>3)
        {
            return 3;
        }
        else
        {
            return dataSet.size();
        }

    }

    public void updateReview(List<RateInfo> rateInfo) {
        this.dataSet = (ArrayList<RateInfo>) rateInfo;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView textView_comments, textView_author, textView_time;
        private RatingBar ratingBar;


        public ViewHolder(View itemView) {
            super(itemView);


            textView_comments = itemView.findViewById(R.id.textView_comment);
            textView_author = itemView.findViewById(R.id.textView_uname);
            textView_time = itemView.findViewById(R.id.textView_time);

            ratingBar = itemView.findViewById(R.id.ratingbar_course);


            Typeface typeface_medium= Typeface.createFromAsset(context.getAssets(), "fonts/ubuntu_m.ttf");
//            textView_catName.setTypeface(typeface_medium);


        }
    }
}
