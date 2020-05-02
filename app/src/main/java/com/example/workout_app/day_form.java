package com.example.workout_app;

import android.content.Context;

public class day_form {
    private Context _context;
    private String _dayNrText;
    private Integer _dayNr;

    public day_form(Context context, int dayNr){
        _context = context;
        _dayNrText = "Day " + dayNr;
        _dayNr = dayNr;
    }

    public Context getContext(){
        return _context;
    }
    public Integer getDayNr(){
        return _dayNr;
    }
    public String getDayNrText(){
        return _dayNrText;
    }
}
