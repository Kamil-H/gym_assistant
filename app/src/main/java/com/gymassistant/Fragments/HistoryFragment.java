package com.gymassistant.Fragments;

import android.app.DatePickerDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.gymassistant.Database.TrainingDoneDB;
import com.gymassistant.DateConverter;
import com.gymassistant.Models.SeriesDone;
import com.gymassistant.Models.SeriesDoneGroup;
import com.gymassistant.Models.TrainingDone;
import com.gymassistant.R;
import com.gymassistant.RecyclerView.HistoryExpandableAdapter;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by KamilH on 2016-03-21.
 */
public class HistoryFragment extends Fragment {
    private View view;
    private List<TrainingDone> trainingDoneList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_history, container, false);

        setUpTrainingDoneList();
        setUpRecyclerView(DateConverter.today());

        FloatingActionButton fab = (FloatingActionButton) (view.findViewById(R.id.fab));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendar();
            }
        });

        return view;
    }

    private void setUpTrainingDoneList(){
        TrainingDoneDB trainingDoneDB = new TrainingDoneDB(getActivity());
        trainingDoneList = trainingDoneDB.getAllTrainingsDone();
    }

    private void showDateDialog(){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(new ContextThemeWrapper(getActivity(), android.R.style.Theme_Holo_Dialog), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Log.i("DATE", String.format("INSIDE: %d, %d, %d", year, monthOfYear, dayOfMonth));
            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void showCalendar(){
        final CaldroidFragment dialogCaldroidFragment = new CaldroidFragment();
        markDates(dialogCaldroidFragment);
        dialogCaldroidFragment.setArguments(styleCalendar());
        dialogCaldroidFragment.setCaldroidListener(new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                setUpRecyclerView(DateConverter.timeToDate(date.getTime()));
                dialogCaldroidFragment.dismiss();
            }
        });
        dialogCaldroidFragment.show(getActivity().getSupportFragmentManager(),"TAG");
    }

    private void setUpRecyclerView(String date){
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        HistoryExpandableAdapter historyExpandableAdapter = new HistoryExpandableAdapter(getActivity(), generateList(getTrainingDonesByDate(date)));
        recyclerView.setAdapter(historyExpandableAdapter);
    }

    private List<TrainingDone> getTrainingDonesByDate(String date){
        List<TrainingDone> parentList = new ArrayList<>();
        for(TrainingDone trainingDone : trainingDoneList){
            if(trainingDone.getDate().matches(date)){
                parentList.add(trainingDone);
            }
        }
        return parentList;
    }

    private List<TrainingDone> generateList(List<TrainingDone> trainingDoneList){
        List<TrainingDone> parentList = new ArrayList<>();

        for(TrainingDone trainingDone : trainingDoneList){
            Map<Integer, List<SeriesDone>> map = new HashMap<>();
            List<SeriesDone> seriesDoneList = trainingDone.getSeriesDoneList();
            for (SeriesDone seriesDone : seriesDoneList) {
                int key  = seriesDone.getExercise().getId();
                if(map.containsKey(key)){
                    List<SeriesDone> list = map.get(key);
                    list.add(seriesDone);
                }else{
                    List<SeriesDone> list = new ArrayList<>();
                    list.add(seriesDone);
                    map.put(key, list);
                }
            }
            Set<Integer> exerciseSet = map.keySet();
            List<SeriesDoneGroup> seriesDoneGroupList = new ArrayList<>();
            for(int key : exerciseSet){
                SeriesDoneGroup seriesDoneGroup = new SeriesDoneGroup(map.get(key), map.get(key).get(0).getExercise());
                seriesDoneGroupList.add(seriesDoneGroup);
            }
            trainingDone.setSeriesDoneGroupList(seriesDoneGroupList);
            parentList.add(trainingDone);
        }

        return parentList;
    }

    private void markDates(CaldroidFragment dialogCaldroidFragment){
        ColorDrawable colorPrimary = new ColorDrawable(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        for(TrainingDone trainingDone : trainingDoneList){
            dialogCaldroidFragment.setBackgroundDrawableForDate(colorPrimary, new Date(DateConverter.dateToTime(trainingDone.getDate())));
        }
    }

    private Bundle styleCalendar(){
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        args.putInt(CaldroidFragment.THEME_RESOURCE, com.caldroid.R.style.CaldroidDefaultDark);

        return args;
    }
}
