package com.shambu.autoattendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.shambu.autoattendance.DataClasses.AttendanceHistoryPojo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AttendanceHistoryTimelineActivity extends AppCompatActivity {

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

        adapter = new AttendanceHistoryRVadapter(this, allHistory);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
    }
}

