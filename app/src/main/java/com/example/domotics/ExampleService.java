package com.example.domotics;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import static com.example.domotics.App.CHANNEL_ID;


public class ExampleService extends Service {
    NotificationCompat.Builder mBuilder;
    int mNotificationId =1;
    String channelId ="my_channel_01";
    private Socket socket;
    public String Nickname ;
    Handler handler;
    @Override
    public void onCreate() {
        Nickname= "SERVICE";
        try {
            socket = IO.socket("http://40.87.1.104:3000");
            socket.connect();
            socket.emit("Connection", Nickname);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        socket.on("notification", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String data = args[0].toString();
                        try {
                            JSONObject jsonRespuesta = new JSONObject(data);
                            String device = jsonRespuesta.getString("message");
                            String value = jsonRespuesta.getString("chipid");
                            notificacion(device,value);
                            //Toast.makeText(Bienvenido.this,device,Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });



        handler = new Handler();
        super.onCreate();

    }

    private void runOnUiThread(Runnable runnable) {
        handler.post(runnable);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        String input = "se inicio";

        Intent notificationIntent = new Intent(this, Fragment_setting.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Example Service")
                .setContentText(input)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);

        //do heavy work on a background thread
        //stopSelf();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void notificacion(String device,String value){
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);



        mBuilder=  new NotificationCompat.Builder(this,channelId);



        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            CharSequence name="Notificaciones Socket";

            String descripcion="Comunicacion de onotificaciones";
            int importance = NotificationManager.IMPORTANCE_HIGH;


            NotificationChannel mChannel = new NotificationChannel(channelId,name,importance);
            mChannel.setDescription(descripcion);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);

            mChannel.setVibrationPattern(new long[]{100,200,300,400,500});
            mNotificationManager.createNotificationChannel(mChannel);


        }
        mBuilder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(device)
                .setContentText(value);

        mNotificationManager.notify(mNotificationId,mBuilder.build());

    }

}