package com.riddhidamani.android_notes_app;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
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
        Log.d(TAG, "onBindViewHolder: Filling ViewHolder Notes: " + position);

        Note note = noteList.get(position);
        // Substring - NoteTitle limit to 80 Characters on MainActivity Display list
        String noteTitleFull = note.getNoteTitle();
        if(noteTitleFull.length() > 80) {
            noteTitleFull = noteTitleFull.substring(0, 80);
            noteTitleFull = noteTitleFull + "...";
        }
        holder.noteTitle.setText(noteTitleFull);
        // Substring - NoteText limit to 80 Characters on MainActivity Display list
        String noteTextFull = note.getNoteText();
        if(noteTextFull.length() > 80) {
            noteTextFull = noteTextFull.substring(0, 80);
            noteTextFull = noteTextFull + "...";
        }
        holder.noteText.setText(noteTextFull);
        holder.lastUpdateTime.setText(note.getLastUpdateTime().toString());
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }
}
