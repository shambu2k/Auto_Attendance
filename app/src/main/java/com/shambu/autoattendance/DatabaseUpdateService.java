package com.shambu.autoattendance;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.shambu.autoattendance.DataClasses.AttendanceHistoryPojo;
import com.shambu.autoattendance.DataClasses.SubjectPojo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DatabaseUpdateService extends Service {

    private boolean isRunning;
    private Context context;
    private Thread backgroundThread;
    private AutoAttendanceData data;
    public static final String TAG = DatabaseUpdateService.class.getSimpleName();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        this.context = this;
        this.isRunning = false;
        this.backgroundThread = new Thread(myTask);
        data = new AutoAttendanceData(context);
    }

    private Runnable myTask = new Runnable() {
        public void run() {
            SharedPreferences preferences = context.getSharedPreferences("AutoAtt", 0);
            if (preferences.getString("IngressOrEgress", "").equals("Ingress")) {
                Calendar calendar = Calendar.getInstance();
                Log.d(TAG, "INGRESS SERvice!!!!!!!!!!!");
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("IngressTime", calendar.get(Calendar.DAY_OF_WEEK) + "," +
                        calendar.get(Calendar.HOUR_OF_DAY) + "," + calendar.get(Calendar.MINUTE));
                editor.commit();
            } else if(preferences.getString("IngressOrEgress", "").equals("Egress")){
                Log.d(TAG, "EGRESS SERvice!!!!!!!!!!!");
                Calendar calendar2 = Calendar.getInstance();
                int[] ingressTimedata = getIngressTimedata(preferences.getString("IngressTime", ""));
                List<String> attendedListCodes = data.getAttendedPeriodCodes(ingressTimedata[0], ingressTimedata[1],
                        ingressTimedata[2], calendar2.get(Calendar.DAY_OF_WEEK), calendar2.get(Calendar.HOUR_OF_DAY),
                        calendar2.get(Calendar.MINUTE));
                AttendanceHistoryPojo attendancePojo;

                if (attendedListCodes != null) {
                    for (int i = 0; i < attendedListCodes.size(); i++) {
                        SubjectPojo pojo = data.getSubjectDataFromCode(attendedListCodes.get(i));
                        attendancePojo = new AttendanceHistoryPojo(attendedListCodes.get(i),
                                new SimpleDateFormat("yyyy-MM-dd").format(calendar2.getTime()),
                                true, true);
                        if (pojo.getAttendanceHistory() == null) {
                            List<AttendanceHistoryPojo> attendanceHistoryPojos = new ArrayList<>();
                            attendanceHistoryPojos.add(attendancePojo);
                            pojo.setAttendanceHistory(attendanceHistoryPojos);
                        } else {
                            pojo.getAttendanceHistory().add(attendancePojo);
                        }
                        data.updateSubject(pojo);
                    }
                }

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("IngressTime", "");
                editor.commit();
            }

            stopSelf();
        }
    };

    @Override
    public void onDestroy() {
        this.isRunning = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!this.isRunning) {
            this.isRunning = true;
            this.backgroundThread.start();
        }
        return START_STICKY;
    }

    private int[] getIngressTimedata(String data) {
        char[] datachar = data.toCharArray();
        int day, ingH, ingM;

        day = Integer.parseInt(String.valueOf(datachar[0]));

        if (datachar[3] == ',') {
            ingH = Integer.parseInt(String.valueOf(datachar[2]));
        } else {
            ingH = Integer.parseInt(String.valueOf(datachar[2])) * 10 + Integer.parseInt(String.valueOf(datachar[3]));
        }

        if (datachar[3] == ',' && datachar.length == 5) {
            ingM = Integer.parseInt(String.valueOf(datachar[4]));
        } else if (datachar[3] == ',' && datachar.length == 6) {
            ingM = Integer.parseInt(String.valueOf(datachar[4])) * 10 + Integer.parseInt(String.valueOf(datachar[5]));
        } else if (datachar[4] == ',' && datachar.length == 6) {
            ingM = Integer.parseInt(String.valueOf(datachar[5]));
        } else if (datachar[4] == ',' && datachar.length == 7) {
            ingM = Integer.parseInt(String.valueOf(datachar[5])) * 10 + Integer.parseInt(String.valueOf(datachar[6]));
        } else {
            ingM = 0;
        }

        return new int[]{day, ingH, ingM};
    }


}
