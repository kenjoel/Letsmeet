package com.kenjoel.letsmeet;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class SignupActivityInstrumentedTest {

    @Rule
    public ActivityTestRule<SignupActivity> activityTestRule =
            new ActivityTestRule<>(SignupActivity.class);


    @Test
    public void validateEditText() {
        onView(withId(R.id.name)).perform(typeText("Ken"))
                .check(matches(withText("Ken")));}

//    @Test
//    public void theTextsAreReceived(){
//        String name = "Abdul";
//        onView(withId(R.id.name)).perform(typeText(name)).perform(closeSoftKeyboard());
//        try {                             // the sleep method requires to be checked and handled so we use try block
//            Thread.sleep(250);
//        } catch (InterruptedException e){
//            System.out.println("got interrupted!");
//        }
//        onView(withId(R.id.launch)).perform(click());
//        onView(withId(R.id.thisList)).check(matches
//                (withText(name +" Welcome to Connect, check you email email to activate account ")));
//    }
}
