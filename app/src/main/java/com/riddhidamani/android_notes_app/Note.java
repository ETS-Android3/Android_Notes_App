package com.riddhidamani.android_notes_app;

import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable {

    private String title;
    private String noteText;
    private Date lastSaveDate;
    private static int counter = 1;

    public Note() {

    }

    public Note(String title, String noteText) {
        this.title = title + " " + counter;
        this.noteText = noteText;
        counter++;
    }

    public String getTitle() {
        return title;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    @Override
    public String toString() {
        return "Note{" +
                "title='" + title + '\'' +
                ", noteText='" + noteText + '\'' +
                ", lastSaveDate=" + lastSaveDate +
                '}';
    }


}
