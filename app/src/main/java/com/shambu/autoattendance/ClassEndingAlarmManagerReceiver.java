package com.shambu.autoattendance;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class ClassEndingAlarmManagerReceiver extends BroadcastReceiver {

    private static final String TAG = ClassEndingAlarmManagerReceiver.class.getSimpleName();
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;


    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationInit(context);
        NotificationNotify("ClassEnding Woke time: "+intent.getStringExtra("time"));
    }

    private void NotificationInit(Context context){
        builder = new NotificationCompat.Builder(context , "default" ) ;
        builder.setContentTitle( "Auto Attendance" ) ;
        builder.setSmallIcon(R.drawable. ic_launcher_foreground ) ;
        builder.setAutoCancel( true ) ;
        builder.setChannelId("default") ;
        notificationManager = (NotificationManager) context.getSystemService(Context. NOTIFICATION_SERVICE ) ;
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new NotificationChannel("default" , "AutoAttendance" , NotificationManager.IMPORTANCE_HIGH) ;
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel) ;
        }
        assert notificationManager != null;
    }

    private void NotificationNotify(String msg){
        builder.setContentText(msg) ;
        Notification notification = builder.build() ;
        notificationManager.notify( 1 , notification) ;
    }
}
