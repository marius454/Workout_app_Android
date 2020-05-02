package com.example.workout_app;

public class exercise_form {
    private Integer _exNr;
    private String _exNrText;

    public exercise_form(Integer exNr){
        _exNr = exNr;
        _exNrText = "Exercise " + exNr;
    }

    public Integer getExNr(){
        return _exNr;
    }
    public String getExNrText(){
        return _exNrText;
    }
}
