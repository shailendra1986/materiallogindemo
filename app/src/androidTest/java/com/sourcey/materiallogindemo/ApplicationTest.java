package com.sourcey.materiallogindemo;

import android.os.SystemClock;
import android.support.design.widget.TextInputLayout;
import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(JUnit4.class)
public class ApplicationTest {

    @Rule
    public ActivityTestRule<MainActivity> myActivityTestRule  =
            new ActivityTestRule<MainActivity>(MainActivity.class);
    private String eMailID = "test@gmail.com";
    private String nPassword = "test_123";
    private String incorrectEMailID = "test@gmail";
    private String mInvalidEmailString = "enter a valid email address";

    @Test
    public void testLoginAppHAppyPathScenario()
    {
        //input a valid email id in email text field
        onView(withId(R.id.input_email)).check(matches(isDisplayed()));
        onView(withId(R.id.input_email)).perform(typeText(eMailID));

        //input valid password as defined
        onView(withId(R.id.input_password)).perform(typeText(nPassword));


        //click on login button
        onView(withId(R.id.btn_login)).perform(click());
        SystemClock.sleep(3000);

        //On successful sign in check 'Hello world' displayed on screen
        onView(withText("Hello world!")).check(matches(isDisplayed()));

    }

    @Test
    public void testEmailTextFormat()
    {
        //input a valid email id in email text field
        onView(withId(R.id.input_email)).check(matches(isDisplayed()));
        onView(withId(R.id.input_email)).perform(typeText(incorrectEMailID));

        //input valid password as defined
        onView(withId(R.id.input_password)).perform(typeText(nPassword));

        //click on login button
        onView(withId(R.id.btn_login)).perform(click());
        SystemClock.sleep(3000);

        //Check 'Hello world' is not displayed on screen
        onView(withText("Hello world!")).check(doesNotExist());

        //Check  proper error message is displayed to user
        onView(withId(R.id.input_email)).perform(click());


        /* Trying to validate error message on email input field when user enters invalid address
        Not working though :( */
        onView(withId(R.id.input_email)).check
                (matches(hasTextInputLayoutErrorText
                        (myActivityTestRule.getActivity().getString(R.string.app_name))));


    }
    public static Matcher<View> hasTextInputLayoutErrorText(final String expectedErrorText) {
        return new TypeSafeMatcher<View>() {

            @Override
            public void describeTo(Description description) {

            }

            @Override
            public boolean matchesSafely(View view) {
                    if (!(view instanceof TextInputLayout)) {
                    return false;
                }

                CharSequence error = ((TextInputLayout) view).getError();

                if (error == null) {
                    return false;
                }

                String errorText = error.toString();

                return expectedErrorText.equals(errorText);
            }

        };
    }
}

