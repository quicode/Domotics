package com.example.domotics;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import static android.widget.Toast.LENGTH_LONG;


public class Fragment_ubicacion extends Fragment {
    public Button start,finish;
    private LocationManager ubicacion;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_ubicacion,container,false);


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
    private void localizacion(){
        ubicacion=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
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