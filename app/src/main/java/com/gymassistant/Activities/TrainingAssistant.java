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
    private boolean isClose = false, CLOSE = true, SAVE = false;

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
    }

    private void nextButtonCheck(){
        if(seriesList.size() == 1){
            nextButton.setText(getString(R.string.save_button));
            isClose = true;
        }
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
        traningDoneId = trainingDoneDB.addTrainingDone(new TrainingDone(DateConverter.today(), day, startedTrainingPlanId), timeFromStart());
    }

    private void saveSeriesDoneListToDatabase(){
        SeriesDoneDB seriesDoneDB = new SeriesDoneDB(this);
        seriesDoneDB.addSeriesDoneList(seriesDoneList, traningDoneId);
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
        nextButtonCheck();
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
                    showDialog(SAVE);
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
                    showDialog(CLOSE);
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

    private void showDialog(final boolean choice){
        String title, message;
        title = getString(R.string.closing_assistant);
        if(choice){
            message = getString(R.string.close_without_saving);
        } else {
            message = getString(R.string.save_traning);
        }
        AlertDialog alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog)).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(choice){
                            onCancel();
                        } else {
                            closeWithSaving();
                            dialog.dismiss();
                        }
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.no),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(choice){
                            dialog.dismiss();
                        } else {
                            onCancel();
                        }
                    }
        });
        alertDialog.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            showDialog(CLOSE);
        }
        return super.onKeyDown(keyCode, event);
    }

    private void onCancel(){
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
}
