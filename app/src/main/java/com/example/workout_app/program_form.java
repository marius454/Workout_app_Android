package com.example.workout_app;

import android.content.Context;

import java.util.ArrayList;

public class program_form {
    private Context _context;
    private String _name;
    private int _weekNr;
    private int _program_ID;

    public program_form(Context context, int id,  String name, int weekNr){
        _context = context;
        _program_ID = id;
        _name = name;
        _weekNr = weekNr;
    }

    public Context getContext(){ return _context; }
    public String getName() { return _name; }
    public int getWeekNr(){
        return _weekNr;
    }
    public int getProgramID() { return _program_ID; }

    public void setName(String name) { _name = name; }
    public void setWeekNr(int weekNR) { _weekNr = weekNR; }
}
