package com.example.bhoomi.lms.Teacher.Activity;
import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.bhoomi.lms.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {


    //Imageloader to load image
    private ImageLoader imageLoader;
    private Context context;

    //List to store all superheroes
    List<ViewCourseList> viewCourseLists;

    //Constructor of this class
    public CardAdapter(List<ViewCourseList> superHeroes, Context context){
        super();
        //Getting all superheroes
        this.viewCourseLists = superHeroes;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_courselist, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //Getting the particular item from the list
        ViewCourseList viewCourseList =  viewCourseLists.get(position);

        //Loading image from url
        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(viewCourseList.getCourse_image(), ImageLoader.getImageListener(holder.imageView, R.drawable.slide_1, android.R.drawable.ic_dialog_alert));

        //Showing data on the views
        holder.imageView.setImageUrl(viewCourseList.getCourse_image(), imageLoader);
        holder.textViewName.setText(viewCourseList.getCategory_name());
        holder.textViewPublisher.setText(viewCourseList.getSubcategory_name());

    }

    @Override
    public int getItemCount() {
        return viewCourseLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        //Views
        public NetworkImageView imageView;
        public TextView textViewName;
        public TextView textViewPublisher;

        //Initializing Views
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (NetworkImageView) itemView.findViewById(R.id.imageView_thumb);
            textViewName = (TextView) itemView.findViewById(R.id.textView_coursename);
            textViewPublisher = (TextView) itemView.findViewById(R.id.textView_coursesubtiitle);
        }
    }
}