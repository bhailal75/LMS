package com.example.bhoomi.lms.Student.Constants;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class MyBoldText extends AppCompatTextView {

    public MyBoldText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyBoldText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyBoldText(Context context) {
        super(context);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/ubuntu_b.ttf");
        setTypeface(tf ,1);

    }
}
