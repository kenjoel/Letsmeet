package com.kenjoel.letsmeet;

import android.content.Intent;
import android.widget.TextView;

import com.kenjoel.letsmeet.authentication.SignupActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

    private MainActivity activity;


    @Before
    public void setUp(){
        activity = Robolectric.setupActivity(MainActivity.class);
    }

    @Config(manifest=Config.NONE)
    @Test
    public void validateTextViewContent(){
        TextView connect = activity.findViewById(R.id.Connect);
        assertTrue("Connect".equals(connect.getText().toString()));
    }

    @Config(manifest=Config.NONE)
    @Test
    public void secondActivityStarted(){
        activity.findViewById(R.id.creation).performClick();
        Intent expectedIntent = new Intent(activity, SignupActivity.class);
        ShadowActivity shadowActivity = org.robolectric.Shadows.shadowOf(activity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertTrue(actualIntent.filterEquals(expectedIntent));
    }

    //mvvm - cleancode(modulariation)
}
