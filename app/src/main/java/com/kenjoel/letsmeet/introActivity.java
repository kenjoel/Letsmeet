package com.kenjoel.letsmeet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.kenjoel.letsmeet.adapters.sliderAdapter;
import com.kenjoel.letsmeet.fragments.Login;
import com.kenjoel.letsmeet.fragments.signup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class introActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager viewPager;

    private sliderAdapter mSliderAdapter;
    private Button start;
    private int mCurrentPage;


    @BindView(R.id.mStartbtn)
    Button starto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);

        viewPager = findViewById(R.id.viewPager);

        mSliderAdapter = new sliderAdapter(this);
        viewPager.setAdapter(mSliderAdapter);
        start = starto;
        viewPager.addOnPageChangeListener(pageChangeListener);
        start.setOnClickListener(this);
    }

    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mCurrentPage=position;
            if(position > 0){
                start.setEnabled(true);
                start.setVisibility(View.VISIBLE);
            }else{
                start.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(introActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}