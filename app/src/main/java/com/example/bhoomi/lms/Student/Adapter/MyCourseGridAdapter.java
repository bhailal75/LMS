package com.example.bhoomi.lms.Student.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.bhoomi.lms.APIModel.CategoryList.CategoryInfo;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Student.Constants.MyRegularText;
import com.example.bhoomi.lms.Teacher.Adapter.Curriculum_Adapter;

import java.util.ArrayList;
import java.util.List;

public class MyCourseGridAdapter extends RecyclerView.Adapter<MyCourseGridAdapter.ViewHolder> {
    private static LayoutInflater inflater = null;
    private Activity _context;
    private ArrayList<CategoryInfo> categoryModelArrayList;
    private ViewGridClickListner viewGridClickListner;

    public MyCourseGridAdapter(Activity context, ArrayList<CategoryInfo> arrayList_categorydata, ViewGridClickListner viewGridClickListner) {
        this._context = context;
        this.categoryModelArrayList = arrayList_categorydata;
        this.viewGridClickListner = viewGridClickListner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_mycoursegrid_item, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryInfo categoryInfo = categoryModelArrayList.get(position);
        Glide.with(_context)
                .load(categoryInfo.getCategoryImage())
                .placeholder(R.drawable.profile_icon)
                .into(holder.imageView_grid);
        Configuration config = _context.getResources().getConfiguration();
        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL)
            holder.textView_grid.setText(categoryInfo.getCategoryNameAr());
        else
            holder.textView_grid.setText(categoryInfo.getCategoryName());
        holder.ll_grid.setTag(position);

    }

    @Override
    public int getItemCount() {
        return categoryModelArrayList.size();
    }

    public interface ViewGridClickListner {
        void onGridClick(int pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView_grid;
        private MyRegularText textView_grid;
        private RelativeLayout ll_grid;

        public ViewHolder(View v) {
            super(v);
            ll_grid = v.findViewById(R.id.ll_grid);
            imageView_grid = (ImageView) v.findViewById(R.id.grid_item_image);
            textView_grid = (MyRegularText) v.findViewById(R.id.grid_item_text);
            ll_grid.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == ll_grid) {
                if (viewGridClickListner != null) {
                    int pos = (int) v.getTag();
                    viewGridClickListner.onGridClick(pos);
                }
            }
        }
    }
}


