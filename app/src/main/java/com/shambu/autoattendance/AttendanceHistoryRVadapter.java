package com.shambu.autoattendance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shambu.autoattendance.DataClasses.AttendanceHistoryPojo;

import java.util.List;

public class AttendanceHistoryRVadapter extends RecyclerView.Adapter {

    private static final String TAG = AttendanceHistoryRVadapter.class.getSimpleName();
    private Context mContext;
    private List<AttendanceHistoryPojo> allHistory;

    public AttendanceHistoryRVadapter(Context mContext, List<AttendanceHistoryPojo> allHistory) {
        this.mContext = mContext;
        this.allHistory = allHistory;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendancehistory_rv_item, parent,
                false);

        return new AttendanceHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AttendanceHistoryPojo pojo = allHistory.get(position);
        ((AttendanceHistoryViewHolder) holder).bind(pojo);
    }

    @Override
    public int getItemCount() {
        return allHistory.size();
    }

    private class AttendanceHistoryViewHolder extends RecyclerView.ViewHolder {

        TextView date_tv, status_tv;

        public AttendanceHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            date_tv = itemView.findViewById(R.id.history_date_tv);
            status_tv = itemView.findViewById(R.id.history_status_tv);
        }

        void bind(AttendanceHistoryPojo pojo){
            date_tv.setText("Date: "+pojo.getDate());

            if(pojo.getClassHappened() && pojo.getAttendance()){
                status_tv.setText("Status: Present");
            } else if(pojo.getClassHappened() && !pojo.getAttendance()){
                status_tv.setText("Status: Absent");
            } else {
                status_tv.setText("Status: Class cancelled");
            }
        }
    }
}
