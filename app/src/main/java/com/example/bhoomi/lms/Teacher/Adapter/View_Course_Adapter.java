package com.example.bhoomi.lms.Teacher.Adapter;

import android.content.Context;
import android.graphics.Paint;
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
import com.example.bhoomi.lms.APIModel.ViewCourse.ViewCourseList;
import com.example.bhoomi.lms.APIModel.ViewCourse.ViewCoursesInfo;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Teacher.Activity.CardAdapter;
import com.example.bhoomi.lms.Teacher.Activity.ViewCourse_Activity;

import java.util.List;

import retrofit2.Callback;

public class View_Course_Adapter extends RecyclerView.Adapter<View_Course_Adapter.ViewHolder> {


    private List<ViewCoursesInfo> dataSet;
    private Context context;
    private ViewCourseClickListner viewCourseClickListner;

    public View_Course_Adapter(ViewCourse_Activity context, List<ViewCoursesInfo> listViewCourseLists, ViewCourseClickListner viewCourseClickListner) {
        this.context = context;
        this.dataSet = listViewCourseLists;
        this.viewCourseClickListner = viewCourseClickListner;
    }

    @NonNull
    @Override
    public View_Course_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_my_cource, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull View_Course_Adapter.ViewHolder holder, int position) {

        ViewCoursesInfo viewCoursesInfo = dataSet.get(position);
        System.out.println("coursedata " + viewCoursesInfo.getCreatedBy());
        Glide.with(context)
                .load(viewCoursesInfo.getCourseImage())
                .placeholder(R.drawable.profile_icon)
                .into(holder.imageView_thumb);
        holder.Sub_category_name.setText(viewCoursesInfo.getSubcategoryName());
        holder.Course_name.setText(viewCoursesInfo.getCourseTitle());
        if (viewCoursesInfo.getCourseNewPrice().equals("0"))
            holder.textView_newPrice.setText("Free");
        else
            holder.textView_newPrice.setText("$ " + viewCoursesInfo.getCourseNewPrice());
        if (viewCoursesInfo.getCoursePrice().equals("0"))
            holder.textView_oldPrice.setText("");
        else
            holder.textView_oldPrice.setText("$ " + viewCoursesInfo.getCoursePrice());
        holder.textView_oldPrice.setPaintFlags(holder.textView_oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.ll_course.setTag(position);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageView_thumb;
        public TextView Course_name;
        public TextView Sub_category_name, textView_oldPrice, textView_newPrice;
        public LinearLayout ll_course;

        public ViewHolder(View itemView) {
            super(itemView);
            ll_course = itemView.findViewById(R.id.ll_course);
            imageView_thumb = itemView.findViewById(R.id.imgview_my_cource);
            textView_newPrice = itemView.findViewById(R.id.text_cource_new_price);
            textView_oldPrice = itemView.findViewById(R.id.text_course_old_price);
            Course_name = itemView.findViewById(R.id.text_cource_type);
            Sub_category_name = itemView.findViewById(R.id.text_cource_name);
            ll_course.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (v == ll_course){
                if (viewCourseClickListner != null){
                    int pos = (int) v.getTag();
                    viewCourseClickListner.onCourseClick(pos);
                }
            }
        }
    }
    public interface ViewCourseClickListner {
        void onCourseClick(int pos);
    }
}
