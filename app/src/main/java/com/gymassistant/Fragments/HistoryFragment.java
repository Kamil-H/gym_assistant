package com.gymassistant.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gymassistant.Activities.FillProfileActivity;
import com.gymassistant.Models.ServerResponse;
import com.gymassistant.Models.User;
import com.gymassistant.R;
import com.gymassistant.Rest.RestClient;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

/**
 * Created by KamilH on 2016-03-21.
 */
public class HistoryFragment extends Fragment {
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_history, container, false);

        FloatingActionButton fab = (FloatingActionButton) (view.findViewById(R.id.fab));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FillProfileActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
