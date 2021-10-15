package com.riddhidamani.android_notes_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private SharedPreferences sharedPreferences;
    private static final String TAG = "MainActivity";
    private final List<Note> notesList = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("MY_PREF", MODE_PRIVATE);
        this.setTitle("Android Notes");

        recyclerView = findViewById(R.id.recycler);
        NotesAdapter notesAdapter = new NotesAdapter(notesList, this);
        recyclerView.setAdapter(notesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        for(int i=0; i < 30; i++) {
            notesList.add(new Note());
        }
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
            // code yet to be written
            return true;
        }
        else if(item.getItemId() == R.id.addNewNoteOpt) {
            // code yet to be written
            return true;
        }
        else {
            Log.d(TAG, "onOptionsItemSelected: Unknown Item" + item.getTitle());
        }
        return super.onOptionsItemSelected(item);
    }

    public void savePrefs(View v) {

    }


    @Override
    public void onClick(View view) {
        int position = recyclerView.getChildAdapterPosition(view);
        Note note = notesList.get(position);
        Toast.makeText(view.getContext(), "onClick" + note.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onLongClick(View view) {
        int position = recyclerView.getChildAdapterPosition(view);
        Note note = notesList.get(position);
        Toast.makeText(view.getContext(), "onClick" + note.toString(), Toast.LENGTH_LONG).show();

        return true;
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "The Back button was pressed - Bye!", Toast.LENGTH_LONG).show();
        super.onBackPressed();
    }
}