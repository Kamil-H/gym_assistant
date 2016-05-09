package com.gymassistant.Fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        view =inflater.inflate(R.layout.fragment_history,container,false);

        FloatingActionButton fab = (FloatingActionButton) (view.findViewById(R.id.fab));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        return view;
    }

    private void getAllUsers(){
        RestClient.UserInterface service = RestClient.getClient();
        Call<User> call = service.getAllUsers();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Response<User> response) {
                Log.d("MainActivity", "Status Code = " + response.code());
                if (response.isSuccess()) {
                    User user = response.body();

                    Log.d("MainActivity", "response = " + user.getUserList().get(0).toString());
                } else {

                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("MainActivity", "onFailure " + t.getMessage());
            }
        });
    }

    private void register(){
        RestClient.UserInterface service = RestClient.getClient();
        Call<ServerResponse> call = service.login(new User("kamilkamilkamil@wp.pl", "haslohaslo", "Kamil654321"));
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Response<ServerResponse> response) {
                Log.d("MainActivity", "Status Code = " + response.code());
                if (response.isSuccess()) {
                    ServerResponse serverResponse = response.body();

                    Log.d("MainActivity", "response = " + serverResponse.isSuccess());
                } else {

                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("MainActivity", "onFailure " + t.getMessage());
            }
        });
    }
}
