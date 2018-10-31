package com.example.bhoomi.lms.Student.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.bhoomi.lms.APIModel.Slider.SliderData;
import com.example.bhoomi.lms.R;

import java.util.ArrayList;

public class SliderAdapter extends PagerAdapter {

    private ArrayList<SliderData> sliderData;
    private LayoutInflater inflater;
    private Context context;

    public SliderAdapter(Context context, ArrayList<SliderData> sliderData) {
        this.context = context;
        this.sliderData=sliderData;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return sliderData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View myImageLayout = inflater.inflate(R.layout.layout_slider, container, false);
        ImageView myImage = (ImageView) myImageLayout
                .findViewById(R.id.image);
        Glide.with(context)
                .load(sliderData.get(position).getSliderAr())
                .placeholder(R.drawable.profile_icon)
                .into(myImage);
//        myImage.setImageResource(sliderData.get(position).);
        container.addView(myImageLayout, 0);
        return myImageLayout;    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);

    }
}
