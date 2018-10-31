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
import android.widget.TextView;

import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Student.Activty.QnA_Activity;
import com.example.bhoomi.lms.Student.Activty.WebDevelopment_Activity;
import com.example.bhoomi.lms.Student.Constants.MyMediumText;
import com.example.bhoomi.lms.Student.Constants.MyRegularText;
import com.example.bhoomi.lms.Student.Model.CategoryModel;
import com.example.bhoomi.lms.Student.Model.QnAModel;

import java.util.ArrayList;

public class QnAAdapter extends RecyclerView.Adapter<QnAAdapter.ViewHolder> {

    private ArrayList<QnAModel> dataSet;
    private Context context;


    public QnAAdapter(QnA_Activity dashboard_activity, ArrayList<QnAModel> qnaArrayList) {
        this.context = dashboard_activity;
        this.dataSet = qnaArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_qna_item, parent, false);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        QnAModel categoryModel = dataSet.get(position);


        holder.textView_uname.setText(categoryModel.getUsername());
        holder.textView_lecnum.setText(categoryModel.getLec_num());
        holder.textView_ans.setText(categoryModel.getAns());
        holder.textView_response.setText(categoryModel.getNumofResponse());
        holder.textView_time.setText(categoryModel.getTime());
        holder.textView_que.setText(categoryModel.getQue());

    }



    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private MyRegularText textView_uname, textView_lecnum, textView_ans, textView_response, textView_time;
        private MyMediumText textView_que;

        public ViewHolder(View itemView) {
            super(itemView);


            textView_uname = itemView.findViewById(R.id.text_uname);
            textView_time = itemView.findViewById(R.id.text_time);
            textView_ans = itemView.findViewById(R.id.text_ans);
            textView_response = itemView.findViewById(R.id.text_response);
            textView_lecnum = itemView.findViewById(R.id.text_lecno);

            textView_que = itemView.findViewById(R.id.text_que);



        }
    }
}

