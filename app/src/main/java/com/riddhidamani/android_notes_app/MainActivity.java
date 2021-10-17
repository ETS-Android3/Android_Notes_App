package com.riddhidamani.android_notes_app;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.JsonWriter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private static final String TAG = "MainActivity";
    private final List<Note> notesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Note note;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private NotesAdapter notesAdapter;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readJSONFile();

        String app_name = getResources().getString(R.string.app_name);
        setTitle( app_name + " (" + notesList.size() + ")");
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler);

        notesAdapter = new NotesAdapter(notesList, this);
        recyclerView.setAdapter(notesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), this::handleResult);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // loads every source file and build objects on it and returns a view
        getMenuInflater().inflate(R.menu.opt_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.aboutOpt) {
            // Invoke About Activity
            openAboutActivity(item);
            return true;
        }
        else if(item.getItemId() == R.id.addNewNoteOpt) {
            // Invoke Add New Note Activity
            openEditActivity();

            return true;
        }
        else {
            Log.d(TAG, "onOptionsItemSelected: Unknown Item" + item.getTitle());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        index = recyclerView.getChildAdapterPosition(view);
        note = notesList.get(index);
        openEditActivityExist(note);
        //Toast.makeText(view.getContext(), "onClick" + note.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onLongClick(View view) {
        int position = recyclerView.getChildAdapterPosition(view);
        Note note = notesList.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Note");
        builder.setMessage("Delete Note '" + note.getNoteTitle() + "'?");
        builder.setPositiveButton("Yes", (dialog, id) -> {
            notesList.remove(position);
            String app_name = getResources().getString(R.string.app_name);
            setTitle( app_name + " (" + notesList.size() + ")");
            notesAdapter.notifyDataSetChanged();
        });
        builder.setNegativeButton("No", (dialog, id) -> {
            //super.onBackPressed();
            return;
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        return false;
    }

    // When + icon is clicked, Edit Activity is opened.
    public void openEditActivity() {
        Intent intent = new Intent(this, EditActivity.class);
        activityResultLauncher.launch(intent);
    }


    // When + icon is clicked, Edit Activity is opened.
    public void openEditActivityExist(Note note) {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("EDIT_NOTE", note);
        activityResultLauncher.launch(intent);
    }

    // When info icon is clicked, About Activity is opened.
    public void openAboutActivity(MenuItem item) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        //Toast.makeText(this, "The Back button was pressed - Bye!", Toast.LENGTH_LONG).show();
        super.onBackPressed();
    }

    public void handleResult(ActivityResult result) {
        Log.d(TAG, "On handleResult Method: ");
        if(result.getResultCode() == 01) {
            Intent data = result.getData();
            if(data != null) {
                // Extract data here
                note = (Note) data.getSerializableExtra("NEW_NOTE");
                if(note != null) {
                    notesList.add(note);
                    Collections.sort(notesList);
                    notesAdapter.notifyDataSetChanged();
                    String app_name = getResources().getString(R.string.app_name);
                    setTitle( app_name + " (" + notesList.size() + ")");
                }
            }
            Log.d(TAG, "onActivityResult: Return from Edit Activity: Add note");
        }
        else if(result.getResultCode() == 02) {
            Intent data = result.getData();
            if(data != null) {
                note = (Note) data.getSerializableExtra("EDIT_NOTE");
                if(note != null) {
                    notesList.get(index).setNoteTitle(note.getNoteTitle());
                    notesList.get(index).setNoteText(note.getNoteText());
                    notesList.get(index).setLastSaveDate(System.currentTimeMillis());
                    Collections.sort(notesList);
                    notesAdapter.notifyDataSetChanged();
                }
            }
            Log.d(TAG, "handleResult: Returning from EditActivity: Edited Existing Note!");
        }
        else {
            Toast.makeText(this, "Unexpected Request Code Received!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        writeDataToJSON();
    }

//    @Override
//    protected void onResume() {
//        notesList.clear();
//        notesList.addAll(readJSONFile());
//        super.onResume();
//    }

    private void readJSONFile() {
        Log.d(TAG, "loadFile: Loading JSON File");
        try {
            InputStream is = getApplicationContext().openFileInput(getString(R.string.file_name));
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONArray jsonArray = new JSONArray(sb.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String noteTitle = jsonObject.getString("note_title");
                String noteText = jsonObject.getString("note_text");
                long dateMS = jsonObject.getLong("editDate");
                Note note = new Note(noteTitle, noteText);
                note.setLastSaveDate(dateMS);
                notesList.add(note);
            }
        }
        catch (FileNotFoundException e) {
            Toast.makeText(this, getString(R.string.no_file), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.d(TAG, "readJSONData: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void writeDataToJSON()  {
        Log.d(TAG, "writeDataToJSON: Saving Notes Data into the JSON File");
        try {
            FileOutputStream fos = getApplicationContext().openFileOutput("NotesFile.json", Context.MODE_PRIVATE);
            JsonWriter jsonWriter = new JsonWriter(new OutputStreamWriter(fos, StandardCharsets.UTF_8));
            jsonWriter.setIndent("  ");
            jsonWriter.beginArray();
            for(Note note : notesList) {
                jsonWriter.beginObject();
                jsonWriter.name("note_title").value(note.getNoteTitle());
                jsonWriter.name("note_text").value(note.getNoteText());
                jsonWriter.name("editDate").value(note.getLastSaveDate().getTime());
                jsonWriter.endObject();
            }
            jsonWriter.endArray();
            jsonWriter.close();
            Log.d(TAG, "writeDataToJSON: JSON:\n" + notesList.toString());
            Toast.makeText(this, "Note Saved!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}