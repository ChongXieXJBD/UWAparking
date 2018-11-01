package com.example.test.uwa_parking;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class registerTest {
    @Rule
    public ActivityTestRule<register> mRegisterRule = new ActivityTestRule<>(register.class);


    private register mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mRegisterRule.getActivity();
    }


    @Test
    public void testLaunch(){
        View et_name = mActivity.findViewById(R.id.et_name);
        assertNotNull(et_name);

        View et_pass = mActivity.findViewById(R.id.et_pass);
        assertNotNull(et_pass);

        View et_repass = mActivity.findViewById(R.id.et_repass);
        assertNotNull(et_repass);

        View et_email = mActivity.findViewById(R.id.et_email);
        assertNotNull(et_email);


        View rg_role = mActivity.findViewById(R.id.rg_role);
        assertNotNull(rg_role);

        View rb_staff = mActivity.findViewById(R.id.rb_staff);
        assertNotNull(rb_staff);

        View rb_student = mActivity.findViewById(R.id.rb_student);
        assertNotNull(rb_student);

        View rg_permission = mActivity.findViewById(R.id.rg_permission);
        assertNotNull(rg_permission);

        View rb_red = mActivity.findViewById(R.id.rb_red);
        assertNotNull(rb_red);


        View rb_yellow = mActivity.findViewById(R.id.rb_yellow);
        assertNotNull(rb_yellow);

        View signin = mActivity.findViewById(R.id.signin);
        assertNotNull(signin);

        View goback = mActivity.findViewById(R.id.goback);
        assertNotNull(goback);


        View register_result = mActivity.findViewById(R.id.register_result);
        assertNotNull(register_result);



    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;

    }
}