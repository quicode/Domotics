package com.example.domotics;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import java.net.URISyntaxException;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;



public class Fragment_home extends Fragment {

    public Button send ;
    public Switch simpleSwitch;


    //declare socket object
    private Socket socket;

    public String Nickname ;

    public Handler mHandler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    //override ctrl + c
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_home,container,false);
        send=v.findViewById(R.id.send);
        simpleSwitch=v.findViewById(R.id.switch1);


        simpleSwitch.setChecked(true);
        Nickname= "nombre";
        try {
            socket = IO.socket("https://iotservice.azurewebsites.net/");
            socket.connect();
            socket.emit("Connection", Nickname);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        simpleSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String statusSwitch;
                if (simpleSwitch.isChecked()) {
                    statusSwitch = simpleSwitch.getTextOn().toString();
                    JSONObject json = new JSONObject();
                    try {
                        json.put("id", "11605328");
                        json.put("author", "switch");
                        json.put("text", "ON");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    socket.emit("new-message", json);
                }else {
                    statusSwitch = simpleSwitch.getTextOff().toString();
                    JSONObject json = new JSONObject();
                    try {
                        json.put("id", "11605328");
                        json.put("author", "switch");
                        json.put("text", "OFF");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    socket.emit("new-message", json);
                }
            }});

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject json = new JSONObject();
                try {
                    json.put("id", "11605328");
                    json.put("author", "student");
                    json.put("text", "mensaje android");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                socket.emit("new-message",json);
            }
        });

        socket.on("userjoinedthechat", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        String data = (String) args[0];
                        Toast.makeText(getActivity(),"Hola",Toast.LENGTH_SHORT).show();
                    }
                });
            }


        });
        socket.on("userdisconnect", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        String data = (String) args[0];
                        Toast.makeText(getActivity(),data,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return v;
    }


    public void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }

}
