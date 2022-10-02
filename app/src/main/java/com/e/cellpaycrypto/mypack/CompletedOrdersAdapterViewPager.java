package com.e.cellpaycrypto.mypack;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/*public class CompletedOrdersAdapterViewPager {
}*/
public class CompletedOrdersAdapterViewPager
        extends FragmentPagerAdapter {

    public CompletedOrdersAdapterViewPager(
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

        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
            title = "All";
        else if (position == 1)
            title = "Completed";
        else if (position == 2)
            title = "Cancelled";

        return title;
    }
}
