package com.riddhidamani.android_notes_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    private static final String TAG = "EditActivity";
    private Note note;
    private EditText noteTitleEdit;
    private EditText noteTextEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        noteTitleEdit = findViewById(R.id.noteTitleEdit);
        noteTextEdit = findViewById(R.id.noteTextEdit);
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
            return true;
        }
        else {
            Log.d(TAG, "onOptionsItemSelected: Unknown Item" + item.getTitle());
        }
        return super.onOptionsItemSelected(item);
    }


    // When + icon is clicked, Edit Activity is opened.
    public boolean saveEditActivity(MenuItem item) {

        String noteTitle = noteTitleEdit.getText().toString();
        String noteText = noteTextEdit.getText().toString();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Note Title", noteTitle);
        intent.putExtra("Note Text", noteText);
        startActivity(intent);
        return true;

//        Intent intent = getIntent();
//        if(intent.hasExtra("Note")) {
//            note = (Note) intent.getSerializableExtra("Note");
//            if(note != null) {
//                noteTitleEdit.setText(note.getTitle().toString());
//                noteTextEdit.setText(note.getNoteText().toString());
//            }
//            else {
//                noteTitleEdit.setText("Note Title not found");
//                noteTextEdit.setText("Note Text not found");
//            }
//        }
    }

}