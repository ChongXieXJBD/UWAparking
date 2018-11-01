package com.example.test.uwa_parking;

import android.app.Activity;
import android.app.Instrumentation;
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
import static org.junit.Assert.*;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mMainActivityRule = new ActivityTestRule<>(MainActivity.class);


    private MainActivity mActivity = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(register.class.getName(),null,false);


    @Before
    public void setUp() throws Exception {

        mActivity = mMainActivityRule.getActivity();

    }


    @Test
    public void testLaunchSecondActivityOnButtonClick(){
        assertNotNull(mActivity.findViewById(R.id.register));

        onView(withId(R.id.register)).perform(click());

        Activity register = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);

        assertNotNull(register);

        register.finish();
    }


    @Test
    public void testLaunch(){
        View et_name = mActivity.findViewById(R.id.et_name);
        assertNotNull(et_name);

        View et_pass = mActivity.findViewById(R.id.et_pass);
        assertNotNull(et_pass);

        View cb = mActivity.findViewById(R.id.cb);
        assertNotNull(cb);

        View login = mActivity.findViewById(R.id.login);
        assertNotNull(login);

        View login_result = mActivity.findViewById(R.id.login_result);
        assertNotNull(login_result);

    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }



}