package com.example.workout_app;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class day_adapter extends RecyclerView.Adapter<day_adapter.viewHolder> {

    private ArrayList<day_form> mdayList;

    public static class viewHolder extends RecyclerView.ViewHolder {
        public Button mButton;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            mButton = itemView.findViewById(R.id.ButtonDay);
        }
    }

    public day_adapter(ArrayList<day_form> dayList){
        mdayList = dayList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_form, parent, false);
        viewHolder vh = new viewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        final day_form currentItem = mdayList.get(position);

        holder.mButton.setText(currentItem.getDayNrText());
        holder.mButton.setId(currentItem.getDayNr());
        if (currentItem.getLocation().equals("create")){
            holder.mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(currentItem.getContext(), DayCustomization.class);
                    intent.putExtra("dayNrText", currentItem.getDayNrText());
                    intent.putExtra("Exercises", currentItem.getExercises());
                    ((Activity) currentItem.getContext()).startActivityForResult(intent, currentItem.getDayNr());
                }
            });
        } else {
            holder.mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(currentItem.getContext(), ViewDay.class);
                    intent.putExtra("dayNrText", currentItem.getDayNrText());
                    intent.putExtra("Exercises", currentItem.getExercises());
                    currentItem.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mdayList.size();
    }
}
