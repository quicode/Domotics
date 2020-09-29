package com.example.domotics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class Fragment_medidores extends Fragment {

    private WebView webView;
    private String Nickname;
    private  Socket socket;
    @Nullable
    @Override
    //override ctrl + c
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Nickname= "SERVICE";

        try {
            socket = IO.socket("http://40.87.1.104:3000");
            socket.connect();
            socket.emit("Connection", Nickname);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        socket.on("event_tem", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String temp = args[0].toString();
                        try {
                            JSONObject jsonRespuesta = new JSONObject(temp);
                            String device = jsonRespuesta.getString("temperatura");
                            String value = jsonRespuesta.getString("humedad");

                            //Toast.makeText(Bienvenido.this,device,Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            private void runOnUiThread(Runnable runnable) {
            }
        });


        super.onCreate(savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_medidores,container,false);



        return v;
    }





}