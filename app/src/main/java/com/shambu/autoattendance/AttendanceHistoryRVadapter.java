package com.shambu.autoattendance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.shambu.autoattendance.DataClasses.AttendanceHistoryPojo;

import java.util.List;

public class AttendanceHistoryRVadapter extends RecyclerView.Adapter {

    private static final String TAG = AttendanceHistoryRVadapter.class.getSimpleName();
    private Context mContext;
    private List<AttendanceHistoryPojo> allHistory;
    private AttendanceHistoryTimelineListener listener;

    public AttendanceHistoryRVadapter(AttendanceHistoryTimelineListener listener,
                                      Context mContext, List<AttendanceHistoryPojo> allHistory) {
        this.listener = listener;
        this.mContext = mContext;
        this.allHistory = allHistory;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendancehistory_rv_item, parent,
                false);

        return new AttendanceHistoryViewHolder(view, listener);
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

    private class AttendanceHistoryViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        TextView date_tv, status_tv;
        CardView card;
        private AttendanceHistoryTimelineListener mListener;

        public AttendanceHistoryViewHolder(@NonNull View itemView, AttendanceHistoryTimelineListener listener) {
            super(itemView);
            this.mListener = listener;
            card = itemView.findViewById(R.id.attHistory_cardview);
            date_tv = itemView.findViewById(R.id.history_date_tv);
            status_tv = itemView.findViewById(R.id.history_status_tv);

            card.setOnLongClickListener(this);
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

        @Override
        public boolean onLongClick(View v) {
            mListener.editHistoryDetails(getAdapterPosition());
            return false;
        }
    }
}
