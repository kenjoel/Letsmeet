package com.kenjoel.letsmeet;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
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

    @Test
    public void theTextsAreReceived(){
        String name = "Abdul";
        String email = "abdulfatah12@gmail.com";
        onView(withId(R.id.name)).perform(typeText(name)).perform(closeSoftKeyboard());
        onView(withId(R.id.email)).perform(typeText(email)).perform(closeSoftKeyboard());

        try {                             // the sleep method requires to be checked and handled so we use try block
            Thread.sleep(250);
        } catch (InterruptedException e){
            System.out.println("got interrupted!");
        }
        onView(withId(R.id.launch)).perform(click());
        withContentDescription(R.id.thisList).matches(
                (withText(name +" Welcome to Connect, check your email " +  email  + " to activate account")));
        withContentDescription(R.id.password).matches(withText(withId(R.id.confirm).toString()));
    }
}
