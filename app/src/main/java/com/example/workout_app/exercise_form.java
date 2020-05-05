package com.example.workout_app;

import java.util.ArrayList;
import java.util.Map;


public class exercise_form {
    private int _exNr;
    private String _exNrText;
    private Exercise _exrecise;

    public exercise_form(int exNr){
        _exNr = exNr;
        _exNrText = "Exercise " + exNr;
    }
    public exercise_form(int exNr, Exercise exercise){
        _exNr = exNr;
        _exNrText = "Exercise " + exNr;
        _exrecise = exercise;
    }

    public Integer getExNr(){ return _exNr; }
    public String getExNrText(){ return _exNrText; }
    public Exercise getExercise() {
        return _exrecise;
    }

    public void setExercise(String name, int sets, int reps) {
        _exrecise = new Exercise(name, sets, reps);
    }
    public void setExercise(Exercise exercise){
        _exrecise = exercise;
    }
}
