package com.shambu.autoattendance.DataClasses;

public class AttendanceHistoryPojo {
    private String subCode;
    private String date;
    private Boolean attendance;
    private Boolean classHappened;

    public AttendanceHistoryPojo(String subCode, String date, Boolean attendance, Boolean classHappened) {
        this.subCode = subCode;
        this.date = date;
        this.attendance = attendance;
        this.classHappened = classHappened;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getAttendance() {
        return attendance;
    }

    public void setAttendance(Boolean attendance) {
        this.attendance = attendance;
    }

    public Boolean getClassHappened() {
        return classHappened;
    }

    public void setClassHappened(Boolean classHappened) {
        this.classHappened = classHappened;
    }
}
