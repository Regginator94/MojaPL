package com.mojapl.mobile_app.main.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mojapl.mobile_app.main.fragments.DashboardFragment;
import com.mojapl.mobile_app.main.fragments.DashboardListFragment;


/**
 * Created by shkliare on 10/22/17.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new DashboardFragment();
            case 1:
                return new DashboardListFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
