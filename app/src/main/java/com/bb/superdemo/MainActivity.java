package com.bb.superdemo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements CommonTabPagerAdapter.TabPagerListener {

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.custom_indicator)
    ShapeIndicatorView shapeIndicatorView;

    private CommonTabPagerAdapter adapter;
    private List<Fragment> mFragment = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        adapter = new CommonTabPagerAdapter(getSupportFragmentManager()
                , 3, Arrays.asList("商品", "评论", "商家"), this);
        adapter.setListener(this);
        viewpager.setOffscreenPageLimit(3);
        viewpager.setAdapter(adapter);

        shapeIndicatorView = (ShapeIndicatorView) findViewById(R.id.custom_indicator);
        tabLayout.setTabsFromPagerAdapter(viewpager.getAdapter());
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        shapeIndicatorView.setupWithTabLayout(tabLayout);
        shapeIndicatorView.setupWithViewPager(viewpager);

        mFragment.add(new ModelFragment("one"));
        mFragment.add(new ModelFragment("two"));
        mFragment.add(new ModelFragment("three"));
    }

    @Override
    public Fragment getFragment(int position) {
        return mFragment.get(position);
    }
}
