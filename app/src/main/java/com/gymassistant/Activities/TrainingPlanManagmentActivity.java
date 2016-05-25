package com.gymassistant.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;

import com.gymassistant.Fragments.BottomFragments.FinishedBottomFragment;
import com.gymassistant.Fragments.BottomFragments.PlansBottomFragment;
import com.gymassistant.Fragments.BottomFragments.StartedBottomFragment;
import com.gymassistant.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

public class TrainingPlanManagmentActivity extends AppCompatActivity {
    private BottomBar bottomBar;
    private String names[] = {"Plany treningowe", "Aktywne plany", "Zakończone plany"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_plan_managment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomBar = BottomBar.attach(this, savedInstanceState);
        bottomBar.setItemsFromMenu(R.menu.menu_bottombar, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.menu_plans) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new PlansBottomFragment()).commit();
                    getSupportActionBar().setTitle(names[0]);
                } else if (menuItemId == R.id.menu_started) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new StartedBottomFragment()).commit();
                    getSupportActionBar().setTitle(names[1]);
                } else if (menuItemId == R.id.menu_finished) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new FinishedBottomFragment()).commit();
                    getSupportActionBar().setTitle(names[2]);
                }
            }
            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
            }
        });
        bottomBar.useDarkTheme();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        bottomBar.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finishWithResult();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void finishWithResult(){
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        this.finish();
    }
}