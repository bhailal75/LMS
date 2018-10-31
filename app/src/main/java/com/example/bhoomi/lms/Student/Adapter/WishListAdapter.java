package com.example.bhoomi.lms.Student.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bhoomi.lms.APIModel.CourseDetails.StudentAlsoView;
import com.example.bhoomi.lms.APIModel.WishList.WishListData;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Student.Activty.WishList_Activity;

import java.util.ArrayList;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.ViewHolder> {
    private Activity context;
    private ViewWhisListClick viewWhisListClick;
    private ArrayList<WishListData> dataArrayList;

    public WishListAdapter(Activity activity, ArrayList<WishListData> wishlistArraylist, ViewWhisListClick  viewWhisListClick) {
        this.context = activity;
        this.viewWhisListClick = viewWhisListClick;
        this.dataArrayList = wishlistArraylist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_courselist, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        WishListData courseModel = dataArrayList.get(position);


        if (courseModel.getCourseImage() != null)
            Glide.with(context)
                    .load(courseModel.getCourseImage())
                    .placeholder(R.drawable.profile_icon)
                    .into(holder.imageView_thumb);

        if (courseModel.getTotalUserRate() != null)
            holder.rating_course.setRating(Float.parseFloat(courseModel.getCourseRating()));

        holder.textView_courseName.setText(courseModel.getCourseTitle());
        if (courseModel.getCreatedByName() != null)
            holder.textView_courseSubtitle.setText(courseModel.getCreatedByName());
        if (courseModel.getCoursePrice() != null)
            holder.textView_oldPrice.setText("$" + String.format("%.02f",Float.parseFloat(courseModel.getCoursePrice())));
        if (courseModel.getCourseNewPrice() != null && courseModel.getCourseNewPrice().equals("0"))
            holder.textView_newPrice.setText(R.string.free);
        else
            holder.textView_newPrice.setText("$" + String.format("%.02f",Float.parseFloat(courseModel.getCourseNewPrice())));
        if (courseModel.getTotalUserRate() != null)
            holder.textView_courseRatingppl.setText("(" + courseModel.getTotalUserRate() + ")");

        holder.ll_wish.setTag(position);

    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout ll_wish;
        private ImageView imageView_thumb;
        private RatingBar rating_course;
        private TextView textView_courseName, textView_courseSubtitle, textView_courseRatingppl, textView_oldPrice, textView_newPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            ll_wish = itemView.findViewById(R.id.ll_course);
            imageView_thumb = itemView.findViewById(R.id.imageView_thumb);
            rating_course = itemView.findViewById(R.id.ratingbar_course);
            textView_courseName = itemView.findViewById(R.id.textView_coursename);
            textView_courseSubtitle = itemView.findViewById(R.id.textiew_coursesubtiitle);
            textView_courseRatingppl = itemView.findViewById(R.id.textView_ratingPpl);
            textView_newPrice = itemView.findViewById(R.id.textView_newPrice);
            textView_oldPrice = itemView.findViewById(R.id.textView_oldPrice);
            textView_oldPrice.setPaintFlags(textView_oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            Typeface typeface_bold = Typeface.createFromAsset(context.getAssets(), "fonts/ubuntu_b.ttf");
            textView_newPrice.setTypeface(typeface_bold);
            textView_courseName.setTypeface(typeface_bold);
            Typeface typeface_reglr = Typeface.createFromAsset(context.getAssets(), "fonts/ubuntu_r.ttf");
            if (textView_courseSubtitle != null)
                textView_courseSubtitle.setTypeface(typeface_reglr);
            if (textView_oldPrice != null)
                textView_oldPrice.setTypeface(typeface_reglr);
            if (textView_courseRatingppl != null)
                textView_courseRatingppl.setTypeface(typeface_reglr);
            ll_wish.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == ll_wish) {
                if (viewWhisListClick != null) {
                    int pos = (int) v.getTag();
                    viewWhisListClick.onWishListClick(pos);
                }
            }
        }
    }

    public interface ViewWhisListClick{
        void onWishListClick(int pos);
    }
}
