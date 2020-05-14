package com.example.workout_app;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class program_adapter extends RecyclerView.Adapter<program_adapter.viewHolder> {

    private ArrayList<program_form> mProgramList;

    public static class viewHolder extends RecyclerView.ViewHolder {
        public Button mButton;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            mButton = itemView.findViewById(R.id.ButtonDay);
        }
    }

    public program_adapter(ArrayList<program_form> programList){
        mProgramList = programList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_form, parent, false);
        program_adapter.viewHolder vh = new program_adapter.viewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        final program_form currentItem = mProgramList.get(position);

        holder.mButton.setText(currentItem.getName());
//        holder.mButton.setId(position);
        holder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(currentItem.getContext(), ViewProgram.class);
                intent.putExtra("programID", currentItem.getProgramID());
                intent.putExtra("programName", currentItem.getName());
                currentItem.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProgramList.size();
    }
}
