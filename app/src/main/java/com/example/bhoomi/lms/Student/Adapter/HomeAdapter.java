package com.example.bhoomi.lms.Student.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bhoomi.lms.APIModel.Categories.CourseDetail;
import com.example.bhoomi.lms.APIModel.Categories.HomeInfo;
import com.example.bhoomi.lms.APIModel.SubCategories.PopularCourse;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Student.Activty.CourseView_Activity;
import com.example.bhoomi.lms.Student.Activty.SeeAllCourse_Activity;
import com.example.bhoomi.lms.Student.Activty.WebDevelopment_Activity;
import com.example.bhoomi.lms.Student.Constants.MyBoldText;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private static RecyclerView horizontalList;
    List<CourseDetail> courseDetails;
    private ArrayList<HomeInfo> dataSet;
    private Context context;
    private HomeChildAdpter homeChildAdpter;

    public HomeAdapter(Activity dashboard_activity, ArrayList<HomeInfo> courseModelArrayList) {
        this.context = dashboard_activity;
        this.dataSet = courseModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_home_item, parent, false);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        HomeInfo courseModel = dataSet.get(position);

        courseDetails = new ArrayList<>();
        courseDetails = courseModel.getCourseDetail();
        homeChildAdpter.setData(courseModel.getCourseDetail()); // List of Strings
        homeChildAdpter.setRowIndex(position);
        Configuration config = context.getResources().getConfiguration();
        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL)
            holder.textView_title.setText(" "+courseModel.getCategoryNameAr());
        else
            holder.textView_title.setText(" "+courseModel.getCategoryName());
        if ((position % 2 == 0)) {
            holder.textView_title.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            holder.textView_seeall.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        } else {
            holder.textView_title.setTextColor(ContextCompat.getColor(context, R.color.colorgPurple));
            holder.textView_seeall.setTextColor(ContextCompat.getColor(context, R.color.colorgPurple));
        }
        int[] rainbow = context.getResources().getIntArray(R.array.rainbow);

//        for (int i = 0; i < rainbow.length; i++) {
//            holder.textView_title.setTextColor(rainbow[i]);
//            // Do something with the paint.
//        }

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void updateSubCategories(List<HomeInfo> popularCourses) {
        dataSet = (ArrayList<HomeInfo>) popularCourses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private MyBoldText textView_title, textView_seeall;
        private RecyclerView recyclerView_child;


        public ViewHolder(View itemView) {
            super(itemView);


            textView_title = itemView.findViewById(R.id.textview_title);
            textView_seeall = itemView.findViewById(R.id.textview_viewall);
            recyclerView_child = itemView.findViewById(R.id.recyclerView_homechild);
            recyclerView_child.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            homeChildAdpter = new HomeChildAdpter(context);
            recyclerView_child.setAdapter(homeChildAdpter);
            recyclerView_child.setHasFixedSize(true);
            recyclerView_child.setNestedScrollingEnabled(false);


            textView_seeall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int i = getAdapterPosition();

                    Intent intent = new Intent(context, SeeAllCourse_Activity.class);
                    intent.putExtra("cat_id", dataSet.get(i).getCategoryId());
                    Configuration config = context.getResources().getConfiguration();
                    if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL)
                        intent.putExtra("cat_name", dataSet.get(i).getCategoryNameAr());
                    else
                        intent.putExtra("cat_name", dataSet.get(i).getCategoryName());
                    context.startActivity(intent);
                }
            });

        }
    }
}

