package com.kenjoel.letsmeet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.kenjoel.letsmeet.R;

public class sliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public sliderAdapter(Context context) {
        this.context = context;
    }

    //Arrays for storing the pages



    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return false;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.)
    }
}
