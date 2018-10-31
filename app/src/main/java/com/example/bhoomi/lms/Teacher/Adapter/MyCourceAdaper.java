package com.example.bhoomi.lms.Teacher.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.bhoomi.lms.APIModel.ViewCourse.ViewCoursesInfo;

import java.util.List;

public class MyCourceAdaper extends RecyclerView.Adapter<MyCourceAdaper.ViewHolder> {
    private Context context;
    private List<ViewCoursesInfo> dataSet;

    public MyCourceAdaper(){

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
