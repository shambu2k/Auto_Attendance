package com.shambu.autoattendance.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.shambu.autoattendance.AttendanceHistoryRVadapter;
import com.shambu.autoattendance.Interfaces.AttendanceHistoryTimelineListener;
import com.shambu.autoattendance.AutoAttendanceData;
import com.shambu.autoattendance.DataClasses.AttendanceHistoryPojo;
import com.shambu.autoattendance.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AttendanceHistoryTimelineActivity extends AppCompatActivity implements AttendanceHistoryTimelineListener {

    private AutoAttendanceData data;
    private AttendanceHistoryRVadapter adapter;
    private List<AttendanceHistoryPojo> allHistory;

    @BindView(R.id.attHistory_rv)
    RecyclerView rv;

    @BindView(R.id.toolbar_attHistory)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_history_timeline);
        ButterKnife.bind(this);

        data = new AutoAttendanceData(this);
        allHistory = new ArrayList<>();

        allHistory = data.getAttendanceHistoryfromSQL(getIntent().getStringExtra("SubjectCode"));

        toolbar.setTitle(getIntent().getStringExtra("SubjectCode"));


        if(allHistory!=null){
            adapter = new AttendanceHistoryRVadapter( this, this, allHistory);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            rv.setLayoutManager(layoutManager);
            rv.setAdapter(adapter);
        }

    }

    @Override
    public void editHistoryDetails(int position) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.edithistory_dialog);
        dialog.setTitle("Edit History");
        TextView historyHead = dialog.findViewById(R.id.edithistory_dialog_tv);
        CheckBox attended = dialog.findViewById(R.id.chk_class_attended);
        CheckBox cancelled = dialog.findViewById(R.id.chk_class_cancelled);
        Button savebutt = dialog.findViewById(R.id.save_button_dialog);

        historyHead.setText(allHistory.get(position).getSubCode()+", "+allHistory.get(position).getDate());
        if(allHistory.get(position).getAttendance()){
            attended.setChecked(true);
            cancelled.setChecked(false);
        } else if(!allHistory.get(position).getAttendance() && allHistory.get(position).getClassHappened()){
            attended.setChecked(false);
            cancelled.setChecked(false);
        } else if(!allHistory.get(position).getClassHappened()){
            attended.setChecked(false);
            cancelled.setChecked(true);
        }

        attended.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked && cancelled.isChecked()){
                    cancelled.setChecked(false);
                }
            }
        });

        cancelled.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    attended.setChecked(false);
                }
            }
        });


        savebutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(attended.isChecked()){
                    allHistory.get(position).setAttendance(true);
                    allHistory.get(position).setClassHappened(true);
                    data.updateSubjectAttendance(getIntent().getStringExtra("SubjectCode"), allHistory);
                    dialog.dismiss();
                    adapter.notifyItemChanged(position);
                } else if(!attended.isChecked() && !cancelled.isChecked()){
                    allHistory.get(position).setAttendance(false);
                    allHistory.get(position).setClassHappened(true);
                    data.updateSubjectAttendance(getIntent().getStringExtra("SubjectCode"), allHistory);
                    dialog.dismiss();
                    adapter.notifyItemChanged(position);
                } else if(cancelled.isChecked()){
                    allHistory.get(position).setAttendance(false);
                    allHistory.get(position).setClassHappened(false);
                    data.updateSubjectAttendance(getIntent().getStringExtra("SubjectCode"), allHistory);
                    dialog.dismiss();
                    adapter.notifyItemChanged(position);
                }
            }
        });


        dialog.show();
    }
}

