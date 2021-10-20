package com.riddhidamani.android_notes_app;


import androidx.annotation.NonNull;
import java.io.Serializable;
import java.util.Date;

// Separate Note class used to represent note data
public class Note implements Serializable, Comparable<Note> {

    private String noteTitle = "";
    private String noteText = "";
    private Date lastUpdateTime;
    //private static int counter = 1;

    public Note(String noteTitle, String noteText) {
        this.noteTitle = noteTitle;
        this.noteText = noteText;
        this.lastUpdateTime = new Date();
        //counter++;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNoteText() {
        return noteText;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = new Date(lastUpdateTime);
    }

    @Override
    public int compareTo(Note note) {
        if(lastUpdateTime.before(note.lastUpdateTime)) {
            return 1;
        }
        else if(lastUpdateTime.after(note.lastUpdateTime)) {
            return -1;
        }
        return 0;
    }

    @NonNull
    @Override
    public String toString() {
        return "Note{" +
                "noteTitle='" + noteTitle + '\'' +
                ", noteText='" + noteText + '\'' +
                ", lastSaveDate=" + lastUpdateTime +
                '}';
    }
}
