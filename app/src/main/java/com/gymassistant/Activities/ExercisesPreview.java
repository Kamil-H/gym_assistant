package com.gymassistant.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.gymassistant.Database.ExerciseDB;
import com.gymassistant.Models.Exercise;
import com.gymassistant.R;
import com.gymassistant.UIComponents.CustomAdapter;
import com.gymassistant.UIComponents.Dialogs.YouTubeDialog;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by KamilH on 2016-03-25.
 */
public class ExercisesPreview extends AppCompatActivity {
    private Exercise exercise;
    private TextView nameTextView, mainMuscleTextView, auxMuscleTextView, stabilizersTextView, howToTextView, attentionsTextView;
    private ViewPager viewPager;
    private ExerciseDB exerciseDB;
    private MenuItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        setUpTextViews();

        long id = readParameter();

        exerciseDB = new ExerciseDB(this);
        exercise = exerciseDB.getExercise(id);

        setUpToolbar();
        fillTextViews();
        setUpViewPager();
    }

    private void setUpViewPager(){
        List<String> names = loadImages();
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        PagerAdapter adapter = new CustomAdapter(ExercisesPreview.this, names);
        viewPager.setAdapter(adapter);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                viewPager.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);
    }

    private void setUpTextViews(){
        nameTextView = (TextView) findViewById(R.id.exerciseNameTextView);
        mainMuscleTextView = (TextView) findViewById(R.id.mainMuscleTextView);
        auxMuscleTextView = (TextView) findViewById(R.id.auxMuscleTextView);
        stabilizersTextView = (TextView) findViewById(R.id.stabilizersTextView);
        howToTextView = (TextView) findViewById(R.id.howToTextView);
        attentionsTextView = (TextView) findViewById(R.id.attentionsTextView);
    }

    private void setUpToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(exercise.getCategory());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_white_24dp));
    }

    private long readParameter(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            return extras.getLong("id");
        }
        return 0;
    }

    private void fillTextViews(){
        if(isNotNull(exercise.getSecondName()))
            nameTextView.setText(String.format("%s, %s", exercise.getName(), exercise.getSecondName()));
        else
            nameTextView.setText(exercise.getName());
        mainMuscleTextView.setText(exercise.getMainMuscles());
        auxMuscleTextView.setText(exercise.getAuxMuscles());
        stabilizersTextView.setText(exercise.getStabilizers());
        howToTextView.setText(exercise.getHowTo());
        attentionsTextView.setText(exercise.getAttentions());
    }

    private boolean isNotNull(String text){
        return text.length() > 2;
    }

    private List<String> loadImages() {
        List<String> names = new ArrayList<String>();
        if (isNotNull(exercise.getImg1()))
            names.add(exercise.getImg1());
        if (isNotNull(exercise.getImg2()))
            names.add(exercise.getImg2());
        if (isNotNull(exercise.getImg3()))
            names.add(exercise.getImg3());

        return names;
    }

    private void initFavoriteIcon(){
        if(exercise.isFavorite()){
            item.setIcon(R.drawable.ic_favorite_dark_24dp);
        } else {
            item.setIcon(R.drawable.ic_favorite_white_24dp);
        }
    }

    private void toggleFavorite(){
        if(exercise.isFavorite()){
            item.setIcon(R.drawable.ic_favorite_white_24dp);
            exercise.setFavorite(false);
            exerciseDB.setFavorite(exercise.getId(), false);
        } else {
            item.setIcon(R.drawable.ic_favorite_dark_24dp);
            exercise.setFavorite(true);
            exerciseDB.setFavorite(exercise.getId(), true);
        }
    }

    private void finishWithResult(){
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        item = menu.findItem(R.id.favorites);
        initFavoriteIcon();
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favorites:
                toggleFavorite();
                return true;

            case R.id.youtube:
                Intent intent = new Intent(getApplicationContext(), YouTubeDialog.class);
                Bundle bundle = new Bundle();
                bundle.putString("url", exercise.getVideo());
                intent.putExtras(bundle);
                startActivity(intent);
                return true;

            // obsługa przycisku EXIT
            case android.R.id.home:
                finishWithResult();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finishWithResult();
        }
        return false;
    }
}
