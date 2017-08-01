package com.bb.superdemo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Fragment> mFragment = new ArrayList<>();
        List<String> mTitle = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            mFragment.add(PlaceholderFragment.newInstance(i + 1));
            mTitle.add("标题" + (i + 1));
        }
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), mFragment, mTitle);


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        ShapeIndicatorView shapeIndicatorView = (ShapeIndicatorView) findViewById(R.id.custom_indicator);

        tabLayout.setTabsFromPagerAdapter(mViewPager.getAdapter());
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        shapeIndicatorView.setupWithTabLayout(tabLayout);
        shapeIndicatorView.setupWithViewPager(mViewPager);
    }
}
