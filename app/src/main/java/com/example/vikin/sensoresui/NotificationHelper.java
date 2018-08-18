package com.example.vikin.sensoresui;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class NotificationHelper extends ContextWrapper {
    private static final String EMDT_CHANNEL_ID = "com.example.vikin.sensoresui.EDMTDEV";
    public static final String EMDT_CHANNEL_NAME = "juan Channel";
    private NotificationManager manager;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificationHelper(Context base) {
        super(base);
        createChannels();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannels(){
        NotificationChannel edmtChannel = new NotificationChannel(EMDT_CHANNEL_ID,EMDT_CHANNEL_NAME,NotificationManager.IMPORTANCE_HIGH);
        edmtChannel.enableLights(true);
        edmtChannel.enableVibration(true);
        edmtChannel.setLightColor(Color.GREEN);
        edmtChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

        getManager().createNotificationChannel(edmtChannel);
    }

    public NotificationManager getManager() {
        if(manager == null)
            manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        return manager;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getEDMTChannelNotification(String title, String body){
        return new Notification.Builder(getApplicationContext(),EMDT_CHANNEL_ID)
                 .setContentText(body)
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true);
    }
}
