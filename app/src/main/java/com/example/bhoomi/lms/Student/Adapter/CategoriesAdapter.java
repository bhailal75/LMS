package com.example.bhoomi.lms.Student.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bhoomi.lms.APIModel.SubCategories.SubCategoryInfo;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Student.Activty.WebDevelopment_Activity;
import com.example.bhoomi.lms.Student.Model.CategoryModel;

import java.util.ArrayList;
import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private ArrayList<SubCategoryInfo> dataSet;
    private Context context;

    public CategoriesAdapter(Activity dashboard_activity, ArrayList<SubCategoryInfo> categoryModelArrayList) {
        this.context = dashboard_activity;
        this.dataSet = categoryModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_category_item, parent, false);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        SubCategoryInfo categoryModel = dataSet.get(position);
        Configuration config = context.getResources().getConfiguration();
        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL)
            holder.textView_catName.setText(categoryModel.getSubCatNameAr());
        else
            holder.textView_catName.setText(categoryModel.getSubCatName());
    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void updateSubCategories(List<SubCategoryInfo> subCategoryInfo) {
        dataSet = (ArrayList<SubCategoryInfo>) subCategoryInfo;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView textView_catName;


        public ViewHolder(View itemView) {
            super(itemView);


            textView_catName = itemView.findViewById(R.id.textView_category);

            Typeface typeface_medium = Typeface.createFromAsset(context.getAssets(), "fonts/ubuntu_m.ttf");
            textView_catName.setTypeface(typeface_medium);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int i = getAdapterPosition();

                    Intent intent = new Intent(context, WebDevelopment_Activity.class);
                    intent.putExtra("Type", "Web Development");
                    intent.putExtra("cat_id", dataSet.get(i).getId());
                    Configuration config = context.getResources().getConfiguration();
                    if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL)
                        intent.putExtra("cat_name", dataSet.get(i).getSubCatNameAr());
                    else
                        intent.putExtra("cat_name", dataSet.get(i).getSubCatName());
                    context.startActivity(intent);
                }
            });

        }
    }
}
