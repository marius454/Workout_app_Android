package com.example.workout_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class exercise_adapter extends RecyclerView.Adapter<exercise_adapter.viewHolder> {

    private ArrayList<exercise_form> mExList;

    public static class viewHolder extends RecyclerView.ViewHolder {
        public TextView mExText;
        public EditText mExName;
        public EditText mExSets;
        public EditText mExReps;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            mExText = itemView.findViewById(R.id.TextExercise);
            mExName = itemView.findViewById(R.id.EditExercise);
            mExSets = itemView.findViewById(R.id.EditSets);
            mExReps = itemView.findViewById(R.id.EditReps);
        }
    }

    public exercise_adapter(ArrayList<exercise_form> exList) {
        mExList = exList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_form, parent, false);
        exercise_adapter.viewHolder vh = new exercise_adapter.viewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        final exercise_form currentItem = mExList.get(position);

        holder.mExText.setText(currentItem.getExNrText());
        holder.mExName.setId(currentItem.getExNr());
        holder.mExSets.setId(currentItem.getExNr()+100);
        holder.mExReps.setId(currentItem.getExNr()+200);
        if (currentItem.getExercise() != null){
            holder.mExName.setText(currentItem.getExercise().getName());
            holder.mExSets.setText(String.valueOf(currentItem.getExercise().getSets()));
            holder.mExReps.setText(String.valueOf(currentItem.getExercise().getReps()));
        }
    }

    @Override
    public int getItemCount() {
        return mExList.size();
    }
}
