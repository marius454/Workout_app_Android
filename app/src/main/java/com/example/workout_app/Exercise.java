package com.example.workout_app;

import android.os.Parcel;
import android.os.Parcelable;

public class Exercise implements Parcelable {
    private String exName;
    private int nrSets;
    private int nrReps;

    public Exercise(String name, int sets, int reps) {
        exName = name;
        nrSets = sets;
        nrReps = reps;
    }

    protected Exercise(Parcel in) {
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
        dest.writeString(exName);
        dest.writeInt(nrSets);
        dest.writeInt(nrReps);
    }
}