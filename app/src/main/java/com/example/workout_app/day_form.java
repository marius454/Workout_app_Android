package com.example.workout_app;

import android.content.Context;

import java.util.ArrayList;

public class day_form {
    private Context _context;
    private String _dayNrText;
    private Integer _dayNr;
    private ArrayList<Exercise> _exercises;
    private String _location;
    private int _weekNr;

    public day_form(Context context, int dayNr){
        _dayNrText = "Day " + dayNr;
        _dayNr = dayNr;
        _exercises = new ArrayList<Exercise>();
        _context = context;
        _location = "create";
    }

    public Integer getDayNr(){
        return _dayNr;
    }
    public String getDayNrText(){
        return _dayNrText;
    }
    public ArrayList<Exercise> getExercises(){ return _exercises; }
    public Context getContext(){ return _context; }
    public String getLocation(){ return _location; }
    public int getWeekNr(){ return _weekNr; }

    public void addExercise(String name, int sets, int reps){
        _exercises.add(new Exercise(name, sets, reps));
    }
    public void setExercises(ArrayList<Exercise> exercises){
        _exercises = exercises;
    }
    public void setLocation(String location){
        _location = location;
    }
    public void setWeekNr(int weekNr){
        _weekNr = weekNr;
    }
}
