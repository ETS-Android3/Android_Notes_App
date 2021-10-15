package com.riddhidamani.android_notes_app;

import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable {

    private String title;
    private String noteText;
    private Date date;
    private static int counter = 1;

    public Note() {

    }

    public Note(String title, String noteText, Date date) {
        this.title = title + " " + counter;
        this.noteText = noteText;
        this.date = date;
        counter++;
    }

    public String getTitle() {
        return title;
    }

    public String getNoteText() {
        return noteText;
    }

    public Date getDate() {
        return date;
    }
}
