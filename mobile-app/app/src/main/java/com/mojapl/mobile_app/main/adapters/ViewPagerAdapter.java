package com.mojapl.mobile_app.main.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mojapl.mobile_app.main.fragments.DashboardFragment;
import com.mojapl.mobile_app.main.fragments.DashboardListFragment;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private FragmentManager fragmentManager;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fragmentManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new DashboardFragment();
            case 1:

                Fragment fragment = fragmentManager.findFragmentByTag(DashboardListFragment.TAG);
                if (fragment == null) {
                    fragment = DashboardListFragment.newInstance(0);
                }

                return fragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
