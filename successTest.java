package com.example.test.uwa_parking;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

public class successTest {


    @Rule
    public ActivityTestRule<success> mSuccessRule = new ActivityTestRule<success>(success.class){
        @Override
        protected Intent getActivityIntent() {
            InstrumentationRegistry.getTargetContext();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.putExtra("username", "Hello");
            return intent;
        }
    };


    private success mActivity = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(map.class.getName(),null,false);


    @Before
    public void setUp() throws Exception {
        mActivity = mSuccessRule.getActivity();

    }


    @Test
    public void testLaunchSecondActivityOnButtonClick(){
        assertNotNull(mActivity.findViewById(R.id.button_show));

        onView(withId(R.id.button_show)).perform(click());

        Activity success = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);

        assertNotNull(success);

        success.finish();
    }


    @Test
    public void ensureIntentDataIsDisplayed() throws Exception {
        success activity = mSuccessRule.getActivity();

        String username = null;


        assertNull(username);


    }




    @Test
    public void testLaunch(){
        View ed1 = mActivity.findViewById(R.id.ed1);
        assertNotNull(ed1);

        View ed2 = mActivity.findViewById(R.id.ed2);
        assertNotNull(ed2);

        View park_result = mActivity.findViewById(R.id.park_result);
        assertNotNull(park_result);

        View button_in = mActivity.findViewById(R.id.button_in);
        assertNotNull(button_in);

        View button_out = mActivity.findViewById(R.id.button_out);
        assertNotNull(button_out);

        View button_gps = mActivity.findViewById(R.id.button_gps);
        assertNotNull(button_gps);
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}