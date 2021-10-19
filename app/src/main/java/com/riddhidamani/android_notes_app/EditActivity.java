package com.riddhidamani.android_notes_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    private static final String TAG = "EditActivity";
    private EditText noteTitleEdit;
    private EditText noteTextEdit;
    private Note tempNote;
    private boolean isNewNote = false;
    private long tempDateTime;
    private String oldNoteTitle;
    private String oldNoteText;

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
                oldNoteTitle = tempNote.getNoteTitle();
                oldNoteText = tempNote.getNoteText();
                tempDateTime = intent.getLongExtra("Time", 0);
                noteTitleEdit.setText(oldNoteTitle);
                noteTextEdit.setText(oldNoteText);
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
                showNoTitleDialog();
                return;
            }
            Note newNote = new Note(noteTitle, noteText);
            Intent intent = new Intent();
            intent.putExtra("NEW_NOTE", newNote);
            setResult(01, intent);
            finish();
        }
        // Edit Existing Note
        else {
            if(noteTitle.trim().isEmpty()) {
                showNoTitleDialog();
                return;
            }
            tempNote.setNoteTitle(noteTitle);
            tempNote.setNoteText(noteText);
            tempNote.setLastUpdateTime(tempDateTime);
            Intent intent = new Intent();
            intent.putExtra("EDIT_NOTE", tempNote);
            setResult(02, intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {

        String noteTitle = noteTitleEdit.getText().toString();
        String noteText = noteTextEdit.getText().toString();

        if(isNewNote && (!noteTitle.trim().isEmpty())) {
            showSaveDialog();
        }
        else if(isNewNote && (!noteText.trim().isEmpty())) {
            showSaveDialog();
        }
        else if(!(noteTitle.equals(tempNote.getNoteTitle()))) {
            showSaveDialog();
        }
        else if(!(noteText.equals(tempNote.getNoteText()))) {
            showSaveDialog();
        }
        else {
            Toast.makeText(this, getResources().getString(R.string.no_changes_made), Toast.LENGTH_SHORT).show();
            super.onBackPressed();
            return;
        }
    }

    public void showNoTitleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("No Title");
        builder.setMessage("Note cannot be saved without title! Do you want to exit without saving?");
        builder.setPositiveButton("YES", (dialogInterface, i) -> EditActivity.super.onBackPressed());
        builder.setNegativeButton("NO", (dialogInterface, i) -> {
                return;
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showSaveDialog() {
        String noteTitle = noteTitleEdit.getText().toString();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Save Note");
        builder.setMessage("Your note is not saved! Save Note '" + noteTitle + "' ?");
        builder.setPositiveButton("YES", (dialogInterface, i) -> saveEditActivity());
        builder.setNegativeButton("NO", (dialogInterface, i) -> EditActivity.super.onBackPressed());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}