package com.example.bhoomi.lms.Student.Adapter;

import android.app.Activity;
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
import com.example.bhoomi.lms.APIModel.Celebrity.CelebrityData;
import com.example.bhoomi.lms.APIModel.WishList.WishListData;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Student.Constants.CircularImageView;

import java.util.ArrayList;

public class CelebrityAdapter extends RecyclerView.Adapter<CelebrityAdapter.ViewHolder> {
    private Activity context;
    private ViewCelebrityClick viewCelebrityClick;
    private ArrayList<CelebrityData> dataArrayList;

    public CelebrityAdapter(Activity activity, ArrayList<CelebrityData> celebrityArraylist, ViewCelebrityClick  viewCelebrityClick) {
        this.context = activity;
        this.viewCelebrityClick = viewCelebrityClick;
        this.dataArrayList = celebrityArraylist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_celebrity_adapter, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CelebrityData courseModel = dataArrayList.get(position);


        if (courseModel.getProfileImage() != null)
            Glide.with(context)
                    .load(courseModel.getProfileImage())
                    .placeholder(R.drawable.profile_icon)
                    .into(holder.imageView_celebrity);

        if (courseModel.getUserName() != null)
            holder.textView_celebrityName.setText(courseModel.getUserName());
        if (courseModel.getUserDesc() != null && courseModel.getAboutTheInstructor() != null)
            holder.textView_celebrityDesignation.setText(courseModel.getUserDesc());
        holder.ll_celebrity.setTag(position);

    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout ll_celebrity;
        private CircularImageView imageView_celebrity;
        private RatingBar rating_course;
        private TextView textView_celebrityName, textView_celebrityDesignation;

        public ViewHolder(View itemView) {
            super(itemView);
            ll_celebrity = itemView.findViewById(R.id.ll_celebrity);
            imageView_celebrity = itemView.findViewById(R.id.image_celebrity_adapter);
            textView_celebrityName = itemView.findViewById(R.id.text_celebrity_name);
            textView_celebrityDesignation = itemView.findViewById(R.id.text_celebrity_designation);

            Typeface typeface_bold = Typeface.createFromAsset(context.getAssets(), "fonts/ubuntu_b.ttf");
            textView_celebrityName.setTypeface(typeface_bold);
            textView_celebrityDesignation.setTypeface(typeface_bold);
            Typeface typeface_reglr = Typeface.createFromAsset(context.getAssets(), "fonts/ubuntu_r.ttf");

            ll_celebrity.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == ll_celebrity) {
                if (viewCelebrityClick != null) {
                    int pos = (int) v.getTag();
                    viewCelebrityClick.onCelebrityClick(pos);
                }
            }
        }
    }

    public interface ViewCelebrityClick{
        void onCelebrityClick(int pos);
    }
}
