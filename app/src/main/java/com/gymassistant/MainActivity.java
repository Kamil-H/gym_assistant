package com.gymassistant;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.gymassistant.Activities.LoginActivity;
import com.gymassistant.Database.ExerciseDB;
import com.gymassistant.Fragments.ExercisesFragment;
import com.gymassistant.Fragments.HistoryFragment;
import com.gymassistant.Fragments.ProfileFragment;
import com.gymassistant.Fragments.TrainingFragment;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private int[] tabIcons = {
            R.drawable.ic_account_circle_white_24dp,
            R.drawable.ic_weightlifting_24dp,
            R.drawable.ic_history_white_24dp,
            R.drawable.ic_fitness_center_white_24dp,
    };
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                String names[] = {getString(R.string.tab_1_name), getString(R.string.tab_2_name), getString(R.string.tab_3_name), getString(R.string.tab_4_name)};
                getSupportActionBar().setTitle(names[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        setupTabIcons(tabLayout);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                saveExercises();
            }
        });
    }

    private void saveExercises(){
        ExerciseDB exerciseDB = new ExerciseDB(MainActivity.this);
        long rowCount = exerciseDB.getRowCount();
        Log.i("ExerciseDB", "populate, rowCount: " + String.valueOf(rowCount));
        if(rowCount < 1){
            exerciseDB.populateExerciseDB();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                refresh();
            }
        }
    }

    public void refresh(){
        mViewPager.getAdapter().notifyDataSetChanged();
        setupTabIcons(tabLayout);
    }

    private void setupTabIcons(TabLayout tabLayout) {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new ProfileFragment();
                case 1:
                    return new TrainingFragment();
                case 2:
                    return new HistoryFragment();
                case 3:
                    return new ExercisesFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
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