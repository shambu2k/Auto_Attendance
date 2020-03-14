package com.shambu.autoattendance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shambu.autoattendance.DataClasses.AttendanceHistoryPojo;
import com.shambu.autoattendance.DataClasses.SubjectPojo;
import com.shambu.autoattendance.DataClasses.SubjectSchedulePojo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AutoAttendanceData extends SQLiteOpenHelper {

    private static final String TAG = "AutoAttendanceData";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "attendance";
    private static final String TABLE_NAME = "AppData";
    public static final String COL_ID = "Sno";
    private static final String COL_SUBCODE = "Subject";
    private static final String COL_SUBNAME = "SubjectName";
    public static final String COL_SUBPROF = "Faculty";
    public static final String COL_MINPER = "MinimumPercentage";
    public static final String COL_SCHEDULE = "Schedule";
    public static final String COL_ATTENDANCE = "Attendance";

    public AutoAttendanceData(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_SUBCODE + " TEXT NOT NULL, " +
                COL_SUBNAME + " TEXT NOT NULL, " +
                COL_SUBPROF + " TEXT NOT NULL, " +
                COL_MINPER + " INTEGER NOT NULL, " +
                COL_SCHEDULE + " TEXT NOT NULL, " +
                COL_ATTENDANCE + " TEXT );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addNewSubtoSQL(SubjectPojo subjectData) {

        String sche = new Gson().toJson(subjectData.getSchedule());

        String ah = new Gson().toJson(subjectData.getAttendanceHistory());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_SUBCODE, subjectData.getSubjectCode());
        values.put(COL_SUBNAME, subjectData.getSubjectName());
        values.put(COL_SUBPROF, subjectData.getSubjectProF());
        values.put(COL_MINPER, subjectData.getMinPer());
        values.put(COL_SCHEDULE, sche);
        Log.d(TAG, "Schedule " + sche);
        values.put(COL_ATTENDANCE, ah);
        Log.d(TAG, "AH " + ah);

        long stat = db.insert(TABLE_NAME, null, values);
        db.close();

        Log.d(TAG, "Added new subject! status :" + stat);
    }

    public List<SubjectPojo> getAllDatafromSQL() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;

        List<SubjectPojo> subjects = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        Type listTypeSch = new TypeToken<List<SubjectSchedulePojo>>() {
        }.getType();
        Type listTypeAH = new TypeToken<List<AttendanceHistoryPojo>>() {
        }.getType();

        if (cursor.moveToFirst()) {
            do {
                SubjectPojo pojo = new SubjectPojo(
                        cursor.getInt(cursor.getColumnIndex(COL_ID)),
                        cursor.getInt(cursor.getColumnIndex(COL_MINPER)),
                        cursor.getString(cursor.getColumnIndex(COL_SUBCODE)),
                        cursor.getString(cursor.getColumnIndex(COL_SUBNAME)),
                        cursor.getString(cursor.getColumnIndex(COL_SUBPROF)),
                        new Gson().fromJson(cursor.getString(cursor.getColumnIndex(COL_SCHEDULE)), listTypeSch),
                        new Gson().fromJson(cursor.getString(cursor.getColumnIndex(COL_ATTENDANCE)), listTypeAH));
                Log.d(TAG, "sche from table: " + cursor.getString(cursor.getColumnIndex(COL_SCHEDULE)));
                subjects.add(pojo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return subjects;
    }

    public SubjectPojo getSubjectData(String subName) {
        SubjectPojo pojo;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_SUBNAME + " = '" + subName + "'";

        Cursor cursor = db.rawQuery(query, null);
        Type listTypeSch = new TypeToken<List<SubjectSchedulePojo>>() {
        }.getType();
        Type listTypeAH = new TypeToken<List<AttendanceHistoryPojo>>() {
        }.getType();

        if (cursor.moveToFirst()) {
            do {
                pojo = new SubjectPojo(
                        cursor.getInt(cursor.getColumnIndex(COL_ID)),
                        cursor.getInt(cursor.getColumnIndex(COL_MINPER)),
                        cursor.getString(cursor.getColumnIndex(COL_SUBCODE)),
                        cursor.getString(cursor.getColumnIndex(COL_SUBNAME)),
                        cursor.getString(cursor.getColumnIndex(COL_SUBPROF)),
                        new Gson().fromJson(cursor.getString(cursor.getColumnIndex(COL_SCHEDULE)), listTypeSch),
                        new Gson().fromJson(cursor.getString(cursor.getColumnIndex(COL_ATTENDANCE)), listTypeAH));
                Log.d(TAG, "sche from table: " + cursor.getString(cursor.getColumnIndex(COL_SCHEDULE)));
            } while (cursor.moveToNext());
        } else {
            return null;
        }
        return pojo;
    }

    public SubjectPojo getSubjectDataFromCode(String subCode) {
        SubjectPojo pojo;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_SUBCODE + " = '" + subCode + "'";

        Cursor cursor = db.rawQuery(query, null);
        Type listTypeSch = new TypeToken<List<SubjectSchedulePojo>>() {
        }.getType();
        Type listTypeAH = new TypeToken<List<AttendanceHistoryPojo>>() {
        }.getType();

        if (cursor.moveToFirst()) {
            do {
                pojo = new SubjectPojo(
                        cursor.getInt(cursor.getColumnIndex(COL_ID)),
                        cursor.getInt(cursor.getColumnIndex(COL_MINPER)),
                        cursor.getString(cursor.getColumnIndex(COL_SUBCODE)),
                        cursor.getString(cursor.getColumnIndex(COL_SUBNAME)),
                        cursor.getString(cursor.getColumnIndex(COL_SUBPROF)),
                        new Gson().fromJson(cursor.getString(cursor.getColumnIndex(COL_SCHEDULE)), listTypeSch),
                        new Gson().fromJson(cursor.getString(cursor.getColumnIndex(COL_ATTENDANCE)), listTypeAH));
                Log.d(TAG, "sche from table: " + cursor.getString(cursor.getColumnIndex(COL_SCHEDULE)));
            } while (cursor.moveToNext());
        } else {
            return null;
        }
        return pojo;
    }

    public List<SubjectSchedulePojo> getSubTimingsfromSQL(String code) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_SUBCODE + " LIKE '%" + code + "%'";

        String subTimingsJson;
        List<SubjectSchedulePojo> subTimings = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.getCount() > 0) {

            cursor.moveToFirst();
            subTimingsJson = cursor.getString(cursor.getColumnIndex(COL_SCHEDULE));
            Type listType = new TypeToken<List<SubjectSchedulePojo>>() {
            }.getType();
            subTimings = new Gson().fromJson(subTimingsJson, listType);
        } else {
            Log.d(TAG, "Cursor count is zero");
        }
        cursor.close();
        db.close();
        return subTimings;
    }

    public List<AttendanceHistoryPojo> getAttendanceHistoryfromSQL(String code) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_SUBCODE + " LIKE '%" + code + "%'";

        String subAttHisJson;
        List<AttendanceHistoryPojo> subAttHis = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.getCount() > 0) {

            cursor.moveToFirst();
            subAttHisJson = cursor.getString(cursor.getColumnIndex(COL_ATTENDANCE));
            Type listType = new TypeToken<List<AttendanceHistoryPojo>>() {
            }.getType();
            subAttHis = new Gson().fromJson(subAttHisJson, listType);
        } else {
            Log.d(TAG, "Cursor count is zero");
        }
        cursor.close();
        db.close();
        return subAttHis;
    }

    public void updateSubject(SubjectPojo subjectData) {
        String sche = new Gson().toJson(subjectData.getSchedule());
        String ah = new Gson().toJson(subjectData.getAttendanceHistory());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_SUBCODE, subjectData.getSubjectCode());
        values.put(COL_SUBNAME, subjectData.getSubjectName());
        values.put(COL_SUBPROF, subjectData.getSubjectProF());
        values.put(COL_MINPER, subjectData.getMinPer());
        values.put(COL_SCHEDULE, sche);
        Log.d(TAG, "Schedule " + sche);
        values.put(COL_ATTENDANCE, ah);
        Log.d(TAG, "AH " + ah);


        db.update(TABLE_NAME, values, COL_SUBCODE + " = '" + subjectData.getSubjectCode() + "'", null);
        db.close();
        Log.d(TAG, "Updated!!");
    }

    public void deleteSubject(String subcode) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, COL_SUBCODE + " = '" + subcode + "'", null);

        db.close();
        Log.d(TAG, "Deleted!!");
    }

    public void updateSubjectAttendance(String subcode, List<AttendanceHistoryPojo> historyPojos) {
        String ah = new Gson().toJson(historyPojos);
        SQLiteDatabase db = this.getWritableDatabase();
        String updateQuery = "UPDATE " + TABLE_NAME + " SET " + COL_ATTENDANCE + " = '" + ah + "' WHERE " + COL_SUBCODE +
                " = '" + subcode + "'";
        db.execSQL(updateQuery);
        db.close();
        Log.d(TAG, "Updated!! " + ah);
    }

    public List<String> getAttendedPeriodCodes(int ingressDay, int ingressHour, int ingressMinute,
                                                        int egressDay, int egressHour, int egressMinute) {


        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;

        String subTimingsJson;
        List<SubjectSchedulePojo> subTimings = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                subTimingsJson = cursor.getString(cursor.getColumnIndex(COL_SCHEDULE));
                Type listType = new TypeToken<List<SubjectSchedulePojo>>() {
                }.getType();
                if (subTimings.size() == 0) {
                    subTimings = new Gson().fromJson(subTimingsJson, listType);
                } else {
                    subTimings.addAll(new Gson().fromJson(subTimingsJson, listType));
                }
            } while (cursor.moveToNext());
        } else {
            return null;
        }

        cursor.close();
        db.close();

        List<String> attendedListSubcode = new ArrayList<>();

        if (ingressDay == egressDay) {
            for (int i = 0; i < subTimings.size(); i++) {
                if (subTimings.get(i).getDay() == ingressDay) {
                    if (ingressHour < subTimings.get(i).getfH() &&
                            egressHour > subTimings.get(i).gettH()) {
                        attendedListSubcode.add(subTimings.get(i).getSubcode());
                        Log.d(TAG, "< > "+subTimings.get(i).getSubcode());
                    } else if (ingressHour == subTimings.get(i).getfH() &&
                            egressHour > subTimings.get(i).gettH()) {
                        if (ingressMinute <= subTimings.get(i).getfM()) {
                            attendedListSubcode.add(subTimings.get(i).getSubcode());
                            Log.d(TAG, "= > "+subTimings.get(i).getSubcode());
                        }
                    } else if (ingressHour < subTimings.get(i).getfH() &&
                            egressHour == subTimings.get(i).gettH()) {
                        if (egressMinute >= subTimings.get(i).gettM()) {
                            attendedListSubcode.add(subTimings.get(i).getSubcode());
                            Log.d(TAG, "< = "+subTimings.get(i).getSubcode());
                        }
                    } else if (ingressHour == subTimings.get(i).getfH() &&
                            egressHour == subTimings.get(i).gettH()) {
                        if (ingressMinute <= subTimings.get(i).getfM() &&
                                egressMinute >= subTimings.get(i).gettM()) {
                            attendedListSubcode.add(subTimings.get(i).getSubcode());
                            Log.d(TAG, "= = "+subTimings.get(i).getSubcode());
                        }
                    } else {

                    }
                } else {

                }
            }
        }

        if(attendedListSubcode.size()==0){
            return null;
        } else {
            return attendedListSubcode;
        }
    }

    public int getSubColID(String subCode){
        int colid;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT "+COL_ID+" FROM " + TABLE_NAME + " WHERE " + COL_SUBCODE + " LIKE '%" + subCode + "%'";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() > 0) {

            cursor.moveToFirst();
            colid = cursor.getInt(cursor.getColumnIndex(COL_ID));
        } else {
            colid = 0;
            Log.d(TAG, "Cursor count is zero");
        }
        cursor.close();
        db.close();
        return colid;
    }
}
