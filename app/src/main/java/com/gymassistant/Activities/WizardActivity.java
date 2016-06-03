package com.gymassistant.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.gymassistant.Fragments.WizardFragments.FirstPage;
import com.gymassistant.Fragments.WizardFragments.SecondPage;
import com.gymassistant.Fragments.WizardFragments.ThirdPage;
import com.gymassistant.Models.Series;
import com.gymassistant.R;

import java.util.List;
import java.util.Collections;

public class WizardActivity extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private int itemCount = 0;
    private List<List<Series>> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizard);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_white_24dp));

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    public void refresh(){
        mViewPager.getAdapter().notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_wizard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finishWithResult(false);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finishWithResult(false);
        }
        return super.onKeyDown(keyCode, event);
    }

    public void navigateToNextPage() {
        refresh();
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
    }

    public void navigateToPreviousPage() {
        refresh();
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
    }

    public void finishWithResult(boolean value){
        Intent returnIntent = new Intent();
        if(value){
            setResult(Activity.RESULT_OK, returnIntent);
        } else {
            setResult(Activity.RESULT_CANCELED, returnIntent);
        }
        this.finish();
    }

    public void removeEmptyObjects(){
        map.removeAll(Collections.singleton(null));
    }

    public void setItemCount(int itemCount){
        this.itemCount = itemCount;
    }

    public int getItemCount(){
        return this.itemCount;
    }

    public List<List<Series>> getMap() {
        return map;
    }

    public void setMap(List<List<Series>> map) {
        this.map = map;
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter{

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FirstPage();
                case 1:
                    return new SecondPage();
                case 2:
                    return new ThirdPage();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}
