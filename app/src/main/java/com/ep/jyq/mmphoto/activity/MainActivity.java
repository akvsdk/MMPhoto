package com.ep.jyq.mmphoto.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ep.jyq.mmphoto.R;
import com.ep.jyq.mmphoto.fragment.PageSectionFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joy on 2015/8/27.
 */
public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private CoordinatorLayout lay_content;
    private ActionBar ab;
    private ActionBarDrawerToggle abdt;

    private static final String[] tabTitles = new String[]{ "小清新", "文艺范",
            "大长腿", "黑丝袜", "小翘臀", "大胸妹"};
    private static final String[] tabIds = new String[]{ "4", "5", "3", "7",
            "6", "2"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
    }

    private void initview() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        lay_content = (CoordinatorLayout) findViewById(R.id.content);
        setSupportActionBar(toolbar);

        ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeButtonEnabled(true);

        //开启汉堡动画
        abdt = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(abdt);
        abdt.syncState();

        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        if (viewPager != null) {
            setupViewPager(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home :
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /* *
         * 设置左滑菜单
         */
    public void setupDrawerContent(NavigationView navigationView) {     //设置自动关闭
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem menuItem) {
                menuItem.setChecked(true);
                selectDrawerItem(menuItem);
                mDrawerLayout.closeDrawers();

                return true;
            }
        });

    }

    private void selectDrawerItem(MenuItem menuItem) {
        Intent in = new Intent();
        switch (menuItem.getItemId()){
            case R.id.nav_weather :
                in.setClass(MainActivity.this,Wheather.class);
                startActivity(in);
                break;
        }
    }

    public void setupViewPager(ViewPager viewPager) {
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        int mCount = tabTitles.length;
        for (int i = 0; i < mCount; i++) {
            adapter.addFragment(PageSectionFragment.newInstance(tabIds[i]), tabTitles[i]);
        }
          viewPager.setAdapter(adapter);
    }

    public class PagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mfragments = new ArrayList<>();
        private final List<String> mfragmentitles = new ArrayList<>();

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mfragmentitles.add(title);
            mfragments.add(fragment);
        }

        @Override
        public Fragment getItem(int position) {
            return mfragments.get(position);
        }

        @Override
        public int getCount() {
            return mfragments.size();
        }

        public CharSequence getPageTitle(int position) {
            return mfragmentitles.get(position);
        }

    }

}
