package com.riddhidamani.android_notes_app;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private static final String TAG = "NotesAdapter";
    private final List<Note> noteList;
    private final MainActivity mainActivity;

    public NotesAdapter(List<Note> noteList, MainActivity mainActivity) {
        this.noteList = noteList;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.d(TAG, "onCreateViewHolder: MAKING NEW MyViewHolder");
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notes_list_entry, parent, false);

        itemView.setOnClickListener(mainActivity);
        itemView.setOnLongClickListener(mainActivity);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: FILLING VIEW HOLDER Note " + position);

        Note note = noteList.get(position);
        holder.noteTitle.setText(note.getNoteTitle());
        // Need to modify this based on the ... display on the Main Activity
        holder.noteText.setText(note.getNoteText());
        holder.lastSaveDate.setText(note.getLastSaveDate().toString());

    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }
}
