package com.shambu.autoattendance;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.pathsense.android.sdk.location.PathsenseGeofenceEvent;

public class PathsenseGeofenceReceiver extends BroadcastReceiver {

    private static final String TAG = PathsenseGeofenceReceiver.class.getSimpleName();
    private SharedPreferences preferences;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        PathsenseGeofenceEvent geofenceEvent = PathsenseGeofenceEvent.fromIntent(intent);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context , "default" ) ;
        builder.setContentTitle( "Auto Attendance" ) ;
        builder.setSmallIcon(R.drawable. ic_launcher_foreground ) ;
        builder.setAutoCancel( true ) ;
        builder.setChannelId("default") ;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context. NOTIFICATION_SERVICE ) ;
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new NotificationChannel("default" , "AutoAttendance" , NotificationManager.IMPORTANCE_HIGH) ;
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel) ;
        }
        assert notificationManager != null;

        if (geofenceEvent != null)
        {
            preferences = context.getSharedPreferences("AutoAtt", 0);
            SharedPreferences.Editor editor = preferences.edit();
            if (geofenceEvent.isIngress())
            {
                Location location = geofenceEvent.getLocation();
                builder.setContentText( "Ingress" ) ;
                Notification notification = builder.build() ;
                notificationManager.notify( 1 , notification) ;
                editor.putString("IngressOrEgress", "Ingress");
                editor.commit();
                context.startService(new Intent(context, DatabaseUpdateService.class));
                Log.i(TAG, "geofenceInress = " + location.getTime() + ", " +
                        location.getProvider() + ", " + location.getLatitude() + ", " +
                        location.getLongitude() + ", " + location.getAltitude() + ", " +
                        location.getSpeed() + ", " + location.getBearing() + ", " + location.getAccuracy());
            }
            else if (geofenceEvent.isEgress())
            {
                builder.setContentText( "Egress" ) ;
                Notification notification = builder.build() ;
                notificationManager.notify( 1 , notification) ;
                Location location = geofenceEvent.getLocation();
                editor.putString("IngressOrEgress", "Egress");
                editor.commit();
                context.startService(new Intent(context, DatabaseUpdateService.class));
                Log.i(TAG, "geofenceEgress = " + location.getTime() + ", " +
                        location.getProvider() + ", " + location.getLatitude() + ", " +
                        location.getLongitude() + ", " + location.getAltitude() + ", " +
                        location.getSpeed() + ", " + location.getBearing() + ", " + location.getAccuracy());
            }
        }
    }
}
