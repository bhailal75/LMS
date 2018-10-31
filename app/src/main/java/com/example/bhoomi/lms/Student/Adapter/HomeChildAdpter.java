package com.example.bhoomi.lms.Student.Adapter;

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
import com.example.bhoomi.lms.APIModel.Categories.CourseDetail;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Student.Activty.CourseView_Activity;

import java.text.DecimalFormat;
import java.util.List;

public class HomeChildAdpter extends RecyclerView.Adapter<HomeChildAdpter.ViewHolder> {

    private Context context;
//    private List<CourseDetail> courseDetailList;

    private List<CourseDetail> mDataList;
    private int mRowIndex = -1;

    public HomeChildAdpter(Context context) {
        this.context = context;
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

        CourseDetail courseDetail = mDataList.get(position);

        Glide.with(context)
                .load(courseDetail.getCourseImage())
                .placeholder(R.drawable.profile_icon)
                .into(holder.imageView_thumb);

        holder.rating_course.setRating(Float.parseFloat(courseDetail.getCourseRating()));
        holder.textView_courseName.setText(courseDetail.getCourseTitle());
        holder.textView_courseSubtitle.setText(courseDetail.getCourseIntro());
        holder.textView_courseRating.setText(String.valueOf(courseDetail.getCourseRating()));
        holder.textView_courseRatingppl.setText("(" + courseDetail.getTotalUserRate() + ")");
        holder.textView_oldPrice.setText("$" + String.format("%.02f",Float.parseFloat(courseDetail.getCoursePrice())));
        if (courseDetail.getCourseNewPrice() != null && courseDetail.getCourseNewPrice().equals("0"))
            holder.textView_newPrice.setText(R.string.free);
        else
            holder.textView_newPrice.setText("$" + String.format("%.02f", Float.parseFloat(courseDetail.getCourseNewPrice())));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void setData(List<CourseDetail> categoryId) {
        if (mDataList != categoryId) {
            mDataList = categoryId;
            notifyDataSetChanged();
        }
    }

    public void setRowIndex(int index) {
        mRowIndex = index;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
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


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int i = getAdapterPosition();

                    Intent intent = new Intent(context, CourseView_Activity.class);
                    intent.putExtra("course_id", mDataList.get(i).getCourseId());
                    context.startActivity(intent);
                }
            });

        }
    }
}
