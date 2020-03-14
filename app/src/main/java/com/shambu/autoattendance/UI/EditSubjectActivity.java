package com.shambu.autoattendance.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shambu.autoattendance.AutoAttendanceData;
import com.shambu.autoattendance.ClassEndingAlarmManagerReceiver;
import com.shambu.autoattendance.DataClasses.SubjectPojo;
import com.shambu.autoattendance.DataClasses.SubjectSchedulePojo;
import com.shambu.autoattendance.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditSubjectActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.subcode_edt_edt)
    TextInputEditText subCode;
    @BindView(R.id.subName_edt_edt)
    TextInputEditText subName;
    @BindView(R.id.profName_edt_edt)
    TextInputEditText profName;
    @BindView(R.id.minper_edt_edt)
    TextInputEditText subMinPercentage;

    @BindView(R.id.save_button)
    Button savebutton;
    @BindView(R.id.delete_button)
    Button deletebutton;

    @BindView(R.id.mon_chk_edt)
    CheckBox mon;
    @BindView(R.id.tue_chk_edt)
    CheckBox tue;
    @BindView(R.id.wed_chk_edt)
    CheckBox wed;
    @BindView(R.id.thur_chk_edt)
    CheckBox thur;
    @BindView(R.id.fri_chk_edt)
    CheckBox fri;
    @BindView(R.id.sat_chk_edt)
    CheckBox sat;
    @BindView(R.id.sun_chk_edt)
    CheckBox sun;

    @BindView(R.id.mon_from_edt)
    TextView fmon;
    @BindView(R.id.tue_from_edt)
    TextView ftue;
    @BindView(R.id.wed_from_edt)
    TextView fwed;
    @BindView(R.id.thur_from_edt)
    TextView fthur;
    @BindView(R.id.fri_from_edt)
    TextView ffri;
    @BindView(R.id.sat_from_edt)
    TextView fsat;
    @BindView(R.id.sun_from_edt)
    TextView fsun;
    @BindView(R.id.mon_to_edt)
    TextView tmon;
    @BindView(R.id.tue_to_edt)
    TextView ttue;
    @BindView(R.id.wed_to_edt)
    TextView twed;
    @BindView(R.id.thur_to_edt)
    TextView tthur;
    @BindView(R.id.fri_to_edt)
    TextView tfri;
    @BindView(R.id.sat_to_edt)
    TextView tsat;
    @BindView(R.id.sun_to_edt)
    TextView tsun;

    private static final String TAG = "EditSubjectActivity";
    private SubjectPojo pojo;
    private int mHour, mMin;
    private List<SubjectSchedulePojo> subSchedule;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_subject);
        ButterKnife.bind(this);

        pref = this.getSharedPreferences("AutoAtt", 0);
        subSchedule = new ArrayList<>();
        Type listType = new TypeToken<SubjectPojo>() {
        }.getType();

        pojo =  new Gson().fromJson(getIntent().getStringExtra("SubjectPojo"), listType);

        subCode.setText(pojo.getSubjectCode());
        subName.setText(pojo.getSubjectName());
        profName.setText(pojo.getSubjectProF());
        subMinPercentage.setText(String.valueOf(pojo.getMinPer()));


        for(int i = 0 ; i < pojo.getSchedule().size() ; i++){
            switch (pojo.getSchedule().get(i).getDay()){
                case 2 : {
                    mon.setChecked(true);
                    setTiminginTextView(fmon, pojo.getSchedule().get(i).getfH(), pojo.getSchedule().get(i).getfM());
                    setTiminginTextView(tmon, pojo.getSchedule().get(i).gettH(), pojo.getSchedule().get(i).gettM());
                    break;
                }
                case 3 : {
                    tue.setChecked(true);
                    setTiminginTextView(ftue, pojo.getSchedule().get(i).getfH(), pojo.getSchedule().get(i).getfM());
                    setTiminginTextView(ttue, pojo.getSchedule().get(i).gettH(), pojo.getSchedule().get(i).gettM());
                    break;
                }
                case 4 : {
                    wed.setChecked(true);
                    setTiminginTextView(fwed, pojo.getSchedule().get(i).getfH(), pojo.getSchedule().get(i).getfM());
                    setTiminginTextView(twed, pojo.getSchedule().get(i).gettH(), pojo.getSchedule().get(i).gettM());
                    break;
                }
                case 5 : {
                    thur.setChecked(true);
                    setTiminginTextView(fthur, pojo.getSchedule().get(i).getfH(), pojo.getSchedule().get(i).getfM());
                    setTiminginTextView(tthur, pojo.getSchedule().get(i).gettH(), pojo.getSchedule().get(i).gettM());
                    break;
                }
                case 6 : {
                    fri.setChecked(true);
                    setTiminginTextView(ffri, pojo.getSchedule().get(i).getfH(), pojo.getSchedule().get(i).getfM());
                    setTiminginTextView(tfri, pojo.getSchedule().get(i).gettH(), pojo.getSchedule().get(i).gettM());
                    break;
                }
                case 7 : {
                    sat.setChecked(true);
                    setTiminginTextView(fsat, pojo.getSchedule().get(i).getfH(), pojo.getSchedule().get(i).getfM());
                    setTiminginTextView(tsat, pojo.getSchedule().get(i).gettH(), pojo.getSchedule().get(i).gettM());
                    break;
                }
                case 1 : {
                    sun.setChecked(true);
                    setTiminginTextView(fsun, pojo.getSchedule().get(i).getfH(), pojo.getSchedule().get(i).getfM());
                    setTiminginTextView(tsun, pojo.getSchedule().get(i).gettH(), pojo.getSchedule().get(i).gettM());
                    break;
                }
            }
        }

        fmon.setOnClickListener(this);
        tmon.setOnClickListener(this);
        ftue.setOnClickListener(this);
        ttue.setOnClickListener(this);
        fwed.setOnClickListener(this);
        twed.setOnClickListener(this);
        fthur.setOnClickListener(this);
        tthur.setOnClickListener(this);
        ffri.setOnClickListener(this);
        tfri.setOnClickListener(this);
        fsat.setOnClickListener(this);
        tsat.setOnClickListener(this);
        fsun.setOnClickListener(this);
        tsun.setOnClickListener(this);
    }

    private void setTiminginTextView(TextView tv,int from, int to){
        if(from/10==0 && to/10==0){
            tv.setText("0"+from+":0"+to);
        } else if(from/10==0 && to/10!=0){
            tv.setText("0"+from+":"+to);
        } else if(from/10!=0 && to/10==0){
            tv.setText(from+":0"+to);
        } else {
            tv.setText(from+":"+to);
        }
    }

    @OnClick(R.id.delete_button)
    void deleteSubject(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This action can't be undone.")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AutoAttendanceData data = new AutoAttendanceData(getApplicationContext());
                        data.deleteSubject(pojo.getSubjectCode());
                        setResult(Activity.RESULT_OK);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Do you want to delete this Subject ?");
        alert.show();
    }

    @OnClick(R.id.save_button)
    void saveSubject() {
        if (mon.isChecked()) {
            SubjectSchedulePojo ss = new SubjectSchedulePojo(subCode.getText().toString(),2,
                    getH(fmon), getM(fmon), getH(tmon), getM(tmon));
            checkFirst(2,  getH(tmon), getM(tmon));
            subSchedule.add(ss);
        }
        if (tue.isChecked()) {
            SubjectSchedulePojo ss = new SubjectSchedulePojo(subCode.getText().toString(),3,
                    getH(ftue), getM(ftue), getH(ttue), getM(ttue));
            checkFirst(3, getH(ttue), getM(ttue));
            subSchedule.add(ss);
        }
        if (wed.isChecked()) {
            SubjectSchedulePojo ss = new SubjectSchedulePojo(subCode.getText().toString(),4,
                    getH(fwed), getM(fwed), getH(twed), getM(twed));
            checkFirst(4, getH(twed), getM(twed));
            subSchedule.add(ss);
        }
        if (thur.isChecked()) {
            SubjectSchedulePojo ss = new SubjectSchedulePojo(subCode.getText().toString(),5,
                    getH(fthur), getM(fthur), getH(tthur), getM(tthur));
            checkFirst(5, getH(tthur), getM(tthur));
            subSchedule.add(ss);
        }
        if (fri.isChecked()) {
            SubjectSchedulePojo ss = new SubjectSchedulePojo(subCode.getText().toString(),6,
                    getH(ffri), getM(ffri), getH(tfri), getM(tfri));
            checkFirst(6, getH(tfri), getM(tfri));
            subSchedule.add(ss);
        }
        if (sat.isChecked()) {
            SubjectSchedulePojo ss = new SubjectSchedulePojo(subCode.getText().toString(),7,
                    getH(fsat), getM(fsat), getH(tsat), getM(tsat));
            checkFirst(7, getH(tsat), getM(tsat));
            subSchedule.add(ss);
        }
        if (sun.isChecked()) {
            SubjectSchedulePojo ss = new SubjectSchedulePojo(subCode.getText().toString(),1,
                    getH(fsun), getM(fsun), getH(tsun), getM(tsun));
            checkFirst(1, getH(tsun), getM(tsun));
            subSchedule.add(ss);
        }
        SubjectPojo subjectData = new SubjectPojo(Integer.parseInt(subMinPercentage.getText().toString()),
                subCode.getText().toString(), subName.getText().toString(), profName.getText().toString(), subSchedule, pojo.getAttendanceHistory());

        fireInitAlarm();
        AutoAttendanceData sqlTable = new AutoAttendanceData(EditSubjectActivity.this);
        sqlTable.updateSubject(subjectData);
        setResult(Activity.RESULT_OK);
        finish();

    }

    private int getH(TextView tv) {
        int h;
        String tvText = tv.getText().toString();
        Log.d(TAG, tvText);
        h = Integer.parseInt(String.valueOf(tvText.charAt(1))) * 1 + Integer.parseInt(String.valueOf(tvText.charAt(0))) * 10;

        return h;
    }

    private int getM(TextView tv) {
        int m;
        String tvText = tv.getText().toString();
        Log.d(TAG, tvText);
        m = Integer.parseInt(String.valueOf(tvText.charAt(4))) * 1 + Integer.parseInt(String.valueOf(tvText.charAt(3))) * 10;

        return m;
    }

    @Override
    public void onClick(View view) {
        TextView v = (TextView) view;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int min) {
                if(hour/10 == 0){
                    if(min/10 == 0){
                        v.setText("0"+hour + ":0" + min);
                    }
                    else{
                        v.setText("0"+hour + ":" + min);
                    }
                }
                else {
                    if(min/10 == 0){
                        v.setText(hour + ":0" + min);
                    }
                    else{
                        v.setText(hour + ":" + min);
                    }
                }
            }
        }, mHour, mMin, true);
        timePickerDialog.show();
    }

    private void checkFirst(int day, int h, int m){
        if(pref.getString("FirstTriggerOfTheWeek", "").equals("")){
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("FirstTriggerOfTheWeek",day+","+h+","+m);
            editor.commit();
        } else if(getIngressTimedata(pref.getString("FirstTriggerOfTheWeek", ""))[0] > day){
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("FirstTriggerOfTheWeek",day+","+h+","+m);
            editor.commit();
        } else if(getIngressTimedata(pref.getString("FirstTriggerOfTheWeek", ""))[0] == day &&
                getIngressTimedata(pref.getString("FirstTriggerOfTheWeek", ""))[1] > h){
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("FirstTriggerOfTheWeek",day+","+h+","+m);
            editor.commit();
        } else if(getIngressTimedata(pref.getString("FirstTriggerOfTheWeek", ""))[0] == day &&
                getIngressTimedata(pref.getString("FirstTriggerOfTheWeek", ""))[1] == h &&
                getIngressTimedata(pref.getString("FirstTriggerOfTheWeek", ""))[2] > m){
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("FirstTriggerOfTheWeek",day+","+h+","+m);
            editor.commit();
        } else {

        }
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

    private void fireInitAlarm(){
        Calendar calendar = Calendar.getInstance();
        if(!pref.getString("FirstTriggerOfTheWeek", "").equals("")){
            int[] firstTimeData = getIngressTimedata(pref.getString("FirstTriggerOfTheWeek", ""));
            calendar.set(Calendar.DAY_OF_WEEK, firstTimeData[0]);
            calendar.set(Calendar.HOUR_OF_DAY, firstTimeData[1]);
            calendar.set(Calendar.MINUTE, firstTimeData[2]);
            calendar.set(Calendar.SECOND, 0);

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, ClassEndingAlarmManagerReceiver.class);
            intent.putExtra("time", firstTimeData[1]+":"+firstTimeData[2]);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 619, intent, 0);

            if(pref.getBoolean("AlarmStarted", false)){
                alarmManager.cancel(pendingIntent);
            }
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("AlarmStarted", true);
            editor.commit();
        }
    }
}
