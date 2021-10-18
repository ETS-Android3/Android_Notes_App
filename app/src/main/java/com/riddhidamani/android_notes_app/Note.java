package com.riddhidamani.android_notes_app;


import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable, Comparable<Note> {

    private String noteTitle = "";
    private String noteText = "";
    private Date lastSaveDate;
    private static int counter = 1;

    public Note(String noteTitle, String noteText) {
        this.noteTitle = noteTitle;
        this.noteText = noteText;
        this.lastSaveDate = new Date();
        counter++;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNoteText() {
        return noteText;
    }

    public Date getLastSaveDate() {
        return lastSaveDate;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public void setLastSaveDate(long lastSaveDate) {
        this.lastSaveDate = new Date(lastSaveDate);
    }

    @Override
    public int compareTo(Note note) {
        if(lastSaveDate.before(note.lastSaveDate)) {
            return 1;
        }
        else if(lastSaveDate.after(note.lastSaveDate)) {
            return -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Note{" +
                "noteTitle='" + noteTitle + '\'' +
                ", noteText='" + noteText + '\'' +
                ", lastSaveDate=" + lastSaveDate +
                '}';
    }
}
