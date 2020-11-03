package com.kenjoel.letsmeet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    public int[] slide_image = {
            R.drawable.draw,
            R.drawable.hibi
    };

    public String[] slide_text = {
            "Find yourOtherHalf",
            "Meet, Like, Connect"
    };



    @Override
    public int getCount() {
        return slide_image.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.fragment_first, container,false);

        ImageView imageView = v.findViewById(R.id.firstImage);
        TextView texts = v.findViewById(R.id.otherHalf);

        imageView.setImageResource(slide_image[position]);
        texts.setText(slide_text[position]);

        container.addView(v);

        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((RelativeLayout)object);
    }
}
