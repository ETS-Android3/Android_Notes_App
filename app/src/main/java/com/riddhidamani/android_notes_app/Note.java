package com.riddhidamani.android_notes_app;

import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable {

    private String title;
    private String noteText;
    private Date date;

    public Note(String title, String noteText, Date date) {
        this.title = title;
        this.noteText = noteText;
        this.date = date;
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
