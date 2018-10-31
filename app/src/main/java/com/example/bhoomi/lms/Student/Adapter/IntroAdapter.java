package com.example.bhoomi.lms.Student.Adapter;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.bhoomi.lms.Student.Fragment.IntroFragment;

public class IntroAdapter extends FragmentPagerAdapter {

    public IntroAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return IntroFragment.newInstance(Color.parseColor("#ffffff"), position); // blue
            case 1:
                return IntroFragment.newInstance(Color.parseColor("#ffffff"), position); // green
            default:
                return IntroFragment.newInstance(Color.parseColor("#ffffff"), position); // green
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

}
