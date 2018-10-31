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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bhoomi.lms.APIModel.ViewCourse.ViewCoursesInfo;
import com.example.bhoomi.lms.APIModel.ViewQuiz.ViewQuizData;
import com.example.bhoomi.lms.APIModel.ViewQuiz.ViewQuizResp;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Teacher.Activity.ViewQuiz_Activity;

import java.util.List;

public class View_Quiz_Adapter extends RecyclerView.Adapter<View_Quiz_Adapter.ViewHolder> {

    private List<ViewQuizData> dataSet;
    private Context context;
    private ViewQuizClickListner viewQuizClickListner;

    public View_Quiz_Adapter(ViewQuiz_Activity context, List<ViewQuizData> listViewQuizList, ViewQuizClickListner viewQuizClickListner) {
        this.context = context;
        this.dataSet = listViewQuizList;
        this.viewQuizClickListner = viewQuizClickListner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_my_quiz, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull View_Quiz_Adapter.ViewHolder holder, int position) {

        ViewQuizData viewQuizInfo = dataSet.get(position);
        Glide.with(context)
                .load(viewQuizInfo.getImageUrl())
                .placeholder(R.drawable.profile_icon)
                .into(holder.imageView_thumb);
        holder.text_quiz_name.setText(viewQuizInfo.getTitleName());
        holder.text_quiz_syllabus.setText(viewQuizInfo.getSyllabus());
        holder.text_quiz_passmark.setText(viewQuizInfo.getPassMark());
        holder.text_quiz_category.setText(viewQuizInfo.getCategoryName());

        if (viewQuizInfo.getExamPrice().equals("0"))
            holder.text_quiz_price.setText("Free");
        else
            holder.text_quiz_price.setText("$ " + viewQuizInfo.getExamPrice());
        holder.ll_quiz.setTag(position);
        holder.imageview_delete_quiz.setTag(position);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageView_thumb,imageview_delete_quiz;
        public TextView text_quiz_name, text_quiz_syllabus, text_quiz_passmark,text_quiz_category,text_quiz_price;
        public LinearLayout ll_quiz;

        public ViewHolder(View itemView) {
            super(itemView);
            ll_quiz = itemView.findViewById(R.id.ll_quiz);
            imageView_thumb = itemView.findViewById(R.id.imgview_my_quiz);
            text_quiz_name = itemView.findViewById(R.id.text_quiz_name);
            text_quiz_syllabus = itemView.findViewById(R.id.text_quiz_syllabus);
            text_quiz_passmark = itemView.findViewById(R.id.text_quiz_passmark);
            text_quiz_category = itemView.findViewById(R.id.text_quiz_category);
            text_quiz_price = itemView.findViewById(R.id.text_quiz_price);
            imageview_delete_quiz = itemView.findViewById(R.id.imageview_delete_quiz);

            ll_quiz.setOnClickListener(this);
            imageview_delete_quiz.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (v == ll_quiz){
                if (viewQuizClickListner != null){
                    int pos = (int) v.getTag();
                    viewQuizClickListner.onQuizClick(pos);
                }
            }else if (v == imageview_delete_quiz){
                if (viewQuizClickListner != null){
                    int pos = (int) v.getTag();
                    viewQuizClickListner.onQuizDelete(pos);
                }
            }
        }
    }
    public interface ViewQuizClickListner {
        void onQuizClick(int pos);
        void onQuizDelete(int pos);
    }
}
