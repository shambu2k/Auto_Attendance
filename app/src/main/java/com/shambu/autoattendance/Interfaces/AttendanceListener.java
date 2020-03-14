package com.shambu.autoattendance.Interfaces;

import android.view.View;

public interface AttendanceListener {

    void markAttendanceClick(View view, int position);
    void openAttendanceHistoryOnClick(int position);
    void editSubjectOnLongClick(int position);
}
