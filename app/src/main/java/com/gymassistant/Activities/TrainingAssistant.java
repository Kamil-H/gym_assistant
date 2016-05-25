package com.gymassistant.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gymassistant.Database.SeriesDoneDB;
import com.gymassistant.Database.TrainingDB;
import com.gymassistant.Database.TrainingDoneDB;
import com.gymassistant.DateConverter;
import com.gymassistant.Models.Series;
import com.gymassistant.Models.SeriesDone;
import com.gymassistant.Models.TrainingDone;
import com.gymassistant.R;
import com.gymassistant.RecyclerView.TrainingAssistantAdapter;
import com.gymassistant.UIComponents.MyCustomLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class TrainingAssistant extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Series> seriesList;
    private Button backButton, nextButton;
    private MyCustomLayoutManager linearLayoutManager;
    private TextView timeTextView;
    private int currentRow = 0;
    private int[] repeatsArray;
    private int[] loadsArray;
    private Handler handler;
    private long tStart = 0;
    private int traningId, startedTrainingPlanId, day;
    private List<SeriesDone> seriesDoneList;
    private long traningDoneId;
    private boolean isClose = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_assistant);
        timeTextView = (TextView) findViewById(R.id.timeTextView);

        readParameters();

        setUpSeriesList();
        seriesDoneList = new ArrayList<>(seriesList.size());

        setUpRecyclerview();
        setUpButtons();

        handler = new Handler();
        startMeasureTime();

        saveTrainingDoneToDatabase();
    }

    private void setUpSeriesList(){
        TrainingDB trainingDB = new TrainingDB(this);
        seriesList = trainingDB.getTraining(traningId).getSeriesList();
    }

    private void setUpRecyclerview(){
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
    }

    private void saveTrainingDoneToDatabase(){
        TrainingDoneDB trainingDoneDB = new TrainingDoneDB(this);
        traningDoneId = trainingDoneDB.addTrainingDone(new TrainingDone(DateConverter.today(), day, startedTrainingPlanId));
    }

    private void saveSeriesDoneListToDatabase(){
        SeriesDoneDB seriesDoneDB = new SeriesDoneDB(this);
        seriesDoneDB.addSeriesDoneList(seriesDoneList);
    }

    private void addSeriesDoneToList(){
        SeriesDone seriesDone = new SeriesDone(seriesList.get(currentRow));
        seriesDone.setActualRepeat(repeatsArray[currentRow]);
        seriesDone.setActualWeight(loadsArray[currentRow]);
        seriesDone.setTrainingDoneId((int)traningDoneId);
        seriesDoneList.add(currentRow, seriesDone);
    }

    private void readParameters(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            traningId = extras.getInt("trainingId");
            startedTrainingPlanId = extras.getInt("startedTrainingPlanId");
            day = extras.getInt("day");
        }
    }

    private void setUpButtons(){
        nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentRow < seriesList.size() - 1){
                    addSeriesDoneToList();
                    scrollDown();
                }
                if(currentRow == 1){
                    backButton.setText(getString(R.string.back_button));
                }
                if(currentRow == seriesList.size() - 1 && isClose){
                    addSeriesDoneToList();
                    stopMeasureTime();
                    saveSeriesDoneListToDatabase();
                }
                if(currentRow == seriesList.size() - 1 && !isClose){
                    nextButton.setText(getString(R.string.close));
                    isClose = true;
                }
            }
        });

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentRow == 0){
                    TrainingAssistant.this.finish();
                }
                if(currentRow == seriesList.size() - 1 && isClose){
                    nextButton.setText(getString(R.string.next_button));
                    isClose = false;
                }
                if(currentRow > 0){
                    addSeriesDoneToList();
                    scrollUp();
                }
                if(currentRow == 0){
                    backButton.setText(getString(R.string.cancel));
                }
            }
        });
    }

    private void initArrays(){
        loadsArray = new int[seriesList.size()];
        repeatsArray = new int[seriesList.size()];

        for(int i = 0; i < seriesList.size(); i++){
            repeatsArray[i] = seriesList.get(i).getRepeat();
        }
    }

    private void populateRecyclerView(){
        initArrays();
        TrainingAssistantAdapter trainingAssistantAdapter = new TrainingAssistantAdapter(this, seriesList, repeatsArray, loadsArray);
        recyclerView.setAdapter(trainingAssistantAdapter);
    }

    private void scrollDown(){
        currentRow++;
        linearLayoutManager.smoothScrollToPosition(recyclerView, null, currentRow);
    }

    private void scrollUp(){
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
    protected void onDestroy() {
        super.onDestroy();
        stopMeasureTime();
    }
}
