package com.example.bhoomi.lms.Student.Constants;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyRegularText extends AppCompatTextView {

    public MyRegularText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyRegularText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyRegularText(Context context) {
        super(context);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/ubuntu_r.ttf");
        setTypeface(tf ,1);

    }
}
