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
import com.example.bhoomi.lms.Student.Activty.AboutCourse_Activity;
import com.example.bhoomi.lms.Student.Activty.Announcement_Activity;
import com.example.bhoomi.lms.Student.Activty.QnA_Activity;
import com.example.bhoomi.lms.Student.Activty.WebDevelopment_Activity;
import com.example.bhoomi.lms.Student.Constants.MyMediumText;
import com.example.bhoomi.lms.Student.Model.LecturesModel;
import com.example.bhoomi.lms.Student.Model.MoreLecModel;

import java.util.ArrayList;

public class MoreLecAdapter extends RecyclerView.Adapter<MoreLecAdapter.ViewHolder> {

    private ArrayList<MoreLecModel> dataSet;
    private Context context;

    public MoreLecAdapter(Activity dashboard_activity, ArrayList<MoreLecModel> categoryModelArrayList) {
        this.context = dashboard_activity;
        this.dataSet = categoryModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_morelec_item, parent, false);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MoreLecModel categoryModel = dataSet.get(position);

        holder.textView_lectitle.setText(categoryModel.getTitle());
    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private MyMediumText textView_lectitle;


        public ViewHolder(View itemView) {
            super(itemView);


            textView_lectitle = itemView.findViewById(R.id.textView_moretitle);


            Typeface typeface_medium = Typeface.createFromAsset(context.getAssets(), "fonts/ubuntu_m.ttf");
            textView_lectitle.setTypeface(typeface_medium);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int i = getAdapterPosition();

                    if (i == 0)
                    {
                        context.startActivity(new Intent(context, AboutCourse_Activity.class));
                    }
                    else if (i == 1)
                    {
                        context.startActivity(new Intent(context, QnA_Activity.class));

                    }
                    else if (i == 2)
                    {
                        context.startActivity(new Intent(context, Announcement_Activity.class));

                    }
                    else
                    {

                    }

                }
            });
        }

    }
}
