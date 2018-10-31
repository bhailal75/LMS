package com.example.bhoomi.lms.Teacher.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.bhoomi.lms.APIModel.LiveClass.LiveClassData;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Teacher.Activity.LiveClass_Activity;
import java.util.List;

public class Live_Class_Adapter extends RecyclerView.Adapter<Live_Class_Adapter.ViewHolder> {
    private List<LiveClassData> dataSet;
    private Context context;
    private ViewLiveClassClickListner viewLiveClassClickListner;

    public Live_Class_Adapter(LiveClass_Activity context, List<LiveClassData> listLivedata, ViewLiveClassClickListner viewLiveClassClickListner) {
        this.context = context;
        this.dataSet = listLivedata;
        this.viewLiveClassClickListner = viewLiveClassClickListner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_live_class, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Live_Class_Adapter.ViewHolder holder, int position) {

        LiveClassData liveClassData = dataSet.get(position);
        Glide.with(context)
                .load(liveClassData.getProfileImage())
                .placeholder(R.drawable.profile_icon)
                .into(holder.imageView_thumb);
        holder.text_liveclass_name.setText(liveClassData.getClassTitle());
        holder.text_liveclass_status.setText(liveClassData.getStatus());
        holder.text_liveclass_tutor_name.setText("By " + liveClassData.getTuterName());
        holder.text_liveclass_datetime.setText(liveClassData.getDate() + " | " + liveClassData.getStartTime() + " -");
        holder.text_liveclass_endtime.setText(liveClassData.getEndTime());
        holder.text_liveclass_time_difference.setText("(" + liveClassData.getTimeDifference() + ")");
        holder.text_liveclass_timezon.setText(liveClassData.getTimeZone());
        holder.text_liveclass_price.setText(liveClassData.getPrice());
        holder.ll_liveclass.setTag(position);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public interface ViewLiveClassClickListner {
        void onLiveClassClick(int pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageView_thumb;
        public TextView text_liveclass_name, text_liveclass_status, text_liveclass_tutor_name, text_liveclass_datetime;
        public TextView text_liveclass_endtime, text_liveclass_time_difference, text_liveclass_timezon, text_liveclass_price;
        public LinearLayout ll_liveclass;

        public ViewHolder(View itemView) {
            super(itemView);
            ll_liveclass = itemView.findViewById(R.id.ll_liveclass);
            imageView_thumb = itemView.findViewById(R.id.imgview_my_liveclass);
            text_liveclass_name = itemView.findViewById(R.id.text_liveclass_name);
            text_liveclass_status = itemView.findViewById(R.id.text_liveclass_status);
            text_liveclass_tutor_name = itemView.findViewById(R.id.text_liveclass_tutor_name);
            text_liveclass_datetime = itemView.findViewById(R.id.text_liveclass_datetime);
            text_liveclass_endtime = itemView.findViewById(R.id.text_liveclass_end_time);
            text_liveclass_time_difference = itemView.findViewById(R.id.text_liveclass_difference_date);
            text_liveclass_timezon = itemView.findViewById(R.id.text_liveclass_timezon);
            text_liveclass_price = itemView.findViewById(R.id.text_liveclass_price);

            ll_liveclass.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == ll_liveclass) {
                if (viewLiveClassClickListner != null) {
                    int pos = (int) v.getTag();
                    viewLiveClassClickListner.onLiveClassClick(pos);
                }
            }
        }
    }
}
