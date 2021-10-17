package com.riddhidamani.android_notes_app;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView noteTitle;
    TextView lastSaveDate;
    TextView noteText;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        noteTitle = itemView.findViewById(R.id.noteTitle);
        noteText = itemView.findViewById(R.id.noteText);
        lastSaveDate = itemView.findViewById(R.id.dateTime);


    }
}
