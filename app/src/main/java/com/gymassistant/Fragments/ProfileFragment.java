package com.gymassistant.Fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.util.LongSparseArray;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gymassistant.Database.DimensionDB;
import com.gymassistant.Database.UserDB;
import com.gymassistant.Models.Dimension;
import com.gymassistant.Models.User;
import com.gymassistant.R;
import com.gymassistant.RecyclerView.DimensionAdapter;
import com.gymassistant.UIComponents.DimensionDialog;

import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by KamilH on 2016-03-21.
 */
public class ProfileFragment extends Fragment {
    private View view;
    private TextView ageTextView, heightTextView, weightTextView, bmiTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        setUpTextViews();
        fillTextViews();

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DimensionDialog();
                newFragment.setTargetFragment(ProfileFragment.this, 0);
                newFragment.show(getActivity().getSupportFragmentManager(), "dialog");
            }
        });

        setUpRecyclerView();

        return view;
    }

    private void setUpTextViews(){
        ageTextView = (TextView) view.findViewById(R.id.ageTextView);
        heightTextView = (TextView) view.findViewById(R.id.heightTextView);
        weightTextView = (TextView) view.findViewById(R.id.weightTextView);
        bmiTextView = (TextView) view.findViewById(R.id.bmiTextView);
    }

    public void fillTextViews(){
        User user = getUser();
        if(user.getBirthday() != null){
            ageTextView.setText(String.valueOf(getAge(user.getBirthday())));
        }
        heightTextView.setText(String.valueOf(user.getHeight()));
        weightTextView.setText(String.valueOf(user.getWeight()));
        bmiTextView.setText(String.format("%.2f", getBMI(user.getWeight(), user.getHeight())));
    }

    private int getAge(String birth_date){
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy");
        LocalDate date = formatter.parseLocalDate(birth_date);
        Years age = Years.yearsBetween(date, LocalDate.now());
        return age.getYears();
    }

    private double getBMI(double weight, double height){
        return 10000 * weight / (height * height);
    }

    private User getUser(){
        UserDB userDB = new UserDB(getActivity());
        return userDB.getUser();
    }

    private List<Dimension> setUpDimensionList(){
        DimensionDB dimensionDB = new DimensionDB(getActivity());
        return dimensionDB.getAllDimension();
    }

    private LongSparseArray<List<Dimension>> groupDimensions(){
        LongSparseArray<List<Dimension>> map = new LongSparseArray<>();
        List<Dimension> dimensionList = setUpDimensionList();
        Collections.reverse(dimensionList);
        for(Dimension dimension : dimensionList){
            if(map.get(dimension.getTypeKey()) == null){
                List<Dimension> dimensions = new ArrayList<>();
                dimensions.add(dimension);
                map.put(dimension.getTypeKey(), dimensions);
            } else {
                map.get(dimension.getTypeKey()).add(dimension);
            }
        }
        return map;
    }

    public void setUpRecyclerView(){
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        DimensionAdapter dimensionAdapter = new DimensionAdapter(getActivity(), groupDimensions());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(dimensionAdapter);
    }
}