package com.gymassistant.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
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
    private Button backButton, nextButton;
    private MyCustomLayoutManager linearLayoutManager;
    private TextView timeTextView;
    private int currentRow = 0;
    private Handler handler;
    private long tStart = 0;
    private long trainingId, startedTrainingPlanId;
    private int day;
    private List<SeriesDone> seriesDoneList = new ArrayList<>();
    private long trainingDoneId;
    private boolean isClose = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_assistant);
        timeTextView = (TextView) findViewById(R.id.timeTextView);

        readParameters();

        setUpSeriesDoneList();

        setUpRecyclerView();
        setUpButtons();

        handler = new Handler();
        startMeasureTime();
    }

    private List<SeriesDone> getLastSeriesDone(){
        SeriesDoneDB seriesDoneDB = new SeriesDoneDB(this);
        return seriesDoneDB.getLastSeriesDoneByTrainingId(trainingId);
    }

    private void setUpSeriesDoneList(){
        List<Series> seriesList = setUpSeriesList();
        for(int i = 0; i < seriesList.size(); i++){
            SeriesDone seriesDone = new SeriesDone(seriesList.get(i));
            seriesDoneList.add(seriesDone);
        }
    }

    private void nextButtonCheck(){
        if(seriesDoneList.size() == 1){
            nextButton.setText(getString(R.string.save_button));
            isClose = true;
        }
    }

    private List<Series> setUpSeriesList(){
        TrainingDB trainingDB = new TrainingDB(this);
        return trainingDB.getTraining(trainingId).getSeriesList();
    }

    private void setUpRecyclerView(){
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
        trainingDoneId = trainingDoneDB.addTrainingDone(new TrainingDone(DateConverter.today(), day, startedTrainingPlanId), timeFromStart());
    }

    private void saveSeriesDoneListToDatabase(){
        SeriesDoneDB seriesDoneDB = new SeriesDoneDB(this);
        seriesDoneDB.addSeriesDoneList(seriesDoneList, trainingDoneId);
    }

    private void readParameters(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            trainingId = extras.getLong("trainingId");
            startedTrainingPlanId = extras.getLong("startedTrainingPlanId");
            day = extras.getInt("day");
        }
    }

    private void setUpButtons(){
        nextButton = (Button) findViewById(R.id.nextButton);
        nextButtonCheck();
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentRow < seriesDoneList.size() - 1){
                    scrollDown();
                }
                if(currentRow == 1){
                    backButton.setText(getString(R.string.back_button));
                }
                if(currentRow == seriesDoneList.size() - 1 && isClose){
                    showDialog(false);
                }
                if(currentRow == seriesDoneList.size() - 1 && !isClose){
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
                    showDialog(true);
                }
                if(currentRow == seriesDoneList.size() - 1 && isClose){
                    nextButton.setText(getString(R.string.next_button));
                    isClose = false;
                }
                if(currentRow > 0){
                    scrollUp();
                }
                if(currentRow == 0){
                    backButton.setText(getString(R.string.cancel));
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            showDialog(true);
        }
        return super.onKeyDown(keyCode, event);
    }

    private void closeWithoutSaving(){
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        this.finish();
    }

    private void closeWithSaving(){
        saveTrainingDoneToDatabase();
        saveSeriesDoneListToDatabase();

        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        this.finish();
    }

    private void onAbort(){
        for(int i = currentRow; i < seriesDoneList.size(); i++){
            seriesDoneList.get(i).setSaved(false);
        }
        closeWithSaving();
    }

    private void populateRecyclerView(){
        TrainingAssistantAdapter trainingAssistantAdapter = new TrainingAssistantAdapter(this, seriesDoneList, getLastSeriesDone());
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

            timeTextView.setText(DateConverter.timeConversion(timeFromStart()));
            int mInterval = 1000;
            handler.postDelayed(mStatusChecker, mInterval);
        }
    };

    private int timeFromStart(){
        long millis = System.currentTimeMillis() - tStart;
        return  (int) (millis / 1000);
    }

    private void startMeasureTime() {
        tStart = System.currentTimeMillis();
        mStatusChecker.run();
    }

    private void stopMeasureTime() {
        handler.removeCallbacks(mStatusChecker);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopMeasureTime();
    }

    private void showDialog(final boolean isAborting){
        AlertDialog alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog)).create();
        alertDialog.setTitle(getString(R.string.closing_assistant));
        alertDialog.setMessage(getString(R.string.save_traning));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(isAborting){
                            onAbort();
                        } else {
                            closeWithSaving();
                        }
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.no),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        closeWithoutSaving();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
        });
        alertDialog.show();
    }
}
