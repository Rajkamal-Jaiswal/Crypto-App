package com.e.cellpaycrypto.menus.ui.wallets;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapterWallet extends FragmentPagerAdapter {
    private final List<Fragment> myFragments = new ArrayList<>();
    private final List<String> myFragmentTitles = new ArrayList<>();
    private Context context;

    public PagerAdapterWallet(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    public void addFragment(Fragment fragment, String title) {
        myFragments.add(fragment);
        myFragmentTitles.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return myFragments.get(position);
    }

    @Override
    public int getCount() {
        return myFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return myFragmentTitles.get(position);
    }
}
