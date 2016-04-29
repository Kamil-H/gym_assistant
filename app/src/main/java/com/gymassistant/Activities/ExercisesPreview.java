package com.gymassistant.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gymassistant.UIComponents.CustomAdapter;
import com.gymassistant.GlobalClass;
import com.gymassistant.Models.Exercise;
import com.gymassistant.R;
import com.gymassistant.UIComponents.UniversalDialog;
import com.gymassistant.UIComponents.YouTubeDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KamilH on 2016-03-25.
 */
public class ExercisesPreview extends AppCompatActivity {
    private List<Exercise> exercises;
    private Exercise exercise;
    private TextView nameTextView, mainMuscleTextView, auxMuscleTextView, stabilizersTextView, howtoTextView, attentionsTextView;
    private ViewPager viewPager;

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("TAG", "onStop");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        nameTextView = (TextView) findViewById(R.id.nameTextView);
        mainMuscleTextView = (TextView) findViewById(R.id.mainMuscleTextView);
        auxMuscleTextView = (TextView) findViewById(R.id.auxMuscleTextView);
        stabilizersTextView = (TextView) findViewById(R.id.stabilizersTextView);
        howtoTextView = (TextView) findViewById(R.id.howtoTextView);
        attentionsTextView = (TextView) findViewById(R.id.attentionsTextView);

        GlobalClass globalClass = (GlobalClass) getApplicationContext();
        exercises = globalClass.getExercises();

        String id = readParameter();
        exercise = getSpecyficExercise(id);

        setUpToolbar();

        fillTextViews();
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
    }

    private void setUpToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(exercise.getCategory());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_white_24dp));
    }

    private Exercise getSpecyficExercise(String id){
        for(Exercise exer : exercises){
            if(exer.getId().matches(id))
                return exer;
        }
        return null;
    }

    private String readParameter(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            return extras.getString("id");
        }
        return null;
    }

    private void fillTextViews(){
        if(isSecondName())
            nameTextView.setText(exercise.getName() + ", " + exercise.getSecondName());
        else
            nameTextView.setText(exercise.getName());
        mainMuscleTextView.setText(exercise.getMainMuscles());
        auxMuscleTextView.setText(exercise.getAuxMuscles());
        stabilizersTextView.setText(exercise.getStabilizers());
        howtoTextView.setText(exercise.getHowTo());
        attentionsTextView.setText(exercise.getAttentions());
    }

    private boolean isSecondName(){
        return exercise.getSecondName().length() > 2;
    }

    private List<String> loadImages() {
        List<String> names = new ArrayList<String>();
        if (exercise.getImg1() != null)
            names.add(exercise.getImg1());
        if (exercise.getImg2() != null)
            names.add(exercise.getImg2());
        if (exercise.getImg3() != null)
            names.add(exercise.getImg3());

        return names;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favorites:
                item.setIcon(R.drawable.ic_favorite_dark_24dp);
                Toast.makeText(this, getString(R.string.add_to_fav), Toast.LENGTH_LONG).show();
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
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
