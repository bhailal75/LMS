package com.example.bhoomi.lms.Student.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Student.Activty.Announcement_Activity;
import com.example.bhoomi.lms.Student.Activty.QnA_Activity;
import com.example.bhoomi.lms.Student.Constants.MyMediumText;
import com.example.bhoomi.lms.Student.Constants.MyRegularText;
import com.example.bhoomi.lms.Student.Model.AnnouncementModel;
import com.example.bhoomi.lms.Student.Model.QnAModel;

import java.util.ArrayList;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.ViewHolder> {

    private ArrayList<AnnouncementModel> dataSet;
    private Context context;


    public AnnouncementAdapter(Announcement_Activity dashboard_activity, ArrayList<AnnouncementModel> qnaArrayList) {
        this.context = dashboard_activity;
        this.dataSet = qnaArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_announcement_item, parent, false);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        AnnouncementModel categoryModel = dataSet.get(position);


        holder.textView_uname.setText(categoryModel.getUsername());
        holder.textView_ans.setText(categoryModel.getAns());
        holder.textView_time.setText(categoryModel.getTime());
        holder.textView_que.setText(categoryModel.getQue());

    }





    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private MyRegularText textView_uname, textView_ans, textView_response, textView_time;
        private MyMediumText textView_que;

        public ViewHolder(View itemView) {
            super(itemView);


            textView_uname = itemView.findViewById(R.id.text_uname);
            textView_time = itemView.findViewById(R.id.text_time);
            textView_ans = itemView.findViewById(R.id.text_ans);

            textView_que = itemView.findViewById(R.id.text_que);



        }
    }
}

