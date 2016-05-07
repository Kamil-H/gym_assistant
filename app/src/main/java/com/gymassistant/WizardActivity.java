package com.gymassistant;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.gymassistant.Database.TrainingPlanDB;
import com.gymassistant.Fragments.WizardFragments.FirstPage;
import com.gymassistant.Fragments.WizardFragments.SecondPage;
import com.gymassistant.Models.TrainingPlan;

public class WizardActivity extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private int itemCount;
    private TrainingPlanDB trainingPlanDB;
    private long trainingPlanId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizard);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_white_24dp));

        trainingPlanDB = new TrainingPlanDB(this);
        addNewTrainingPlan();

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        mViewPager.setAdapter(mSectionsPagerAdapter);
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
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void navigateToNextPage() {
        mViewPager.getAdapter().notifyDataSetChanged();
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
    }

    public void navigateToPreviousPage() {
        mViewPager.getAdapter().notifyDataSetChanged();
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
    }

    private void addNewTrainingPlan(){
        trainingPlanId = trainingPlanDB.addTrainingPlan(new TrainingPlan(0, 0, false, null, null));
        Log.i("Added", String.format("TrainingPlan ID: %d", trainingPlanId));
    }

    public void deleteTrainingPlan(){
        trainingPlanDB.deleteTrainingPlan(trainingPlanId);
        Log.i("Deleted", String.format("TrainingPlan ID: %d", trainingPlanId));
    }

    public void setItemCount(int itemCount){
        this.itemCount = itemCount;
    }

    public int getItemCount(){
        return this.itemCount;
    }

    public long getTrainingPlanId() {
        return trainingPlanId;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

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
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
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
