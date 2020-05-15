package com.example.workout_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";

    private static String TABLE_NAME = "program_table";
    private static String COL1 = "ID";
    private static String COL2 = "name";
    private static String COL3 = "week_nr";
    private static String COL4 = "nr_days";

    private static String eTABLE_NAME = "exercise_table";
    private static String eCOL1 = "ID";
    private static String eCOL2 = "program_ID";
    private static String eCOL3 = "day_nr";
    private static String eCOL4 = "name";
    private static String eCOL5 = "nr_sets";
    private static String eCOL6 = "nr_reps";

    private static String jTABLE_NAME = "journal_table";
    private static String jCOL1 = "ID";
    private static String jCOL2 = "exercise_ID";
    private static String jCOL3 = "week_nr";
    private static String jCOL4 = "set_nr";
    private static String jCOL5 = "weight_done";
    private static String jCOL6 = "reps_done";

    public DatabaseHelper(Context context){
        super(context, TABLE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String program_table = "CREATE TABLE " + TABLE_NAME +
                " (" + COL1 + " integer primary key autoincrement, " +
                COL2 + " varchar, " +
                COL3 + " integer not null, " +
                COL4 + " integer not null) ";

        String exercise_table = "CREATE TABLE " + eTABLE_NAME +
                " (" + eCOL1 + " integer primary key autoincrement, \n" +
                eCOL2 + " integer not null, " +
                eCOL3 + " integer not null, " +
                eCOL4 + " varchar not null, " +
                eCOL5 + " integer not null, " +
                eCOL6 + " integer not null, " +
                "constraint fk_" + eCOL2 +
                " foreign key (" + eCOL2 + ") " +
                "references " + TABLE_NAME + "(" + COL1 + ")" +
                " on delete cascade);";


        String journal_table = "CREATE TABLE " + jTABLE_NAME +
                " (" + jCOL1 + " integer primary key autoincrement, " +
                jCOL2 + " integer not null, " +
                jCOL3 + " integer not null, " +
                jCOL4 + " integer not null, " +
                jCOL5 + " real not null, " +
                jCOL6 + " integer not null, " +
                " constraint fk_" + jCOL2 +
                " foreign key (" + jCOL2 + ") " +
                "references " + eTABLE_NAME + "(" + eCOL1 + ")" +
                " on delete cascade, " +
                "unique(" + jCOL2 + ", " + jCOL3 + ", " + jCOL4 + "));";

        db.execSQL(program_table);
        db.execSQL(exercise_table);
        db.execSQL(journal_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        db.execSQL("drop table if exists " + eTABLE_NAME);
        db.execSQL("drop table if exists " + jTABLE_NAME);
        onCreate(db);
    }

    public boolean addProgram(String name, ArrayList<day_form> days){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL2, name);
        cv.put(COL3, 0);
        cv.put(COL4, days.size());

        Log.d(TAG, "addData: Adding values to " + TABLE_NAME);
        long result1 = db.insert(TABLE_NAME, null, cv);
        if (result1 == -1){
            return false;
        }

        int result2 = 0;
        for (int i = 0; i < days.size(); i++){
            day_form day = days.get(i);
            ArrayList<Exercise> exercises = day.getExercises();
            for (int j = 0; j < exercises.size(); j++){
                Exercise ex = exercises.get(j);
                ContentValues cv2 = new ContentValues();
                cv2.put(eCOL2, result1);
                cv2.put(eCOL3, day.getDayNr());
                cv2.put(eCOL4, ex.getName());
                cv2.put(eCOL5, ex.getSets());
                cv2.put(eCOL6, ex.getReps());

                Log.d(TAG, "addData: Adding values to " + eTABLE_NAME);
                if(db.insert(eTABLE_NAME, null, cv2) == -1){
                    result2 = -1;
                }
            }
        }

        if (result2 == -1){
            return false;
        } else {
            return true;
        }
    }

    public Boolean EditProgram(String name, int program_id, ArrayList<day_form> days){
        SQLiteDatabase db = this.getWritableDatabase();
        String updateProgram = ("Update " + TABLE_NAME +
                " set " + COL2 + " = " + " '" + name + "', " +
                COL4 + " = " + days.size() +
                " where " + COL1 + " = " + program_id + ";");
        db.execSQL(updateProgram);

        String deleteOld = ("Delete from " + eTABLE_NAME +
                " where program_id = " + program_id + ";");
        db.execSQL(deleteOld);

        int result = 0;
        for (int i = 0; i < days.size(); i++){
            day_form day = days.get(i);
            ArrayList<Exercise> exercises = day.getExercises();
            for (int j = 0; j < exercises.size(); j++){
                Exercise ex = exercises.get(j);
                ContentValues cv2 = new ContentValues();
                cv2.put(eCOL2, program_id);
                cv2.put(eCOL3, day.getDayNr());
                cv2.put(eCOL4, ex.getName());
                cv2.put(eCOL5, ex.getSets());
                cv2.put(eCOL6, ex.getReps());

                Log.d(TAG, "addData: Adding values to " + eTABLE_NAME);
                if(db.insert(eTABLE_NAME, null, cv2) == -1){
                    result = -1;
                }
            }
        }
        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    public Cursor getAllPrograms(){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getProgram(int program_id){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME +
                " where " + COL1 + " = " + program_id;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getProgramExercises(int program_id){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + eTABLE_NAME +
                " where " + eCOL2 + " = " + program_id;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void clearDB(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("drop table if exists " + TABLE_NAME);
        db.execSQL("drop table if exists " + eTABLE_NAME);
        db.execSQL("drop table if exists " + jTABLE_NAME);
        onCreate(db);
    }

    public void DeleteProgram(int program_id){
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteProgram = ("Delete from " + TABLE_NAME +
                " where " + COL1 + " = " + program_id + ";");
        db.execSQL(deleteProgram);
    }

    public void setProgramWeek(int weekNr){
        SQLiteDatabase db = this.getWritableDatabase();
        String updateProgram = "Update " + TABLE_NAME +
                " set " + COL3 + " = " + weekNr + ";";
        db.execSQL(updateProgram);
    }
    public void addToJournal(int exID, int week, int setNr, float weight, int reps){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(jCOL2, exID);
        cv.put(jCOL3, week);
        cv.put(jCOL4, setNr);
        cv.put(jCOL5, weight);
        cv.put(jCOL6, reps);

        Log.d(TAG, "addData: Adding values to " + jTABLE_NAME);
        long result = db.insertWithOnConflict(jTABLE_NAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
//        if (result == -1){
//            return false;
//        }else {
//            return true;
//        }
    }

    public ArrayList getJournalEntry(int exID, int week, int setNr){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + jTABLE_NAME +
                " where " + jCOL2 + " = " + exID +
                " and " + jCOL3 + " = " + week +
                " and " + jCOL4 + " = " + setNr + ";";
        Cursor data = db.rawQuery(query, null);
        if (data.moveToNext()){
            ArrayList result = new ArrayList();
            result.add(data.getFloat(4));
            result.add(data.getInt(5));
            return result;
        }
        return new ArrayList();
    }
}