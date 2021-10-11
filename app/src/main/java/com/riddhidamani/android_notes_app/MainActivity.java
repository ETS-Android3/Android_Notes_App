package com.riddhidamani.android_notes_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("MY_PREF", MODE_PRIVATE);
        this.setTitle("Android Notes");
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


}