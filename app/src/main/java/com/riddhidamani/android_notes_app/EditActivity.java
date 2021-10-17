package com.riddhidamani.android_notes_app;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    private static final String TAG = "EditActivity";
    private Note note;
    private EditText noteTitleEdit;
    private EditText noteTextEdit;
    private Note tempNote;
    private boolean isNewNote;
    private long tempDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        noteTitleEdit = findViewById(R.id.noteTitleEdit);
        noteTextEdit = findViewById(R.id.noteTextEdit);

        Intent intent = getIntent();
        if(intent.hasExtra("EDIT_NOTE")) {
            tempNote = (Note) intent.getSerializableExtra("EDIT_NOTE");
            if(tempNote != null) {
                String noteTitle = tempNote.getNoteTitle();
                String noteText = tempNote.getNoteText();
                tempDateTime = intent.getLongExtra("Time", 0);
                noteTitleEdit.setText(noteTitle);
                noteTextEdit.setText(noteText);
            }
            else {
                noteTitleEdit.setText("Note not found!");
            }
            isNewNote = false;
        }
        else {
            isNewNote = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // loads every source file and build objects on it and returns a view
        getMenuInflater().inflate(R.menu.edit_notes_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.noteSaveIcon) {
            // code yet to be written
            saveEditActivity();
            return true;
        }
        else {
            Log.d(TAG, "onOptionsItemSelected: Unknown Item" + item.getTitle());
        }
        return super.onOptionsItemSelected(item);
    }


    // When + icon is clicked, Edit Activity is opened.
    public void saveEditActivity() {

        String noteTitle = noteTitleEdit.getText().toString();
        String noteText = noteTextEdit.getText().toString();
        // Add New Note
        if(isNewNote) {
            if(noteTitle.trim().isEmpty()) {
                Toast.makeText(this, "Untitled activity was not saved", Toast.LENGTH_LONG).show();
                EditActivity.super.onBackPressed();
                return;
            }
            Note newNote = new Note(noteTitle, noteText);
            Intent intent = new Intent();
            intent.putExtra("NEW_NOTE", newNote);
            setResult(01, intent);
            finish();
            super.onBackPressed();
        }
        // Edit Existing Note
        else {
            if(noteTitle.trim().isEmpty()) {
                Toast.makeText(this, "Un-titled activity was not saved", Toast.LENGTH_LONG).show();
                EditActivity.super.onBackPressed();
                return;
            }
            tempNote.setNoteTitle(noteTitle);
            tempNote.setNoteText(noteText);
            tempNote.setLastSaveDate(tempDateTime);
            Intent intent = new Intent();
            intent.putExtra("EDIT_NOTE",  tempNote);
            setResult(02, intent);
            finish();
        }
    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Save Note");
        builder.setMessage("Your note is not saved! Save Note?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                saveEditActivity();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditActivity.super.onBackPressed();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}