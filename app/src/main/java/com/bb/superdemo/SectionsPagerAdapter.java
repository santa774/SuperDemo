package com.bb.superdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author ethan
 * @version 创建时间:  2017/8/1.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {


    private List<Fragment> mFragment;
    private List<String> mTitle;

    public SectionsPagerAdapter(FragmentManager fm, List<Fragment> mFragment, List<String> mTitle) {
        super(fm);
        this.mFragment = mFragment;
        this.mTitle = mTitle;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragment.get(position);
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle.get(position);
    }
}