package com.gymassistant.Activities;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gymassistant.GlobalClass;
import com.gymassistant.Models.Exercise;
import com.gymassistant.R;
import com.gymassistant.RecyclerView.TrainingAssistantAdapter;
import com.gymassistant.UIComponents.MyCustomLayoutManager;

import java.util.List;

public class TrainingAssistant extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Exercise> exercises;
    private Button backButton, nextButton;
    private MyCustomLayoutManager linearLayoutManager;
    private TextView timeTextView;
    private int currentRow = 0;
    private int[] repeatsArray;
    private int[] loadsArray;
    private Handler handler;
    private long tStart = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_assistant);

        GlobalClass globalClass = (GlobalClass) getApplicationContext();
        exercises = globalClass.getExercises();

        handler = new Handler();

        timeTextView = (TextView) findViewById(R.id.timeTextView);
        startMeasureTime();

        linearLayoutManager = new MyCustomLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        recyclerView.setLayoutManager(linearLayoutManager);

        populateRecyclerView();
        setUpButtons();
    }

    private void setUpButtons(){
        nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentRow < exercises.size()){
                    scrollUp();
                }
            }
        });

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentRow > 0){
                    scrollDown();
                }
            }
        });
    }

    private void initArrays(){
        repeatsArray = new int[exercises.size()];
        loadsArray = new int[exercises.size()];
    }

    private void populateRecyclerView(){
        initArrays();
        TrainingAssistantAdapter trainingAssistantAdapter = new TrainingAssistantAdapter(this, exercises, repeatsArray, loadsArray);
        recyclerView.setAdapter(trainingAssistantAdapter);
    }

    private void scrollUp(){
        currentRow++;
        linearLayoutManager.smoothScrollToPosition(recyclerView, null, currentRow);
    }

    private void scrollDown(){
        currentRow--;
        linearLayoutManager.smoothScrollToPosition(recyclerView, null, currentRow);
    }

    private Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            long millis = System.currentTimeMillis() - tStart;
            int seconds = (int) (millis / 1000);

            timeTextView.setText(timeConversion(seconds));
            int mInterval = 1000;
            handler.postDelayed(mStatusChecker, mInterval);
        }
    };

    private void startMeasureTime() {
        tStart = System.currentTimeMillis();
        mStatusChecker.run();
    }

    private void stopMeasureTime() {
        handler.removeCallbacks(mStatusChecker);
    }

    public static String timeConversion(double time) {
        int seconds = (int) time;

        final int MINUTES_IN_AN_HOUR = 60;
        final int SECONDS_IN_A_MINUTE = 60;

        int minutes = seconds / SECONDS_IN_A_MINUTE;
        seconds -= minutes * SECONDS_IN_A_MINUTE;

        int hours = minutes / MINUTES_IN_AN_HOUR;
        minutes -= hours * MINUTES_IN_AN_HOUR;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopMeasureTime();
    }
}
