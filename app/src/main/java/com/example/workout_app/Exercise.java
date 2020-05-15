package com.example.workout_app;

import android.os.Parcel;
import android.os.Parcelable;

public class Exercise implements Parcelable {
    private int exID;
    private String exName;
    private int nrSets;
    private int nrReps;

    public Exercise(int id, String name, int sets, int reps) {
        exID = id;
        exName = name;
        nrSets = sets;
        nrReps = reps;
    }

    public Exercise(String name, int sets, int reps) {
        exID = -1;
        exName = name;
        nrSets = sets;
        nrReps = reps;
    }

    protected Exercise(Parcel in) {
        exID = in.readInt();
        exName = in.readString();
        nrSets = in.readInt();
        nrReps = in.readInt();
    }

    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

    public int getID(){
        return exID;
    }
    public String getName(){
        return exName;
    }
    public int getSets(){
        return nrSets;
    }
    public int getReps(){
        return nrReps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(exID);
        dest.writeString(exName);
        dest.writeInt(nrSets);
        dest.writeInt(nrReps);
    }
}