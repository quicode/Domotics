package com.example.domotics;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;



public class Fragment_setting extends Fragment {
    public Button start,finish;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_setting,container,false);

        start=v.findViewById(R.id.startserv);
        finish=v.findViewById(R.id.finishserv);

        start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startService(view);
            }


        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(view);
            }


        });


        return v;
    }

    public void startService(View v) {
        String input = "se creo";
        Intent serviceIntent = new Intent(getActivity(), ExampleService.class);
        serviceIntent.putExtra("inputExtra", input);
        ContextCompat.startForegroundService(getContext(), serviceIntent);
    }

    public void stopService(View v) {
        Intent serviceIntent = new Intent(getActivity(),ExampleService.class);
        getActivity().stopService(serviceIntent);
    }



}