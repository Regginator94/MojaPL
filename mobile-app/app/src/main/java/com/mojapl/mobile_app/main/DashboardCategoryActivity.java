package com.mojapl.mobile_app.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.mojapl.mobile_app.R;
import com.mojapl.mobile_app.main.fragments.DashboardListFragment;

public class DashboardCategoryActivity extends FragmentActivity {
    public final static String CATEGORY_KEY = "category_key";

    public final static int UNIVERSITY = 1;
    public final static int EVENTS = 2;
    public final static int HOBBY = 3;
    public final static int SALE = 4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard_category);

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(DashboardListFragment.TAG);
        if (fragment == null) {
            fragment = DashboardListFragment.newInstance(getIntent().getIntExtra(CATEGORY_KEY, 0));
        }

        manager.beginTransaction()
                .replace(R.id.new_dashboard_list_fragment, fragment, DashboardListFragment.TAG)
                .commit();
    }
}
