package com.gymassistant;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gymassistant.Activities.LoginActivity;
import com.gymassistant.Fragments.ExercisesFragment;
import com.gymassistant.Fragments.HistoryFragment;
import com.gymassistant.Fragments.ProfileFragment;
import com.gymassistant.Fragments.TrainingFragment;
import com.gymassistant.Models.Categories;
import com.gymassistant.Models.Category;
import com.gymassistant.Models.Exercise;
import com.gymassistant.Models.Exercises;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private int[] tabIcons = {
            R.drawable.ic_account_circle_white_24dp,
            R.drawable.ic_weightlifting_24dp,
            R.drawable.ic_history_white_24dp,
            R.drawable.ic_fitness_center_white_24dp,
    };

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

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        setupTabIcons(tabLayout);

        GlobalClass global = (GlobalClass) getApplicationContext();
        global.setCategories(getCategories());
        global.setExercises(getExercises());
    }

    private List<Category> getCategories(){
        BufferedReader in = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.categories)));
        Gson gson = new GsonBuilder().create();
        Categories cat = gson.fromJson(in, Categories.class);
        List<Category> categories = cat.getCategories();
        return categories;
    }

    private List<Exercise> getExercises(){
        BufferedReader in = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.exercies)));
        Gson gson = new GsonBuilder().create();
        Exercises exer = gson.fromJson(in, Exercises.class);
        List<Exercise> exercises = exer.getExercises();
        return exercises;
    }

    private void setupTabIcons(TabLayout tabLayout) {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

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
    }
}