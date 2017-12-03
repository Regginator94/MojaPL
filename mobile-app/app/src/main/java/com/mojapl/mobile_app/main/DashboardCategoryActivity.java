package com.mojapl.mobile_app.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.mojapl.mobile_app.R;
import com.mojapl.mobile_app.main.fragments.DashboardListFragment;

public class DashboardCategoryActivity extends FragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard_category);

        Fragment fragment = new DashboardListFragment();
        FragmentManager manager = getSupportFragmentManager();

        manager.beginTransaction().replace(R.id.new_dashboard_list_fragment, fragment).commit();

    }
}
