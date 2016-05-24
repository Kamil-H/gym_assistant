package com.gymassistant.Fragments.BottomFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gymassistant.R;

/**
 * Created by KamilH on 2016-05-20.
 */
public class FinishedBottomFragment extends Fragment {
    View view;
    String TAG = "FinishedBottomFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bottom_finished, container, false);
        Log.i(TAG, "onCreatView");


        return view;
    }
}
