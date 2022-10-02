package com.e.cellpaycrypto.mypack;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PendingOrdersAdapterViewPager
        extends FragmentPagerAdapter {

    public PendingOrdersAdapterViewPager(
            @NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
            fragment = new AllOrdersFragment();
        else if (position == 1)
            fragment = new AllOrdersFragment();
        else if (position == 2)
            fragment = new AllOrdersFragment();
        else if (position == 3)
            fragment = new AllOrdersFragment();

        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
            title = "All";
        else if (position == 1)
            title = "Unpaid";
        else if (position == 2)
            title = "Paid";
        else if (position == 3)
            title = "Appeal Pending";
        return title;
    }
}